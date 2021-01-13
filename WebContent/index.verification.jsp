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
	<jsp:include page="includes/index.header.jsp">
		<jsp:param name="currentIndex" value="0" />
	</jsp:include>
	<div id="validate" class="wrapper">
		<div class="bs-component">
			<div class="jumbotron">
				<h1 class="display-3">Welcome to 511 Domains!</h1>
				<p class="lead" id="messagePending" style="display: none;">
					You have been successfully registered, please complete verification
					process. Verification email was sent.<br />
					<button class="verificationButton btn btn-primary">Resend verification
						email</button>
				</p>
				<p class="lead" id="messageWaiting" style="display: none;">
					Verification email was sent. You can try again in <span
						id="secondsSpan"></span><br />
					<button class="verificationButton btn btn-primary">Resend verification
						email</button>
				</p>
				<p class="lead" id="messageDone" style="display: none;">Your
					account is verified.</p>
				<p class="lead" id="messageException" style="display: none;">Sorry, something unexpected happened.</p>
			</div>
		</div>
	</div>
	<jsp:include page="includes/index.footer.jsp">
		<jsp:param name="currentIndex" value="-1" />
	</jsp:include>
	<script type="text/javascript" src="scriptsVerification?<%=codeScripts%>"></script>
</body>
</html>