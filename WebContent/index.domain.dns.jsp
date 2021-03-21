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
	<div id="mails" class="wrapper">
		<div class="bs-component">
			<div class="jumbotron">
				<p class="lead">
					DNS settings for <span id="domainName"></span>
				</p>
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Type</th>
						<th>Host</th>
						<th>Values</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>MX</td>
						<td>
							<p>
								<input type="text" class="form-control" readonly
									id="mx-host-including" />
							</p>
							<p>or</p>
							<p>
								<input type="text" class="form-control" readonly
									id="mx-host-not-including" />
							</p>
						</td>
						<td>
							<p>
								<input type="text" class="form-control" readonly
									value="bjorkfan.club" />
							</p>
							<p>Priority: 5</p>
						</td>
					</tr>
					<tr>
						<td>TXT</td>
						<td>
							<p>
								<input type="text" class="form-control" readonly
									id="spf-host-including" />
							</p>
							<p>or</p>
							<p>
								<input type="text" class="form-control" readonly
									id="spf-host-not-including" />
							</p>
						</td>
						<td>
							<p>
								<input type="text" class="form-control" readonly
									value="v=spf1 a mx ~all" />
							</p>
						</td>

					</tr>
					<tr>
						<td>TXT</td>
						<td>
							<p>
								<input type="text" class="form-control" readonly
									id="dmarc-host-including" />
							</p>
							<p>or</p>
							<p>
								<input type="text" class="form-control" readonly
									id="dmarc-host-not-including" />
							</p>
						</td>
						<td>
							<p>
								<input type="text" class="form-control" readonly
									value="v=DMARC1; p=quarantine;" />
							</p>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="jumbotron">
				<p class="lead">DKIM record</p>
				<fieldset id="addRecord">
					<div class="form-group">
						<label for="selector">Selector</label> <input type="text"
							class="form-control" id="selector" aria-describedby="emailHelp"
							placeholder="Selector">
					</div>
					<button class="btn btn-primary" id="dkimAdd">Add</button>
				</fieldset>
				<br />
				<table id="currectRecord" class="table table-hover">
					<tbody id="tbody">

					</tbody>
				</table>
			</div>
		</div>
	</div>
	<jsp:include page="includes/account.footer.jsp">
		<jsp:param name="currentIndex" value="-1" />
	</jsp:include>
	<div class="modal" id="modalValidDKIMselector">
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
					<p>DKIM selector must contain numbers and lowercase letters
						only and not be longer than 16 bytes.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="modalKeyDeleteDialog">
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
					<p>
						Are you sure that you want to delete <span id="confirmSelector"></span>
						DKIM key?
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" id="confirmDeleteNo"
						class="btn btn-secondary" data-dismiss="modal">No</button>
					<button type="button" id="confirmDeleteYes" class="btn btn-primary"
						data-dismiss="modal">Yes</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="scriptsDomainDNS?<%=codeScripts%>"></script>
</body>
</html>