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
import stuff.ajaxResult;

public class domains {
	public ajaxResult addDomain(domain _domain, client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "INSERT INTO haa.domains\n";
			sql += "(clientId, domainName, isActive1, isActive2, isActive3, dateTimeCreated, dateTimeModified, isValidated)\n";
			sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?);\n";

			Timestamp now = new Timestamp(new java.util.Date().getTime());

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setLong(1, _client.getClientId());
			preparedStatement.setString(2, _domain.getDomainName());
			preparedStatement.setBoolean(3, true);
			preparedStatement.setBoolean(4, true);
			preparedStatement.setBoolean(5, true);
			preparedStatement.setTimestamp(6, now);
			preparedStatement.setTimestamp(7, now);
			preparedStatement.setBoolean(8, false);

			int rowsNumber = _db.preparedUpdate(preparedStatement);

			_ajaxResult.insert(true, "", rowsNumber > 0);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("addDomain()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult validateDomain(domain _domain) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "UPDATE haa.domains\n";
			sql += "SET dateTimeModified=?, isValidated=?\n";
			sql += "WHERE domainId=?;\n";
			Timestamp now = new Timestamp(new java.util.Date().getTime());

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setTimestamp(1, now);
			preparedStatement.setBoolean(2, _domain.isValidated());
			preparedStatement.setLong(3, _domain.getDomainId());

			_db.preparedUpdate(preparedStatement);

			_ajaxResult.insert(true, "", true);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("validateDomain()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult deleteDomain(domain _domain) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "DELETE FROM haa.domains\n";
			sql += "WHERE domainId=?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setLong(1, _domain.getDomainId());

			_db.preparedUpdate(preparedStatement);

			_ajaxResult.insert(true, "", true);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("deleteDomain()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult getByDomainName(domain _domain, client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT domainId, clientId, domainName, isActive1, isActive2, isActive3, dateTimeCreated, dateTimeModified, isValidated\n";
			sql += "FROM haa.domains\n";
			sql += "WHERE domainName = ? AND clientId = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _domain.getDomainName());
			preparedStatement.setLong(2, _client.getClientId());

			ResultSet resultSet = _db.preparedQuery(preparedStatement);
			JSONObject jsonObject = new JSONObject();
			if (resultSet.next()) {
				jsonObject.put("domainId", resultSet.getLong("domainId"));
				jsonObject.put("clientId", resultSet.getLong("clientId"));
				jsonObject.put("domainName", resultSet.getString("domainName"));
				jsonObject.put("isActive1", resultSet.getBoolean("isActive1"));
				jsonObject.put("isActive2", resultSet.getBoolean("isActive2"));
				jsonObject.put("isActive3", resultSet.getBoolean("isActive3"));
				jsonObject.put("isValidated", resultSet.getBoolean("isValidated"));
			}

			_ajaxResult.insert(true, "", jsonObject);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("getByDomainName()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public domain getObjectDomainName(domain _domain, client _client) {
		domain _dbDomain = new domain();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT domainId, clientId, domainName, isActive1, isActive2, isActive3, dateTimeCreated, dateTimeModified, isValidated\n";
			sql += "FROM haa.domains\n";
			sql += "WHERE domainName = ? AND clientId = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _domain.getDomainName());
			preparedStatement.setLong(2, _client.getClientId());

			ResultSet resultSet = _db.preparedQuery(preparedStatement);
			if (resultSet.next()) {
				_dbDomain.setActive1(resultSet.getBoolean("isActive1"));
				_dbDomain.setActive2(resultSet.getBoolean("isActive2"));
				_dbDomain.setActive3(resultSet.getBoolean("isActive3"));
				_dbDomain.setClientId(resultSet.getLong("clientId"));
				_dbDomain.setDomainId(resultSet.getLong("domainId"));
				_dbDomain.setDomainName(resultSet.getString("domainName"));
				_dbDomain.setValidated(resultSet.getBoolean("isValidated"));
			}

			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("getObjectDomainName()");
			System.err.println(exception.toString());
		}

		return _dbDomain;
	}

	public ajaxResult getByDomainId(domain _domain, client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT domainId, clientId, domainName, isActive1, isActive2, isActive3, dateTimeCreated, dateTimeModified, isValidated\n";
			sql += "FROM haa.domains\n";
			sql += "WHERE domainId = ? AND clientId = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setLong(1, _domain.getDomainId());
			preparedStatement.setLong(2, _client.getClientId());

			ResultSet resultSet = _db.preparedQuery(preparedStatement);
			JSONObject jsonObject = new JSONObject();
			if (resultSet.next()) {
				jsonObject.put("domainId", resultSet.getLong("domainId"));
				jsonObject.put("clientId", resultSet.getLong("clientId"));
				jsonObject.put("domainName", resultSet.getString("domainName"));
				jsonObject.put("isActive1", resultSet.getBoolean("isActive1"));
				jsonObject.put("isActive2", resultSet.getBoolean("isActive2"));
				jsonObject.put("isActive3", resultSet.getBoolean("isActive3"));
				jsonObject.put("isValidated", resultSet.getBoolean("isValidated"));
			}

			_ajaxResult.insert(true, "", jsonObject);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("getByDomainId()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult searchDomains(client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT domainId, clientId, domainName, isActive1, isActive2, isActive3, dateTimeCreated, dateTimeModified, isValidated\n";
			sql += "FROM haa.domains\n";
			sql += "WHERE clientId = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setLong(1, _client.getClientId());

			ResultSet resultSet = _db.preparedQuery(preparedStatement);
			JSONArray jsonArray = new JSONArray();
			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				
				jsonObject.put("domainId", resultSet.getLong("domainId"));
				jsonObject.put("clientId", resultSet.getLong("clientId"));
				jsonObject.put("domainName", resultSet.getString("domainName"));
				jsonObject.put("isActive1", resultSet.getBoolean("isActive1"));
				jsonObject.put("isActive2", resultSet.getBoolean("isActive2"));
				jsonObject.put("isActive3", resultSet.getBoolean("isActive3"));
				jsonObject.put("isValidated", resultSet.getBoolean("isValidated"));
				
				jsonArray.add(jsonObject);
			}

			_ajaxResult.insert(true, "", jsonArray);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("searchDomains()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}
}
