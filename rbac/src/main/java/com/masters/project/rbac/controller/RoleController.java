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
	public String addRole(@RequestBody RoleMapperUI role) throws SQLException {
		String roleName = role.getRoleName(); 
		int readAccess = 0, writeAccess = 0, deleteAccess = 0, updateAccess = 0;
		if(role.isReadAccess())
			readAccess = 3;
		else if(role.isPartialReadAccess())
			readAccess = 2;
		else if(role.isNoReadAccess())
			readAccess = 1;
		
		if(role.isWriteAccess())
			writeAccess = 3;
		else if(role.isPartialWriteAccess())
			writeAccess = 2;
		else if(role.isNoWriteAccess())
			writeAccess = 1;
		
		if(role.isUpdateAccess())
			updateAccess = 3;
		else if(role.isPartialUpdateAccess())
			updateAccess = 2;
		else if(role.isNoUpdateAccess())
			updateAccess = 1;
		
		if(role.isDeleteAccess())
			deleteAccess = 3;
		else if(role.isPartialDeleteAccess())
			deleteAccess = 2;
		else if(role.isNoDeleteAccess())
			deleteAccess = 1;
		
		RoleUtilities roleUtilities = new RoleUtilities();
		if(roleUtilities.fetchAllRoleNames().contains(role.getRoleName())) {
			roleUtilities.updateRoles(roleName, readAccess, writeAccess, deleteAccess, updateAccess);
		}
		return roleUtilities.createRoles(roleName, readAccess, writeAccess, deleteAccess, updateAccess);
	}
	
	@RequestMapping(value = "/userRole/{userName}", method = RequestMethod.GET)
	public List<UserRole> userRole(String userName) {
		return new UserRoleDbMapping().fetchUserRoleData();
	}
}
