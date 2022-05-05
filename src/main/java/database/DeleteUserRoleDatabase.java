package database;

import resource.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public final class DeleteUserRoleDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "DELETE FROM theater_wa_db.userrole WHERE username = ? AND seasonID = ? AND playID = ? AND name = ? RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The badge of the userrole
	 */
	private final String username;
	private final int playID;
	private final int seasonID;
	private final String name;

	/**
	 * Creates a new object for deleting an userrole.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param username
	 *            the badge of the userrole.
	 */
	public DeleteUserRoleDatabase(final Connection con, final UserRole userrole) {
		this.con = con;
		this.username = userrole.getUsername();
		this.playID = userrole.getPlayID();
		this.seasonID = userrole.getSeasonID();
		this.name = userrole.getName();
	}

	/**
	 * Deletes an userrole from the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the userrole.
	 */
	public UserRole deleteUserRole() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the deleted userrole
		UserRole a = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, username);
			pstmt.setInt(2, seasonID);
			pstmt.setInt(3, playID);
			pstmt.setString(4, name);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				a = new UserRole(
						rs.getString("username"), 
						rs.getInt("seasonID"), 
						rs.getInt("playID"), 
						rs.getString("name"),
						rs.getString("role")
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
