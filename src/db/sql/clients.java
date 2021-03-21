package db.sql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.simple.JSONObject;

import db.db;
import db.sql.beans.client;
import stuff.ajaxResult;

public class clients {
	public ajaxResult addClient(client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "INSERT INTO haa.clients\n";
			sql += "(fullName, email, gender, password1, password2, password3, organization, `role`, city, country, isValidated, isActive1, isActive2, isActive3, dateTimeCreated, dateTimeModified)\n";
			sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\n";

			Timestamp now = new Timestamp(new java.util.Date().getTime());

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _client.getFullName());
			preparedStatement.setString(2, _client.getEmail());
			preparedStatement.setString(3, _client.getGender());
			preparedStatement.setString(4, stuff.main.encryptPassword1(_client.getPassword()));
			preparedStatement.setString(5, stuff.main.encryptPassword2(_client.getPassword()));
			preparedStatement.setString(6, stuff.main.encryptPassword3(_client.getPassword()));
			preparedStatement.setString(7, _client.getOrganization());
			preparedStatement.setString(8, _client.getRole());
			preparedStatement.setString(9, _client.getCity());
			preparedStatement.setString(10, _client.getCountry());
			preparedStatement.setBoolean(11, false);
			preparedStatement.setBoolean(12, true);
			preparedStatement.setBoolean(13, true);
			preparedStatement.setBoolean(14, true);
			preparedStatement.setTimestamp(15, now);
			preparedStatement.setTimestamp(16, now);

			int rowsNumber = _db.preparedUpdate(preparedStatement);

