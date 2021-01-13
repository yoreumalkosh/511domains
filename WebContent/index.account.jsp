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
		<jsp:param name="currentIndex" value="1" />
	</jsp:include>
	<div id="account" class="wrapper">
		<div class="bs-component">
			<div class="jumbotron">
				<p class="lead">Account</p>
				<fieldset>
					<div class="form-group">
						<label for="accountEmail">Email address</label> <input
							type="email" class="form-control" id="accountEmail"
							aria-describedby="emailHelp" placeholder="Email" readonly="">
					</div>
					<div class="form-group">
						<label for="accountFullName">Full name</label> <input type="text"
							class="form-control" id="accountFullName" placeholder="Full name">
					</div>
					<div class="form-group">
						<label for="accountGender">Gender</label> <select
							class="form-control" id="accountGender">
							<option value="<%=stuff.main.genderMale%>"><%=stuff.main.genderMale%></option>
							<option value="<%=stuff.main.genderFemale%>"><%=stuff.main.genderFemale%></option>
						</select>
					</div>
					<div class="form-group">
						<label for="accountCountry">Country</label> <input type="text"
							class="form-control" id="accountCountry" placeholder="Country">
					</div>
					<div class="form-group">
						<label for="accountCity">City</label> <input type="text"
							class="form-control" id="accountCity" placeholder="City">
					</div>
					<div class="form-group">
						<label for="accountRole">Occupation</label> <select
							class="form-control" id="accountRole">
							<option value="<%=stuff.main.roleIndividual%>"><%=stuff.main.roleIndividual%></option>
							<option value="<%=stuff.main.roleBusiness%>"><%=stuff.main.roleBusiness%></option>
						</select>
					</div>
					<div class="form-group">
						<label for="accountOrganization">Organization</label> <input
							type="text" class="form-control" id="accountOrganization"
							placeholder="Organization">
					</div>
					<button class="btn btn-primary" id="accountUpdateButton">Submit</button>
				</fieldset>
			</div>
		</div>
	</div>
	<jsp:include page="includes/account.footer.jsp">
		<jsp:param name="currentIndex" value="-1" />
	</jsp:include>
	<script type="text/javascript" src="scriptsAccount?<%=codeScripts%>"></script>
</body>
</html>