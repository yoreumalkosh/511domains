package servlets.internal.clients;

import java.sql.Date;

import db.sql.clients;
import db.sql.sessions;
import db.sql.beans.client;
import db.sql.beans.session;
import stuff.main;

public class _validateSession {
	public _validateSessionBean validateSession(String sessionCode) {

		sessions _sessions = new sessions();
		session _session = new session();
		_session.setSessionCode(sessionCode);

		session _dbSession = _sessions.searchSession(_session);
		boolean sessionActive = _dbSession.isActive();
		boolean sessionExpired = false;
		long clientId = 0;
		if (sessionActive) {
			clients _clients = new clients();
			client _client = new client();
			_client.setClientId(_dbSession.getClientId());
			clientId = _clients.searchClientBeanByClientId(_client).getClientId();
			if (!_dbSession.isInfinite()) {
				Date now = new Date(new java.util.Date().getTime());
				Date modified = _dbSession.getDateTimeModified();
				
				long time = now.getTime() - modified.getTime();
				sessionExpired = time > main.sessionExpiryMilliseconds;
				if (sessionExpired) {
					//_sessions.deactivateSession(_session);
				}
			}
		}
		
		//boolean booleanResult = sessionActive && !sessionExpired;
		boolean booleanResult = sessionActive;

		_validateSessionBean __validateSessionBean = new _validateSessionBean();
		__validateSessionBean.setBooleanResult(booleanResult);
		__validateSessionBean.setClientId(clientId);
		
		return __validateSessionBean;
	}
}
