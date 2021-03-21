package servlets.internal.domains;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import db.sql.dkims;
import db.sql.domains;
import db.sql.mails;
import db.sql.beans.client;
import db.sql.beans.domain;
import db.sql.beans.mail;
import stuff.aes;
import stuff.ajaxResult;
import stuff.dkim;
import stuff.main;

/**
 * Servlet implementation class accountSearch
 */
@WebServlet(main.internalServletsPrefix + "/domainDeleteDKIM")
public class domainDeleteDKIM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public domainDeleteDKIM() {
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
			
			dkims _dkims = new dkims();
			
			String domainName = (String)jsonObject.get("domainName");
			String selector = (String)jsonObject.get("selector");
			
			client _client = new client();
			_client.setClientId((long)jsonObject.get("clientId"));
			
			domains _domains = new domains();
			domain _domain = new domain();
			_domain.setDomainName(domainName);
			
			domain _dbDomain = _domains.getObjectDomainName(_domain, _client);

			db.sql.beans.dkim _dkim = new db.sql.beans.dkim();
			_dkim.setDomainId(_dbDomain.getDomainId());
			_dkim.setSelector(selector);
			
			if (_dbDomain.getDomainName() != null) {
				_ajaxResult.insert(true, jsonPair, _dkims.deleteDKIM(_dkim).retrieve().get("object"));	
			} else {
				_ajaxResult.insert(true, jsonPair, false);
			}
			
			stuff.dkim.write();
			stuff.dkim.restart();
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
