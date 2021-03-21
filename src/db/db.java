package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class db {
	private Connection connection;

	public ResultSet query(String sql) {
		ResultSet resultSet = null;
		
		try {
			Statement statement = connection.createStatement();
			
			resultSet = statement.executeQuery(sql);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("query()");
			System.err.println(exception.toString());
		}
		
		return resultSet;
	}

	public PreparedStatement createPreparedStatement(String sql) {
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("createPreparedStatement()");
			System.err.println(exception.toString());
		}
		
		return preparedStatement;
	}

	public ResultSet preparedQuery(PreparedStatement preparedStatement) {
		ResultSet resultSet = null;
		
		try {
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("preparedQuery()");
			System.err.println(exception.toString());
		}
		
		return resultSet;
	}

	public int preparedUpdate(PreparedStatement preparedStatement) {
		int rowsNumber = 0;
		
		try {
			rowsNumber = preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("preparedUpdate()");
			System.err.println(exception.toString());
		}
		
		return rowsNumber;
	}

	public void connect()  {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/haa?useUnicode=yes&characterEncoding=UTF-8", "haa", "password");
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("connect()");
			System.err.println(exception.toString());
		} catch (ClassNotFoundException classNotFoundException) {
			System.err.println("ClassNotFoundException");
			System.err.println("connect()");
			System.err.println(classNotFoundException.toString());
		}
	}

	public void disconnect() throws SQLException {
		try {
			connection.close();
		} catch (SQLException exception) {
			System.err.println("SQLException");
			System.err.println("disconnect()");
			System.err.println(exception.toString());
		}
	}
}
