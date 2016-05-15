package co.za.zwidehsights.jpa.entity.cit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "Grave")
@NamedQueries({
	@NamedQuery(name="Grave.findByName"						, query="SELECT o FROM Grave o WHERE o.name    = :name"   ),
	@NamedQuery(name="Grave.findBySurname"	    			, query="SELECT o FROM Grave o WHERE o.surname = :surname"),
	@NamedQuery(name="Grave.findByGraveNumberNameSurname"	, query="SELECT o FROM Grave o WHERE o.graveNumber = :graveNumber AND o.name = :name AND o.surname = :surname"),
	
})
public class Grave implements Serializable {
	
 	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
 	
 	@Column(name = "grave_number", length=100) 
	private String graveNumber;
 	
 	@Column(name = "name", length=100) 
	private String name;
 	
 	@Column(name = "surname", length=100) 
	private String surname;

 	@Column(name = "sex", length=10) 
	private String sex;
 	
 	@Column(name = "age",length=20) 
	private String age;

 	@Column(name = "address", length=100) 
	private String address;
	
 	@Column(name = "burial_order_number", length=20) 
	private String burialOrderNumber;
	 
 	@Column(name = "issued_at", length=50) 
	private String issuedAt;
 	
 	@Column(name = "city_council_reg_no", length=20) 
	private String cityCouncilRegNo;
 	
 	@Column(name = "cit_debit_note_number", length=20) 
	private String cityDebitNoteNumber;
 	
 	@Column(name = "reciept_no", length=15) 
	private String recieptNo;
 	
 	@Column(name = "dateOfDeath",unique=false) 
 	@Temporal(TemporalType.DATE)
	private Date dateOfDeath;
 	
    @Column(name = "deleted")
    private Boolean deleted;

	public Grave() {
		super();
	}

	public Grave(String graveNumber, String name, String surname, String sex, String age, String address, String burialOrderNumber, String issuedAt, String cityCouncilRegNo, String cityDebitNoteNumber, String recieptNo, Date dateOfDeath) {
		super();
		this.graveNumber = graveNumber;
		this.name = name;
		this.surname = surname;
		this.sex = sex;
		this.age = age;
		this.address = address;
		this.burialOrderNumber = burialOrderNumber;
		this.issuedAt = issuedAt;
		this.cityCouncilRegNo = cityCouncilRegNo;
		this.cityDebitNoteNumber = cityDebitNoteNumber;
		this.recieptNo = recieptNo;
		this.dateOfDeath = dateOfDeath;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCitDebitNoteNumber() {
		return cityDebitNoteNumber;
	}

	public void setCitDebitNoteNumber(String cityDebitNoteNumber) {
		this.cityDebitNoteNumber = cityDebitNoteNumber;
	}

	public String getRecieptNo() {
		return recieptNo;
	}

	public void setRecieptNo(String recieptNo) {
		this.recieptNo = recieptNo;
	}

	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	 
}