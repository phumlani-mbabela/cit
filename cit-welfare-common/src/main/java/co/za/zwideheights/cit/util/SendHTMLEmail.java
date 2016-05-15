package co.za.zwideheights.cit.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.logging.Logger;

public class SendHTMLEmail {
	
	private static final Logger LOGGER = Logger.getLogger(SendHTMLEmail.class);
	
    private static Properties props = null;
    private Session session = null;
    public static String username = null;
    public static String password = null;
    public static String fromEmail = null;
    
    static {
    	
    	try {
	        props = new Properties();
	    	props.load(SendHTMLEmail.class.getResourceAsStream("/email.properties"));
	    	username = props.getProperty("mail.smtp.username");
	    	password = props.getProperty("mail.smtp.password");
	    	fromEmail = props.getProperty("mail.smtp.from.email");
		} catch (IOException e) {
			LOGGER.error(e);
		}
    	
    }
    
    public String getUsername() {
		return username;
	}
    
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void send(String toEmail, String fromEmail,String subject, String htmlMSG) {
		
		try{

				session = Session.getInstance(props,new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
				
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(fromEmail));
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toEmail));
				message.setSubject(subject);
				message.setContent(htmlMSG,"text/html");
				Transport.send(message);
				LOGGER.info("Email sent to " + toEmail + " , subject " + subject);
				
		}catch( MessagingException me ){
			LOGGER.error(me);
		}
	}
	
	public void send(String toEmail,String subject, String htmlMSG) throws MessagingException{

			session = Session.getInstance(props,new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					  });
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setContent(htmlMSG,"text/html");
			Transport.send(message);
			LOGGER.info("Email sent to " + toEmail + " , subject " + subject);
    }
    
}