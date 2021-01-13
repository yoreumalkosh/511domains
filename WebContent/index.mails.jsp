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
<title>511Domains</title>
<link rel="stylesheet" href="styles?<%=codeStyles%>">
<link rel="shortcut icon" href="511.ico" type="image/x-icon">
</head>
<body>
	<jsp:include page="includes/account.header.jsp">
		<jsp:param name="currentIndex" value="0" />
	</jsp:include>
	<div id="mails" class="wrapper">
		<div class="bs-component">
			<div class="jumbotron">
				<p class="lead">Emails</p>
				<fieldset>
					<div class="form-group">
						<label for="mailName">Email</label> <input type="text"
							class="form-control" id="mailName" aria-describedby="emailHelp"
							placeholder="Email">
					</div>
					<button class="btn btn-primary" id="mailAdd">Add</button>
				</fieldset>
			</div>
			<table id="mailsContent" class="table table-hover">
			</table>
		</div>
	</div>
	<jsp:include page="includes/account.footer.jsp">
		<jsp:param name="currentIndex" value="-1" />
	</jsp:include>
	<script type="text/javascript" src="scriptsMails?<%=codeScripts%>"></script>
</body>
</html>