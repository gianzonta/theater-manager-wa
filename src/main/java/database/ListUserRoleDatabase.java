package database;

import resource.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ListUserRoleDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENTBYUSERNAME = "SELECT * FROM theater_wa_db.userrole WHERE username = ?";// ORDER BY seasonID DESC, ORDER BY playID DESC";
	private static final String STATEMENTBYPLAYID = "SELECT * FROM theater_wa_db.userrole WHERE playID = ?";// ORDER BY seasonID DESC, ORDER BY playID DESC,ORDER BY username ASC";
	private static final String STATEMENTALL = "SELECT * FROM theater_wa_db.userrole";// ORDER BY seasonID DESC, ORDER BY playID DESC,ORDER BY username ASC, ORDER BY name ASC, ORDER BY role ASC";

	/**
	 * The connection to the database
	 */
	private final Connection con;


	/**
	 * Creates a new object for listing the userRoles.
	 * 
	 * @param con
	 *            the connection to the database.
	 */

	public ListUserRoleDatabase(final Connection con) {
		this.con = con;
	}

	/**
	 * Lists all the userRoles in the database.
	 * 
	 * @return a list of {@code Member} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for userRoles.
	 */
	public List<UserRole> listbyUsername(final String username) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<UserRole> userroles = new ArrayList<UserRole>();

		try {
			pstmt = con.prepareStatement(STATEMENTBYUSERNAME);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				userroles.add(new UserRole(
						rs.getString("username"), 
						rs.getInt("seasonid"),
						rs.getInt("playid"),
                        rs.getString("name"), 
                        rs.getString("role")
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

		return userroles;
	}


	public List<UserRole> listbyPlayID(final int playID) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<UserRole> userroles = new ArrayList<UserRole>();

		try {
			pstmt = con.prepareStatement(STATEMENTBYPLAYID);
			pstmt.setInt(1, playID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				userroles.add(new UserRole(
						rs.getString("username"), 
						rs.getInt("seasonid"),
						rs.getInt("playid"),						
                        rs.getString("name"), 
                        rs.getString("role")
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

		return userroles;
	}

	public List<UserRole> listAll() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<UserRole> userroles = new ArrayList<UserRole>();

		try {
			pstmt = con.prepareStatement(STATEMENTALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				userroles.add(new UserRole(
						rs.getString("username"), 
						rs.getInt("seasonid"),
						rs.getInt("playid"),						
                        rs.getString("name"), 
                        rs.getString("role")
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

		return userroles;
	}

}
