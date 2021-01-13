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
					class="nav-link" href="/">Home</a></li>
				<li class="nav-item<%=currentIndex == 1 ? " active" : ""%>"><a
					class="nav-link" href="signIn">Sign In</a></li>
				<li class="nav-item<%=currentIndex == 2 ? " active" : ""%>"><a
					class="nav-link" href="register">Register</a></li>
<!-- 
				<li class="nav-item<%=currentIndex == 10 ? " active" : ""%>"><a
					class="nav-link" href="contactUs">Contact Us</a></li>
 -->
 				<li class="nav-item<%=currentIndex == 11 ? " active" : ""%>"><a
					class="nav-link" href="about">About</a></li>
			</ul>
		</div>
	</nav>
</div>