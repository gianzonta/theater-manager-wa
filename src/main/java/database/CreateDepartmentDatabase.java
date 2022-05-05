package database;

import resource.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

/**
 * Creates an employee in the database.
 * 
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class CreateDepartmentDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO theater_wa_db.department ( name, description) VALUES (?, ?) RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The employee to be updated in the database
	 */
	private final Department department;

	/**
	 * Creates a new object for updat an employee.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param employee
	 *            the employee to be created in the database.
	 */
	public CreateDepartmentDatabase(final Connection con, final Department department) {
		this.con = con;
		this.department = department;
	}

	/**
	 * Creates an employee in the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public Department createDepartment() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the create employee
		Department a = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, department.getName());
			pstmt.setString(2, department.getDescription());
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