/**
 * 
 */
package com.masters.project.rbac.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.masters.project.rbac.roledefinitions.RoleMapperUI;
import com.masters.project.rbac.roledefinitions.RoleUtilities;
import com.masters.project.rbac.usermapping.UserRole;
import com.masters.project.rbac.usermapping.UserRoleDbMapping;

/**
 * @author rahul.vudutala
 *
 */

@RestController
@RequestMapping(value = "/roledata/")
public class RoleController {
	@RequestMapping(value = "/getRoles", method = RequestMethod.GET)
	public List<RoleMapperUI> fetchAllRoles() throws ClassNotFoundException, SQLException {
		return new RoleUtilities().fetchAllRoles();
	}

	@RequestMapping(value = "/deleteRole/{roleName}", method = RequestMethod.DELETE)
	public String deleteRole(@PathVariable("roleName") String roleName) throws ClassNotFoundException, SQLException {
		String response = new RoleUtilities().deleteRoles(roleName);
		return response;
	}

	@RequestMapping(value = "/newRole", method = RequestMethod.POST)
	public String addRole(@RequestBody String role) throws SQLException {
		String[] roleArray = role.split(",");
		String roleName = roleArray[0];
		int readAccess = 0, writeAccess = 0, deleteAccess = 0, updateAccess = 0;
		if (roleArray[1].equals("true"))
			readAccess = 3;
		else if (roleArray[2].equals("true"))
			readAccess = 2;
		else if (roleArray[3].equals("true"))
			readAccess = 1;

		if (roleArray[4].equals("true"))
			writeAccess = 3;
		else if (roleArray[5].equals("true"))
			writeAccess = 2;
		else if (roleArray[6].equals("true"))
			writeAccess = 1;

		if (roleArray[10].equals("true"))
			updateAccess = 3;
		else if (roleArray[11].equals("true"))
			updateAccess = 2;
		else if (roleArray[12].equals("true"))
			updateAccess = 1;

		if (roleArray[7].equals("true"))
			deleteAccess = 3;
		else if (roleArray[8].equals("true"))
			deleteAccess = 2;
		else if (roleArray[9].equals("true"))
			deleteAccess = 1;

		RoleUtilities roleUtilities = new RoleUtilities();
		if (roleUtilities.fetchAllRoleNames().contains(roleArray[0])) {
			roleUtilities.updateRoles(roleName, readAccess, writeAccess, deleteAccess, updateAccess);
		}
		return roleUtilities.createRoles(roleName, readAccess, writeAccess, deleteAccess, updateAccess);
	}

	@RequestMapping(value = "/userRole/{userName}", method = RequestMethod.GET)
	public List<UserRole> userRole(String userName) {
		return new UserRoleDbMapping().fetchUserRoleData();
	}
	
	@RequestMapping(value = "/userRole/update", method = RequestMethod.POST)
	public String updateRole(@RequestBody String userRole) {
		String[] roleArray = userRole.split(",");
		return new UserRoleDbMapping().updateUserRole(roleArray[0], roleArray[1]);
	}
}
