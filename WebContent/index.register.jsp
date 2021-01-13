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
		<jsp:param name="currentIndex" value="2" />
	</jsp:include>
	<div id="register" class="wrapper">
		<div class="bs-component">
			<div class="jumbotron">
				<p class="lead">Register</p>
				<fieldset>
					<div class="form-group">
						<label for="registerEmail">Email address</label> <input
							type="email" class="form-control" id="registerEmail"
							aria-describedby="emailHelp" placeholder="Email" maxlength="100">
					</div>
					<div class="form-group">
						<label for="registerPassword1">Password</label> <input
							type="password" class="form-control" id="registerPassword1"
							placeholder="Password" maxlength="50">
					</div>
					<div class="form-group">
						<label for="registerPassword2">Confirm password</label> <input
							type="password" class="form-control" id="registerPassword2"
							placeholder="Confirm password" maxlength="50">
					</div>
					<div class="form-group">
						<label for="registerFullName">Full name</label> <input type="text"
							class="form-control" id="registerFullName"
							placeholder="Full name" maxlength="120">
					</div>
					<div class="form-group">
						<label for="registerGender">Gender</label> <select
							class="form-control" id="registerGender">
							<option value="<%=stuff.main.genderMale%>"><%=stuff.main.genderMale%></option>
							<option value="<%=stuff.main.genderFemale%>"><%=stuff.main.genderFemale%></option>
						</select>
					</div>
					<div class="form-group">
						<label for="registerCountry">Country</label> <input type="text"
							class="form-control" id="registerCountry" placeholder="Country" maxlength="120">
					</div>
					<div class="form-group">
						<label for="registerCity">City</label> <input type="text"
							class="form-control" id="registerCity" placeholder="City" maxlength="120">
					</div>
					<div class="form-group">
						<label for="registerRole">Occupation</label> <select
							class="form-control" id="registerRole">
							<option value="<%=stuff.main.roleIndividual%>"><%=stuff.main.roleIndividual%></option>
							<option value="<%=stuff.main.roleBusiness%>"><%=stuff.main.roleBusiness%></option>
						</select>
					</div>
					<div class="form-group">
						<label for="registerOrganization">Organization</label> <input type="text"
							class="form-control" id="registerOrganization" placeholder="Organization" maxlength="200">
					</div>
					<button class="btn btn-primary" id="registerButton">Submit</button>
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