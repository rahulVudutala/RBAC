/**
 * 
 */
package com.masters.project.rbac.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.masters.project.rbac.dbutilities.ListTablesDbUtil;
import com.masters.project.rbac.roledefinitions.Role;
import com.masters.project.rbac.roledefinitions.RoleUtilities;
import com.masters.project.rbac.usermapping.UserRoleDbMapping;

/**
 * @author rahul.vudutala
 *
 */

@RestController
public class TableController {
	@RequestMapping(value = "/user/{userName}/tabledata/{tableName}", method = RequestMethod.GET)
	public List<List<String>> getTableData(@PathVariable("userName") String user, @PathVariable("tableName") String tableName)
			throws ClassNotFoundException, SQLException {
		UserRoleDbMapping roleMapper = new UserRoleDbMapping();
		String s = roleMapper.getUserRole(user);
		Role r = new RoleUtilities().fetchRole(s);
		if(r.getReadAccess() == 3)
			return new ListTablesDbUtil().fetchTableData(tableName);
		else if(r.getReadAccess() == 2)
			return new ListTablesDbUtil().fetchTableDataPartial(tableName);
		else
			return null;
	}
	
	@RequestMapping(value = "/user/{userName}", method = RequestMethod.GET)
	public List<String> getTables(@PathVariable("userName") String user) {
		UserRoleDbMapping roleMapper = new UserRoleDbMapping();
		String s = roleMapper.getUserRole(user);
		Role r = new RoleUtilities().fetchRole(s);
		if(r.getReadAccess() == 3)
			return new ListTablesDbUtil().listAllTables();
		else if(r.getReadAccess() == 2)
			return new ListTablesDbUtil().listAllTables();
		else
			return new ListTablesDbUtil().listAllTables();
	}
}
