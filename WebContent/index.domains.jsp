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
	<jsp:include page="includes/account.header.jsp">
		<jsp:param name="currentIndex" value="0" />
	</jsp:include>
	<div id="domains" class="wrapper">
		<div class="bs-component">
			<div class="jumbotron">
				<p class="lead">Domains</p>
				<fieldset>
					<div class="form-group">
						<label for="domainName">Domain name</label> <input type="text"
							class="form-control" id="domainName" aria-describedby="emailHelp"
							placeholder="Domain name">
					</div>
					<button class="btn btn-primary" id="domainAdd">Add</button>
				</fieldset>
			</div>
			<table id="domainsContent" class="table table-hover">
			</table>
		</div>
	</div>
	<jsp:include page="includes/account.footer.jsp">
		<jsp:param name="currentIndex" value="-1" />
	</jsp:include>
	<div class="modal" id="modalValidDomainName">
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
					<p>Please enter valid domain name.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="modalDomainNameTaken">
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
					<p>This domain name is already in use.</p>
				</div>
				<div class="modal-footer">
					<button type="button" id="takenReload" class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="modalDomainDeleteDialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Confirmation</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>Are you sure that you want to delete <span id="confirmDomainName"></span>?</p>
				</div>
				<div class="modal-footer">
					<button type="button" id="confirmDeleteNo" class="btn btn-secondary" data-dismiss="modal">No</button>
					<button type="button" id="confirmDeleteYes" class="btn btn-primary" data-dismiss="modal">Yes</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="scriptsDomains?<%=codeScripts%>"></script>
</body>
</html>