			_ajaxResult.insert(true, "", rowsNumber > 0);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("addClient()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult validateClient(String validationCode) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			java.util.Date utilNow = new java.util.Date();
			
			String sql1 = "";
			sql1 += "SELECT clientId, dateTimeCreated, used FROM haa.emailValidation WHERE validationCode = ? AND used = ?;\n";
			PreparedStatement preparedStatement1 = _db.createPreparedStatement(sql1);
			preparedStatement1.setString(1, validationCode);
			preparedStatement1.setBoolean(2, false);
			long clientId = 0;
			Date dateTimeCreated = null;
			ResultSet resultSet1 = _db.preparedQuery(preparedStatement1);
			if (resultSet1.next()) {
				dateTimeCreated = resultSet1.getDate("dateTimeCreated");
				clientId = resultSet1.getLong("clientId");
			} else {
				_ajaxResult.insert(true, "", false);
				_db.disconnect();
				
				return _ajaxResult;
			}
			
			boolean notExpired = utilNow.getTime() - dateTimeCreated.getTime() < stuff.main.emailValidationCodeExpiryMilliseconds;
			if (!notExpired) {
				_ajaxResult.insert(true, "", false);
				_db.disconnect();
				
				return _ajaxResult;
			}
			
			String sql4 = "";
			sql4 += "UPDATE haa.emailValidation\n";
			sql4 += "SET used=?\n";
			sql4 += "WHERE validationCode=?;\n";

			PreparedStatement preparedStatement4 = _db.createPreparedStatement(sql4);
			preparedStatement4.setBoolean(1, true);
			preparedStatement4.setString(2, validationCode);

			_db.preparedUpdate(preparedStatement4);

			String sql3 = "";
			sql3 += "UPDATE haa.clients\n";
			sql3 += "SET isValidated=?, dateTimeModified=?\n";
			sql3 += "WHERE clientId=?;\n";
			
			PreparedStatement preparedStatement3 = _db.createPreparedStatement(sql3);
			preparedStatement3.setBoolean(1, true);
			preparedStatement3.setTimestamp(2, new Timestamp(utilNow.getTime()));
			preparedStatement3.setLong(3, clientId);

			_db.preparedUpdate(preparedStatement3);

			_ajaxResult.insert(true, "", true);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("validateClient()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult validatePasswordReset(String passwordResetCode, client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			java.util.Date utilNow = new java.util.Date();
			
			String sql1 = "";
			sql1 += "SELECT clientId, dateTimeCreated, isUsed FROM haa.passwordReset WHERE passwordResetCode = ? AND isUsed = ?;\n";
			PreparedStatement preparedStatement1 = _db.createPreparedStatement(sql1);
			preparedStatement1.setString(1, passwordResetCode);
			preparedStatement1.setBoolean(2, false);
			long clientId = 0;
			Date dateTimeCreated = null;
			ResultSet resultSet1 = _db.preparedQuery(preparedStatement1);
			if (resultSet1.next()) {
				dateTimeCreated = new Date(resultSet1.getTimestamp("dateTimeCreated").getTime());
				clientId = resultSet1.getLong("clientId");
			} else {
				_ajaxResult.insert(true, "", false);
				_db.disconnect();
				
				return _ajaxResult;
			}
			
			boolean notExpired = utilNow.getTime() - dateTimeCreated.getTime() < stuff.main.passwordResetExpiryMilliseconds;
			if (!notExpired) {
				_ajaxResult.insert(true, "", false);
				_db.disconnect();
				
				return _ajaxResult;
			}
			
			String sql4 = "";
			sql4 += "UPDATE haa.passwordReset\n";
			sql4 += "SET isUsed=?\n";
			sql4 += "WHERE passwordResetCode=?;\n";

			PreparedStatement preparedStatement4 = _db.createPreparedStatement(sql4);
			preparedStatement4.setBoolean(1, true);
			preparedStatement4.setString(2, passwordResetCode);

			_db.preparedUpdate(preparedStatement4);

			String sql3 = "";
			sql3 += "UPDATE haa.clients\n";
			sql3 += "SET `password1`=?, `password2`=?, `password3`=?, dateTimeModified=?\n";
			sql3 += "WHERE clientId=?;\n";
			
			PreparedStatement preparedStatement3 = _db.createPreparedStatement(sql3);
			preparedStatement3.setString(1, stuff.main.encryptPassword1(_client.getPassword()));
			preparedStatement3.setString(2, stuff.main.encryptPassword2(_client.getPassword()));
			preparedStatement3.setString(3, stuff.main.encryptPassword3(_client.getPassword()));
			preparedStatement3.setTimestamp(4, new Timestamp(utilNow.getTime()));
			preparedStatement3.setLong(5, clientId);

			_db.preparedUpdate(preparedStatement3);

			_ajaxResult.insert(true, "", true);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("validatePasswordReset()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult validateEmailChange(String emailChangeValidationCode) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			java.util.Date utilNow = new java.util.Date();
			
			String email = "";
			String sql1 = "";
			sql1 += "SELECT clientId, email, dateTimeCreated, isUsed FROM haa.emailChangeValidation WHERE emailChangeValidationCode = ? AND isUsed = ?;\n";
			PreparedStatement preparedStatement1 = _db.createPreparedStatement(sql1);
			preparedStatement1.setString(1, emailChangeValidationCode);
			preparedStatement1.setBoolean(2, false);
			long clientId = 0;
			Date dateTimeCreated = null;
			ResultSet resultSet1 = _db.preparedQuery(preparedStatement1);
			if (resultSet1.next()) {
				dateTimeCreated = new Date(resultSet1.getTimestamp("dateTimeCreated").getTime());
				clientId = resultSet1.getLong("clientId");
				email = resultSet1.getString("email");
			} else {
				_ajaxResult.insert(true, "", false);
				_db.disconnect();
				
				return _ajaxResult;
			}
			
			boolean notExpired = utilNow.getTime() - dateTimeCreated.getTime() < stuff.main.emailChangeValidationExpiryMilliseconds;
			if (!notExpired) {
				_ajaxResult.insert(true, "", false);
				_db.disconnect();
				
				return _ajaxResult;
			}
			
			String sql4 = "";
			sql4 += "UPDATE haa.emailChangeValidation\n";
			sql4 += "SET isUsed=?\n";
			sql4 += "WHERE emailChangeValidationCode=?;\n";

			PreparedStatement preparedStatement4 = _db.createPreparedStatement(sql4);
			preparedStatement4.setBoolean(1, true);
			preparedStatement4.setString(2, emailChangeValidationCode);

			_db.preparedUpdate(preparedStatement4);

			String sql3 = "";
			sql3 += "UPDATE haa.clients\n";
			sql3 += "SET email=?, dateTimeModified=?\n";
			sql3 += "WHERE clientId=?;\n";
			
			PreparedStatement preparedStatement3 = _db.createPreparedStatement(sql3);
			preparedStatement3.setString(1, email);
			preparedStatement3.setTimestamp(2, new Timestamp(utilNow.getTime()));
			preparedStatement3.setLong(3, clientId);

			_db.preparedUpdate(preparedStatement3);

			_ajaxResult.insert(true, "", true);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("validateEmailChange()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult sendValidationEmail(client _client) {
		return new ajaxResult();
	}

	/*
	 * public ajaxResult signIn(client _client) { return new ajaxResult(); }
	 * 
	 * public ajaxResult signOut(client _client) { return new ajaxResult(); }
	 */
	public ajaxResult modifyClient(client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "UPDATE haa.clients\n";
			sql += "SET fullName=?, gender=?, organization=?, `role`=?, city=?, country=?, dateTimeModified=?\n";
			sql += "WHERE clientId=?;\n";

			Timestamp now = new Timestamp(new java.util.Date().getTime());

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _client.getFullName());
			preparedStatement.setString(2, _client.getGender());
			preparedStatement.setString(3, _client.getOrganization());
			preparedStatement.setString(4, _client.getRole());
			preparedStatement.setString(5, _client.getCity());
			preparedStatement.setString(6, _client.getCountry());
			preparedStatement.setTimestamp(7, now);
			preparedStatement.setLong(8, _client.getClientId());

			_db.preparedUpdate(preparedStatement);

			_ajaxResult.insert(true, "", true);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("modifyClient()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult modifyEmail(client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "UPDATE haa.clients\n";
			sql += "SET email=?, dateTimeModified=?\n";
			sql += "WHERE clientId=?;\n";
			Timestamp now = new Timestamp(new java.util.Date().getTime());

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _client.getEmail());
			preparedStatement.setTimestamp(2, now);
			preparedStatement.setLong(3, _client.getClientId());

			_db.preparedUpdate(preparedStatement);

			_ajaxResult.success();
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("modifyEmail()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult modifyPassword(client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "UPDATE haa.clients\n";
			sql += "SET password1=?, password2=?, password3=?, dateTimeModified=?\n";
			sql += "WHERE clientId=?;\n";
			Timestamp now = new Timestamp(new java.util.Date().getTime());

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, stuff.main.encryptPassword1(_client.getPassword()));
			preparedStatement.setString(2, stuff.main.encryptPassword2(_client.getPassword()));
			preparedStatement.setString(3, stuff.main.encryptPassword3(_client.getPassword()));
			preparedStatement.setTimestamp(4, now);
			preparedStatement.setLong(5, _client.getClientId());

			_db.preparedUpdate(preparedStatement);

			_ajaxResult.insert(true, "", true);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("modifyPassword()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult deactivateClient(client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "UPDATE haa.clients\n";
			sql += "SET isActive1=?, dateTimeModified=?\n";
			sql += "WHERE clientId=?;\n";
			Timestamp now = new Timestamp(new java.util.Date().getTime());

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setBoolean(1, false);
			preparedStatement.setTimestamp(2, now);
			preparedStatement.setLong(3, _client.getClientId());

			_db.preparedUpdate(preparedStatement);

			_ajaxResult.success();
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("deactivateClient()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public ajaxResult reactivateClient(client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "UPDATE haa.clients\n";
			sql += "SET isActive1=?, dateTimeModified=?\n";
			sql += "WHERE clientId=?;\n";
			Timestamp now = new Timestamp(new java.util.Date().getTime());

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setTimestamp(2, now);
			preparedStatement.setLong(3, _client.getClientId());

			_db.preparedUpdate(preparedStatement);

			_ajaxResult.success();
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("reactivateClient()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public boolean checkPassword(client _client) {
		boolean result = false;

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT email, password1, password2, password3 FROM haa.clients WHERE email = ? AND password1 = ? AND password2 = ? AND password3 = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _client.getEmail());
			preparedStatement.setString(2, stuff.main.encryptPassword1(_client.getPassword()));
			preparedStatement.setString(3, stuff.main.encryptPassword2(_client.getPassword()));
			preparedStatement.setString(4, stuff.main.encryptPassword3(_client.getPassword()));

			result = _db.preparedQuery(preparedStatement).next();
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("checkPassword()");
			System.err.println(exception.toString());
		}

		return result;
	}

	public ajaxResult searchClientJSONObjectByEmail(client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT clientId, fullName, email, gender, organization, `role`, city, country, isValidated, isActive1, isActive2, isActive3, dateTimeCreated, dateTimeModified\n";
			sql += "FROM haa.clients\n";
			sql += "WHERE email = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _client.getEmail());

			JSONObject jsonObject = new JSONObject();
			ResultSet resultSet = _db.preparedQuery(preparedStatement);
			if (resultSet.next()) {
				jsonObject.put("clientId", resultSet.getLong("clientId"));
				jsonObject.put("fullName", resultSet.getString("fullName"));
				jsonObject.put("email", resultSet.getString("email"));
				jsonObject.put("gender", resultSet.getString("gender"));
				jsonObject.put("organization", resultSet.getString("organization"));
				jsonObject.put("role", resultSet.getString("role"));
				jsonObject.put("city", resultSet.getString("city"));
				jsonObject.put("country", resultSet.getString("country"));
			}

			_ajaxResult.insert(true, "", jsonObject);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("searchClientJSONObjectByEmail()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public client searchClientBeanByEmail(client _client) {
		client result = new client();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT clientId, fullName, email, gender, organization, `role`, city, country, isValidated, isActive1, isActive2, isActive3, dateTimeCreated, dateTimeModified\n";
			sql += "FROM haa.clients\n";
			sql += "WHERE email = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _client.getEmail());

			ResultSet resultSet = _db.preparedQuery(preparedStatement);
			if (resultSet.next()) {
				result.setActive1(resultSet.getBoolean("isActive1"));
				result.setActive2(resultSet.getBoolean("isActive2"));
				result.setActive3(resultSet.getBoolean("isActive3"));
				result.setCity(resultSet.getString("city"));
				result.setClientId(resultSet.getLong("clientId"));
				result.setCountry(resultSet.getString("country"));
				result.setDateTimeCreated(resultSet.getDate("dateTimeCreated"));
				result.setDateTimeModified(resultSet.getDate("dateTimeModified"));
				result.setEmail(resultSet.getString("email"));
				result.setFullName(resultSet.getString("fullName"));
				result.setGender(resultSet.getString("gender"));
				result.setOrganization(resultSet.getString("organization"));
				result.setRole(resultSet.getString("role"));
				result.setValidated(resultSet.getBoolean("isValidated"));
			}

			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("searchClientBeanByEmail()");
			System.err.println(exception.toString());
		}

		return result;
	}

	public ajaxResult searchClientJSONObjectByClientId(client _client) {
		ajaxResult _ajaxResult = new ajaxResult();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT clientId, fullName, email, gender, organization, `role`, city, country, isValidated, isActive1, isActive2, isActive3, dateTimeCreated, dateTimeModified\n";
			sql += "FROM haa.clients\n";
			sql += "WHERE clientId = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setLong(1, _client.getClientId());

			JSONObject jsonObject = new JSONObject();
			ResultSet resultSet = _db.preparedQuery(preparedStatement);
			if (resultSet.next()) {
				jsonObject.put("clientId", resultSet.getLong("clientId"));
				jsonObject.put("fullName", resultSet.getString("fullName"));
				jsonObject.put("email", resultSet.getString("email"));
				jsonObject.put("gender", resultSet.getString("gender"));
				jsonObject.put("organization", resultSet.getString("organization"));
				jsonObject.put("role", resultSet.getString("role"));
				jsonObject.put("city", resultSet.getString("city"));
				jsonObject.put("country", resultSet.getString("country"));
			}

			_ajaxResult.insert(true, "", jsonObject);
			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("searchClientJSONObjectByClientId()");
			System.err.println(exception.toString());

			_ajaxResult.exception();
		}

		return _ajaxResult;
	}

	public client searchClientBeanByClientId(client _client) {
		client result = new client();

		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "SELECT clientId, fullName, email, gender, organization, `role`, city, country, isValidated, isActive1, isActive2, isActive3, dateTimeCreated, dateTimeModified\n";
			sql += "FROM haa.clients\n";
			sql += "WHERE clientId = ?;\n";

			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setLong(1, _client.getClientId());

			ResultSet resultSet = _db.preparedQuery(preparedStatement);
			if (resultSet.next()) {
				result.setActive1(resultSet.getBoolean("isActive1"));
				result.setActive2(resultSet.getBoolean("isActive2"));
				result.setActive3(resultSet.getBoolean("isActive3"));
				result.setCity(resultSet.getString("city"));
				result.setClientId(resultSet.getLong("clientId"));
				result.setCountry(resultSet.getString("country"));
				result.setDateTimeCreated(resultSet.getDate("dateTimeCreated"));
				result.setDateTimeModified(resultSet.getDate("dateTimeModified"));
				result.setEmail(resultSet.getString("email"));
				result.setFullName(resultSet.getString("fullName"));
				result.setGender(resultSet.getString("gender"));
				result.setOrganization(resultSet.getString("organization"));
				result.setRole(resultSet.getString("role"));
				result.setValidated(resultSet.getBoolean("isValidated"));
			}

			_db.disconnect();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("searchClientBeanByClientId()");
			System.err.println(exception.toString());
		}

		return result;
	}

	public boolean createEmailValidationCode(String _emailValidationCode, client _client) {
		boolean booleanResult = false;
		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "INSERT INTO haa.emailValidation\n";
			sql += "(validationCode, dateTimeCreated, used, clientId)\n";
			sql += "VALUES(?, ?, ?, ?);\n";
			Timestamp now = new Timestamp(new java.util.Date().getTime());
			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _emailValidationCode);
			preparedStatement.setTimestamp(2, now);
			preparedStatement.setBoolean(3, false);
			preparedStatement.setLong(4, _client.getClientId());
			
			_db.preparedUpdate(preparedStatement);

			_db.disconnect();
			
			booleanResult = true;
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("createEmailValidationCode()");
			System.err.println(exception.toString());
		}

