package co.za.zwideheights.cit.ws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.apache.log4j.Logger;

import co.za.zwideheights.ejb.cit.GraveService;
import co.za.zwidehsights.jpa.entity.cit.Grave;


@WebService
@SOAPBinding(style = Style.RPC)
public class GraveSearchWS {
	
	private static final Logger LOGGER = Logger.getLogger(GraveSearchWS.class);

	@EJB
	private GraveService graveService;

	
	@WebMethod(action="search")
	public List<Grave> searchMobile( @WebParam(name="issuer")String issuer, @WebParam(name="reference")String reference, @WebParam(name="title")String title, @WebParam(name="sector")String sector, @WebParam(name="sectorTypes")String sectorTypes, @WebParam(name="closingDate")long closingDate, @WebParam(name="description")String description, @WebParam(name="startIndex")int startIndex, @WebParam(name="pageSize")int pageSize ) {
		
		//LOGGER.info("issuer="+issuer+", reference="+reference+", title="+title+", sector="+sector+", sectorTypeList="+sectorTypes+", closingDate="+closingDate+", description="+description+", startIndex="+startIndex+", pageSize="+pageSize);
		
		issuer = ( (issuer==null) || (issuer.equals("")) ) ? null: issuer ; 
		reference = ( (reference==null) || (reference.equals("")) ) ? null: reference ; 
		title = ( (title==null) || (title.equals("")) ) ? null: title ; 
		sector = ( (sector==null) || (sector.equals("")) ) ? null: sector ; 
		sectorTypes = ( (sectorTypes==null) || (sectorTypes.equals("")) ) ? null: sectorTypes ; 
		description = ( (description==null) || (description.equals("")) ) ? null: description ; 
		
		List<String> sectorTypeList = null;
		if( sectorTypes != null ){
			String[] sts = sectorTypes.split(";");
			sectorTypeList = Arrays.asList(sts);
		}
		
		List<Grave> graves = null; //graveService.find( issuer, reference, title, sector, sectorTypeList, new Date(closingDate), description, startIndex, pageSize);

		if( graves != null ){
			LOGGER.info("The number of records found is " + graves.size());
		}	
		
		return graves;
	}


}