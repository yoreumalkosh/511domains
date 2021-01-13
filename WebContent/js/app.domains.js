app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			app.validateSession();
			app.getClient();
			app.searchDomains();
			$(document).on("click","button.domainDelete", function () {
   				var domainId = $(this).attr('domain-id');
   				app.deleteDomain(domainId);
			});
			$(document).on("click","button.domainMails", function () {
   				var domainName = $(this).attr('domain-name');
   				document.location.href = "mails?" + domainName;
			});
			$("#domainAdd").click(app.addDomain);
			$("#signOut").click(function() {
				app.signOut();
			});
		},
		searchDomains: function() {
			app.ajax({
				servlet: "domainsSearch",
				clientId: app.clientId
			}, function(reply) {
				app.domains = reply.object;
				$.each(app.domains, function(key, value) {
					var tr = "<tr><td class=\"button\"><button class=\"btn btn-primary domainDelete\" domain-id=\"" + value.domainId + "\">Delete</button></td><td class=\"button\"><button class=\"domainMails btn btn-primary\" domain-name=\"" + value.domainName + "\">Mails</button></td><td>" + value.domainName + "</td></tr>";
					$("#domainsContent").html($("#domainsContent").html() + tr);
				});
			});
		},
		addDomain: function() {
			if (!app.validateDomain($("#domainName").val())) {
				alert("Please enter valid domain name.");
				return;
			}
			app.ajax({
				servlet: "domainAdd",
				clientId: app.clientId,
				domainName: $("#domainName").val()
			}, function(reply) {
				document.location.reload(true);
			});
		},
		deleteDomain: function(domainId) {
			app.ajax({
				servlet: "domainDelete",
				clientId: app.clientId,
				domainId: domainId
			}, function(reply) {
				document.location.reload(true);
			});
		},
		domains: [],
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
		validateDomain: function(input) {
			var domainFormat = /^((?!-))(xn--)?[a-z0-9][a-z0-9-_]{0,61}[a-z0-9]{0,1}\.(xn--)?([a-z0-9\-]{1,61}|[a-z0-9-]{1,30}\.[a-z]{2,})$/;
			return input.match(domainFormat);
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
