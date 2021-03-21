package stuff;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;

import org.apache.commons.lang3.StringUtils;

import db.db;

public class dkim {
	public static void write() {
		db _db = new db();

		String keys = "";
		String signs = "";

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT dkims.domainId, domains.domainName, dkims.selector FROM haa.dkims\n";
			sql += "INNER JOIN domains ON dkims.domainId = domains.domainId;";
			ResultSet resultSet = _db.query(sql);

			while (resultSet.next()) {
				String domainName = resultSet.getString("domainName");
				String selector = resultSet.getString("selector");
				keys += selector + "._domainkey." + domainName + " " + domainName + ":" + selector + ":/etc/mail/dkim-keys/"
						+ domainName + "/" + selector + ".private\n";
				signs += "*@" + domainName + " " + selector + "._domainkey." + domainName + "\n";
			}

			_db.disconnect();
		} catch (Exception exception) {
			System.err.println("SQLException");
			System.err.println("dkim");
			System.err.println(exception.toString());
		}

		try {
			BufferedWriter keysWriter = new BufferedWriter(new FileWriter("/etc/mail/dkim.key"));
			keysWriter.write(keys);
			keysWriter.close();

			BufferedWriter signsWriter = new BufferedWriter(new FileWriter("/etc/mail/dkim.sign"));
			signsWriter.write(signs);
			signsWriter.close();
		} catch (IOException ioException) {

		}
	}

	public static void generate(String domainName, String selector) {
		String cmd = "/root/dkim.generate.sh";
		ProcessBuilder pb = new ProcessBuilder(cmd, domainName, selector);
		try {
			Process process = pb.start();
		} catch (IOException ioException) {

		}
	}

	public static void restart() {
		String cmd = "/root/dkim.restart.sh";
		ProcessBuilder pb = new ProcessBuilder(cmd);
		try {
			Process process = pb.start();
		} catch (IOException ioException) {

		}
	}

	/*
	 * public static String readDKIM(String domainName) { String result = ""; String
	 * fileText = "";
	 * 
	 * try { fileText = new
	 * String(Files.readAllBytes(Paths.get("/etc/mail/dkim-keys/" + domainName +
	 * "/default.txt"))); } catch (IOException ioException) {
	 * 
	 * }
	 * 
	 * String[] valuesInQuotes = StringUtils.substringsBetween(fileText, "\"",
	 * "\"");
	 * 
	 * for (int i = 0; i < valuesInQuotes.length; i++) { result +=
	 * valuesInQuotes[i]; }
	 * 
	 * result = result.replace("\n", "");
	 * 
	 * return result; }
	 */

	public static String readDKIM(String domainName, String selector) {
		String result = "";
		String fileText = "";

		try {
			fileText = new String(
					Files.readAllBytes(Paths.get("/etc/mail/dkim-keys/" + domainName + "/" + selector + ".txt")));
		} catch (IOException ioException) {

		}

		String[] valuesInQuotes = StringUtils.substringsBetween(fileText, "\"", "\"");

		for (int i = 0; i < valuesInQuotes.length; i++) {
			result += valuesInQuotes[i];
		}

		result = result.replace("\n", "");

		return result;
	}
}
