package db.sql.beans;

import java.sql.Date;

public class domain {
	private long id;
	private long domainId;
	private long clientId;
	private String domainName;
	private boolean isActive1;
	private boolean isActive2;
	private boolean isActive3;
	private Date dateTimeCreated;
	private Date dateTimeModified;
	private boolean isValidated;

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDomainId() {
		return domainId;
	}
	public void setDomainId(long domainId) {
		this.domainId = domainId;
	}
	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
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
	public boolean isValidated() {
		return isValidated;
	}
	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
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
