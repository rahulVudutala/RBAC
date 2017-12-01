/**
 * 
 */
package com.masters.project.rbac.constants;

/**
 * @author rahul.vudutala
 *
 */
public class AccessControls {
	
	public static final int NoRead = 1;
	public static final String NoReadVal = "No Read Access";
	
	public static final int PartialRead = 2;
	public static final String PartialReadVal = "Partial Read Access";
	
	public static final int Read = 3;
	public static final String ReadVal = "Full Read Access";
	
	public static final int NoWrite = 1;
	public static final String NoWriteVal = "No Write Access";
	
	public static final int PartialWrite = 2;
	public static final String PartialWriteVal = "Partial Write Access";
	
	public static final int Write = 3;
	public static final String WriteVal = "Full Write Access";
	
	public static final int NoUpdate = 1;
	public static final int PartialUpdate = 2;
	public static final int Update = 3;
	
	public static final int NoDelete = 1;
	public static final int PartialDelete = 2;
	public static final int Delete = 3;
	
}
