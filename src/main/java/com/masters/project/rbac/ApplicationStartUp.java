package com.masters.project.rbac;

/**
 * 
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.masters.project.rbac.constants.UtilConstants;
import com.masters.project.rbac.dbutilities.DbConnections;

/**
 * @author rahul.vudutala
 *
 */
@Component
public class ApplicationStartUp implements ApplicationListener<ApplicationReadyEvent> {
	public void onApplicationEvent(ApplicationReadyEvent arg0) {
		try {
			Connection c = DbConnections.createDbConnection();
			Statement statement = c.createStatement();
			String metaDataQuery = "select column_detail from column_metadata";
			ResultSet rs = statement.executeQuery(metaDataQuery);
			while (rs.next()) {
				UtilConstants.columnMetaData.add(rs.getString("column_detail"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
