app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			app.validateSession();
			$("#resetPasswordButton").click(function() {
				app.resetPasswordRequest();
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
		resetPasswordRequest: function() {
			var validEmail = app.validateEmail($("#email").val());
			
			if (!validEmail) {
				//alert("Please enter valid email.");
				$("#modalValidEmail").modal();
				return;
			}
			app.ajax({
				servlet: "clientResetPassword",
				email: $("#email").val()
			}, function(reply) {
				if(reply.object === true) {
					//alert("Email was sent successfully.");
					$("#modalEmailSuccessful").modal();
					$("#emailReload").click(function() {
						document.location.reload(true);
					});
				}
			});
		}
	}
);
$(document).ready(app.init);