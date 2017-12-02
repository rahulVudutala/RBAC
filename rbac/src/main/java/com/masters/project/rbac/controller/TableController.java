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

import com.masters.project.rbac.dbutilities.ListTablesDbUtil;
import com.masters.project.rbac.roledefinitions.Role;
import com.masters.project.rbac.roledefinitions.RoleUtilities;
import com.masters.project.rbac.usermapping.UserRoleDbMapping;

/**
 * @author rahul.vudutala
 *
 */
@CrossOrigin(origins = { "http://datagovernance.us-west-1.elasticbeanstalk.com" })
@RestController
public class TableController {
	@RequestMapping(value = "/user/{userName}/tabledata/{tableName}", method = RequestMethod.GET)
	public List<List<String>> getTableData(@PathVariable("userName") String user,
			@PathVariable("tableName") String tableName) throws ClassNotFoundException, SQLException {
		UserRoleDbMapping roleMapper = new UserRoleDbMapping();
		String s = roleMapper.getUserRole(user);
		Role r = new RoleUtilities().fetchRole(s);
		if (r.getReadAccess() == 3)
			return new ListTablesDbUtil().fetchTableData(tableName);
		else if (r.getReadAccess() == 2)
			return new ListTablesDbUtil().fetchTableDataPartial(tableName);
		else
			return null;
	}

	@RequestMapping(value = "/user/{userName}", method = RequestMethod.GET)
	public List<String> getTables(@PathVariable("userName") String user) {
		UserRoleDbMapping roleMapper = new UserRoleDbMapping();
		String s = roleMapper.getUserRole(user);
		Role r = new RoleUtilities().fetchRole(s);
		if (r.getReadAccess() == 3)
			return new ListTablesDbUtil().listAllTables();
		else if (r.getReadAccess() == 2)
			return new ListTablesDbUtil().listAllTables();
		else
			return new ListTablesDbUtil().listAllTables();
	}

	@RequestMapping(value = "/user/{userName}/table/{tableName}/column/{columnName}/data/{data}", method = RequestMethod.DELETE)
	public String deleteTableData(@PathVariable("userName") String userName,
			@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName,
			@PathVariable("data") String data) {
		UserRoleDbMapping roleMapper = new UserRoleDbMapping();
		String s = roleMapper.getUserRole(userName);
		Role r = new RoleUtilities().fetchRole(s);
		if (r.getDeleteAccess() == 3)
			return new ListTablesDbUtil().deleteTableData(tableName, columnName, data);
		else if (r.getDeleteAccess() == 2)
			return "Can't delete data";
		else
			return "Can't delete data";
	}

	@RequestMapping(value = "/user/updateTableData", method = RequestMethod.POST)
	public String updateTableData(@RequestBody String updateData) {
		String[] updateDataArary = updateData.split(",");
		UserRoleDbMapping roleMapper = new UserRoleDbMapping();
		String s = roleMapper.getUserRole(updateDataArary[0]);
		Role r = new RoleUtilities().fetchRole(s);
		if(r.getUpdateAccess() == 3 || r.getUpdateAccess() == 2)
			return new ListTablesDbUtil().updateTableData(updateDataArary);
		else
			return "You dont have update access";
	}
}
