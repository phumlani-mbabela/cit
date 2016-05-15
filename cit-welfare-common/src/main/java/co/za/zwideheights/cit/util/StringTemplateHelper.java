package co.za.zwideheights.cit.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.jboss.logging.Logger;
import org.stringtemplate.v4.ST;

public class StringTemplateHelper {
	
	private static final Logger LOGGER = Logger.getLogger(StringTemplateHelper.class);
	
	private static ST emailConfirmUserTemplate;
	private static String emailConfirmUserHtmlFile = null;
	
	private static ST tenderEmailLinkTemplate;	
	private static String emailLinkHtmlFile = null;

    static{
    	try {
    		Properties props = new Properties();
	    	props.load(SendHTMLEmail.class.getResourceAsStream("/email.properties"));
	    	
	    	emailConfirmUserHtmlFile = props.getProperty("email.confirmuser.html");
	    	LOGGER.info("Email confirm File = " + emailConfirmUserHtmlFile);
			emailConfirmUserTemplate = new ST(new String(Files.readAllBytes(Paths.get(emailConfirmUserHtmlFile))), '$', '$');
					
	    	emailLinkHtmlFile = props.getProperty("email.emaillink.html");	   
	    	LOGGER.info("Email email link File = " + emailLinkHtmlFile);
			tenderEmailLinkTemplate = new ST(new String(Files.readAllBytes(Paths.get(emailLinkHtmlFile))), '$', '$');
		
		} catch (IOException e) {
			LOGGER.error(e);
		}
    }
	
    public synchronized String processTenderLinkEmailTemplate(String id, String time, String ref, String closingDate, String title) {
 		addAttributeToEmailTemplate(tenderEmailLinkTemplate, "id", id);
 		addAttributeToEmailTemplate(tenderEmailLinkTemplate, "time", time);
 		addAttributeToEmailTemplate(tenderEmailLinkTemplate, "ref", ref);
 		addAttributeToEmailTemplate(tenderEmailLinkTemplate, "closing_date", closingDate);
 		addAttributeToEmailTemplate(tenderEmailLinkTemplate, "title", title);
 		return tenderEmailLinkTemplate.render();
 	}
    
   public synchronized String processConfirmUserEmailTemplate(String user_id) {
		addAttributeToEmailTemplate(emailConfirmUserTemplate, "user_id_0", user_id);
		addAttributeToEmailTemplate(emailConfirmUserTemplate, "user_id_1", user_id);
		return emailConfirmUserTemplate.render();
	}
	
    public void addAttributeToEmailTemplate(ST emailTemplate,String attributeName, String attributeValue) {
    	try { emailTemplate.remove(attributeName);
        } catch (Exception e) { }
    	emailTemplate.add(attributeName, attributeValue);
    }
    
}