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


public final class SearchActivityByPlayDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT A.activityid, title, description, type, location, dateandtime, privacytag, maxaudience, audiencesize, seasonid FROM theater_wa_db.Activity AS A INNER JOIN theater_wa_db.schedule AS S ON A.activityid = S.activityid WHERE S.playid = ?";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The salary of the employee
	 */
	private final int playid;

	/**
	 * Creates a new object for searching employees by salary.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param salary
	 *            the salary of the employee.
	 */
	public SearchActivityByPlayDatabase(final Connection con, final int playid) {
		this.con = con;
		this.playid = playid;
	}

	/**
	 * Searches employees by their salary.
	 * 
	 * @return a list of {@code Employee} object matching the salary.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for employees.
	 */
	public List<Activity> searchActivityByPlay() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Activity> acts = new ArrayList<Activity>();

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, playid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
                acts.add(new Activity(
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
                        ));						
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

		return acts;
	}
}
