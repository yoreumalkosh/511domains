package db.sql.beans;

import java.sql.Date;

public class session {
	private long sessionId;
	private String sessionCode;
	
	private Date dateTimeCreated;
	private Date dateTimeExpired;
	private Date dateTimeModified;
	
	private boolean isActive;
	private boolean isInfinite;
	
	private long clientId;
	
	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionCode() {
		return sessionCode;
	}
	public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}
	public Date getDateTimeCreated() {
		return dateTimeCreated;
	}
	public void setDateTimeCreated(Date dateTimeCreated) {
		this.dateTimeCreated = dateTimeCreated;
	}
	public Date getDateTimeExpired() {
		return dateTimeExpired;
	}
	public void setDateTimeExpired(Date dateTimeExpired) {
		this.dateTimeExpired = dateTimeExpired;
	}
	public Date getDateTimeModified() {
		return dateTimeModified;
	}
	public void setDateTimeModified(Date dateTimeModified) {
		this.dateTimeModified = dateTimeModified;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isInfinite() {
		return isInfinite;
	}
	public void setInfinite(boolean isInfinite) {
		this.isInfinite = isInfinite;
	}
}
