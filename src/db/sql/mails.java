package db.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import db.db;
import db.serverDb;
import db.sql.beans.client;
import db.sql.beans.domain;
import db.sql.beans.mail;
import stuff.ajaxResult;
import stuff.dkim;

public class mails {
	public ajaxResult addMail(mail _mail, domain _domain, client _client) {
		ajaxResult _ajaxResult = new ajaxResult();
		db _db = new db();
		// long mailId = 0L;

		try {
			_db.connect();

			String sql = "";
			sql += "INSERT INTO haa.mails\n";
			sql += "(name, domainId, clientId, isEnabled, dateTimeCreated, isActive1, isActive2, isActive3)\n";
			sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?);\n";

			Timestamp now = new Timestamp(new java.util.Date().getTime());
			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _mail.getName());
			preparedStatement.setLong(2, _domain.getDomainId());
			preparedStatement.setLong(3, _client.getClientId());
			preparedStatement.setBoolean(4, true);
			preparedStatement.setTimestamp(5, now);
			preparedStatement.setBoolean(6, true);
			preparedStatement.setBoolean(7, true);
			preparedStatement.setBoolean(8, true);

			int insertedRows = _db.preparedUpdate(preparedStatement);
			/*
			 * String sql2 =
			 * "SELECT mailId FROM haa.mails WHERE `name` = ? AND domainId = ?;";
			 * PreparedStatement preparedStatement2 = _db.createPreparedStatement(sql2);
			 * ResultSet resultSet2 = _db.preparedQuery(preparedStatement2); if
			 * (resultSet2.next()) { mailId = resultSet2.getLong("mailId"); }
			 */

