package database;

import resource.Activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Deletes an employee from the database.
 * 
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class DeleteActivityDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "DELETE FROM theater_wa_db.activity WHERE activityid = ? RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The badge of the employee
	 */
	private final int activityid;

	/**
	 * Creates a new object for deleting an employee.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param activityid
	 *            the badge of the employee.
	 */
	public DeleteActivityDatabase(final Connection con, final int activityid) {
		this.con = con;
		this.activityid = activityid;
	}

	/**
	 * Deletes an employee from the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public Activity deleteActivity() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the deleted activity
		Activity a = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, activityid);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				a = new Activity(
                        rs.getInt("activityID"), 
						rs.getString("title"), 
                        rs.getString("description"), 
                        rs.getString("type"),
                        rs.getString("location"),
                        rs.getString("dateandtime"),
                        rs.getString("privacytag"),
						rs.getInt("maxaudience"),
                        rs.getInt("audiencesize"),
                        rs.getInt("seasonid")
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
