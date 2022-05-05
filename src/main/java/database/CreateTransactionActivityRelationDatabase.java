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

import resource.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public final class CreateTransactionActivityRelationDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO theater_wa_db.generatedby (transid,activityid) VALUES (?,?) RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The salary of the employee
	 */
	private final int activityid;

	Transaction srcTrans;

	/**
	 * Creates a new object for searching employees by salary.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param salary
	 *            the salary of the employee.
	 */
	public CreateTransactionActivityRelationDatabase(final Connection con, final int actid,Transaction transaction) {
		this.con = con;
		this.activityid = actid;
		this.srcTrans = transaction;
	}

	/**
	 * Searches employees by their salary.
	 * 
	 * @return a list of {@code Employee} object matching the salary.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for employees.
	 */
	public Transaction createTransactionActivityRelation() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the replied transaction

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, srcTrans.getTransID());
			pstmt.setInt(2, activityid);

			rs = pstmt.executeQuery();

		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

		return srcTrans;

	}
}
