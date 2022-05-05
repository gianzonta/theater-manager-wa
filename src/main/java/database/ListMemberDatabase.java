package database;

import resource.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ListMemberDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT * FROM theater_wa_db.Member ORDER BY name ASC";
	private static final String STATEMENT2 = "SELECT * FROM theater_wa_db.Member ORDER BY HiringDate DESC LIMIT 5";
	private static final String COUNT = "SELECT count(*) as total FROM theater_wa_db.Member";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * Creates a new object for listing all the members.
	 * 
	 * @param con
	 *            the connection to the database.
	 */
	public ListMemberDatabase(final Connection con) {
		this.con = con;
	}

	/**
	 * Lists all the members in the database.
	 * 
	 * @return a list of {@code Member} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for members.
	 */
	public List<Member> listMember() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Member> members = new ArrayList<Member>();

		try {
			pstmt = con.prepareStatement(STATEMENT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				members.add(new Member(rs.getString("username"), rs.getString("pswHash"), rs.getString("name"), rs.getString("surname"), rs.getString("photo"), 
				rs.getString("birthDate"), rs.getString("phoneNumber"), rs.getString("email"), rs.getString("hiringDate"), rs.getBoolean("isUnipdStudent"),
				rs.getBoolean("isMemberPro"), rs.getString("userGroup")));						
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

		return members;
	}

	/**
	 * Lists the last 5 hired members.
	 * 
	 * @return a list of {@code Member} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for members.
	 */
	public List<Member> lastFiveMembers() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Member> members = new ArrayList<Member>();

		try {
			pstmt = con.prepareStatement(STATEMENT2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				members.add(new Member(rs.getString("username"), rs.getString("pswHash"), rs.getString("name"), rs.getString("surname"), rs.getString("photo"), 
				rs.getString("birthDate"), rs.getString("phoneNumber"), rs.getString("email"), rs.getString("hiringDate"), rs.getBoolean("isUnipdStudent"),
				rs.getBoolean("isMemberPro"), rs.getString("userGroup")));						
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

		return members;
	}

	/**
	 * Returns the total number of members.
	 * 
	 * @return a list of {@code Member} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for members.
	 */
	public int count() throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			pstmt = con.prepareStatement(COUNT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("total");
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

		return count;
	}
}
