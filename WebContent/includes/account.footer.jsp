<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int currentIndex = Integer.valueOf(request.getParameter("currentIndex"));
%>
<div class="bs-component">
	<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
		<div class="navbar-collapse" id="navbarColor02">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item<%=currentIndex == 0 ? " active" : ""%>"><a
					class="nav-link" href="termsAndConditions">Terms And Conditions</a></li>
				<li class="nav-item<%=currentIndex == 1 ? " active" : ""%>"><a
					class="nav-link" href="privacyPolicy">Privacy Policy</a></li>
			</ul>
		</div>
	</nav>
</div>