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
@WebServlet(main.internalServletsPrefix + "/clientModifyPassword")
public class clientModifyPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public clientModifyPassword() {
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

			String data = aes.decrypt((String) request.getParameter("data"), ivIn.getBytes(), keyIn.getBytes());

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(data);

			clients _clients = new clients();
			
			client _client1 = new client();
			_client1.setClientId((long) jsonObject.get("clientId"));
			
			client _client2 = _clients.searchClientBeanByClientId(_client1);
			
			client _client3 = new client();
			_client3.setPassword((String) jsonObject.get("oldPassword"));
			_client3.setEmail(_client2.getEmail());
			
			boolean booleanResult = _clients.checkPassword(_client3);
			
			if (booleanResult) {
				client _client4 = new client();
				_client4.setClientId(_client2.getClientId());
				_client4.setPassword((String) jsonObject.get("password"));
				_ajaxResult.insert(true, jsonPair, _clients.modifyPassword(_client4).retrieve().get("object"));	
			} else {
				_ajaxResult.insert(true, jsonPair, booleanResult);
			}
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