		return booleanResult;
	}

	public boolean createEmailChangeValidationCode(String _emailChangeValidationCode, client _client) {
		boolean booleanResult = false;
		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "INSERT INTO haa.emailChangeValidation\n";
			sql += "(emailChangeValidationCode, dateTimeCreated, isUsed, clientId, email)\n";
			sql += "VALUES(?, ?, ?, ?, ?);\n";
			Timestamp now = new Timestamp(new java.util.Date().getTime());
			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _emailChangeValidationCode);
			preparedStatement.setTimestamp(2, now);
			preparedStatement.setBoolean(3, false);
			preparedStatement.setLong(4, _client.getClientId());
			preparedStatement.setString(5, _client.getEmail());
			
			_db.preparedUpdate(preparedStatement);

			_db.disconnect();
			
			booleanResult = true;
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("createEmailChangeValidationCode()");
			System.err.println(exception.toString());
		}

		return booleanResult;
	}

	public boolean createPasswordResetCode(String _passwordResetCode, client _client) {
		boolean booleanResult = false;
		db _db = new db();

		try {
			_db.connect();

			String sql = "";
			sql += "INSERT INTO haa.passwordReset\n";
			sql += "(passwordResetCode, dateTimeCreated, isUsed, clientId)\n";
			sql += "VALUES(?, ?, ?, ?);\n";
			Timestamp now = new Timestamp(new java.util.Date().getTime());
			PreparedStatement preparedStatement = _db.createPreparedStatement(sql);
			preparedStatement.setString(1, _passwordResetCode);
			preparedStatement.setTimestamp(2, now);
			preparedStatement.setBoolean(3, false);
			preparedStatement.setLong(4, _client.getClientId());
			
			_db.preparedUpdate(preparedStatement);

			_db.disconnect();
			
			booleanResult = true;
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("createPasswordResetCode()");
			System.err.println(exception.toString());
		}

		return booleanResult;
	}
}
