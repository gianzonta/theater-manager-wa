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

import resource.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;


public final class UpdateDepartmentDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "UPDATE theater_wa_db.department SET description = ? WHERE name = ? RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;


	private final Department department;


	public UpdateDepartmentDatabase(final Connection con, final Department department) {
		this.con = con;
		this.department = department;
	}


	public Department updateDepartment() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the updated employee
		Department a = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			
			pstmt.setString(1, department.getDescription());
			pstmt.setString(2, department.getName());
			

			rs = pstmt.executeQuery();

			if (rs.next()) {
				a = new Department(
                        
						rs.getString("name"), 
                        rs.getString("description")
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
