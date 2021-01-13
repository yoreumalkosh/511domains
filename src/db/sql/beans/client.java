package db.sql.beans;

import java.sql.Date;

public class client {
	private long clientId;
	private String fullName;
	private String email;
	private String gender;
	private String password;
	private String organization;
	private String role;
	private String city;
	private String country;
	private boolean isValidated;
	private boolean isActive1;
	private boolean isActive2;
	private boolean isActive3;
	
	private Date dateTimeCreated;
	private Date dateTimeModified;

	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public boolean isValidated() {
		return isValidated;
	}
	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}
	public boolean isActive1() {
		return isActive1;
	}
	public void setActive1(boolean isActive1) {
		this.isActive1 = isActive1;
	}
	public boolean isActive2() {
		return isActive2;
	}
	public void setActive2(boolean isActive2) {
		this.isActive2 = isActive2;
	}
	public boolean isActive3() {
		return isActive3;
	}
	public void setActive3(boolean isActive3) {
		this.isActive3 = isActive3;
	}
	public Date getDateTimeCreated() {
		return dateTimeCreated;
	}
	public void setDateTimeCreated(Date dateTimeCreated) {
		this.dateTimeCreated = dateTimeCreated;
	}
	public Date getDateTimeModified() {
		return dateTimeModified;
	}
	public void setDateTimeModified(Date dateTimeModified) {
		this.dateTimeModified = dateTimeModified;
	}
}
