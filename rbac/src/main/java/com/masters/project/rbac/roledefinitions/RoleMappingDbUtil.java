/**
 * 
 */
package com.masters.project.rbac.roledefinitions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.masters.project.rbac.dbutilities.DbConnections;

/**
 * @author rahul.vudutala
 *
 */
public class RoleMappingDbUtil {

	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;

	public RoleMappingDbUtil() {
		try {
			this.connection = DbConnections.createDbConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// inserts data in the role_employee_information table
	public String insertNewRoleData(Role roleInfo) {
		String insertData = "insert into role_employee_information "
				+ "(role_id, read_access_level, write_access_level, update_access_level, delete_access_level)"
				+ " values" + "(?,?,?,?,?)";

		try {
			preparedStatement = connection.prepareStatement(insertData);
			preparedStatement.setString(1, roleInfo.getRoleName());
			preparedStatement.setInt(2, roleInfo.getReadAccess());
			preparedStatement.setInt(3, roleInfo.getWriteAccess());
			preparedStatement.setInt(4, roleInfo.getUpdateAccess());
			preparedStatement.setInt(5, roleInfo.getDeleteAccess());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnections.closeConnection(connection);
		}
		return "Succesfully created";
	}

	// deletes data from the role_employee_information
	public String deleteRoleData(String roleName) {
		try {
			statement = connection.createStatement();
			String deleteData = "DELETE from role_employee_information where role_id = " + "'" + roleName + "'";
			statement.executeUpdate(deleteData);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnections.closeConnection(connection);
		}
		return "Role Deleted Successfully";
	}

	// updates data from the role_employee_information
	public String updateRoleData(Role roleInfo) {
		try {
			StringBuilder updateData = new StringBuilder();
			updateData.append("update role_employee_information set ");
			int count = 1;

			if (roleInfo.getReadAccess() != 0) {
				updateData.append("read_access_level=?").append(",");
			}

			if (roleInfo.getWriteAccess() != 0) {
				updateData.append("write_access_level=?").append(",");
			}

			if (roleInfo.getUpdateAccess() != 0) {
				updateData.append("update_access_level=?").append(",");
			}

			if (roleInfo.getDeleteAccess() != 0) {
				updateData.append("delete_access_level=?").append(",");
			}
			updateData.deleteCharAt(updateData.length() - 1);
			updateData.append(" where role_id=?");
			preparedStatement = connection.prepareStatement(updateData.toString());
			if (roleInfo.getReadAccess() != 0) {
				preparedStatement.setInt(count, roleInfo.getReadAccess());
				count++;
			}

			if (roleInfo.getWriteAccess() != 0) {
				preparedStatement.setInt(count, roleInfo.getWriteAccess());
				count++;
			}

			if (roleInfo.getUpdateAccess() != 0) {
				preparedStatement.setInt(count, roleInfo.getUpdateAccess());
				count++;
			}

			if (roleInfo.getDeleteAccess() != 0) {
				preparedStatement.setInt(count, roleInfo.getDeleteAccess());
				count++;
			}
			preparedStatement.setString(count, roleInfo.getRoleName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnections.closeConnection(connection);
		}
		return "Successfully Updated";
	}

	// retrieves roles and their permission levels from the
	// role_employee_information table
	public List<Role> fetchAllRoles() {
		List<Role> roles = new ArrayList<Role>();
		try {
			statement = connection.createStatement();
			String roleData = "SELECT * FROM role_employee_information";
			ResultSet rs = statement.executeQuery(roleData);
			while (rs.next()) {
				String role_name = rs.getString("role_id");
				int read_access_level = rs.getInt("read_access_level");
				int write_access_level = rs.getInt("write_access_level");
				int update_access_level = rs.getInt("update_access_level");
				int delete_access_level = rs.getInt("delete_access_level");
				System.out.print(role_name + " " + read_access_level + " " + write_access_level + " "
						+ update_access_level + " " + delete_access_level);
				System.out.println("");
				Role r = new Role(role_name, read_access_level, write_access_level, update_access_level,
						delete_access_level);
				roles.add(r);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnections.closeConnection(connection);
		}
		return roles;
	}

	public Role fetchRole(String roleName) {
		Role r = null;
		try {
			statement = connection.createStatement();
			String roleData = "SELECT * FROM role_employee_information where role_id = '" + roleName + "'";
			ResultSet rs = statement.executeQuery(roleData);
			while (rs.next()) {
				String role_name = rs.getString("role_id");
				int read_access_level = rs.getInt("read_access_level");
				int write_access_level = rs.getInt("write_access_level");
				int update_access_level = rs.getInt("update_access_level");
				int delete_access_level = rs.getInt("delete_access_level");
				r = new Role(role_name, read_access_level, write_access_level, delete_access_level,
						update_access_level);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnections.closeConnection(connection);
		}
		return r;
	}

}
