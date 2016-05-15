package co.za.zwideheights.cit.initialiser;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitialiserServletContextListener implements ServletContextListener {

	private String tenderDocumentFolder;
	private String fileSizeLimit;
	private String supportedFileTypes;
	private String fileLimit;
	private String zipOutputFolder;
	private String tenderEmaillinkTemplateHtml;
	private String passwordResetTemplateHtml;
	private String userAccountConfirmationTemplateHtml;
	
	private String invoiceBackdateMonths;
	private String geoDataSourceFolder;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext ctx = sce.getServletContext();
		
		tenderDocumentFolder = ctx.getInitParameter("tenderDocumentFolder");
		fileSizeLimit = ctx.getInitParameter("fileSizeLimit");
		supportedFileTypes = ctx.getInitParameter("supportedFileTypes");
		fileLimit = ctx.getInitParameter("fileLimit");
		zipOutputFolder = ctx.getInitParameter("zipOutputFolder");
		tenderEmaillinkTemplateHtml = ctx.getInitParameter("tenderEmaillinkTemplateHtml");
	
		passwordResetTemplateHtml = ctx.getInitParameter("passwordResetTemplateHtml");
		userAccountConfirmationTemplateHtml = ctx.getInitParameter("userAccountConfirmationTemplateHtml");
		
		invoiceBackdateMonths = ctx.getInitParameter("invoiceBackdateMonths");
		geoDataSourceFolder = ctx.getInitParameter("geoDataSourceFolder");
		
		System.setProperty("tender.document.folder", tenderDocumentFolder);
		System.setProperty("supported.file.types", supportedFileTypes);
		System.setProperty("file.size.limit", fileSizeLimit);
		System.setProperty("file.limit", fileLimit);
		System.setProperty("zip.output.folder", zipOutputFolder);
		
		System.setProperty("tender.emaillink.template.html", tenderEmaillinkTemplateHtml);
		System.setProperty("password.reset.template.html", passwordResetTemplateHtml);
		System.setProperty("user.confirmation.template.html", userAccountConfirmationTemplateHtml);
		
		System.setProperty("invoice.backdate.months", invoiceBackdateMonths);
		System.setProperty("geo.data.source.folder", geoDataSourceFolder);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

}