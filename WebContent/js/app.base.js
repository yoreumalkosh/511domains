var app = {
	ivInClue: function() {
		return "IV_IN_CLUE";		
	},
	keyInClue: function() {
		return "KEY_IN_CLUE";
	},
	ivOutClue: function() {
		return "IV_OUT_CLUE";
		//return CryptoJS.MD5("ואהבת לרעך כמוך").toString().substring(0, 16);
	},
	keyOutClue: function() {
		return "KEY_OUT_CLUE";
		//return CryptoJS.MD5("תן חיוך הכל לטובה").toString().substring(0, 32);
	},
	encryptData: function(msg, _iv, _key) {
		var iv = CryptoJS.enc.Utf8.parse(_iv);
		var key = CryptoJS.enc.Utf8.parse(_key);
			return CryptoJS.AES.encrypt(msg, key, {
			iv : iv,
			mode: CryptoJS.mode.CBC,
	        padding: CryptoJS.pad.Pkcs7
		}).toString();
	},
	decryptData: function(msg, _iv, _key) {
		var aesCbc = new aesjs.ModeOfOperation.cbc(app.stringToByteArray(_key), app.stringToByteArray(_iv));
		var decryptedBytes = aesCbc.decrypt(base64js.toByteArray(msg));
		
		return app.cleanJson(aesjs.utils.utf8.fromBytes(decryptedBytes));
	},
	cleanJson: function(jsonIn) {
		var foundValidChar = false;
		var i  = jsonIn.length - 1;
		
		while (!foundValidChar) {
			if (jsonIn[i] !==  "}") {
				i--;
				continue;
			} else {
				foundValidChar = true;
			}
		}
	
		return jsonIn.substring(0, i + 1);
	},		
	stringToByteArray: function(string) {
		    var utf8 = [];
		    for (var i=0; i < string.length; i++) {
		        var charcode = string.charCodeAt(i);
		        if (charcode < 0x80) utf8.push(charcode);
		        else if (charcode < 0x800) {
		            utf8.push(0xc0 | (charcode >> 6), 
		                      0x80 | (charcode & 0x3f));
		        }
		        else if (charcode < 0xd800 || charcode >= 0xe000) {
		            utf8.push(0xe0 | (charcode >> 12), 
		                      0x80 | ((charcode>>6) & 0x3f), 
		                      0x80 | (charcode & 0x3f));
		        }
		        // surrogate pair
		        else {
		            i++;
		            // UTF-16 encodes 0x10000-0x10FFFF by
		            // subtracting 0x10000 and splitting the
		            // 20 bits of 0x0-0xFFFFF into two halves
		            charcode = 0x10000 + (((charcode & 0x3ff)<<10)
		                      | (string.charCodeAt(i) & 0x3ff));
		            utf8.push(0xf0 | (charcode >>18), 
		                      0x80 | ((charcode>>12) & 0x3f), 
		                      0x80 | ((charcode>>6) & 0x3f), 
		                      0x80 | (charcode & 0x3f));
		        }
		    }
		    return utf8;
	},
	ajax: function(js, successFunction) {
		var encrypted = app.encryptData(JSON.stringify(js), app.ivIn, app.keyIn);;
			$.ajax("upload", {
			method : "POST",
			data : {
				init: false,
				data : encrypted,
				sessionId: app.sessionId
			},
			async : false,
			success : function(reply) {
				var json = JSON.parse(app.decryptData(reply, app.ivOut, app.keyOut));
									
				if (json.success === false) {
					alert("servlet exception");
				} else {
					app.ivIn = json.pair.ivIn;
					app.keyIn = json.pair.keyIn;
					app.ivOut = json.pair.ivOut;
					app.keyOut = json.pair.keyOut;
				
					successFunction(json);
				}
			},
			error : function() {
				alert("ajax exception");
			}
		});
	},
	sessionId: "SESSIONID",
	ivIn: undefined,
	keyIn: undefined,
	ivOut: undefined,
	keyOut: undefined,
	ajaxInit: function() {
		var js = {
			servlet: "init"
		};
		var encrypted = app.encryptData(JSON.stringify(js), app.ivInClue(), app.keyInClue());
	
		$.ajax("upload", {
			data: {
				init: true,
				data: encrypted,
				sessionId: app.sessionId
			},
			method : "POST",
			async : false,
			success : function(reply) {
				var json = JSON.parse(app.decryptData(reply, app.ivOutClue(), app.keyOutClue()));
			
				app.ivIn = json.pair.ivIn;
				app.keyIn = json.pair.keyIn;
				app.ivOut = json.pair.ivOut;
				app.keyOut = json.pair.keyOut;
			}
		});
	}
};