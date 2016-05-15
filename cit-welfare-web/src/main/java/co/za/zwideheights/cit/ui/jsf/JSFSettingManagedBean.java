package co.za.zwideheights.cit.ui.jsf;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
@ManagedBean(name="jsfSettingManagedBean")
@ApplicationScoped
public class JSFSettingManagedBean implements Serializable{

private static final Logger LOGGER = Logger.getLogger(JSFSettingManagedBean.class);
	
	private String fileSizeLimit;
	private String fileLimit;
	
	{
		fileSizeLimit = System.getProperty("file.size.limit");
		fileLimit = System.getProperty("file.limit");
	}
	
	private static final String[] HEADERS_TO_TRY = { 
	    "X-Forwarded-For",
	    "Proxy-Client-IP",
	    "WL-Proxy-Client-IP",
	    "HTTP_X_FORWARDED_FOR",
	    "HTTP_X_FORWARDED",
	    "HTTP_X_CLUSTER_CLIENT_IP",
	    "HTTP_CLIENT_IP",
	    "HTTP_FORWARDED_FOR",
	    "HTTP_FORWARDED",
	    "HTTP_VIA",
	    "REMOTE_ADDR" };

    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            	LOGGER.debug("The ip is " + ip);
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

	public String getFileSizeLimit() {
		return fileSizeLimit;
	}
	public void setFileSizeLimit(String fileSizeLimit) {
		this.fileSizeLimit = fileSizeLimit;
	}
	public String getFileLimit() {
		return fileLimit;
	}
	public void setFileLimit(String fileLimit) {
		this.fileLimit = fileLimit;
	}
	
}
