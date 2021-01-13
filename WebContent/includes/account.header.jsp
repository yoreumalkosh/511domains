<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int currentIndex = Integer.valueOf(request.getParameter("currentIndex"));
%>
<div class="bs-component">
	<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
		<a class="navbar-brand" href="#">511 Domains</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarColor01" aria-controls="navbarColor01"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarColor01">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item<%=currentIndex == 0 ? " active" : ""%>"><a
					class="nav-link" href="domains">Domains</a></li>
				<li class="nav-item<%=currentIndex == 1 ? " active" : ""%> dropdown"><a
					class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"
					role="button" aria-haspopup="true" aria-expanded="false">Account</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="account">Details</a> <a
							class="dropdown-item" href="accountEmail">Email</a> <a class="dropdown-item"
							href="accountPassword">Password</a>
					</div></li>
				<li class="nav-item<%=currentIndex == 10 ? " active" : ""%>"><a
					class="nav-link" href="contactUs">Contact Us</a></li>
				<li class="nav-item<%=currentIndex == 11 ? " active" : ""%>"><a
					class="nav-link" href="about">About</a></li>
				<li class="nav-item<%=currentIndex == 4 ? " active" : ""%>"><a
					class="nav-link" href="#" id="signOut">Sign Out</a></li>
			</ul>
		</div>
	</nav>
</div>