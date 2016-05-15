package co.za.zwideheights.cit.ui.jsf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.primefaces.model.UploadedFile;

import co.za.zwideheights.ejb.cit.GraveService;
import co.za.zwideheights.ejb.cit.TUserService;
import co.za.zwidehsights.jpa.entity.cit.Grave;
import co.za.zwidehsights.jpa.entity.cit.TUser;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class GraveManagedBean implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(GraveManagedBean.class);

	@EJB
	private GraveService graveService;
	@EJB
	private TUserService tUserService;

	private TUser user;
	private Grave grave;
	private Boolean saved = false;
	private Boolean editMode = true;

	public void reset() {
		this.saved = null;
		this.editMode = null;
		this.graveNumber = null;
		this.name = null;
		this.surname = null;
		this.sex = null;
		this.age = null;
		this.address = null;
		this.burialOrderNumber = null;
		this.issuedAt = null;
		this.cityCouncilRegNo = null;
		this.cityDebitNoteNumber = null;
		this.recieptNo = null;
		this.dod = null;
	}

	private String graveNumber;
	private String name;
	private String surname;
	private String sex;
	private String age;
	private String address;
	private String burialOrderNumber;
	private String issuedAt;
	private String cityCouncilRegNo;
	private String cityDebitNoteNumber;
	private String recieptNo;
	private Date dod;
	
	private UploadedFile file;

	@PostConstruct
	public void init() {
		try{
			Long id = Long.getLong( FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("grave_id").toString() );
			grave = graveService.findById( id );
		}catch( Exception e ){
		}
	}

	public void edit(){
		editMode = !editMode;
	}
	
    public void upload(){
        // Get uploaded file from the FileUploadEvent
    	int uploadedRecords = 0;
    	int failedRecords = 0;
    	 try{
    	      BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputstream(), "UTF-8"));
    	      String line = "";
    	      int index = 0;
    	      while ((line = br.readLine()) != null) {
    	    	  
    	    	  if( index == 0 ){
    	    		  index++;
    	    		  continue;
    	    	  }
    	    	  
    	    	  //Clear values.
    	    	  reset();
    	    	  
    	    	  try{
    	    		  String csv[] = line.split(";");
        	    	  
    	    		  if(csv.length>=1)
    	    			  graveNumber = csv[0].trim(); 
    	    		  
    	    		  if(csv.length>=2)
    	    			  name = csv[1].trim();
        	    	  
    	    		  if(csv.length>=3)
    	    			  surname = csv[2].trim(); 
    	    		  
        	    	  try {
            	    	  String startDateString = csv[3].trim()+"-"+csv[4].trim()+"-"+csv[5].trim();
            	    	  DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
        	    		  dod = df.parse(startDateString);
        	    	  } catch (Exception ee) {
        	    	  }
        	    	  
        	    	  if(csv.length>=7)
        	    		  sex = csv[6].trim(); 
        	    	  
        	    	  if(csv.length>=8)
        	    		  age = csv[7].trim(); 
        	    	  
        	    	  if(csv.length>=9)
        	    		  address = csv[8].trim();  
        	    	  
        	    	  if(csv.length>=10)
        	    		  burialOrderNumber = csv[9].trim();
        	    	  
        	    	  if(csv.length>=11)
        	    		  issuedAt = csv[10].trim(); 
        	    	  
        	    	  if(csv.length>=12)
        	    		  cityCouncilRegNo = csv[11].trim();
        	    	  
        	    	  if(csv.length>=13)
        	    		  cityDebitNoteNumber = csv[12].trim(); 
        	    	   
        	    	  if(csv.length>=14)
        	    		  recieptNo  = csv[13].trim(); 
        	    	  
              		  grave = new Grave( graveNumber,  name,  surname,  sex,  age,  address, burialOrderNumber,  issuedAt,  cityCouncilRegNo,  cityDebitNoteNumber,recieptNo,  dod) ;
              		  graveService.save(grave);
              		  uploadedRecords++;
              		               		  
    	    	  }catch( Exception ee ){
    	    		 LOGGER.error("FAILED-UPLOAD-RECORD;"+line); 
    	    		 LOGGER.error(ee); 
    	    		 failedRecords++;
    	    	  }
    	    	  
              }
    	    } catch (Exception ex) {

    	    }
    	
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", "Uploaded " + uploadedRecords+ " records from file " + file.getFileName() +", "+failedRecords+" records failed to upload.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

	
	
	public void update(){
		
		grave.setGraveNumber(graveNumber);
		grave.setName(name);
		grave.setSurname(surname) ;
		grave.setSex(sex);
		grave.setAge(age);
		grave.setAddress(address);
		grave.setBurialOrderNumber(burialOrderNumber);
		grave.setIssuedAt(issuedAt);
		grave.setCityCouncilRegNo(cityCouncilRegNo);
		grave.setCitDebitNoteNumber(cityDebitNoteNumber);
		grave.setRecieptNo(recieptNo);
		grave.setDateOfDeath( dod);
		graveService.merge(grave);
		
		FacesMessage msg = new FacesMessage("Successful", "Record updated sucessfully.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void delete(){
		grave.setDeleted(true);
		graveService.merge(grave);
		FacesMessage msg = new FacesMessage("Successful", "Record deleted sucessfully.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void create() {

		grave = new Grave( graveNumber,  name,  surname,  sex,  age,  address, burialOrderNumber,  issuedAt,  cityCouncilRegNo,  cityDebitNoteNumber,recieptNo,  dod) ;

		try {

			Object userIdObject=  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
			if( userIdObject == null){
				FacesMessage msg = new FacesMessage("Logon", "Please log on");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}else{
				user = tUserService.findById((Long)userIdObject);
			}

			graveService.save(grave);
			
			this.saved = true;
			grave = graveService.findByGraveNumberNameSurname( graveNumber,  name,  surname );
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("grave_id", grave.getId());

			FacesMessage msg = new FacesMessage("Success! ", "The entry was created successfully. ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			try {
			    ec.redirect(ec.getRequestContextPath()+ "/grave.xhtml");
			} catch (IOException e) {
				LOGGER.error(e);
			}

		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error", "An error has occured. The engineering team is going to investigate.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void done() {

		try {
			this.saved = false;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("grave_id", -1);
			FacesMessage msg = new FacesMessage("Success! ", "The entry was created successfully. ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			try {
				grave = null;
				ec.redirect(ec.getRequestContextPath() + "/graves.xhtml");
			} catch (IOException e) {
				LOGGER.error(e);
				ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
			}

		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error","An error has occured. Please escalate the error to Phumlani Mbabela(pmbabela@gmail.com).");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}

	public GraveService getGraveService() {
		return graveService;
	}

	public void setGraveService(GraveService graveService) {
		this.graveService = graveService;
	}

	public TUserService gettUserService() {
		return tUserService;
	}

	public void settUserService(TUserService tUserService) {
		this.tUserService = tUserService;
	}

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}

	public Grave getGrave() {
		return grave;
	}

	public void setGrave(Grave grave) {
		this.grave = grave;
	}

	public Boolean getSaved() {
		return saved;
	}

	public void setSaved(Boolean saved) {
		this.saved = saved;
	}

	public String getGraveNumber() {
		return graveNumber;
	}

	public void setGraveNumber(String graveNumber) {
		this.graveNumber = graveNumber;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBurialOrderNumber() {
		return burialOrderNumber;
	}

	public void setBurialOrderNumber(String burialOrderNumber) {
		this.burialOrderNumber = burialOrderNumber;
	}

	public String getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(String issuedAt) {
		this.issuedAt = issuedAt;
	}

	public String getCityCouncilRegNo() {
		return cityCouncilRegNo;
	}

	public void setCityCouncilRegNo(String cityCouncilRegNo) {
		this.cityCouncilRegNo = cityCouncilRegNo;
	}

	public String getCityDebitNoteNumber() {
		return cityDebitNoteNumber;
	}

	public void setCityDebitNoteNumber(String cityDebitNoteNumber) {
		this.cityDebitNoteNumber = cityDebitNoteNumber;
	}

	public String getRecieptNo() {
		return recieptNo;
	}

	public void setRecieptNo(String recieptNo) {
		this.recieptNo = recieptNo;
	}

	public Date getDod() {
		return dod;
	}

	public void setDod(Date dod) {
		this.dod = dod;
	}

	public Boolean getEditMode() {
		return editMode;
	}

	public void setEditMode(Boolean editMode) {
		this.editMode = editMode;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
}