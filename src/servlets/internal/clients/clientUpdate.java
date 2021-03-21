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
@WebServlet(main.internalServletsPrefix + "/clientUpdate")
public class clientUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public clientUpdate() {
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
			
			JSONParser jsonParser = new JSONParser(); JSONObject jsonObject =
			(JSONObject) jsonParser.parse(data);
			
			clients _clients = new clients();
			client _client = new client();
			_client.setClientId((long)jsonObject.get("clientId"));
			
			client _dbClient = _clients.searchClientBeanByClientId(_client);
			_dbClient.setCity((String)jsonObject.get("city"));
			_dbClient.setCountry((String)jsonObject.get("country"));
			_dbClient.setFullName((String)jsonObject.get("fullName"));
			_dbClient.setGender((String)jsonObject.get("gender"));
			_dbClient.setOrganization((String)jsonObject.get("organization"));
			_dbClient.setRole((String)jsonObject.get("role"));
			
			boolean booleanResult = (boolean)_clients.modifyClient(_dbClient).retrieve().get("object");
			
			_ajaxResult.insert(true, jsonPair, booleanResult);
		} catch (Exception exception) {
			_ajaxResult.exception();
		}

		response.getWriter()
				.write(aes.encrypt(_ajaxResult.retrieve().toJSONString(),
						((String) request.getParameter("ivOut")).getBytes(),
						((String) request.getParameter("keyOut")).getBytes()));
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
