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
</head>
<body>
	<jsp:include page="includes/index.header.jsp">
		<jsp:param name="currentIndex" value="1" />
	</jsp:include>
	<div id="signIn" class="wrapper">
		<div class="bs-component">
			<div class="jumbotron">
				<p class="lead">Sign In</p>
				<fieldset>
					<div class="form-group">
						<label for="signInEmail">Email address</label> <input type="email"
							class="form-control" id="signInEmail"
							aria-describedby="emailHelp" placeholder="Email" maxlength="100">
					</div>
					<div class="form-group">
						<label for="signInPassword">Password</label> <input
							type="password" class="form-control" id="signInPassword"
							placeholder="Password" maxlength="50">
					</div>
					<button class="btn btn-primary" id="signInButton">Submit</button>
					<button class="btn btn-secondary" id="forgotPasswordButton">Forgot password</button>
				</fieldset>
			</div>
		</div>
	</div>
	<jsp:include page="includes/index.footer.jsp">
		<jsp:param name="currentIndex" value="-1" />
	</jsp:include>
	<script type="text/javascript" src="scriptsHome?<%=codeScripts%>"></script>
</body>
</html>