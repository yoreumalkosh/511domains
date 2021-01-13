<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.RandomStringUtils"%>
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
	<jsp:include page="includes/account.header.jsp">
		<jsp:param name="currentIndex" value="1" />
	</jsp:include>
	<div id="changeEmail" class="wrapper">
		<div class="bs-component">
			<div class="jumbotron">
				<div class="form-group">
					<label for="email1">New email</label> <input type="email"
						class="form-control" id="email1" aria-describedby="emailHelp"
						placeholder="New email" maxlength="50">
				</div>
				<div class="form-group">
					<label for="email2">Confirm email</label> <input type="email"
						class="form-control" id="email2" aria-describedby="emailHelp"
						placeholder="Confirm email" maxlength="50">
				</div>
				<button class="btn btn-primary" id="modifyEmailButton">Submit</button>
			</div>
		</div>
	</div>
	<jsp:include page="includes/account.footer.jsp">
		<jsp:param name="currentIndex" value="-1" />
	</jsp:include>
	<script type="text/javascript" src="scriptsModifyEmail?<%=codeScripts%>"></script>
</body>
</html>