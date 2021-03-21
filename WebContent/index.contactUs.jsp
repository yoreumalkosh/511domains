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
		<jsp:param name="currentIndex" value="10" />
	</jsp:include>
	<div id="messages" class="wrapper">
		<div class="bs-component">
			<div class="jumbotron">
				<p class="lead">Contact us</p>
				<fieldset>
					<div class="form-group">
						<label for="messageSubject">Subject</label> <input type="text"
							class="form-control" id="messageSubject"
							aria-describedby="emailHelp" placeholder="Subject">
					</div>
					<div class="form-group">
						<label for="messageText">Message</label>
						<textarea class="form-control" id="messageText" rows="12"></textarea>
					</div>
					<button class="btn btn-primary" id="sendMessage">Send</button>
				</fieldset>
			</div>
		</div>
	</div>
	<jsp:include page="includes/account.footer.jsp">
		<jsp:param name="currentIndex" value="-1" />
	</jsp:include>
	<div class="modal" id="modalSubjectText">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Error</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>Please enter valid subject and message.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="modalMessageSuccessful">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Info</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>Message was sent successfully.</p>
				</div>
				<div class="modal-footer">
					<button type="button" id="messageReload"
						class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="scriptsMessages?<%=codeScripts%>"></script>
</body>
</html>