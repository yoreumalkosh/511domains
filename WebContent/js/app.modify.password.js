app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			app.validateSession();
			app.getClient();
			$("#signOut").click(function() {
				app.signOut();
			});
			$("#modifyPasswordButton").click(function() {
				app.modifyPassword();
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
					app.clientId = reply.object.clientId;
				}
			});
		},
		modifyPassword: function() {
			var oldPassword = $("#oldPassword").val();
			var password1 = $("#password1").val();
			var password2 = $("#password2").val();
			
			var validPassword1 = password1 !== "";
			var validPassword2 = password2 !== "";
			var equalPasswords = password1 === password2;
			
			var validPassword = validPassword1 && validPassword2;
			
			if (!equalPasswords) {
				alert("Passwords are not equal.");
				return;
			}
			
			if (!validPassword) {
				alert("Password is empty.");
				return;
			}

			app.ajax({
				servlet: "clientModifyPassword",
				oldPassword: $("#oldPassword").val(),
				password: $("#password1").val(),
				clientId: app.clientId
			}, function(reply) {
				if(reply.object === true) {
					alert("Password was updated successfully.");
					document.location.reload(true);
				} else {
					alert("Something unexpected happened.");
				}
			});
		},
		clientId: undefined,
		signOut: function() {
			app.ajax({
				servlet: "clientSignOut"
			}, function(reply) {
				if (reply.object === true) {
					document.location.reload(true);
				}
			});
		},
		getClient: function() {
			if (app.clientId === undefined || app.clientId === 0) {
				document.location.href = "/";
			}
			app.ajax({
				servlet: "clientGet",
				clientId: app.clientId
			}, function(reply) {
				$("#accountEmail").val(reply.object.email);
				$("#accountFullName").val(reply.object.fullName);							
				$("#accountGender").val(reply.object.gender);							
				$("#accountCountry").val(reply.object.country);
				$("#accountCity").val(reply.object.city);
				$("#accountRole").val(reply.object.role);
				$("#accountOrganization").val(reply.object.organization);
			});
		}
	}
);
$(document).ready(app.init);