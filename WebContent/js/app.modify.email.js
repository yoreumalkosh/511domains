app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			app.validateSession();
			app.getClient();
			$("#signOut").click(function() {
				app.signOut();
			});
			$("#modifyEmailButton").click(function() {
				app.modifyEmail();
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
		modifyEmail: function() {
			var email1 = $("#email1").val();
			var email2 = $("#email2").val();
			
			var equalEmails = email1 === email2;
			
			if (!equalEmails) {
				alert("Emails are not equal.");
				return;
			}
			
			var validEmails = app.validateEmail(email1);
			
			if (!validEmails) {
				alert("Email is invalid.");
				return;
			}
			
			app.ajax({
				servlet: "clientModifyEmail",
				email: $("#email1").val(),
				clientId: app.clientId
			}, function(reply) {
				if(reply.object === true) {
					alert("Verification message was sent.");
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