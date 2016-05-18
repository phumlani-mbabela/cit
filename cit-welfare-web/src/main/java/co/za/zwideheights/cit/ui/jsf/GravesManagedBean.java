package co.za.zwideheights.cit.ui.jsf;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.PageEvent;

import co.za.zwideheights.cit.param.GraveConstant;
import co.za.zwideheights.ejb.cit.GraveService;
import co.za.zwidehsights.jpa.entity.cit.Grave;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class GravesManagedBean implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(GravesManagedBean.class);

	private Date startDateSearch;
	private Date endDateSearch;
	private String genderSearch;
	private String statusSearch="0";
	private String nameSearch;
	private String surnameSearch;
	private String ageSearch;
	private String graveNumberSearch;
	private String addressSearch;
	private String burialOrderNumberSearch;
	private String issuedAtSearch;
	private String cityCouncilRegNoSearch;
	private String cityDebitNoteNumberSearch;
	private String recieptNoSearch;
	
	private Grave grave;
	private List<Grave> graves;
	private Long listSize;


	private DataTable graveDataTable;
	private GraveListDataModel graveDataModel;


	@EJB
	private GraveService graveService;

	public void search() {

		graveDataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("searchGraveForm:graveTable");

		int rowCount = 10;
		int pageNumber = 0;
		if (graveDataTable != null) {
			if (graveDataTable.getRowCount() != 0) {
				int rowCountDelete = graveDataTable.getRowCount();
			}
			pageNumber = graveDataTable.getPage();
		}

		Map<String, Object> params = new HashMap<String, Object>();
		processSearchParameters(params);

		try {
			listSize = graveService.count(params);
			graves = graveService.find(params, rowCount, pageNumber);
			graveDataModel = new GraveListDataModel(graves, listSize.intValue(), graves.size());
		} catch (Exception e) {
			listSize = 0l;
			LOGGER.error(e);
		}

		if (listSize == 0) {
			FacesMessage msg = new FacesMessage("No results", "No records found");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	private void processSearchParameters(Map<String, Object> params) {
		
			if ((startDateSearch != null)) {
				params.put(GraveConstant.START_DATE, startDateSearch);
			}
			if ((endDateSearch != null)) {
				params.put(GraveConstant.END_DATE, endDateSearch);
			}
			if ( genderSearch != null && (!genderSearch.equals(""))  ) {
				params.put(GraveConstant.SEX, genderSearch);
			}
			if (statusSearch != null && (!statusSearch.equals("")) ) {
				params.put(GraveConstant.STATUS, statusSearch);
			}
			if (nameSearch != null && (!nameSearch.equals("")) ) {
				params.put(GraveConstant.NAME, nameSearch);
			}
			if (surnameSearch != null && (!surnameSearch.equals("")) ) {
				params.put(GraveConstant.SURNAME, surnameSearch);
			}
			if (ageSearch != null && (!ageSearch.equals("")) ) {
				params.put(GraveConstant.AGE, ageSearch);
			}
			if (graveNumberSearch != null && (!graveNumberSearch.equals("")) ) {
				params.put(GraveConstant.GRAVENUMBER, graveNumberSearch);
			}
			if (addressSearch != null && (!addressSearch.equals("")) ) {
				params.put(GraveConstant.ADDRESS, addressSearch);
			}
			if (burialOrderNumberSearch != null && (!burialOrderNumberSearch.equals("")) ) {
				params.put(GraveConstant.BURIALORDERNUMBER, burialOrderNumberSearch);
			}
			if (issuedAtSearch != null && (!issuedAtSearch.equals("")) ) {
				params.put(GraveConstant.ISSUEDAT, issuedAtSearch);
			}
			if (cityCouncilRegNoSearch != null && (!cityCouncilRegNoSearch.equals("")) ) {
				params.put(GraveConstant.CITYCOUNCILREGNO, cityCouncilRegNoSearch);
			}
			if (cityDebitNoteNumberSearch != null && (!cityDebitNoteNumberSearch.equals("")) ) {
				params.put(GraveConstant.CITDEBITNOTENUMBER, cityDebitNoteNumberSearch);
			}
			if (recieptNoSearch != null && (!recieptNoSearch.equals("")) ) {
				params.put(GraveConstant.RECIEPTNO, recieptNoSearch);
			}
			
	}

	public void resetSearch() {
		
		startDateSearch=null;
		endDateSearch=null;
		genderSearch=null;
		statusSearch="0";
		nameSearch=null;
		surnameSearch=null;
		ageSearch=null;
		graveNumberSearch=null;
		addressSearch=null;
		burialOrderNumberSearch=null;
		issuedAtSearch=null;
		cityCouncilRegNoSearch=null;
		cityDebitNoteNumberSearch=null;
		recieptNoSearch=null;
		
		grave=null;
		graves=null;
		listSize=null;

		graveDataTable=null;
		graveDataModel=null;
	}

	public void onPageSelect(PageEvent event) {
		search();
	}

	public void onRowSelect(SelectEvent event) {
		try {
			
			grave = ((Grave) event.getObject());
			Long id = grave.getId();
			
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("grave_id", id);
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("grave_id",id);
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.getSessionMap().put("grave_id", id);
			ec.redirect(ec.getRequestContextPath() + "/graveview.xhtml?id=" + id.toString() );
			
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	@PostConstruct
	public void init() {

	}

	public Date getStartDateSearch() {
		return startDateSearch;
	}

	public void setStartDateSearch(Date startDateSearch) {
		this.startDateSearch = startDateSearch;
	}

	public Date getEndDateSearch() {
		return endDateSearch;
	}

	public void setEndDateSearch(Date endDateSearch) {
		this.endDateSearch = endDateSearch;
	}

	public String getGenderSearch() {
		return genderSearch;
	}

	public void setGenderSearch(String genderSearch) {
		this.genderSearch = genderSearch;
	}

	public String getStatusSearch() {
		return statusSearch;
	}

	public void setStatusSearch(String statusSearch) {
		this.statusSearch = statusSearch;
	}

	public String getNameSearch() {
		return nameSearch;
	}

	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}

	public String getSurnameSearch() {
		return surnameSearch;
	}

	public void setSurnameSearch(String surnameSearch) {
		this.surnameSearch = surnameSearch;
	}

	public String getAgeSearch() {
		return ageSearch;
	}

	public void setAgeSearch(String ageSearch) {
		this.ageSearch = ageSearch;
	}

	public String getGraveNumberSearch() {
		return graveNumberSearch;
	}

	public void setGraveNumberSearch(String graveNumberSearch) {
		this.graveNumberSearch = graveNumberSearch;
	}

	public String getAddressSearch() {
		return addressSearch;
	}

	public void setAddressSearch(String addressSearch) {
		this.addressSearch = addressSearch;
	}

	public String getBurialOrderNumberSearch() {
		return burialOrderNumberSearch;
	}

	public void setBurialOrderNumberSearch(String burialOrderNumberSearch) {
		this.burialOrderNumberSearch = burialOrderNumberSearch;
	}

	public String getIssuedAtSearch() {
		return issuedAtSearch;
	}

	public void setIssuedAtSearch(String issuedAtSearch) {
		this.issuedAtSearch = issuedAtSearch;
	}

	public String getCityCouncilRegNoSearch() {
		return cityCouncilRegNoSearch;
	}

	public void setCityCouncilRegNoSearch(String cityCouncilRegNoSearch) {
		this.cityCouncilRegNoSearch = cityCouncilRegNoSearch;
	}

	public String getCityDebitNoteNumberSearch() {
		return cityDebitNoteNumberSearch;
	}

	public void setCityDebitNoteNumberSearch(String cityDebitNoteNumberSearch) {
		this.cityDebitNoteNumberSearch = cityDebitNoteNumberSearch;
	}

	public String getRecieptNoSearch() {
		return recieptNoSearch;
	}

	public void setRecieptNoSearch(String recieptNoSearch) {
		this.recieptNoSearch = recieptNoSearch;
	}

	public List<Grave> getGraves() {
		return graves;
	}

	public void setGraves(List<Grave> graves) {
		this.graves = graves;
	}

	public Long getListSize() {
		return listSize;
	}

	public void setListSize(Long listSize) {
		this.listSize = listSize;
	}

	public DataTable getGraveDataTable() {
		return graveDataTable;
	}

	public void setGraveDataTable(DataTable graveDataTable) {
		this.graveDataTable = graveDataTable;
	}

	public GraveListDataModel getGraveDataModel() {
		return graveDataModel;
	}

	public void setGraveDataModel(GraveListDataModel graveDataModel) {
		this.graveDataModel = graveDataModel;
	}

	public GraveService getGraveService() {
		return graveService;
	}

	public void setGraveService(GraveService graveService) {
		this.graveService = graveService;
	}

	public Grave getGrave() {
		return grave;
	}

	public void setGrave(Grave grave) {
		this.grave = grave;
	}

}
