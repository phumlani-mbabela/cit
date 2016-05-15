package co.za.zwideheights.cit.ui.jsf;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import org.apache.commons.validator.EmailValidator;
import org.jboss.logging.Logger;

import co.za.zwideheights.cit.util.PasswordEncryptionService;
import co.za.zwideheights.cit.util.SendHTMLEmail;
import co.za.zwideheights.cit.util.StringTemplateHelper;
import co.za.zwideheights.ejb.cit.TUserService;
import co.za.zwidehsights.jpa.entity.cit.TUser;

@SuppressWarnings("serial")
@ManagedBean
@RequestScoped
public class UserManagedBean  implements Serializable{
	
	private static final Logger LOGGER = Logger.getLogger(UserManagedBean.class);

	@EJB
	private TUserService tUserService;
	
	/* logon */
	private String email;
	private String password;
	private String name;
	private String surname;
	private String gender;
	private String countryCreate;
	private Date dob;
	private Boolean remember;
	private boolean terms;
	
	public void reset(){
		email="";
		password="";
		name="";
		surname="";
		gender="";
		countryCreate="";
		dob=null;
	}
	
	public void login(){
		
		if( !EmailValidator.getInstance().isValid(email) ){
            FacesMessage message = new FacesMessage("Error", "Please enter a valid email address.");
            FacesContext.getCurrentInstance().addMessage("messages", message);
			return;
		}
		
		PasswordEncryptionService pes =  new PasswordEncryptionService();
		try {
			password = new String(pes.getEncryptedPassword(password, "salt".getBytes()) );
			if(password.length() > 13){
				password = password.substring(0, 12);
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			LOGGER.error(e);
		}
		
		TUser tUser = null;
		try{
			tUser = tUserService.login(email, password);
		}catch(Exception e){
			
		}
		if( tUser == null ){
            FacesMessage message = new FacesMessage("Unsucessfuly", "Invalid cridentials, reset your password.");
            FacesContext.getCurrentInstance().addMessage("messages", message);
		}else if( !tUser.getVerified() ){
            FacesMessage message = new FacesMessage("Info", "The account has not been verified. An email has been sent to your email account.");
            FacesContext.getCurrentInstance().addMessage("messages", message);
            /* Send  the email verification */
            sendEmailVerification();
		}else{
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			try {
				tUser = tUserService.findByEmail(email);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user_id",tUser.getId());
			    ec.redirect(ec.getRequestContextPath()+ "/tenders.xhtml");
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}
		
	}
	
	public void resetPassword(){
        /* Send  the email verification */
        sendEmailVerification();
	}
	
	private void sendEmailVerification() {
		StringTemplateHelper sth = new StringTemplateHelper();
		TUser tUser = tUserService.findByEmail(email);
		String htmlMSG = "";
		htmlMSG = sth.processConfirmUserEmailTemplate(tUser.getId().toString());		
		SendHTMLEmail sendHTMLEmail =  new SendHTMLEmail();
		sendHTMLEmail.send(email, "support@tender4sure.com", "Tender4Sure - User comfirmation.", htmlMSG);
	}

	public Boolean getRemember() {
		return remember;
	}

	public void setRemember(Boolean remember) {
		this.remember = remember;
	}

	public void signup(){
		
		if( !terms ){
            FacesMessage message = new FacesMessage("Info", "Please read and accept the terms and conditions.");
            FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}
		
		if( !EmailValidator.getInstance().isValid(email) ){
            FacesMessage message = new FacesMessage("Error", "Please enter a valid email. We need it for correspendency.");
            FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}
		
		String destinationPage="/tenders.xhtml";
		try{
			TUser tUser = null;
			try{
				tUser = tUserService.findByEmail(email);
			}catch( Exception nre){				
			}
			
			if( tUser != null ){
	            FacesMessage message = new FacesMessage("Error", "Email address already exist. Reset your password.");
	            FacesContext.getCurrentInstance().addMessage(null, message);
	            return;
			}else{
				
				Date timestamp = new Date();
				
				PasswordEncryptionService pes =  new PasswordEncryptionService();
				password = new String(pes.getEncryptedPassword(password, "salt".getBytes()) );
				if(password.length() > 13){
					password = password.substring(0, 12);
				}
				
				TUser user =  new TUser( email,  password,  name,  surname,  null,  new Date(), new Date(),  dob,  gender,  "active") ;
				user.setCreateDate( timestamp );
				tUserService.save(user);
				
				user = tUserService.findByEmail(email);
				
				sendEmailVerification();
				tUser = tUserService.findByEmail(email);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user_id",tUser.getId());
				destinationPage="/tenders.xhtml";
			}
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			try {
			    ec.redirect(ec.getRequestContextPath()+ destinationPage);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}catch(NoResultException nre){
			TUser user =  new TUser( email,  password,  name,  surname,  null,  new Date(), new Date(),  dob,  gender,  "active") ;
			tUserService.save(user);
		}catch(Exception e){
            FacesMessage message = new FacesMessage("Error", "An error has occured. " + e.getMessage() );
            FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public boolean getTerms() {
		return terms;
	}

	public void setTerms(boolean terms) {
		this.terms = terms;
	}

	public String getCountryCreate() {
		return countryCreate;
	}

	public void setCountryCreate(String countryCreate) {
		this.countryCreate = countryCreate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
}