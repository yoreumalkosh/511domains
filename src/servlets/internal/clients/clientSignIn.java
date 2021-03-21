package servlets.internal.clients;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import db.sql.mails;
import db.sql.clients;
import db.sql.sessions;
import db.sql.beans.client;
import db.sql.beans.session;
import stuff.aes;
import stuff.ajaxResult;
import stuff.main;

/**
 * Servlet implementation class accountAdd
 */
@WebServlet(main.internalServletsPrefix + "/clientSignIn")
public class clientSignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public clientSignIn() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ajaxResult _ajaxResult = new ajaxResult();
		
		String ivInNew = request.getParameter("ivInNew");
		String keyInNew = request.getParameter("keyInNew");
		String ivOutNew = request.getParameter("ivOutNew");
		String keyOutNew = request.getParameter("keyOutNew");
		JSONObject jsonPair = new JSONObject();
		jsonPair.put("ivIn", ivInNew);
		jsonPair.put("keyIn", keyInNew);
		jsonPair.put("ivOut", ivOutNew);
		jsonPair.put("keyOut", keyOutNew);
		
		try {
			String ivIn = request.getParameter("ivIn");
			String keyIn = request.getParameter("keyIn");
			String cleanData = request.getParameter("data");
			
			String data = aes.decrypt(
					(String) request.getParameter("data"),
					ivIn.getBytes(),
					keyIn.getBytes());

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(data);

			clients _clients = new clients();
			client _client = new client();
			_client.setEmail((String)jsonObject.get("email"));
			_client.setPassword((String)jsonObject.get("password"));
			
			client _dbClient = _clients.searchClientBeanByEmail(_client);
			boolean validPassword = _clients.checkPassword(_client);
			boolean isActive = _dbClient.isActive1() && _dbClient.isValidated();
			
			boolean booleanResult = validPassword && isActive;
			
			if (booleanResult) {
				sessions _sessions = new sessions();
				session _session = new session();

				int sessionCodeLength = 32;
				boolean sessionCodeUseLetters = true;
				boolean sessionCodeUseNumbers = false;
				String sessionCode = RandomStringUtils.random(sessionCodeLength, sessionCodeUseLetters, sessionCodeUseNumbers);
				_session.setSessionCode(sessionCode);
				
				Cookie cookie = new Cookie(main.sessionCode, sessionCode);
				cookie.setMaxAge(10 * 365 * 24 * 60 * 60);
				response.addCookie(cookie);
				
				Date now = new Date(new java.util.Date().getTime());
				
				_session.setDateTimeCreated(now);
				_session.setDateTimeExpired(now);
				_session.setDateTimeModified(now);
				
				//_session.setActive(true);
				
				_session.setInfinite((boolean)jsonObject.get("isInfinite"));
				_sessions.createSession(_session, _dbClient);
			}
		
			_ajaxResult.insert(true, jsonPair, booleanResult);
		} catch (Exception exception) {
			_ajaxResult.exception();
		}
		
		response.getWriter().write(aes.encrypt(_ajaxResult.retrieve().toJSONString(), ((String) request.getParameter("ivOut")).getBytes(), ((String) request.getParameter("keyOut")).getBytes()));
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
