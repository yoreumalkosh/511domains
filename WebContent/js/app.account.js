app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			app.validateSession();
			app.getClient();
			$("#accountUpdateButton").click(function() {
				app.update();
			});
			$("#signOut").click(function() {
				app.signOut();
			});
		},
		clientId: undefined,
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
		signOut: function() {
			app.ajax({
				servlet: "clientSignOut"
			}, function(reply) {
				if (reply.object === true) {
					document.location.href = "/";
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
		},
		update: function() {
			var validFullName = $("#accountFullName").val() !== "";
			var validCountry = $("#accountCountry").val() !== "";
			var validCity = $("#accountCity").val() !== "";
			var validOrganization = $("#accountOrganization").val() !== "";
			
			var validData = validFullName && validCountry && validCity && validOrganization;

			if (!validData) {
				$("#modalEnterValidData").modal();
				return;
			}

			app.ajax({
				servlet: "clientUpdate",
				clientId: app.clientId,
				fullName: $("#accountFullName").val(),
				gender: $("#accountGender").val(),
				country: $("#accountCountry").val(),
				city: $("#accountCity").val(),
				role: $("#accountRole").val(),
				organization: $("#accountOrganization").val()
			}, function(reply) {
				if(reply.object === true) {
					document.location.reload(true);
				}
			});
		}
	}
);
$(document).ready(app.init);