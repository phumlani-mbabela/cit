package co.za.zwideheights.cit.ui.jsf;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.za.zwideheights.ejb.cit.GraveService;
import co.za.zwidehsights.jpa.entity.cit.Grave;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class FileUploadManagedBean implements Serializable{
	
	private static final Logger LOGGER = Logger.getLogger(FileUploadManagedBean.class);
	
	private UploadedFile file;
    private List<UploadedFile> files =  new ArrayList<UploadedFile>();
    private List<UploadedFile> processedFiles =  new ArrayList<UploadedFile>();
    private String description;
    
	@EJB
	private GraveService graveService;

	private Grave grave;
	
	public int listSize(){
		return processedFiles.size();
	}
	
	public int getListSize(){
		return processedFiles.size();
	}
	
	public void reset() {
		files =  new ArrayList<UploadedFile>();
	}
	
	public void addFile(){
		
	}
    
    public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<UploadedFile> getFiles() {
		return files;
	}

	public void setFiles(List<UploadedFile> files) {
		this.files = files;
	}

	public List<UploadedFile> getProcessedFiles() {
		return processedFiles;
	}

	public void setProcessedFiles(List<UploadedFile> processedFiles) {
		this.processedFiles = processedFiles;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void convert()  {
    	
    	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	String ipAddress = JSFSettingManagedBean.getClientIpAddress(request);
    	
    	List<String> msgs =  new ArrayList<String>();

    	String  inputFolder = System.getProperty("input.folder");
    		
    	for( UploadedFile file : files){
    		long time =  (new Date()).getTime();
        	try {
        	File f = new File(inputFolder, file.getFileName());
        	if(f.exists()){
        		f = new File(inputFolder, time+"-"+file.getFileName());
        	}
        	InputStream input = file.getInputstream() ;
        	Files.copy(input, f.toPath());      	
        	processedFiles.add(file);
        	
        	msgs.add("Successful- " + file.getFileName() + " processed successfully.");
        	LOGGER.debug("Successful- " + file.getFileName() + " processed successfully.");
			} catch (Exception e) {				
				LOGGER.error(e);
				msgs.add("Unsuccessful- " + file.getFileName() + " is not processed. "+e.getMessage());
			}	
    	}
    	
    	for( UploadedFile file : processedFiles){
    		files.remove(file);
    	}
    	
    	for(String msg : msgs){
    		String[] tempMsg =  msg.split("- ");
            FacesMessage message = new FacesMessage(tempMsg[0], tempMsg[1]);
            FacesContext.getCurrentInstance().addMessage(null, message);	
    	}
    }
 
    public void fileUploadListener(FileUploadEvent e){
        // Get uploaded file from the FileUploadEvent
        this.file = e.getFile();
        files.add(file);
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }
}
