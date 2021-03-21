app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			app.clientByEmail();
			$(".verificationButton").click(function() {
				app.verification();
			});
		},
		clientByEmail: function() {
			var pending = false;
			
			app.ajax({
				servlet: "clientByEmail",
				email: document.location.href.split("?")[1]
			}, function(reply) {
				if(reply.object === true) {
					$("#messageException").hide();
					$("#messageDone").hide();
					$("#messageWaiting").hide();
					$("#messagePending").show();
				} else {
					$("#messageException").show();
					$("#messageDone").hide();
					$("#messageWaiting").hide();
					$("#messagePending").hide();
				}
				
				pending = reply.object;
			});
			
			return pending;
		},
		verification: function() {
			var pending = app.clientByEmail();
			if (!pending) {
				$("#messageException").hide();
				$("#messageDone").show();
				$("#messageWaiting").hide();
				$("#messagePending").hide();
				return;
			}
			app.ajax({
				servlet: "clientVerification",
				email: document.location.href.split("?")[1]
			}, function(reply) {
				if(reply.object === true) {
					$("#messageException").hide();
					$("#messageDone").hide();
					$("#messageWaiting").show();
					$("#messagePending").hide();
					$(".verificationButton").hide();
					var second = 30;
					$("#secondsSpan").html(second + " seconds");
					var interval = setInterval(function() {
						second--;
						if (second > 1) {
							$("#secondsSpan").html(second + " seconds");
						} else {
							$("#secondsSpan").html(second + " second");
						}
						if (second == 0) {
							clearInterval(interval);
							$("#secondsSpan").html("now");
							$(".verificationButton").show();
						}
					}, 1000);
				}
			});
		}		
	}
);
$(document).ready(app.init);