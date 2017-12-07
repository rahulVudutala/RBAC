/**
 * 
 */
package com.masters.project.rbac.usermapping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.masters.project.rbac.dbutilities.DbConnections;
import com.masters.project.rbac.roledefinitions.RoleUtilities;

/**
 * @author rahul.vudutala
 *
 */
public class UserRoleDbMapping {
	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;

	public UserRoleDbMapping() {
		try {
			this.connection = DbConnections.createDbConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Fetches user and their role information along with the available roles
	public List<UserRole> fetchUserRoleData() {
		List<UserRole> userRoleData = null;
		try {
			statement = connection.createStatement();
			RoleUtilities rUtil = new RoleUtilities();
			UserRole userrole;
			userRoleData = new ArrayList<UserRole>();
			String roleData = "select ud.user_id, user_name, role from user_data ud left outer join user_role ur on ud.user_id = ur.user_id";
			ResultSet rs = statement.executeQuery(roleData);
			while (rs.next()) {
				userrole = new UserRole();

				userrole.setEmpId(Integer.toString(rs.getInt("ud.user_id")));
				userrole.setEmpName(rs.getString("user_name"));
				userrole.setRole(rs.getString("role"));
				userrole.setAllroles(rUtil.fetchAllRoleNames());
				userRoleData.add(userrole);
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
		return userRoleData;
	}

	// Fetches the role of the user when user id is given as input. Used to
	// fetch the roles assigned to user during login.
	public String getUserRole(String userId) {
		StringBuilder userRoles = new StringBuilder();
		try {
			statement = connection.createStatement();
			String role = "select role from user_role where user_id = " + Integer.parseInt(userId);
			ResultSet rs = statement.executeQuery(role);
			while (rs.next()) {
				userRoles.append(rs.getString("role")).append(",");
			}
			if (userRoles.length() != 0)
				userRoles.deleteCharAt(userRoles.length() - 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userRoles.toString();
	}

	// updates the role of the user as requested by admin
	public String updateUserRole(String userId, String newRole) {
		try {

			String userRole = getUserRole(userId);

			if (newRole.equals(userRole))
				return "No changes made";

			if (newRole.equals(null))
				return "please select atleast one role";

			if (userRole.equals(""))
				return insertUserRole(userId, newRole);

			String updateRole = "update user_role set role = ? where user_id= ?";
			Connection c1 = DbConnections.createDbConnection();
			preparedStatement = c1.prepareStatement(updateRole);
			preparedStatement.setString(1, newRole);
			preparedStatement.setInt(2, Integer.parseInt(userId));
			preparedStatement.executeUpdate();
			c1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Updated the role to " + newRole;
	}

	// deletes the role of the user as requested by admin
	public String deleteUserRole(String userId) {
		String role = "";
		try {
			Connection c1 = DbConnections.createDbConnection();
			statement = c1.createStatement();
			role = getUserRole(userId);
			String deleteData = "DELETE from user_role where role_id = " + Integer.parseInt(userId) + " and role = "
					+ role;
			statement.executeUpdate(deleteData);
			c1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Removed the user from " + role + "role";
	}

	public String insertUserRole(String userId, String userRole) {
		Connection c2 = null;
		try {
			String insertData = "insert into user_role " + " values" + "(?,?)";
			c2 = DbConnections.createDbConnection();
			preparedStatement = c2.prepareStatement(insertData);
			preparedStatement.setInt(1, Integer.parseInt(userId));
			preparedStatement.setString(2, userRole);
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnections.closeConnection(c2);
		}
		return "Role Updated";
	}
}
