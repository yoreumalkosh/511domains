<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.RandomStringUtils"%>
<%@ page import="servlets.internal.clients._validateSession"%>
<%@ page import="servlets.internal.clients._validateSessionBean"%>
<%@ page import="stuff.main"%>
<%
	int codeScriptsLength = 36;
	boolean codeScriptsUseLetters = true;
	boolean codeScriptsUseNumbers = false;
	String codeScripts = RandomStringUtils.random(codeScriptsLength, codeScriptsUseLetters,
			codeScriptsUseNumbers);

	int codeStylesLength = 36;
	boolean codeStylesUseLetters = true;
	boolean codeStylesUseNumbers = false;
	String codeStyles = RandomStringUtils.random(codeStylesLength, codeStylesUseLetters, codeStylesUseNumbers);

	String sessionCode = main.getCookie(request, main.sessionCode);

	_validateSession __validateSession = new _validateSession();
	_validateSessionBean __validateSessionBean = __validateSession.validateSession(sessionCode);
	
	boolean loggedIn = __validateSessionBean.isBooleanResult() && __validateSessionBean.getClientId() != 0;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>511 Domains</title>
<link rel="stylesheet" href="styles?<%=codeStyles%>">
<link rel="shortcut icon" href="511.ico" type="image/x-icon">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<%if (loggedIn) { %>
	<jsp:include page="includes/account.header.jsp">
		<jsp:param name="currentIndex" value="3" />
	</jsp:include>
	<% } else { %>
	<jsp:include page="includes/index.header.jsp">
		<jsp:param name="currentIndex" value="3" />
	</jsp:include>
	<% } %>
	<jsp:include page="includes/include.settings.jsp" />
	<% if (loggedIn) { %>
	<jsp:include page="includes/account.footer.jsp">
		<jsp:param name="currentIndex" value="-1" />
	</jsp:include>
	<% } else { %>
	<jsp:include page="includes/index.footer.jsp">
		<jsp:param name="currentIndex" value="-1" />
	</jsp:include>
	<% } %>
	<% if (loggedIn) { %>
		<script type="text/javascript" src="scriptsStatic?<%=codeScripts%>"></script>
	<% } else { %>
		<script type="text/javascript" src="scriptsHome?<%=codeScripts%>"></script>
	<% } %>
</body>
</html>