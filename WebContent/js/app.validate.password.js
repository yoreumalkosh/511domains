app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			$("#passwordReset").click(function() {
				app.passwordReset();
			});
		},
		passwordReset: function() {
			var password1 = $("#password1").val();
			var password2 = $("#password2").val();
			
			var validPassword1 = password1.length > 0 && password1.length <= 64;
			var validPassword2 = password2.length > 0 && password2.length <= 64;
			var equalPasswords = password1 === password2;
			
			var validPassword = validPassword1 && validPassword2;
			
			if (!equalPasswords) {
				$("#modalPasswordsNotEqual").modal();
				return;
			}
			
			if (!validPassword) {
				$("#modalInvalidPassword").modal();
				return;
			}
		
			app.ajax({
				servlet: "clientValidatePassword",
				passwordResetCode: document.location.href.split("?")[1],
				password: $("#password1").val()
			}, function(reply) {
				if(reply.object === true) {
					//alert("Password was reset successfully.");
					$("#modalResetSuccessful").modal();
					$("#resetReload").click(function() {
						document.location.reload(true);
					});
				} else {
					$("#modalUnexpected").modal();
				}
			});
		}
	}
);
$(document).ready(app.init);