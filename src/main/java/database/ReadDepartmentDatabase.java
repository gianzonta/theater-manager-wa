package database;

import resource.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ReadDepartmentDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT * FROM theater_wa_db.department WHERE name = ?";

	/**
	 * The connection to the database
	 */
	private final Connection con;
	private final String name;

	/**
	 * Creates a new object for listing all the Departments.
	 * 
	 * @param con
	 *            the connection to the database.
	 */
	public ReadDepartmentDatabase(final Connection con,final String name) {
		this.con = con;
		this.name = name;
	}

	/**
	 * Lists all the Departments in the database.
	 * 
	 * @return a list of {@code Department} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for Departments.
	 */
	public Department readDepartment() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		Department department = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
			department = new Department(
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

		return department;
	}
}
