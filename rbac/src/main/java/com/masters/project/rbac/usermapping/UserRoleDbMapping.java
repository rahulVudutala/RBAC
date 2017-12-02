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
			String role = "select role from user_role where user_id = " + userId;
			ResultSet rs = statement.executeQuery(role);
			while (rs.next()) {
				userRoles.append(rs.getString("role")).append(",");
			}
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
			if (newRole.equals(getUserRole(userId)))
				return "No changes made";

			if (newRole.equals(null))
				return "please select atleast one role";

			String updateRole = "update user_role set role = ? where user_id= ?";

			preparedStatement = connection.prepareStatement(updateRole);
			preparedStatement.setString(1, newRole);
			preparedStatement.setInt(2, Integer.parseInt(userId));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
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
			statement = connection.createStatement();
			role = getUserRole(userId);
			String deleteData = "DELETE from user_role where role_id = " + userId + " and role = " + role;
			statement.executeUpdate(deleteData);
		} catch (SQLException e) {
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
}
