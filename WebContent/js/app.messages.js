app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			app.validateSession();
			app.getClient();
			$("#signOut").click(function() {
				app.signOut();
			});
			$("#sendMessage").click(function() {
				app.sendMessage();
			});
		},
		sendMessage: function() {
			var validSubject = $("#messageSubject").val().length > 0 && $("#messageSubject").val().length < 1024;
			var validText = $("#messageText").val().length > 0 && $("#messageText").val().length < 4096;
			
			var valid = validSubject && validText;
			
			if (!valid) {
				$("#modalSubjectText").modal();
				return;
			}
			app.ajax({
				servlet: "clientContact",
				clientId: app.clientId,
				messageText: $("#messageText").val(),
				messageSubject: $("#messageSubject").val()
			}, function(reply) {
				if (reply.object === true) {
					//alert("Message was sent successfully.");
					$("#modalMessageSuccessful").modal();
					$("#messageReload").click(function() {
						document.location.reload(true);
					});
				}
			});
		},
		clientId: undefined,
		signOut: function() {
			app.ajax({
				servlet: "clientSignOut"
			}, function(reply) {
				if (reply.object === true) {
					document.location.href = "/";
				}
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
				app.clientId = reply.object.clientId;	
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