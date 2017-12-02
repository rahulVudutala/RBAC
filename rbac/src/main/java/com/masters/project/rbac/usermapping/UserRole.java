/**
 * 
 */
package com.masters.project.rbac.usermapping;

import java.util.List;

/**
 * @author rahul.vudutala Class to display user and their role information to
 *         admin
 */
public class UserRole {
	private String empId;
	private String empName;
	private String role;
	private List<String> allroles;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<String> getAllroles() {
		return allroles;
	}

	public void setAllroles(List<String> allroles) {
		this.allroles = allroles;
	}

	@Override
	public String toString() {
		return "UserRole [empId=" + empId + ", empName=" + empName + ", role=" + role + ", allroles=" + allroles + "]";
	}

}