			_db.disconnect();
			_ajaxResult.insert(true, "", insertedRows > 0);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("addMail()");
			System.err.println(exception.toString());
		}

		serverDb _serverDb = new serverDb();

		try {
			_serverDb.connect();

			String sql = "";
			sql += "INSERT INTO servermail.virtual_users\n";
			sql += "(password, email, isActive, domain_name)\n";
			sql += "VALUES(ENCRYPT(?, CONCAT('$6$', SUBSTRING(SHA(RAND()), -16))), ?, ?, ?);\n";

			Timestamp now = new Timestamp(new java.util.Date().getTime());
			PreparedStatement preparedStatement = _serverDb.createPreparedStatement(sql);
			// preparedStatement.setLong(1, mailId);
			preparedStatement.setString(1, "");
			preparedStatement.setString(2, _mail.getName() + "@" + _domain.getDomainName());
			preparedStatement.setBoolean(3, true);
			preparedStatement.setString(4, _domain.getDomainName());

			int insertedRows = _serverDb.preparedUpdate(preparedStatement);

			_serverDb.disconnect();
			//_ajaxResult.insert(true, "", insertedRows > 0);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("addMail()");
			System.err.println(exception.toString());
		}

		return _ajaxResult;
	}

	public ajaxResult passwordMail(mail _mail, domain _domain) {
		ajaxResult _ajaxResult = new ajaxResult();
		serverDb _serverDb = new serverDb();

		try {
			_serverDb.connect();

			String sql = "";
			sql += "UPDATE servermail.virtual_users\n";
			sql += "SET password=ENCRYPT(?, CONCAT('$6$', SUBSTRING(SHA(RAND()), -16)))\n";
			sql += "WHERE email = ? AND domain_name = ?;\n";

			Timestamp now = new Timestamp(new java.util.Date().getTime());
			PreparedStatement preparedStatement = _serverDb.createPreparedStatement(sql);
			preparedStatement.setString(1, _mail.getPassword());
			preparedStatement.setString(2, _mail.getName() + "@" + _domain.getDomainName());
			preparedStatement.setString(3, _domain.getDomainName());

			int insertedRows = _serverDb.preparedUpdate(preparedStatement);

			_serverDb.disconnect();
			_ajaxResult.insert(true, "", insertedRows > 0);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("passwordMail()");
			System.err.println(exception.toString());
		}

		_ajaxResult.insert(true, "", true);
		return _ajaxResult;
	}

	public ajaxResult mailValidate(client _client, domain _domain, mail _mail) {
		ajaxResult _ajaxResult = new ajaxResult();
		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT mailId, name, domainId, clientId, isEnabled, dateTimeCreated, isActive1, isActive2, isActive3\n";
			sql += "FROM haa.mails\n";
			sql += "WHERE name = ? AND domainId = ? AND clientId = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _mail.getName());
			preparedStatement.setLong(2, _domain.getDomainId());
			preparedStatement.setLong(3, _client.getClientId());

			boolean isValid = _db.preparedQuery(preparedStatement).next();

			_db.disconnect();
			_ajaxResult.insert(true, "", isValid);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("passwordMail()");
			System.err.println(exception.toString());
		}

		return _ajaxResult;
	}

	public ajaxResult setIsEnabledMail(mail _mail, domain _domain, client _client) {
		ajaxResult _ajaxResult = new ajaxResult();
		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "UPDATE haa.mails\n";
			sql += "SET isEnabled=CASE WHEN isEnabled = 1 THEN 0 ELSE 1 END\n";
			sql += "WHERE name = ? AND domainId = ? AND clientId = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _mail.getName());
			preparedStatement.setLong(2, _domain.getDomainId());
			preparedStatement.setLong(3, _client.getClientId());

			_db.preparedUpdate(preparedStatement);

			_db.disconnect();
			_ajaxResult.insert(true, "", true);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("setIsEnabledMail()");
			System.err.println(exception.toString());
		}

		serverDb _serverDb = new serverDb();

		try {
			_serverDb.connect();

			String sql = "";
			sql += "UPDATE servermail.virtual_users\n";
			sql += "SET isActive=CASE WHEN isActive = b'1' THEN b'0' ELSE b'1' END\n";
			sql += "WHERE email = ?;\n";

			Timestamp now = new Timestamp(new java.util.Date().getTime());
			PreparedStatement preparedStatement = _serverDb.createPreparedStatement(sql);
			preparedStatement.setString(1, _mail.getName());

			int insertedRows = _serverDb.preparedUpdate(preparedStatement);

			_serverDb.disconnect();
			_ajaxResult.insert(true, "", insertedRows > 0);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("setIsEnabledMail()");
			System.err.println(exception.toString());
		}

		return _ajaxResult;
	}

	public ajaxResult searchMails(domain _domain, client _client) {
		ajaxResult _ajaxResult = new ajaxResult();
		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT mailId, name, domainId, clientId, isEnabled, dateTimeCreated, isActive1, isActive2, isActive3\n";
			sql += "FROM haa.mails\n";
			sql += "WHERE domainId = ? AND clientId = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setLong(1, _domain.getDomainId());
			preparedStatement.setLong(2, _client.getClientId());

			JSONArray jsonArray = new JSONArray();
			ResultSet resultSet = _db.preparedQuery(preparedStatement);
			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("mailId", resultSet.getLong("mailId"));
				jsonObject.put("name", resultSet.getString("name"));
				jsonObject.put("domainId", resultSet.getLong("domainId"));
				jsonObject.put("clientId", resultSet.getLong("clientId"));
				jsonObject.put("isEnabled", resultSet.getBoolean("isEnabled"));
				jsonObject.put("domainName", _domain.getDomainName());
				jsonObject.put("isActive1", resultSet.getBoolean("isActive1"));
				jsonObject.put("isActive2", resultSet.getBoolean("isActive2"));
				jsonObject.put("isActive3", resultSet.getBoolean("isActive3"));

				jsonArray.add(jsonObject);
			}

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("array", jsonArray);
			// jsonObject.put("dkim", dkim.readDKIM(_domain.getDomainName()));

			_db.disconnect();
			_ajaxResult.insert(true, "", jsonObject);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("searchMails()");
			System.err.println(exception.toString());
		}

		return _ajaxResult;
	}

	public ajaxResult getByNameMail(mail _mail, domain _domain, client _client) {
		ajaxResult _ajaxResult = new ajaxResult();
		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT mailId, name, domainId, clientId, isEnabled, dateTimeCreated, isActive1, isActive2, isActive3\n";
			sql += "FROM haa.mails\n";
			sql += "WHERE name = ? AND domainId = ? AND clientId = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _mail.getName());
			preparedStatement.setLong(2, _domain.getDomainId());
			preparedStatement.setLong(3, _client.getClientId());

			JSONObject jsonObject = new JSONObject();
			ResultSet resultSet = _db.preparedQuery(preparedStatement);
			if (resultSet.next()) {
				jsonObject.put("mailId", resultSet.getLong("mailId"));
				jsonObject.put("name", resultSet.getString("name"));
				jsonObject.put("domainId", resultSet.getLong("domainId"));
				jsonObject.put("clientId", resultSet.getLong("clientId"));
				jsonObject.put("isEnabled", resultSet.getBoolean("isEnabled"));
				jsonObject.put("isActive1", resultSet.getBoolean("isActive1"));
				jsonObject.put("isActive2", resultSet.getBoolean("isActive2"));
				jsonObject.put("isActive3", resultSet.getBoolean("isActive3"));
			}

			_db.disconnect();
			_ajaxResult.insert(true, "", jsonObject);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("getByNameMail()");
			System.err.println(exception.toString());
		}

		return _ajaxResult;
	}

	public ajaxResult getByIdMail(mail _mail, domain _domain, client _client) {
		ajaxResult _ajaxResult = new ajaxResult();
		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT mailId, name, domainId, clientId, isEnabled, dateTimeCreated, isActive1, isActive2, isActive3\n";
			sql += "FROM haa.mails\n";
			sql += "WHERE mailId = ? AND domainId = ? AND clientId = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setLong(1, _mail.getMailId());
			preparedStatement.setLong(2, _domain.getDomainId());
			preparedStatement.setLong(3, _client.getClientId());

			JSONObject jsonObject = new JSONObject();
			ResultSet resultSet = _db.preparedQuery(preparedStatement);
			if (resultSet.next()) {
				jsonObject.put("mailId", resultSet.getLong("mailId"));
				jsonObject.put("name", resultSet.getString("name"));
				jsonObject.put("domainId", resultSet.getLong("domainId"));
				jsonObject.put("clientId", resultSet.getLong("clientId"));
				jsonObject.put("isEnabled", resultSet.getBoolean("isEnabled"));
				jsonObject.put("isActive1", resultSet.getBoolean("isActive1"));
				jsonObject.put("isActive2", resultSet.getBoolean("isActive2"));
				jsonObject.put("isActive3", resultSet.getBoolean("isActive3"));
			}

			_db.disconnect();
			_ajaxResult.insert(true, "", jsonObject);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("getByIdMail()");
			System.err.println(exception.toString());
		}

		return _ajaxResult;
	}

	public ajaxResult deleteMail(mail _mail, domain _domain, client _client) {
		ajaxResult _ajaxResult = new ajaxResult();
		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "DELETE FROM haa.mails\n";
			sql += "WHERE name=? AND domainId = ? AND clientId = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _mail.getName());
			preparedStatement.setLong(2, _domain.getDomainId());
			preparedStatement.setLong(3, _client.getClientId());

			_db.preparedUpdate(preparedStatement);

			_db.disconnect();
			_ajaxResult.insert(true, "", true);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("deleteMail()");
			System.err.println(exception.toString());
		}

		serverDb _serverDb = new serverDb();

		try {
			_serverDb.connect();

			String sql = "";
			sql += "DELETE FROM servermail.virtual_users\n";
			sql += "WHERE email = ?;\n";

			Timestamp now = new Timestamp(new java.util.Date().getTime());
			PreparedStatement preparedStatement = _serverDb.createPreparedStatement(sql);
			preparedStatement.setString(1, _mail.getName() + "@" + _domain.getDomainName());

			int insertedRows = _serverDb.preparedUpdate(preparedStatement);

			_serverDb.disconnect();
			_ajaxResult.insert(true, "", insertedRows > 0);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("setIsEnabledMail()");
			System.err.println(exception.toString());
		}

		return _ajaxResult;
	}
}
