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
			
			var validPassword1 = password1 !== "";
			var validPassword2 = password2 !== "";
			var equalPasswords = password1 === password2;
			
			var validPassword = validPassword1 && validPassword2;
			
			if (!equalPasswords) {
				alert("Passwords are not equal.");
				return;
			}
			
			if (!validPassword) {
				alert("Password is empty.");
				return;
			}
		
			app.ajax({
				servlet: "clientValidatePassword",
				passwordResetCode: document.location.href.split("?")[1],
				password: $("#password1").val()
			}, function(reply) {
				if(reply.object === true) {
					alert("Password was reset successfully.");
					document.location.reload(true);
				} else {
					alert("Something unexpected happened.");
				}
			});
		}
	}
);
$(document).ready(app.init);