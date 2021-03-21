package stuff;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class aes {
	private static String iv = "a5583a92f6fb19bd";
	private static String key = "99a1d138844d0a80693d19a4ce61c367";

	public static String encrypt(String string) {
		try {
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

			return Base64.getEncoder().encodeToString(cipher.doFinal(string.getBytes("UTF-8")));
		} catch (Exception exception) {
			return "";
		}
	}

	public static String decrypt(String string) {
		try {
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);

			return new String(cipher.doFinal(Base64.getDecoder().decode(string)));			
		} catch (Exception exception) {
			return "";
		}
	}
	
	public static String encrypt(String string, byte[] _iv, byte[] _key) {
		try {
			IvParameterSpec ivspec = new IvParameterSpec(_iv);
			SecretKeySpec secretKey = new SecretKeySpec(_key, "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

			return Base64.getEncoder().encodeToString(cipher.doFinal(string.getBytes("UTF-8")));
		} catch (Exception exception) {
			return "";
		}
	}

	public static String decrypt(String string, byte[] _iv, byte[] _key) {
		try {
			IvParameterSpec ivspec = new IvParameterSpec(_iv);
			SecretKeySpec secretKey = new SecretKeySpec(_key, "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);

			return new String(cipher.doFinal(Base64.getDecoder().decode(string)));			
		} catch (Exception exception) {
			return "";
		}
	}
}
