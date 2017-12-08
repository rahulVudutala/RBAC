/**
 * 
 */
package com.masters.project.rbac.roledefinitions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rahul.vudutala
 *
 */

public class RoleUtilities {

	// creates roles when requested by the admin
	public String createRoles(String roleName, int readAccess, int writeAccess, int deleteAccess, int updateAccess)
			throws SQLException {
		RoleMappingDbUtil roleMapper = new RoleMappingDbUtil();
		Role role = new Role(roleName, readAccess, writeAccess, deleteAccess, updateAccess);
		roleMapper.insertNewRoleData(role);
		return "";
	}

	// updates roles when requested by admin
	public String updateRoles(String roleName, int readAccess, int writeAccess, int deleteAccess, int updateAccess)
			throws SQLException {
		RoleMappingDbUtil roleMapper = new RoleMappingDbUtil();
		Role role = new Role(roleName, readAccess, writeAccess, deleteAccess, updateAccess);
		return roleMapper.updateRoleData(role);
	}

	// deletes roles when requested by admin
	public String deleteRoles(String roleName) throws SQLException {
		RoleMappingDbUtil roleMapper = new RoleMappingDbUtil();
		String response = roleMapper.deleteRoleData(roleName);
		return response;
	}

	// Displays all roles to display on admin dashboard
	public List<RoleMapperUI> fetchAllRoles() throws SQLException {
		RoleMappingDbUtil roleMapper = new RoleMappingDbUtil();
		List<Role> roles = roleMapper.fetchAllRoles();
		return mapForUI(roles);
	}

	// Displays role names which is used to display user role information
	public List<String> fetchAllRoleNames() throws SQLException {
		List<String> roleNames = new ArrayList<String>();
		RoleMappingDbUtil roleMapper = new RoleMappingDbUtil();
		List<Role> roles = roleMapper.fetchAllRoles();
		for (Role role : roles) {
			roleNames.add(role.getRoleName());
		}
		return roleNames;
	}

	public Role fetchRole(String roleName) {
		RoleMappingDbUtil roleMapper = new RoleMappingDbUtil();
		Role r = roleMapper.fetchRole(roleName);
		return r;
	}

	private List<RoleMapperUI> mapForUI(List<Role> roles) {
		RoleMapperUI roleMapper;
		List<RoleMapperUI> roleMapperList = new ArrayList<RoleMapperUI>();
		for (Role r : roles) {
			roleMapper = new RoleMapperUI();
			roleMapper.setRoleName(r.getRoleName());
			if (r.getReadAccess() == 1) {
				roleMapper.setNoReadAccess(true);
			} else if (r.getReadAccess() == 2) {
				roleMapper.setPartialReadAccess(true);
			} else if (r.getReadAccess() == 3) {
				roleMapper.setReadAccess(true);
			}

			if (r.getWriteAccess() == 1) {
				roleMapper.setNoWriteAccess(true);
			} else if (r.getWriteAccess() == 2) {
				roleMapper.setPartialWriteAccess(true);
			} else if (r.getWriteAccess() == 3) {
				roleMapper.setWriteAccess(true);
			}

			if (r.getUpdateAccess() == 1) {
				roleMapper.setNoUpdateAccess(true);
			} else if (r.getUpdateAccess() == 2) {
				roleMapper.setPartialUpdateAccess(true);
			} else if (r.getUpdateAccess() == 3) {
				roleMapper.setUpdateAccess(true);
			}

			if (r.getDeleteAccess() == 1) {
				roleMapper.setNoDeleteAccess(true);
			} else if (r.getDeleteAccess() == 2) {
				roleMapper.setPartialDeleteAccess(true);
			} else if (r.getDeleteAccess() == 3) {
				roleMapper.setDeleteAccess(true);
			}
			roleMapperList.add(roleMapper);
		}
		return roleMapperList;
	}
}
