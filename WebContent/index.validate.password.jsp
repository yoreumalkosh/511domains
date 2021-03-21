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
	<div id="resetPassword" class="wrapper">
		<div class="bs-component">
			<div class="jumbotron">
				<div class="form-group">
					<label for="password1">New password</label> <input type="password"
						class="form-control" id="password1" aria-describedby="emailHelp"
						placeholder="New password" maxlength="50">
				</div>
				<div class="form-group">
					<label for="password2">Confirm password</label> <input
						type="password" class="form-control" id="password2"
						aria-describedby="emailHelp" placeholder="Confirm password"
						maxlength="50">
				</div>
				<button class="btn btn-primary" id="passwordReset">Reset</button>
			</div>
		</div>
	</div>
	<jsp:include page="includes/index.footer.jsp">
		<jsp:param name="currentIndex" value="-1" />
	</jsp:include>
	<div class="modal" id="modalInvalidPassword">
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
					<p>Please enter valid password.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="modalPasswordsNotEqual">
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
					<p>Passwords are not equal.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="modalResetSuccessful">
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
					<p>Password was reset successfully.</p>
				</div>
				<div class="modal-footer">
					<button type="button" id="resetReload"
						class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="modalUnexpected">
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
					<p>Something unexpected happened.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="scriptsValidatePassword?<%=codeScripts%>"></script>
</body>
</html>