package db.sql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import db.db;
import db.sql.beans.client;
import db.sql.beans.session;
import stuff.ajaxResult;

public class sessions {
	public ajaxResult createSession(session _session, client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "INSERT INTO haa.sessions\n";
			sql += "(sessionCode, dateTimeCreated, dateTimeExpired, dateTimeModified, isActive, isInfinite, clientId)\n";
			sql += "VALUES(?, ?, ?, ?, ?, ?, ?);\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _session.getSessionCode());
			preparedStatement.setTimestamp(2, new Timestamp(_session.getDateTimeCreated().getTime()));
			preparedStatement.setTimestamp(3, new Timestamp(_session.getDateTimeExpired().getTime()));
			preparedStatement.setTimestamp(4, new Timestamp(_session.getDateTimeModified().getTime()));
			preparedStatement.setBoolean(5, true);
			preparedStatement.setBoolean(6, _session.isInfinite());
			preparedStatement.setLong(7, _client.getClientId());
			
			_db.preparedUpdate(preparedStatement);

			_ajaxResult.success();
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("createSession()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return new ajaxResult();
	}

	public session searchSession(session _session) {
		session _sessionResult = new session();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT sessionId, sessionCode, dateTimeCreated, dateTimeExpired, dateTimeModified, isActive, isInfinite, clientId\n";
			sql += "FROM haa.sessions\n";
			sql += "WHERE sessionCode = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _session.getSessionCode());

			ResultSet resultSet = _db.preparedQuery(preparedStatement);
			if (resultSet.next()) {
				_sessionResult.setSessionId(resultSet.getLong("sessionId"));
				_sessionResult.setSessionCode(resultSet.getString("sessionCode"));
				_sessionResult.setDateTimeCreated(resultSet.getDate("dateTimeCreated"));
				_sessionResult.setDateTimeExpired(resultSet.getDate("dateTimeExpired"));
				_sessionResult.setDateTimeModified(resultSet.getDate("dateTimeModified"));
				_sessionResult.setActive(resultSet.getBoolean("isActive"));
				_sessionResult.setInfinite(resultSet.getBoolean("isInfinite"));
				_sessionResult.setClientId(resultSet.getLong("clientId"));
			}

			String sql2 = "";
			sql2 += "UPDATE haa.sessions\n";
			sql2 += "SET dateTimeModified=?\n";
			sql2 += "WHERE sessionCode=?;\n";

			PreparedStatement preparedStatement1 = _db.createPreparedStatement(sql2);
			preparedStatement1.setTimestamp(1, new Timestamp(new java.util.Date().getTime()));
			preparedStatement1.setString(2, _session.getSessionCode());

			_db.preparedUpdate(preparedStatement1);
			
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("searchSession()");
			System.err.println(exception.toString());
		}

		return _sessionResult;
	}

	public ajaxResult deactivateSession(session _session) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "UPDATE haa.sessions\n";
			sql += "SET dateTimeExpired=?, isActive=?\n";
			sql += "WHERE sessionCode=?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setTimestamp(1, new Timestamp(new java.util.Date().getTime()));
			preparedStatement.setBoolean(2, false);
			preparedStatement.setString(3, _session.getSessionCode());

			_db.preparedUpdate(preparedStatement);

			_ajaxResult.success();
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("deactivateSession()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return new ajaxResult();
	}
}
