package db.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import db.db;
import db.sql.beans.client;
import db.sql.beans.dkim;
import db.sql.beans.domain;
import db.sql.beans.message;
import stuff.ajaxResult;

public class dkims {
	public ajaxResult insertDKIM(dkim _dkim) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "INSERT INTO haa.dkims\n";
			sql += "(domainId, selector)\n";
			sql += "VALUES(?, ?);\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setLong(1, _dkim.getDomainId());
			preparedStatement.setString(2, _dkim.getSelector());

			int insertedRows = _db.preparedUpdate(preparedStatement);

			_ajaxResult.insert(true, "", insertedRows > 0);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("insertDKIM()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult deleteDKIM(dkim _dkim) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "DELETE FROM haa.dkims\n";
			sql += "WHERE domainId=? AND selector=?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setLong(1, _dkim.getDomainId());
			preparedStatement.setString(2, _dkim.getSelector());

			_db.preparedUpdate(preparedStatement);

			_ajaxResult.success();
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("deleteDKIM()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult searchDKIMs(domain _domain, client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT dkimId, domainId, selector\n";
			sql += "FROM haa.dkims\n";
			sql += "WHERE domainId = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setLong(1, _domain.getDomainId());

			JSONArray jsonArray = new JSONArray();
			ResultSet resultSet = _db.preparedQuery(preparedStatement);
			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("dkimId", resultSet.getLong("dkimId"));
				jsonObject.put("domainId", resultSet.getLong("domainId"));
				jsonObject.put("selector", resultSet.getString("selector"));
				jsonObject.put("value", stuff.dkim.readDKIM(_domain.getDomainName(), resultSet.getString("selector")));
				
				jsonArray.add(jsonObject);
			}

			_ajaxResult.insert(true, "", jsonArray);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("deleteDKIM()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}
}
