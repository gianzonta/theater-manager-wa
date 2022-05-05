package database;

import resource.Play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public final class ListPlayDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT * FROM theater_wa_db.Play ORDER BY PlayID DESC LIMIT 5";
	private static final String STATEMENT2 = "SELECT * FROM theater_wa_db.Play";
	private static final String COUNT = "SELECT count(*) as total FROM theater_wa_db.Play";

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
	public ListPlayDatabase(final Connection con) {
		this.con = con;
	}

	/**
	 * Lists the last 5 plays ordered DESC by PlayID.
	 * 
	 * @return a list of {@code Member} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for members.
	 */
	public List<Play> lastFivePlays() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Play> plays = new ArrayList<Play>();

		try {
			pstmt = con.prepareStatement(STATEMENT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				plays.add(new Play(
                            rs.getInt("PlayID"), 
                            rs.getString("Title"), 
                            rs.getString("Description"), 
                            rs.getString("Script"), 
                            rs.getInt("Duration"),
								rs.getString("posterimage"),
								rs.getString("rehearsalschedule")
                            )
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

		return plays;
	}

	public List<Play> listPlay() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Play> plays = new ArrayList<Play>();

		try {
			pstmt = con.prepareStatement(STATEMENT2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				plays.add(new Play(
                            rs.getInt("PlayID"), 
                            rs.getString("Title"), 
                            rs.getString("Description"), 
                            rs.getString("Script"), 
                            rs.getInt("Duration"),
							rs.getString("posterimage"),
							rs.getString("rehearsalschedule")
                            )
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

		return plays;
	}

	/**
	 * Returns the total number of plays.
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
