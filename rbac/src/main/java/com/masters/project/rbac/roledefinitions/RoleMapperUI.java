/**
 * 
 */
package com.masters.project.rbac.roledefinitions;

/**
 * @author rahul.vudutala
 *
 */
public class RoleMapperUI {
	private String roleName;

	private boolean readAccess = false;
	private boolean partialReadAccess = false;
	private boolean noReadAccess = false;

	private boolean writeAccess = false;
	private boolean partialWriteAccess = false;
	private boolean noWriteAccess = false;

	private boolean deleteAccess = false;
	private boolean partialDeleteAccess = false;
	private boolean noDeleteAccess = false;

	private boolean updateAccess = false;
	private boolean partialUpdateAccess = false;
	private boolean noUpdateAccess = false;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isReadAccess() {
		return readAccess;
	}

	public void setReadAccess(boolean readAccess) {
		this.readAccess = readAccess;
	}

	public boolean isPartialReadAccess() {
		return partialReadAccess;
	}

	public void setPartialReadAccess(boolean partialReadAccess) {
		this.partialReadAccess = partialReadAccess;
	}

	public boolean isNoReadAccess() {
		return noReadAccess;
	}

	public void setNoReadAccess(boolean noReadAccess) {
		this.noReadAccess = noReadAccess;
	}

	public boolean isWriteAccess() {
		return writeAccess;
	}

	public void setWriteAccess(boolean writeAccess) {
		this.writeAccess = writeAccess;
	}

	public boolean isPartialWriteAccess() {
		return partialWriteAccess;
	}

	public void setPartialWriteAccess(boolean partialWriteAccess) {
		this.partialWriteAccess = partialWriteAccess;
	}

	public boolean isNoWriteAccess() {
		return noWriteAccess;
	}

	public void setNoWriteAccess(boolean noWriteAccess) {
		this.noWriteAccess = noWriteAccess;
	}

	public boolean isDeleteAccess() {
		return deleteAccess;
	}

	public void setDeleteAccess(boolean deleteAccess) {
		this.deleteAccess = deleteAccess;
	}

	public boolean isPartialDeleteAccess() {
		return partialDeleteAccess;
	}

	public void setPartialDeleteAccess(boolean partialDeleteAccess) {
		this.partialDeleteAccess = partialDeleteAccess;
	}

	public boolean isNoDeleteAccess() {
		return noDeleteAccess;
	}

	public void setNoDeleteAccess(boolean noDeleteAccess) {
		this.noDeleteAccess = noDeleteAccess;
	}

	public boolean isUpdateAccess() {
		return updateAccess;
	}

	public void setUpdateAccess(boolean updateAccess) {
		this.updateAccess = updateAccess;
	}

	public boolean isPartialUpdateAccess() {
		return partialUpdateAccess;
	}

	public void setPartialUpdateAccess(boolean partialUpdateAccess) {
		this.partialUpdateAccess = partialUpdateAccess;
	}

	public boolean isNoUpdateAccess() {
		return noUpdateAccess;
	}

	public void setNoUpdateAccess(boolean noUpdateAccess) {
		this.noUpdateAccess = noUpdateAccess;
	}

	@Override
	public String toString() {
		return "RoleMapperUI [roleName=" + roleName + ", readAccess=" + readAccess + ", partialReadAccess="
				+ partialReadAccess + ", noReadAccess=" + noReadAccess + ", writeAccess=" + writeAccess
				+ ", partialWriteAccess=" + partialWriteAccess + ", noWriteAccess=" + noWriteAccess + ", deleteAccess="
				+ deleteAccess + ", partialDeleteAccess=" + partialDeleteAccess + ", noDeleteAccess=" + noDeleteAccess
				+ ", updateAccess=" + updateAccess + ", partialUpdateAccess=" + partialUpdateAccess
				+ ", noUpdateAccess=" + noUpdateAccess + "]";
	}

}
