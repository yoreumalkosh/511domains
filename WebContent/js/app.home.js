app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			app.validateSession();
			$("#registerButton").click(function() {
				app.signUp();
			});
			$("#signInButton").click(function() {
				app.signIn();
			});
			$("#forgotPasswordButton").click(function() {
				document.location.href = "resetPasswordRequest";
			});
		},
		validateEmail: function(input) {
			var mailformat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
			return input.match(mailformat);
		},
		validateSession: function() {
			app.ajax({
				servlet: "clientValidateSession"
			}, function(reply) {
				if(reply.object.booleanResult === true) {
					document.location.href = "domains";
				}
			});
		},
		signIn: function() {
			var validEmail = app.validateEmail($("#signInEmail").val());
		
			var validPassword = $("#signInPassword").val() !== "";
			var validData = validEmail && validPassword;
				if (!validData) {
				alert("Please use valid email address and password.");
				return;
			}
			app.ajax({
				servlet: "clientSignIn",
				email: $("#signInEmail").val(),
				password: $("#signInPassword").val(),
				isInfinite: $("#signInRemember").is(':checked')
			}, function(reply) {
				if(reply.object === true) {
					document.location.href = "domains";
				} else {
					alert("Incorrect credentials.");
				}
			});
		},
		signUp: function() {
			var validEmail = app.validateEmail($("#registerEmail").val());
			var validFullName = $("#registerFullName").val() !== "";
			var equalPasswords = $("#registerPassword1").val() === $("#registerPassword2").val();
			var validPassword = $("#registerPassword").val() !== "";
			var validData = validEmail && validFullName && validPassword;
			if (!equalPasswords) {
				alert("Passwords are not equal.");
				return;
			}
			if (!validData) {
				alert("Please enter valid email, name and password.");
				return;
			}
			app.ajax({
				servlet: "clientAdd",
				city: $("#registerCity").val(),
				country: $("#registerCountry").val(),
				email: $("#registerEmail").val(),
				fullName: $("#registerFullName").val(),
				gender: $("#registerGender").val(),
				organization: $("#registerOrganization").val(),
				password: $("#registerPassword1").val(),
				role: $("#registerRole").val()
			}, function(reply) {
				if(reply.object === true) {
					document.location.href = "verification?" + encodeURI($("#registerEmail").val());
				}
			});
		}
	}
);
$(document).ready(app.init);