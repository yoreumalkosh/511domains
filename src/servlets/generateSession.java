package servlets;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import stuff.cacheSession;
import stuff.main;

public class generateSession {
	public jsData generateData(HttpServletRequest httpRequest) {
		int sessionIdLength = 36;
		boolean sessionIdUseLetters = true;
		boolean sessionIdUseNumbers = false;
		String sessionId = RandomStringUtils.random(sessionIdLength, sessionIdUseLetters, sessionIdUseNumbers);

		Cache<String, ArrayList<cacheSession>> csrfPreventionSaltCache = (Cache<String, ArrayList<cacheSession>>) httpRequest
				.getSession().getAttribute("csrfPreventionSaltCache");

		ArrayList<cacheSession> _sessions = new ArrayList<>();

		if (csrfPreventionSaltCache != null) {
			_sessions = csrfPreventionSaltCache.getIfPresent("sessions");
			if (_sessions == null) {
				_sessions = new ArrayList<>();
			}
		} else {
			csrfPreventionSaltCache = CacheBuilder.newBuilder().build();

			httpRequest.getSession().setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);
		}

		cacheSession _session = new cacheSession();
		_session.setIvIn(main.generateIV());
		_session.setKeyIn(main.generateKey());
		_session.setIvOut(main.generateIV());
		_session.setKeyOut(main.generateKey());
		_session.setSessionId(sessionId);
		_sessions.add(_session);

		csrfPreventionSaltCache.put("sessions", _sessions);
		
		jsData _jsData = new jsData();
		_jsData.setSessionId(sessionId);
		_jsData.setIvIn(_session.getIvIn());
		_jsData.setKeyIn(_session.getKeyIn());
		_jsData.setIvOut(_session.getIvOut());
		_jsData.setKeyOut(_session.getKeyOut());
		
		return _jsData;
	}
}
