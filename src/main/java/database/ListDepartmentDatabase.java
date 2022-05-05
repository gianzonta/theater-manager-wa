package database;

import resource.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ListDepartmentDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT * FROM theater_wa_db.department ORDER BY name ASC";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * Creates a new object for listing all the departments.
	 * 
	 * @param con
	 *            the connection to the database.
	 */
	public ListDepartmentDatabase(final Connection con) {
		this.con = con;
	}

	/**
	 * Lists all the members in the database.
	 * 
	 * @return a list of {@code Department} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for departments.
	 */
	public List<Department> listDepartment() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Department> a = new ArrayList<Department>();

		try {
			pstmt = con.prepareStatement(STATEMENT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				a.add(new Department(
                        rs.getString("name"), 
                        rs.getString("description")
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

		return a;
	}
}
