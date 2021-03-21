package servlets.internal.mails;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import db.sql.domains;
import db.sql.mails;
import db.sql.beans.client;
import db.sql.beans.domain;
import db.sql.beans.mail;
import stuff.aes;
import stuff.ajaxResult;
import stuff.main;

/**
 * Servlet implementation class accountModify
 */
@WebServlet(main.internalServletsPrefix + "/mailPassword")
public class mailPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public mailPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			
			mails _mails = new mails();
			mail _mail = new mail();
			_mail.setName((String)jsonObject.get("name"));
			_mail.setPassword((String)jsonObject.get("password"));
			domains _domains = new domains();
			domain _domain = new domain();
			_domain.setDomainName((String)jsonObject.get("domainName"));
			
			client _client = new client();
			_client.setClientId((long)jsonObject.get("clientId"));
			
			domain _dbDomain = _domains.getObjectDomainName(_domain, _client);
			
			_ajaxResult.insert(true, jsonPair, _mails.passwordMail(_mail, _dbDomain).retrieve().get("object"));
		} catch (Exception exception) {
			_ajaxResult.exception();
		}

		response.getWriter()
				.write(aes.encrypt(_ajaxResult.retrieve().toJSONString(),
						((String) request.getParameter("ivOut")).getBytes(),
						((String) request.getParameter("keyOut")).getBytes()));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
