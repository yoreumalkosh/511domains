package db.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import db.db;
import db.sql.beans.client;
import db.sql.beans.domain;
import db.sql.beans.mail;
import stuff.ajaxResult;

public class mails {
	public ajaxResult addMail(mail _mail, domain _domain, client _client) {
		ajaxResult _ajaxResult = new ajaxResult();
		db _db = new db();

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

			_db.disconnect();
			_ajaxResult.insert(true, "", insertedRows > 0);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("addMail()");
			System.err.println(exception.toString());
		}

		return _ajaxResult;
	}

	public ajaxResult passwordMail(mail _mail) {
		ajaxResult _ajaxResult = new ajaxResult();
		_ajaxResult.insert(true, "", true);
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

			_db.disconnect();
			_ajaxResult.insert(true, "", jsonArray);
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

		return _ajaxResult;
	}
}
