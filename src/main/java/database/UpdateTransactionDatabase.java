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
import java.sql.Timestamp;


public final class UpdateTransactionDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "UPDATE theater_wa_db.transact SET name = ?, description = ?, amount = ?, invoice = ? WHERE transid = ? RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;


	private final Transaction transaction;


	public UpdateTransactionDatabase(final Connection con, final Transaction transaction) {
		this.con = con;
		this.transaction = transaction;
	}


	public Transaction updateTransaction() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the updated employee
		Transaction upTransact = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, transaction.getName());
			pstmt.setString(2, transaction.getDescription());
			pstmt.setDouble(3, transaction.getAmount());
			pstmt.setString(4, transaction.getInvoice());
			pstmt.setInt(5, transaction.getTransID());

			rs = pstmt.executeQuery();

			if (rs.next()) {
					upTransact = new Transaction(
                        rs.getInt("transid"), //may need some uppercase
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

		return upTransact;
	}
}
