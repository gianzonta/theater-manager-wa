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


public final class SearchTransactionByActivityDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT T.transid, T.name, description, amount, invoice, transactiondate, M.name AS username, M.surname AS usersurname, seasonid FROM theater_wa_db.Transact AS T INNER JOIN theater_wa_db.GeneratedBy AS GB ON T.transid = GB.transid INNER JOIN theater_wa_db.member AS M ON T.username = M.username WHERE GB.activityid = ?";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The salary of the employee
	 */
	private final int activityid;

	/**
	 * Creates a new object for searching employees by salary.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param salary
	 *            the salary of the employee.
	 */
	public SearchTransactionByActivityDatabase(final Connection con, final int actid) {
		this.con = con;
		this.activityid = actid;
	}

	/**
	 * Searches employees by their salary.
	 * 
	 * @return a list of {@code Employee} object matching the salary.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for employees.
	 */
	public List<Transaction> searchTransactionByActivity() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Transaction> transacts = new ArrayList<Transaction>();

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, activityid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
                transacts.add(new Transaction(
                        rs.getInt("transid"), 
						rs.getString("name"), 
                        rs.getString("description"), 
                        rs.getDouble("amount"),
                        rs.getString("invoice"),
                        rs.getString("transactiondate"),
                        rs.getString("username") + " " + rs.getString("usersurname"),
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

		return transacts;
	}
}
