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

/**
 * Reads an employee from the database.
 * 
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class ReadTransactionDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT transid, name, description, amount, invoice, transactiondate, username, seasonid FROM theater_wa_db.transact WHERE transid = ?";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The badge of the employee
	 */
	private final int transactid;


	public ReadTransactionDatabase(final Connection con, final int transactid) {
		this.con = con;
		this.transactid = transactid;
	}

	/**
	 * Reads an employee from the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public Transaction readTransaction() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the read transaction
		Transaction newTransact = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, transactid);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				newTransact = new Transaction(
                        rs.getInt("transid"),
						rs.getString("name"), 
                        rs.getString("description"), 
                        rs.getDouble("amount"),
                        rs.getString("invoice"),
                        rs.getString("transactiondate"),
                        rs.getString("username"),
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

		return newTransact;
	}
}
