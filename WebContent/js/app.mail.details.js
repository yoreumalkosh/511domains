app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			app.validateSession();
			app.getClient();
			app.initParams();
			app.validateMail();
			$(document).on("click", "#mailPasswordReset", function () {
				var password = $("#password").val();
				app.passwordMail(mailName, password);
			});
			$("#signOut").click(function() {
				app.signOut();
			});
		},
		mailName: undefined,
		domainName: undefined,
		initParams: function() {
			var query = document.location.href.split("?")[1];
			var params = query.split("@");
			
			app.mailName = params[0];
			app.domainName = params[1];
		},
		validateMail: function() {
			app.ajax({
				servlet: "mailValidate",
				clientId: app.clientId,
				mailName: app.mailName,
				domainName: app.domainName
			}, function(reply) {
				if (reply.object === false) {
					app.signOut();
				}
				app.setHeader();
			});
		},
		setHeader: function() {
			$("#mailName").html(app.mailName + "@" + app.domainName + " details");
		},
		passwordMail: function(mailName, mailPassword) {
			var validPassword = mailPassword.length > 0 && mailPassword.length <= 64;
			if (!validPassword) {
				$("#modalValidPassword").modal();
				return;
			}
			app.ajax({
				servlet: "mailPassword",
				clientId: app.clientId,
				domainName: app.domainName,
				name: app.mailName,
				password: mailPassword
			}, function(reply) {
				document.location.reload(true);
			});
		},
		mails: [],
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