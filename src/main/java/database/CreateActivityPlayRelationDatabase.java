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


public final class CreateActivityPlayRelationDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT1 = "DELETE FROM theater_wa_db.schedule WHERE activityid = ? RETURNING *";

	private static final String STATEMENT2 = "INSERT INTO theater_wa_db.schedule (playid,activityid) VALUES (?,?) RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The salary of the employee
	 */
	private final int playid;

	Activity srcAct;

	/**
	 * Creates a new object for searching employees by salary.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param salary
	 *            the salary of the employee.
	 */
	public CreateActivityPlayRelationDatabase(final Connection con, Activity act, final int playid) {
		this.con = con;
		this.playid = playid;
		this.srcAct = act;
	}

	/**
	 * Searches employees by their salary.
	 * 
	 * @return a list of {@code Employee} object matching the salary.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for employees.
	 */
	public Activity createActivityPlayRelation() throws SQLException {

		//---deletes the current existing relation(s)

		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;

		// the replied transaction

		try {
			pstmt1 = con.prepareStatement(STATEMENT1);
			pstmt1.setInt(1, srcAct.getActivityID());
			

			rs1 = pstmt1.executeQuery();

		} finally {
			if (rs1 != null) {
				rs1.close();
			}

			if (pstmt1 != null) {
				pstmt1.close();
			}

		}

		if (playid == -1) {
			return srcAct;
		}
		//---creates the new relation
		
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;

		// the replied transaction

		try {
			pstmt2 = con.prepareStatement(STATEMENT2);
			pstmt2.setInt(1, playid);
			pstmt2.setInt(2, srcAct.getActivityID());
			

			rs2 = pstmt2.executeQuery();

		} finally {
			if (rs2 != null) {
				rs2.close();
			}

			if (pstmt2 != null) {
				pstmt2.close();
			}

			con.close();
		}

		return srcAct;

	}
}
