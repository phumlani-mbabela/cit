package com.tender4sure.ws;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TenderM implements Serializable{

	private String issuer;
	private String reference;
	private String title;
	private String sector;
	private String sectorType;
	private String website;
	private String email;
	private String contact;
	private String address;
	private long   closingDate;
	private long   publishDate;
	private long   meetingDate;
	private long   id;
	private String description;
	
	public TenderM() {
		super();
	}

	public TenderM(String issuer, String reference, String title, String sector, String sectorType, String website, String email, String contact, String address, long closingDate, long publishDate, long meetingDate, long id, String description) {
		super();
		this.issuer = issuer;
		this.reference = reference;
		this.title = title;
		this.sector = sector;
		this.sectorType = sectorType;
		this.website = website;
		this.email = email;
		this.contact = contact;
		this.address = address;
		this.closingDate = closingDate;
		this.publishDate = publishDate;
		this.meetingDate = meetingDate;
		this.id = id;
		this.description = description;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getSectorType() {
		return sectorType;
	}

	public void setSectorType(String sectorType) {
		this.sectorType = sectorType;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(long closingDate) {
		this.closingDate = closingDate;
	}

	public long getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(long publishDate) {
		this.publishDate = publishDate;
	}

	public long getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(long meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getDescription() {
		return description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}