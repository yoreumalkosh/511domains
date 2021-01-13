package db.sql.beans;

import java.sql.Date;

public class mail {
	private long mailId;
	private String name;
	private long domainId;
	private long clientId;
	private boolean isEnabled;
	private Date dateTimeCreated;
	private boolean isActive1;
	private boolean isActive2;
	private boolean isActive3;
	
	public Date getDateTimeCreated() {
		return dateTimeCreated;
	}
	public void setDateTimeCreated(Date dateTimeCreated) {
		this.dateTimeCreated = dateTimeCreated;
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
	public long getMailId() {
		return mailId;
	}
	public void setMailId(long mailId) {
		this.mailId = mailId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
