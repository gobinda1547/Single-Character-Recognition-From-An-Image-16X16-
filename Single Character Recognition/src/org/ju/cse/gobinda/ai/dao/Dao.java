package org.ju.cse.gobinda.ai.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.DeleteDbFiles;

public class Dao {

	public static String selectNameWhereDataIs(String data) {
		String ans = "Not Found!";
		try {
			Connection conn = getDatabaseConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT name FROM Data where data='" + data+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				ans = rs.getString(1);
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ans;
	}

	public static void insertWithPreparedStatement(String name, String value) {

		try {
			Connection connection = getDatabaseConnection();
			PreparedStatement insertPreparedStatement = null;
			String InsertQuery = "INSERT INTO Data (name,data) values (?,?)";

			connection.setAutoCommit(false);

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setString(1, name);
			insertPreparedStatement.setString(2, value);

			insertPreparedStatement.executeUpdate();
			insertPreparedStatement.close();

			connection.commit();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createTable() {

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("CREATE TABLE IF NOT EXISTS Data(");
			sql.append("id INTEGER auto_increment, ");
			sql.append("name VARCHAR(10), ");
			sql.append("data VARCHAR(300), ");
			sql.append("PRIMARY KEY(id))");

			Connection conn = getDatabaseConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql.toString());
			System.out.println("Created table in given database...");

			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Connection getDatabaseConnection() {

		try {
			String dbUrl = "jdbc:h2:./test";
			String dbUserName = "";
			String dbPassword = "";
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void deleteDatabase() {
		try {
			DeleteDbFiles.execute(".", "test", true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			DeleteDbFiles.execute("~", "test", true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("done");
	}
}
