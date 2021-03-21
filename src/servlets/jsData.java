package servlets;

public class jsData {
	private String sessionId;
	private String ivIn;
	private String keyIn;
	private String ivOut;
	private String keyOut;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getIvIn() {
		return ivIn;
	}
	public void setIvIn(String ivIn) {
		this.ivIn = ivIn;
	}
	public String getKeyIn() {
		return keyIn;
	}
	public void setKeyIn(String keyIn) {
		this.keyIn = keyIn;
	}
	public String getIvOut() {
		return ivOut;
	}
	public void setIvOut(String ivOut) {
		this.ivOut = ivOut;
	}
	public String getKeyOut() {
		return keyOut;
	}
	public void setKeyOut(String keyOut) {
		this.keyOut = keyOut;
	}
}
