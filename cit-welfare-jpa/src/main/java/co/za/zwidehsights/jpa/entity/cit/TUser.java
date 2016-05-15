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
@Table(name = "TUser")
@NamedQueries({
	@NamedQuery(name="TUser.findById"			, query="SELECT o FROM TUser o WHERE o.id = :id"),
	@NamedQuery(name="TUser.findByEmail"	    , query="SELECT o FROM TUser o WHERE o.email = :email"),
	@NamedQuery(name="TUser.findByIdCreateDate"	, query="SELECT o FROM TUser o WHERE o.id = :id AND o.createDate = :createDate "),
	@NamedQuery(name="TUser.findByPrincipal"	, query="SELECT o FROM TUser o WHERE o.email = :email AND o.password = :password")
})
public class TUser implements Serializable{
	
 	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
 	
 	@Column(name = "email", length=100) 
	private String email;
 	
 	@Column(name = "password", length=30) 
	private String password;
 	
 	@Column(name = "name", length=30) 
	private String name;
 	
 	@Column(name = "surname", length=30) 
	private String surname;
 	
 	@Column(name = "verified") 
	private Boolean verified;
 	
 	@Column(name = "create_date",unique=false) 
 	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
 	
 	@Column(name = "update_date",unique=false) 
 	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
 	
 	@Column(name = "dob_date",unique=false) 
 	@Temporal(TemporalType.TIMESTAMP)
	private Date dob;
 	
 	@Column(name = "gender", length=6) 
	private String gender;
 	
 	@Column(name = "status", length=6) 
 	private String status;
 	
 	
	
	public TUser(String email, String password, String name, String surname, Boolean verified, Date createDate,Date updateDate, Date dob, String gender, String status) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.verified = verified;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.dob = dob;
		this.gender = gender;
		this.status = status;
	}

	public TUser() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}