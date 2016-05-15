package co.za.zwideheights.cit.ui.jsf;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import co.za.zwideheights.cit.util.PasswordEncryptionService;
import co.za.zwideheights.ejb.cit.TUserService;
import co.za.zwidehsights.jpa.entity.cit.TUser;

@SuppressWarnings("serial")
@ManagedBean(name="profileManagedBean")
@SessionScoped
public class ProfileManagedBean implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(ProfileManagedBean.class);
	
	@EJB
	private TUserService tUserService;
	
	/* Password */
	private String changePassword;
	private String password;
	private TUser user;
	
	/* Print invoice */
	private Long printInvoiceId;
	
	public void changePassword(){
		if( password.equals(changePassword) ){
			
			Object userIdObject=  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
			if( userIdObject == null){
				FacesMessage msg = new FacesMessage("Logon", "Please log on");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}else{
				user = tUserService.findById((Long)userIdObject);
				PasswordEncryptionService pes =  new PasswordEncryptionService();
				try {
					password = new String(pes.getEncryptedPassword(password, "salt".getBytes()) );
					if(password.length() > 13){
						password = password.substring(0, 12);
					}
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					LOGGER.error(e);
				}
				user.setPassword(password);
				tUserService.merge(user);
			}
			
		}else{
			
		}
	}
	
	@PostConstruct
	public void init(){
		try{
			
			String ibm = System.getProperty("invoice.backdate.months");
			Integer monthsBackDate = Integer.parseInt(ibm);
			
			Long  userId=  (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, monthsBackDate);
			Date createdDate = cal.getTime();
			
			try{
				user =  tUserService.findById(userId);
			}catch( Exception e ){
			}
			
			
		}catch( Exception e ){
			LOGGER.error(e);
		}
	}

	public void selectedInvoice(){
		
	}
	
	public void download(){
		/* Instantiate the pdf generater library */
		//printInvoiceId = printInvoiceId;
	}
	
	public void updateUser(){
		
	}
	
	public String logout() {
	    try {
	    	 	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	 	    	ec.invalidateSession();	    	
		} catch (Exception e) {
			LOGGER.error(e);
		}
	    	return "logout";	 
	}
	

	public String getChangePassword() {
		return changePassword;
	}

	public void setChangePassword(String changePassword) {
		this.changePassword = changePassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}


}