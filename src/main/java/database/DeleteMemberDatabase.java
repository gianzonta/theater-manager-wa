package database;

import resource.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public final class DeleteMemberDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "DELETE FROM theater_wa_db.member WHERE username = ? RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The badge of the employee
	 */
	private final String username;

	/**
	 * Creates a new object for deleting an employee.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param username
	 *            the badge of the employee.
	 */
	public DeleteMemberDatabase(final Connection con, final String username) {
		this.con = con;
		this.username = username;
	}

	/**
	 * Deletes an employee from the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public Member deleteMember() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the deleted member
		Member a = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, username);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				a = new Member(
						rs.getString("username"), 
						rs.getString("pswHash"), 
						rs.getString("name"), 
						rs.getString("surname"),
						rs.getString("photo"),
						rs.getString("birthDate"),
						rs.getString("phoneNumber"),
						rs.getString("email"),
						rs.getString("hiringDate"),
						rs.getBoolean("isUnipdStudent"),
						rs.getBoolean("isMemberPro"),
						rs.getString("userGroup")
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
