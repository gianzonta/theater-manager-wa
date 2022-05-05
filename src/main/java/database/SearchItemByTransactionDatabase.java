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

import resource.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public final class SearchItemByTransactionDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT I.itemid, name, description, quantity, size, historicalgenre, image, username FROM theater_wa_db.item AS I INNER JOIN theater_wa_db.acquires AS ACQ ON I.itemid = ACQ.itemid WHERE ACQ.transid = ?";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The salary of the employee
	 */
	private final int transid;

	/**
	 * Creates a new object for searching employees by salary.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param salary
	 *            the salary of the employee.
	 */
	public SearchItemByTransactionDatabase(final Connection con, final int transactionid) {
		this.con = con;
		this.transid= transactionid;
	}

	/**
	 * Searches employees by their salary.
	 * 
	 * @return a list of {@code Employee} object matching the salary.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for employees.
	 */
	public List<Item> searchItemByTransaction() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Item> items = new ArrayList<Item>();

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, transid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
                items.add(new Item(
					rs.getInt("itemid"), 
                    rs.getString("name"), 
                    rs.getString("description"),
                    rs.getInt("quantity"),
                    rs.getString("size"), 
                    rs.getString("historicalgenre"),
                    rs.getString("image"), 
                    rs.getString("username")));						
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

		return items;
	}
}
