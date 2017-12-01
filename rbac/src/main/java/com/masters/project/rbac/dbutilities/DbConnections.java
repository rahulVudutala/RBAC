/**
 * 
 */
package com.masters.project.rbac.dbutilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.masters.project.rbac.constants.UtilConstants;

/**
 * @author rahul.vudutala
 *
 */
public class DbConnections {
	public static Connection createDbConnection() throws SQLException, ClassNotFoundException {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(UtilConstants.URL + UtilConstants.DB, UtilConstants.USER,
					UtilConstants.PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void closeConnection(Connection connection) {
		if (connection != null)
			try {
				connection.close();
				System.out.println("Connection Closed");
			} catch (SQLException e) {
				System.out.println(e.getStackTrace());
			}
	}
}
