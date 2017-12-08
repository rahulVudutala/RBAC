/**
 * 
 */
package com.masters.project.rbac.dbutilities;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.masters.project.rbac.constants.UtilConstants;

/**
 * @author rahul.vudutala
 *
 */
public class ListTablesDbUtil {
	private Connection connection;

	public ListTablesDbUtil() {
		try {
			this.connection = DbConnections.createDbConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> listAllTables() {
		List<String> tablesList = new ArrayList<String>();
		DatabaseMetaData metaData;
		try {
			metaData = connection.getMetaData();
			ResultSet resultSet = metaData.getTables(null, null, "%", null);
			while (resultSet.next()) {
				tablesList.add(resultSet.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tablesList;
	}
	
	public List<String> listAllTablesPartial() {
		List<String> tablesList = new ArrayList<String>();
		DatabaseMetaData metaData;
		try {
			metaData = connection.getMetaData();
			ResultSet resultSet = metaData.getTables(null, null, "%", null);
			while (resultSet.next()) {
				if(!UtilConstants.tableMetaData.contains(resultSet.getString(3)))
					tablesList.add(resultSet.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tablesList;
	}

	public List<List<String>> fetchTableData(String tableName) {
		List<List<String>> tableVal = new ArrayList<List<String>>();

		ResultSet rscols, rsdata;

		DatabaseMetaData meta;
		try {
			meta = connection.getMetaData();
			rscols = meta.getColumns("", "", tableName, "");

			List<String> header = new LinkedList<String>();
			List<String> headerAccess = new LinkedList<String>();
			while (rscols.next()) {
				String s = rscols.getString("COLUMN_NAME");
				header.add(s);
				if (UtilConstants.columnMetaData.contains(s)) {
					headerAccess.add("false");
				} else {
					headerAccess.add("true");
				}
			}
			tableVal.add(header);
			tableVal.add(headerAccess);
			rscols.beforeFirst();

			String tableData = "SELECT * FROM " + tableName;
			Statement statement;
			statement = connection.createStatement();
			rsdata = statement.executeQuery(tableData);
			while (rsdata.next()) {
				List<String> row = new ArrayList<String>();
				while (rscols.next()) {
					if (rscols.getString("TYPE_NAME").equals("VARCHAR")) {
						String data = rsdata.getString(rscols.getString("COLUMN_NAME"));
						row.add(data);
					} else {
						int data = rsdata.getInt(rscols.getString("COLUMN_NAME"));
						row.add(Integer.toString(data));
					}
				}
				rscols.beforeFirst();
				tableVal.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tableVal;
	}

	public List<List<String>> fetchTableDataPartial(String tableName) {
		List<List<String>> tableVal = new ArrayList<List<String>>();

		ResultSet rscols, rsdata;

		DatabaseMetaData meta;
		try {
			meta = connection.getMetaData();
			rscols = meta.getColumns("", "", tableName, "");
			List<String> header = new LinkedList<String>();
			List<String> headerAccess = new LinkedList<String>();
			while (rscols.next()) {
				String s = rscols.getString("COLUMN_NAME");
				header.add(s);
				if (UtilConstants.columnMetaData.contains(s)) {
					headerAccess.add("false");
				} else {
					headerAccess.add("true");
				}
			}
			tableVal.add(header);
			tableVal.add(headerAccess);
			rscols.beforeFirst();

			String tableData = "SELECT * FROM " + tableName;
			Statement statement;
			statement = connection.createStatement();
			rsdata = statement.executeQuery(tableData);
			while (rsdata.next()) {
				List<String> row = new ArrayList<String>();
				while (rscols.next()) {
					if (rscols.getString("TYPE_NAME").equals("VARCHAR")) {
						String data = rsdata.getString(rscols.getString("COLUMN_NAME"));
						if (UtilConstants.columnMetaData.contains(rscols.getString("COLUMN_NAME"))) {
							data = "****" + data.substring(data.length() - 4, data.length());
						}
						row.add(data);
					} else {
						String data = Integer.toString(rsdata.getInt(rscols.getString("COLUMN_NAME")));
						if (UtilConstants.columnMetaData.contains(rscols.getString("COLUMN_NAME"))) {
							data = "****" + data.substring(data.length() - 4, data.length());
						}
						row.add(data);
					}
				}
				rscols.beforeFirst();
				tableVal.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tableVal;
	}

	public String updateTableData(String[] tableRecord) {
		DatabaseMetaData meta;
		ResultSet rscols;
		try {
			String tableName = tableRecord[1];
			String firstColVal = tableRecord[2];
			
			meta = connection.getMetaData();
			rscols = meta.getColumns("", "", tableName, "");
			
			StringBuilder updateRecord = new StringBuilder();
			updateRecord.append("update ").append(tableName).append(" set ");
			rscols.first();
			String firstCol = rscols.getString("COLUMN_NAME");
			rscols.beforeFirst();
			while(rscols.next()) {
				updateRecord.append(rscols.getString("COLUMN_NAME")).append(" = ?,");
			}
			
			updateRecord.deleteCharAt(updateRecord.length()-1).append(" ");
			updateRecord.append("where ").append(firstCol).append(" =?");
			System.out.println(updateRecord.toString());
			
			PreparedStatement preparedStatement = connection.prepareStatement(updateRecord.toString());
			for(int i= 3;i<tableRecord.length;i++) {
				preparedStatement.setString(i-2,tableRecord[i]);
			}
			preparedStatement.setString(tableRecord.length-2,firstColVal);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnections.closeConnection(connection);
		}
		return "Succesfully Updated";
	}

	public String deleteTableData(String tableName, String columnName, String id) {
		Statement statement;
		try {
			statement = connection.createStatement();
			String deleteData = "DELETE from "+tableName+" where " + columnName +  "='" + id + "'";
			statement.executeUpdate(deleteData);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnections.closeConnection(connection);
		}
		return "Table Data Deleted Successfully";
	}
}
