app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			app.validateSession();
			app.getClient();
			app.setHeader();
			app.searchDomainDKIMS();
			$("#dkimAdd").click(function() {
				app.addDomainDKIM();
			});
			$(document).on("click","button.dkimDelete", function () {
   				var selector = $(this).attr('selector');
   				function _delete() {
   					app.deleteDomainDKIM(selector);
   				}
   				
   				$("#confirmSelector").html(selector);
   				$("#confirmDeleteYes").off("click");
   				$("#confirmDeleteYes").click(_delete);
   				
   				$("#modalKeyDeleteDialog").modal();
			});
			$("#signOut").click(function() {
				app.signOut();
			});
		},
		searchDomainDKIMS: function() {
			app.ajax({
				servlet: "domainDKIMs",
				clientId: app.clientId,
				domainName: document.location.href.split("?")[1]
			}, function(reply) {
				var tbody = "";
				var contains = false;
				$.each(reply.object, function(key, value) {
					contains = true;
					var tr1 = "<tr rowspan=\"2\"><td>Type: TXT</td><td><p>Host:</p><p><input class=\"form-control\" readonly value=\"" + value.selector + "._domainkey\"/></p><p>or</p><p><input class=\"form-control\" readonly value=\"" + value.selector + "._domainkey." + document.location.href.split("?")[1] + "\"/></p><span id=\"domainNameDKIM\"></span><p>Value:</p></td><td class=\"button\"><button class=\"dkimDelete btn btn-primary\" selector=\"" + value.selector + "\">Delete</button></td></tr>";
					var tr2 = "<tr><td colspan=\"3\"><textarea class=\"form-control\" rows=\"5\" style=\"resize: none;\" readonly>" + value.value + "</textarea></td></tr>";
					
					var trs = tr1 + tr2;
					tbody += trs;
				});
				if (contains) {
					$("#currectRecord").show();
					$("#addRecord").hide();
					$("#tbody").html(tbody);
				} else {
					$("#currectRecord").hide();
					$("#addRecord").show();
				}
			});
		},
		addDomainDKIM: function() {
			var selector = $("#selector").val();
			var isValid = app.validateSelector(selector);
			
			if (!isValid) {
				//alert("DKIM selector must contain numbers and lowercase letters only and be not longer than 16 bytes.");
				$("#modalValidDKIMselector").modal();
				return;
			}
		
			app.ajax({
				servlet: "domainAddDKIM",
				clientId: app.clientId,
				domainName: document.location.href.split("?")[1],
				selector: selector
			}, function(reply) {
				document.location.reload(true);
			});
		},
		deleteDomainDKIM: function(selector) {
			app.ajax({
				servlet: "domainDeleteDKIM",
				clientId: app.clientId,
				domainName: document.location.href.split("?")[1],
				selector: selector
			}, function(reply) {
				document.location.reload(true);
			});
		},
		setHeader: function() {
			var domainName = document.location.href.split("?")[1];
			
			$("#domainName").html(domainName);
			
			$("#mx-host-including").val("@");
			$("#mx-host-not-including").val(domainName);

			$("#spf-host-including").val("@");
			$("#spf-host-not-including").val(domainName);

			$("#dmarc-host-including").val("_dmarc");
			$("#dmarc-host-not-including").val("_dmarc." + domainName);
		},
		validateSelector: function(input) {
			var selectorFormat = /^[a-z0-9]+$/;
			return input.match(selectorFormat) && input.length <= 16;
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
