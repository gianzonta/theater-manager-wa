package database;

import resource.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ReadMemberDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT * FROM theater_wa_db.Member WHERE username = ?";

	/**
	 * The connection to the database
	 */
	private final Connection con;
	private final String username;

	/**
	 * Creates a new object for listing all the members.
	 * 
	 * @param con
	 *            the connection to the database.
	 */
	public ReadMemberDatabase(final Connection con,final String username) {
		this.con = con;
		this.username = username;
	}

	/**
	 * Lists all the members in the database.
	 * 
	 * @return a list of {@code Member} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for members.
	 */
	public Member readMember() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		Member member = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if (rs.next()) {
			member = new Member(rs.getString("username"), rs.getString("pswHash"), rs.getString("name"), rs.getString("surname"), rs.getString("photo"), 
			rs.getString("birthDate"), rs.getString("phoneNumber"), rs.getString("email"), rs.getString("hiringDate"), rs.getBoolean("isUnipdStudent"),
			rs.getBoolean("isMemberPro"), rs.getString("userGroup"));						
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

		return member;
	}
}
