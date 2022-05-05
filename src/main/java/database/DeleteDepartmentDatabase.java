package database;

import resource.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public final class DeleteDepartmentDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "DELETE FROM theater_wa_db.department WHERE name = ? RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The badge of the employee
	 */
	private final String name;

	/**
	 * Creates a new object for deleting an employee.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param username
	 *            the badge of the employee.
	 */
	public DeleteDepartmentDatabase(final Connection con, final String name) {
		this.con = con;
		this.name = name;
	}

	/**
	 * Deletes an employee from the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public Department deleteDepartment() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the deleted Department
		Department a = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, name);

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
