package co.za.zwideheights.cit.ui.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import co.za.zwideheights.cit.util.SendHTMLEmail;
import co.za.zwideheights.ejb.cit.TUserService;
import co.za.zwidehsights.jpa.entity.cit.TUser;

@SuppressWarnings("serial")
@ManagedBean
@RequestScoped
public class ContactManagedBean  implements Serializable{
	
	private static final Logger LOGGER = Logger.getLogger(ContactManagedBean.class);

	private String name;
	private String text;
	private String email;
	private String subject;
	
	@EJB
	private TUserService tUserService;
	
	public void send(){
				
		try{
			SendHTMLEmail sendHTMLEmail =  new SendHTMLEmail();
			sendHTMLEmail.send("support@tender4sure.com", email, subject, text);
			
            FacesMessage message = new FacesMessage("Thank you", "We have recieved your message. We are going to reply shortly. Thank you.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            clear();
		}catch( Exception e ){
			LOGGER.error(e);
            FacesMessage message = new FacesMessage("Error", "An error has occured. The support team is going to investigate. Thank you.");
            FacesContext.getCurrentInstance().addMessage(null, message);
		}
	
	}

	public ContactManagedBean() {
		super();	
	}
	
	@PostConstruct
	public void init() {
		try{
			Long userId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");	
			if( userId != null ){
				TUser user = tUserService.findById(userId);
				name=user.getName() +" "+ user.getSurname() ;
				text="";
				email= user.getEmail();
				subject="";
			}
		}catch(Exception e){			
		}	
	}

	private void clear() {
		name="";
		text="";
		email="";
		subject="";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
