app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			app.validateSession();
			app.getClient();
			app.searchMails();
			$(document).on("click","button.mailDelete", function () {
   				var mailName = $(this).attr('mail-name');
   				app.deleteMail(mailName);
			});
			$(document).on("click","button.mailToggle", function () {
   				var mailName = $(this).attr('mail-name');
   				app.toggleMail(mailName);
			});
			$("#mailAdd").click(app.addMail);
			$("#signOut").click(function() {
				app.signOut();
			});
		},
		searchMails: function() {
			app.ajax({
				servlet: "mailsSearch",
				clientId: app.clientId,
				domainName: document.location.href.split("?")[1]
			}, function(reply) {
				if (reply.object !== false) {
					app.domains = reply.object;
					$.each(app.domains, function(key, value) {
						var isEnabled = value.isEnabled ? "enabled" : "disabled";
						var tr = "<tr><td class=\"button\"><button class=\"mailDelete btn btn-primary\" mail-name=\"" + value.name + "\">Delete</button></td><td class=\"button\"><button class=\"mailToggle btn btn-primary\" mail-name=\"" + value.name + "\">Toggle</button></td><td>" + value.name + "@" + value.domainName + " (" + isEnabled + ")</td></tr>";
						$("#mailsContent").html($("#mailsContent").html() + tr);
					});
				} else {
					app.signOut();
				}
			});
		},
		addMail: function() {
			var email = $("#mailName").val() + "@" + document.location.href.split("?")[1];
			if (!app.validateEmail(email)) {
				alert("Invalid mail name");
				return;
			}
			app.ajax({
				servlet: "mailAdd",
				clientId: app.clientId,
				domainName: document.location.href.split("?")[1],
				name: $("#mailName").val()
			}, function(reply) {
				document.location.reload(true);
			});
		},
		deleteMail: function(mailName) {
			app.ajax({
				servlet: "mailDelete",
				clientId: app.clientId,
				domainName: document.location.href.split("?")[1],
				name: mailName
			}, function(reply) {
				document.location.reload(true);
			});
		},
		toggleMail: function(mailName) {
			app.ajax({
				servlet: "mailToggle",
				clientId: app.clientId,
				domainName: document.location.href.split("?")[1],
				name: mailName
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
					document.location.reload(true);
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