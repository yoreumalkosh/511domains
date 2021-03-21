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

import db.sql.domains;
import db.sql.beans.client;
import db.sql.beans.domain;
import stuff.aes;
import stuff.ajaxResult;
import stuff.dkim;
import stuff.main;

/**
 * Servlet implementation class domainAdd
 */
@WebServlet(main.internalServletsPrefix + "/domainAdd")
public class domainAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public domainAdd() {
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

			String data = aes.decrypt((String) request.getParameter("data"), ivIn.getBytes(), keyIn.getBytes());

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(data);

			domains _domains = new domains();
			client _client = new client();
			_client.setClientId((long)jsonObject.get("clientId"));

			domain _domain = new domain();
			_domain.setActive1(true);
			_domain.setActive2(true);
			_domain.setActive3(true);
			_domain.setDomainName((String)jsonObject.get("domainName"));
			_domain.setValidated(false);
			
			_ajaxResult.insert(true, jsonPair, _domains.addDomain(_domain, _client).retrieve().get("object"));
			
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
