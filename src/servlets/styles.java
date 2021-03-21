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

import stuff.aes;

@WebServlet("/styles")
public class styles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public styles() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> styleFileNames = new ArrayList<>();

		String webAppPath = getServletContext().getRealPath("/css/");
		styleFileNames.add(webAppPath + "bootstrap.min.css");
		styleFileNames.add(webAppPath + "page.min.css");

		String scripts = "";
		for (String fileName : styleFileNames) {
			byte[] encoded = Files.readAllBytes(Paths.get(fileName));
			scripts += new String(encoded, StandardCharsets.UTF_8);
		}
	
		response.setContentType("text/css;charset=UTF-8");
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
