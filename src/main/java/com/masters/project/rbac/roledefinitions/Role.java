/**
 * 
 */
package com.masters.project.rbac.roledefinitions;

/**
 * @author rahul.vudutala
 *
 */
public class Role {
	private String roleName;
	private int readAccess;
	private int writeAccess;
	private int deleteAccess;
	private int updateAccess;

	public Role(String roleName, int readAccess, int writeAccess, int deleteAccess, int updateAccess) {
		this.roleName = roleName;
		this.readAccess = readAccess;
		this.writeAccess = writeAccess;
		this.deleteAccess = deleteAccess;
		this.updateAccess = updateAccess;
	}

	public String getRoleName() {
		return roleName;
	}

	public int getReadAccess() {
		return readAccess;
	}

	public int getWriteAccess() {
		return writeAccess;
	}

	public int getDeleteAccess() {
		return deleteAccess;
	}

	public int getUpdateAccess() {
		return updateAccess;
	}
}
