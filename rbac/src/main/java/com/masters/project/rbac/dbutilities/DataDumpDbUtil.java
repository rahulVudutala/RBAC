/**
 * 
 */
package com.masters.project.rbac.dbutilities;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author rahul.vudutala
 *
 */

public class DataDumpDbUtil {

	private Connection connection = null;
	private Statement statement = null;

	public DataDumpDbUtil(Connection c) {
		this.connection = c;
	}

	public void createTable(String tableName, String[] columnArray) throws ClassNotFoundException, SQLException {
		statement = connection.createStatement();
		StringBuilder sql = new StringBuilder();
		sql.append("create table ").append(tableName).append(" (");
		for (String name : columnArray) {
			if(name.trim().contains(" "))
				name = name.replaceAll("\\s","");
			sql.append(name).append(" varchar(255)").append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		System.out.println(sql.toString());
		statement.executeUpdate(sql.toString());
		System.out.println("Created table in given database...");
	}

	public void writeData(String tableName, String location) throws ClassNotFoundException, SQLException {
		ResultSet rscols;
		StringBuilder sql = new StringBuilder();
		DatabaseMetaData meta = connection.getMetaData();
		rscols = meta.getColumns("", "", tableName, "");
		sql.append("LOAD DATA LOCAL INFILE '").append(location).append("' INTO TABLE ").append(tableName)
				.append(" FIELDS TERMINATED BY ',' ESCAPED BY '\"'")
				.append(" LINES TERMINATED BY '\n' IGNORE 1 LINES (");

		while (rscols.next()) {
			sql.append(rscols.getString("COLUMN_NAME")).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		System.out.println(sql.toString());
		statement = connection.createStatement();
		statement.execute(sql.toString());
	}
	
	public String createTableName(String loc) {
		String[] locFolders = loc.split("/");
		return locFolders[locFolders.length-1].split("\\.")[0].toLowerCase();
	}
}