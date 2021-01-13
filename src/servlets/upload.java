package servlets;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import stuff.aes;
import stuff.ajaxResult;
import stuff.main;
import stuff.cacheSession;

/**
 * Servlet implementation class getRequest
 */
@WebServlet("/upload")
public class upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public upload() {
		super();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ajaxResult _ajaxResult = new ajaxResult();

		try {
			boolean isInit = Boolean.valueOf(request.getParameter("init"));

			String ivIn = "";
			String keyIn = "";
			String ivOut = "";
			String keyOut = "";
			String sessionId = request.getParameter("sessionId");

			Cache<String, ArrayList<cacheSession>> csrfPreventionSaltCache = (Cache<String, ArrayList<cacheSession>>) request
					.getSession().getAttribute("csrfPreventionSaltCache");

			cacheSession _session = new cacheSession();
			ArrayList<cacheSession> _sessions = new ArrayList<>();
			
			/*
			if (isInit) {
				ivIn = "a5583a92f6fb19bd";
				keyIn = "99a1d138844d0a80693d19a4ce61c367";
				ivOut = "27b9c8c0ad71d537";
				keyOut = "84bb1d4c2a847493a2377c9ab546523b";

				if (csrfPreventionSaltCache == null) {
					csrfPreventionSaltCache = CacheBuilder.newBuilder().build();
				}

				request.getSession().setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);
			} else
			*/
			
			if (csrfPreventionSaltCache != null) {
				_sessions = csrfPreventionSaltCache.getIfPresent("sessions");
				if (_sessions == null) {
					_sessions = new ArrayList<>();
				}

				for (cacheSession __session : _sessions) {
					if (__session.getSessionId().equals(sessionId)) {
						ivIn = __session.getIvIn();
						keyIn = __session.getKeyIn();
						ivOut = __session.getIvOut();
						keyOut = __session.getKeyOut();
					}
				}
			} /*
				 * else { ivIn = "a5583a92f6fb19bd"; keyIn = "99a1d138844d0a80693d19a4ce61c367";
				 * ivOut = "27b9c8c0ad71d537"; keyOut = "84bb1d4c2a847493a2377c9ab546523b";
				 * 
				 * csrfPreventionSaltCache = CacheBuilder.newBuilder().build();
				 * 
				 * request.getSession().setAttribute("csrfPreventionSaltCache",
				 * csrfPreventionSaltCache); }
				 */

			int ivInLength = 16;
			boolean ivInUseLetters = true;
			boolean ivInUseNumbers = false;
			String ivInNew = RandomStringUtils.random(ivInLength, ivInUseLetters, ivInUseNumbers);

			int keyInLength = 32;
			boolean keyInUseLetters = true;
			boolean keyInUseNumbers = false;
			String keyInNew = RandomStringUtils.random(keyInLength, keyInUseLetters, keyInUseNumbers);

			int ivOutLength = 16;
			boolean ivOutUseLetters = true;
			boolean ivOutUseNumbers = false;
			String ivOutNew = RandomStringUtils.random(ivOutLength, ivOutUseLetters, ivOutUseNumbers);

			int keyOutLength = 32;
			boolean keyOutUseLetters = true;
			boolean keyOutUseNumbers = false;
			String keyOutNew = RandomStringUtils.random(keyOutLength, keyOutUseLetters, keyOutUseNumbers);

			_sessions = csrfPreventionSaltCache.getIfPresent("sessions");
			if (_sessions == null) {
				_sessions = new ArrayList<>();
			}

			boolean found = false;
			for (cacheSession __session : _sessions) {
				if (__session.getSessionId().equals(sessionId)) {
					__session.setIvIn(ivInNew);
					__session.setKeyIn(keyInNew);
					__session.setIvOut(ivOutNew);
					__session.setKeyOut(keyOutNew);
					__session.setSessionId(sessionId);

					found = true;
				}
			}

			if (!found) {
				_session.setIvIn(ivInNew);
				_session.setKeyIn(keyInNew);
				_session.setIvOut(ivOutNew);
				_session.setKeyOut(keyOutNew);
				_session.setSessionId(sessionId);
				_sessions.add(_session);
			}

			csrfPreventionSaltCache.put("sessions", _sessions);

			String data = request.getParameter("data");
			String decryptedData = aes.decrypt((String) request.getParameter("data"), ivIn.getBytes(), keyIn.getBytes());

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(decryptedData);

			String servlet = (String) jsonObject.get("servlet");

			String target = 
					main.internalServletsPrefix + "/" + servlet + "?data=" + URLEncoder.encode(data, StandardCharsets.UTF_8.toString()) + "&ivIn=" + ivIn + "&keyIn="
							+ keyIn + "&ivOut=" + ivOut + "&keyOut=" + keyOut + "&ivInNew=" + ivInNew + "&keyInNew="
							+ keyInNew + "&ivOutNew=" + ivOutNew + "&keyOutNew=" + keyOutNew;
			request.getRequestDispatcher(target).forward(request, response);
		} catch (Exception exception) {
			_ajaxResult.exception();
			response.getWriter().write(aes.encrypt(_ajaxResult.retrieve().toJSONString()));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
