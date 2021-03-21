package db.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import db.db;
import db.sql.beans.client;
import db.sql.beans.message;
import stuff.ajaxResult;

public class messages {
	public ajaxResult insertMessage(message _message) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "INSERT INTO haa.messages\n";
			sql += "(messageText, dateTimeCreated, subject, clientId)\n";
			sql += "VALUES(?, ?, ?, ?);\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _message.getMessageText());
			preparedStatement.setTimestamp(2, new Timestamp(_message.getDateTimeCreated().getTime()));
			preparedStatement.setString(3, _message.getSubject());
			preparedStatement.setLong(4, _message.getClientId());

			_db.preparedUpdate(preparedStatement);

			_ajaxResult.success();
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("insertMessage()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}
}
