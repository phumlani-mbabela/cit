package co.za.zwideheights.cit.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import co.za.zwideheights.ejb.cit.TUserService;
import co.za.zwidehsights.jpa.entity.cit.TUser;


@WebServlet(description = "Confirm user account.", urlPatterns = { "/ConfirmUser" })
public class ConfirmUser extends HttpServlet {
	
	private static final Logger LOGGER = Logger.getLogger(ConfirmUser.class);
	private static final long serialVersionUID = 1L;
	
	@EJB
	private TUserService tUserService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmUser() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			Long userId = Long.valueOf( request.getParameter("c").toString() );
			TUser tUser = tUserService.findById(userId);
			tUser.setVerified(true);
			tUserService.merge(tUser);
			response.sendRedirect("confirmed.xhtml");
		}catch( Exception e ){
			LOGGER.error( e );
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet( request,  response);
	}

}