/*
 * Copyright 2018 University of Padua, Italy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package database;

import resource.Activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads an employee from the database.
 * 
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class ReadActivityDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT activityid, title, description, type, location, dateandtime, privacytag, maxaudience, audiencesize, seasonid FROM theater_wa_db.activity WHERE activityid = ?";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The badge of the employee
	 */
	private final int activityid;

	/**
	 * Creates a new object for reading an employee.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param salary
	 *            the badge of the employee.
	 */
	public ReadActivityDatabase(final Connection con, final int activityid) {
		this.con = con;
		this.activityid = activityid;
	}

	/**
	 * Reads an employee from the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public Activity readActivity() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the read employee
		Activity a = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, activityid);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				a = new Activity(
                        rs.getInt("activityid"), 
						rs.getString("title"), 
                        rs.getString("description"), 
                        rs.getString("type"),
                        rs.getString("location"),
                        rs.getString("dateandtime"),
                        rs.getString("privacytag"),
						rs.getInt("maxaudience"),
                        rs.getInt("audiencesize"),
                        rs.getInt("seasonid")
                        );
			}
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

		return a;
	}
}
