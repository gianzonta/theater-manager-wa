package database;

import resource.Activity;
import resource.Season;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.text.*;
import java.util.Date;
import java.util.Iterator;

/**
 * Creates an employee in the database.
 * 
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class CreateActivityDatabase {

	/**
	 * The SQL statement to be executed
	 */

	private static final String STATEMENT1 = "SELECT * FROM theater_wa_db.season";
	
	private static final String STATEMENT2 = "INSERT INTO theater_wa_db.activity ( title, description, type, location, dateandtime, privacytag, maxaudience, audiencesize, seasonid) VALUES (?, ?, ?, ?, ?, CAST(? AS PRIVACY), ?, ?, ?) RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The employee to be updated in the database
	 */
	private final Activity activity;

	/**
	 * Creates a new object for updat an employee.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param employee
	 *            the employee to be created in the database.
	 */
	public CreateActivityDatabase(final Connection con, final Activity activity) {
		this.con = con;
		this.activity = activity;
	}

	/**
	 * Creates an employee in the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public Activity createActivity() throws SQLException {

		
		//1. search for corresponding season (based on date)

		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;

		final List<Season> seasonList = new ArrayList<Season>();

		try {
			pstmt1 = con.prepareStatement(STATEMENT1);
			rs1 = pstmt1.executeQuery();

			while (rs1.next()) {
					seasonList.add(new Season(
                        rs1.getInt("seasonid"), 
						rs1.getString("period"), 
                        rs1.getDouble("revenues"), 
                        rs1.getDouble("expenses"),
                        rs1.getDouble("initialFund")
                        ));
			}
		} finally {
			if (rs1 != null) {
				rs1.close();
			}

			if (pstmt1 != null) {
				pstmt1.close();
			}

		}

		Season selectedSeason = null;
		int i = 0;
		while (i < seasonList.size()){
			Season s = seasonList.get(i);
			if (activityInSeason(activity, s))
			{
				selectedSeason = s;
			}
			i++;
		}
		
		
		
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;

		// the created activity
		Activity a = null;

		try {
			pstmt2 = con.prepareStatement(STATEMENT2);
			pstmt2.setString(1, activity.getTitle());
			pstmt2.setString(2, activity.getDescription());
			pstmt2.setString(3, activity.getType());
			pstmt2.setString(4, activity.getLocation());

			Timestamp ts = Timestamp.valueOf(activity.getDate());
            pstmt2.setTimestamp(5, ts);
            pstmt2.setString(6, activity.getPrivacyTag());
            pstmt2.setInt(7, activity.getMaxAudience());
            pstmt2.setInt(8, activity.getAudienceSize());
            pstmt2.setInt(9, selectedSeason.getSeasonID());

			rs2 = pstmt2.executeQuery();

			if (rs2.next()) {
				a = new Activity(
                        rs2.getInt("activityID"), 
						rs2.getString("title"), 
                        rs2.getString("description"), 
                        rs2.getString("type"),
                        rs2.getString("location"),
                        rs2.getString("dateandtime"),
                        rs2.getString("privacytag"),
						rs2.getInt("maxaudience"),
                        rs2.getInt("audiencesize"),
                        rs2.getInt("seasonid")
                        );
			}
		} finally {
			if (rs2 != null) {
				rs2.close();
			}

			if (pstmt2 != null) {
				pstmt2.close();
			}

			con.close();
		}

		return a;
	}

	public boolean activityInSeason(Activity a, Season s)  {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date src = null;
		Date start = null;
		Date end = null;

		try {
			src = sdformat.parse(a.getDate());
			start = sdformat.parse(s.getStartDate());
			end = sdformat.parse(s.getEndDate());
		} catch (Exception e)
		{
			System.out.println("Error parsing date");
		}
		return (src.compareTo(start) > 0) && (src.compareTo(end) < 0);
	}
}