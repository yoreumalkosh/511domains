app = $.extend({}, app, {
		init: function() {
			app.ajaxInit();
			app.validate();
		},
		validate: function() {
			app.ajax({
				servlet: "clientValidate",
				validationCode: document.location.href.split("?")[1]
			}, function(reply) {
				if(reply.object === true) {
					$("#messageDone").show();
				} else {
					$("#messageException").show();
				}
			});
		}
	}
);
$(document).ready(app.init);