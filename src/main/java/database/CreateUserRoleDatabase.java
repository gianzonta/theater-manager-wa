package database;

import resource.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public final class CreateUserRoleDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO theater_wa_db.userrole (username, seasonID, playID, name, role) VALUES (CAST(? AS NAMEDOM), ?, ?, CAST(? AS NAMEDOM), CAST(? AS NAMEDOM)) RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The userrole to be updated in the database
	 */
	private final UserRole userrole;

	/**
	 * Creates a new object for updat an userrole.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param userrole
	 *            the userrole to be created in the database.
	 */
	public CreateUserRoleDatabase(final Connection con, final UserRole userrole) {
		this.con = con;
		this.userrole = userrole;
	}

	/**
	 * Creates an userrole in the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the userrole.
	 */
	public UserRole createUserRole() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the create userrole
		UserRole a = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, userrole.getUsername());
			pstmt.setInt(2, userrole.getSeasonID());
			pstmt.setInt(3, userrole.getPlayID());
			pstmt.setString(4, userrole.getName());
            pstmt.setString(5, userrole.getRole());
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