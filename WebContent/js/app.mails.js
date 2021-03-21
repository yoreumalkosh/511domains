app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			app.validateSession();
			app.getClient();
			app.searchMails();
			$(document).on("click","button.mailDelete", function () {
   				var mailName = $(this).attr('mail-name');
   				function _delete() {
   					app.deleteMail(mailName);
   				}
   				
   				$("#confirmEmailName").html(mailName + "@" + document.location.href.split("?")[1]);
   				$("#confirmDeleteYes").off("click");
   				$("#confirmDeleteYes").click(_delete);
   				
   				$("#modalEmailDeleteDialog").modal();   				
			});
			$(document).on("click","button.mailToggle", function () {
   				var mailName = $(this).attr('mail-name');
   				app.toggleMail(mailName);
			});
			$(document).on("click", "button.mailDetails", function () {
				var mailName = $(this).attr('mail-name');
				document.location.href = "mail?" + mailName + "@" + document.location.href.split("?")[1]; 
			});
			$("#mailAdd").click(app.addMail);
			$("#signOut").click(function() {
				app.signOut();
			});
		},
		setHeader: function() {
			$("#domainName").html(document.location.href.split("?")[1] + " emails");
		},		
		searchMails: function() {
			app.ajax({
				servlet: "mailsSearch",
				clientId: app.clientId,
				domainName: document.location.href.split("?")[1]
			}, function(reply) {
				if (reply.object !== false) {
					app.domains = reply.object.array;
					$.each(app.domains, function(key, value) {
						var isEnabled = value.isEnabled ? "enabled" : "disabled";
						var tr = "<tr><td>" + value.name + "@" + value.domainName + " (" + isEnabled + ")</td><td class=\"button\"><button class=\"mailDetails btn btn-primary\" mail-name=\"" + value.name + "\">Details</button></td><td class=\"button\"><button class=\"mailToggle btn btn-primary\" mail-name=\"" + value.name + "\">Toggle</button></td><td class=\"button\"><button class=\"mailDelete btn btn-primary\" mail-name=\"" + value.name + "\">Delete</button></td></tr>";
						$("#mailsContent").html($("#mailsContent").html() + tr);
					});
					$("#dkim").val(reply.object.dkim);
					app.setHeader();
				} else {
					app.signOut();
				}
			});
		},
		addMail: function() {
			var email = $("#mailName").val() + "@" + document.location.href.split("?")[1];
			if (!app.validateEmail(email)) {
				//alert("Invalid email name");
				$("#modalInvalidEmail").modal();
				return;
			}
			app.ajax({
				servlet: "mailAdd",
				clientId: app.clientId,
				domainName: document.location.href.split("?")[1],
				name: $("#mailName").val()
			}, function(reply) {
				if (!reply.object) {
					$("#modalEmailNameTaken").modal();
					$("#takenReload").click(function() {
						document.location.reload(true);					
					});
				} else {
					document.location.reload(true);
				}
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
		passwordMail: function(mailName, mailPassword) {
			app.ajax({
				servlet: "mailPassword",
				clientId: app.clientId,
				domainName: document.location.href.split("?")[1],
				name: mailName,
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