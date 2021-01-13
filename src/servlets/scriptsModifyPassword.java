package servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import stuff.aes;
import stuff.cacheSession;
import stuff.main;

@WebServlet("/scriptsModifyPassword")
public class scriptsModifyPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public scriptsModifyPassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> scriptFileNames = new ArrayList<>();

		String webAppPath = getServletContext().getRealPath("/js/");
		scriptFileNames.add(webAppPath + "jquery-3.5.1.min.js");
		scriptFileNames.add(webAppPath + "bootstrap.min.js");
		scriptFileNames.add(webAppPath + "base64.min.js");
		//scriptFileNames.add(webAppPath + "core-min.js");
		scriptFileNames.add(webAppPath + "aes.js");
		scriptFileNames.add(webAppPath + "md5.js");
		scriptFileNames.add(webAppPath + "aesjs.js");
		scriptFileNames.add(webAppPath + "app.base.min.js");
		scriptFileNames.add(webAppPath + "app.modify.password.min.js");

		String scripts = "";
		for (String fileName : scriptFileNames) {
			byte[] encoded = Files.readAllBytes(Paths.get(fileName));
			scripts += new String(encoded, StandardCharsets.UTF_8);
		}

		generateSession _generateSession = new generateSession();
		jsData _jsData = _generateSession.generateData(request);
		
		scripts = scripts.replace("SESSIONID", _jsData.getSessionId());
		scripts = scripts.replace("IV_IN_CLUE", _jsData.getIvIn());
		scripts = scripts.replace("KEY_IN_CLUE", _jsData.getKeyIn());
		scripts = scripts.replace("IV_OUT_CLUE", _jsData.getIvOut());
		scripts = scripts.replace("KEY_OUT_CLUE", _jsData.getKeyOut());

		response.setContentType("text/javascript;charset=UTF-8");
		response.getWriter().write(scripts);
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
