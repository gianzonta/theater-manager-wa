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


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public final class IsPartOfDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String INSERTSTATEMENT = "INSERT INTO theater_wa_db.ispartof (username,name) VALUES (CAST (? AS NAMEDOM),CAST (? AS NAMEDOM)) RETURNING *";
	private static final String DELETESTATEMENT = "DELETE FROM theater_wa_db.ispartof WHERE username = ? RETURNING *";
	private static final String LISTBYUSERNAMESTATEMENT = "SELECT * FROM theater_wa_db.ispartof WHERE username = ?";
	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The salary of the employee
	 */
	private final String username;

	/**
	 * Creates a new object for searching employees by salary.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param salary
	 *            the salary of the employee.
	 */
	public IsPartOfDatabase(final Connection con,  final String username) {
		this.con = con;
		this.username = username;
	}

	/**
	 * Searches employees by their salary.
	 * 
	 * @return a list of {@code Employee} object matching the salary.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for employees.
	 */


	public String listIsPartOf() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "{\"resource-list\":[";
		// the replied transaction

		try {
			pstmt = con.prepareStatement(LISTBYUSERNAMESTATEMENT);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = result + "{\"department\":\"";
				result = result + rs.getString("name");
				result = result + "\"}";
				if (!rs.isLast()) {result = result + ",";}
			}
			result = result + "]}";

		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}
		return result;
	}



	public void createIsPartOf(final String name) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] deplist = name.split("[,]");
		// the replied transaction
		deleteIsPartOf();
		try {
			for (String dep : deplist){
			pstmt = con.prepareStatement(INSERTSTATEMENT);
			pstmt.setString(1, username);
			pstmt.setString(2, dep);
			rs = pstmt.executeQuery();
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

	}


	public void deleteIsPartOf() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the replied transaction

		try {
			pstmt = con.prepareStatement(DELETESTATEMENT);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();

		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			//con.close();
		}

	}
}
