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
				<p class="lead">Reset Password</p>
				<fieldset>
					<div class="form-group">
						<label for="email">Email address</label> <input type="email"
							class="form-control" id="email" aria-describedby="emailHelp"
							placeholder="Email" maxlength="100">
					</div>
					<button class="btn btn-primary" id="resetPasswordButton">Submit</button>
				</fieldset>
			</div>
		</div>
	</div>
	<jsp:include page="includes/index.footer.jsp">
		<jsp:param name="currentIndex" value="-1" />
	</jsp:include>
	<div class="modal" id="modalValidEmail">
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
					<p>Please enter valid email.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="modalEmailSuccessful">
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
					<p>Email was sent successfully.</p>
				</div>
				<div class="modal-footer">
					<button type="button" id="emailReload" class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="scriptsResetPasswordRequest?<%=codeScripts%>"></script>
</body>
</html>