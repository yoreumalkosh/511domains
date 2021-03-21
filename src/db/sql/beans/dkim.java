package db.sql.beans;

public class dkim {
	private long dkimId;
	private long domainId;
	private String selector;
	
	public long getDkimId() {
		return dkimId;
	}
	public void setDkimId(long dkimId) {
		this.dkimId = dkimId;
	}
	public long getDomainId() {
		return domainId;
	}
	public void setDomainId(long domainId) {
		this.domainId = domainId;
	}
	public String getSelector() {
		return selector;
	}
	public void setSelector(String selector) {
		this.selector = selector;
	}
}
