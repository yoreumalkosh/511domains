package stuff;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;

public class main {
	public static final String internalServletsPrefix = "/internalServletsPrefix";

	public static final String roleIndividual = "Individual";
	public static final String roleBusiness = "Business";

	public static final String genderMale = "Male";
	public static final String genderFemale = "Female";
	public static final String genderOther = "Other";
	public static final String genderUnknown = "I prefer not to say";

	public static final String sessionCode = "sessionCode";
	public static final long sessionExpiryMilliseconds = 20 * 60 * 1000;
	
	public static final long emailValidationCodeExpiryMilliseconds = 2 * 24 * 60 * 60 * 1000;
	public static final long emailChangeValidationExpiryMilliseconds = 2 * 60 * 60 * 1000;
	public static final long passwordResetExpiryMilliseconds = 2 * 60 * 60 * 1000;

	public static final String getCookie(HttpServletRequest request, String cookieName) {
		String cookieValue = "";
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					cookieValue = cookie.getValue();
				}
			}
		}
		
		return cookieValue;
	}

	public static final String encryptPassword(String plainTextPassword) {
		String next = MD5(plainTextPassword);

		String path = stuff.ajaxResult.class.getClassLoader().getResource("").getPath();
		String fileName = path.split("WEB-INF/classes/")[0] + "string";

		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(fileName));
		} catch (IOException exception) {
			encoded = new byte[] {};
		}

		String string = new String(encoded, StandardCharsets.UTF_8);

		return MD5(next + string);
	}

	private static final String MD5(String passwordToHash) {
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(passwordToHash.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return generatedPassword;
	}
	
	public final static String generateEmailValidationCode() {
		int codeLength = 32;
		boolean codeUseLetters = true;
		boolean codeUseNumbers = false;
		return RandomStringUtils.random(codeLength, codeUseLetters, codeUseNumbers);
	}
	
	public final static String generateEmailChangeValidationCode() {
		int codeLength = 32;
		boolean codeUseLetters = true;
		boolean codeUseNumbers = false;
		return RandomStringUtils.random(codeLength, codeUseLetters, codeUseNumbers);
	}
	
	public final static String generatePasswordResetCode() {
		int codeLength = 32;
		boolean codeUseLetters = true;
		boolean codeUseNumbers = false;
		return RandomStringUtils.random(codeLength, codeUseLetters, codeUseNumbers);
	}
	
	public final static String dateToString(Date date) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");  
		return df.format(date);
	}
	
	public final static String generateIV() {
		int ivLength = 16;
		boolean ivUseLetters = true;
		boolean ivUseNumbers = false;
		
		return RandomStringUtils.random(ivLength, ivUseLetters, ivUseNumbers);
	}
	
	public final static String generateKey() {
		int keyLength = 32;
		boolean keyUseLetters = true;
		boolean keyUseNumbers = false;
		
		return RandomStringUtils.random(keyLength, keyUseLetters, keyUseNumbers);
	}
}
