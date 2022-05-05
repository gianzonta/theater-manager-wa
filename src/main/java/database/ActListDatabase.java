
package database;

import resource.Activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;


public final class ActListDatabase {

	/**
	 * The SQL statementS to be executed
	 */
	private static final String STATEMENT1 = "SELECT activityid, title, description, type, location, dateandtime, privacytag, maxaudience, audiencesize, seasonid FROM theater_wa_db.activity WHERE dateandtime > ? ORDER BY dateandtime ASC";

	private static final String STATEMENT2 = "SELECT activityid, title, description, type, location, dateandtime, privacytag, maxaudience, audiencesize, seasonid FROM theater_wa_db.activity WHERE dateandtime < ? ORDER BY dateandtime DESC LIMIT ? OFFSET ? ";
	
	private static final String STATEMENT3 = "SELECT COUNT (*) AS actcounter FROM theater_wa_db.activity";

	private static final String STATEMENT4 = "SELECT AVG(CAST(audiencesize AS float)/CAST(maxaudience AS float)) AS audindex FROM theater_wa_db.activity WHERE maxaudience != 0 AND audiencesize != 0";
	
	private static final String STATEMENT5 = "SELECT SUM(audiencesize) AS totaudience FROM theater_wa_db.activity";

	private static final String STATEMENT6 = "SELECT activityid, title, description, type, location, dateandtime, privacytag, maxaudience, audiencesize, seasonid FROM theater_wa_db.activity WHERE dateandtime > ? ORDER BY dateandtime ASC LIMIT 5";
	
	private static final String STATEMENT7 = "SELECT * FROM theater_wa_db.activity";
	private static final String COUNT = "SELECT count(*) as total FROM theater_wa_db.activity";
	private static final String AUDIENCE = "SELECT SUM(AudienceSize) as total FROM theater_wa_db.activity";
	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * Creates a new object for searching employees by salary.
	 * 
	 * @param con
	 *            the connection to the database.
	 */
	public ActListDatabase(final Connection con) {
		this.con = con;
	}

	/**
	 * Searches employees by their salary.
	 * 
	 * @return a list of {@code Employee} object matching the salary.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for employees.
	 */
	public List<Activity> listUpcomingAct() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Activity> upActList = new ArrayList<Activity>();

		try {
			pstmt = con.prepareStatement(STATEMENT1);
			
            Timestamp now = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(1, now);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				upActList.add(new Activity(
                        rs.getInt("activityid"), 
						rs.getString("title"), 
                        rs.getString("description"), 
                        rs.getString("type"),
                        rs.getString("location"),
                        rs.getString("dateandtime"),
                        rs.getString("privacytag"),
						rs.getInt("maxaudience"),
                        rs.getInt("audiencesize"),
                        rs.getInt("seasonid")
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

		return upActList;
	}

	public List<Activity> listPastAct(int o,int n) throws SQLException {

		PreparedStatement pstmt = null; //initialiazation
		ResultSet rs = null;

		// the results of the search
		final List<Activity> pastActList = new ArrayList<Activity>();

		try {
			pstmt = con.prepareStatement(STATEMENT2);
			
            Timestamp now = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(1, now);
			pstmt.setInt(2, n);
			pstmt.setInt(3, o);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				pastActList.add(new Activity(
                        rs.getInt("activityid"), 
						rs.getString("title"), 
                        rs.getString("description"), 
                        rs.getString("type"),
                        rs.getString("location"),
                        rs.getString("dateandtime"),
                        rs.getString("privacytag"),
						rs.getInt("maxaudience"),
                        rs.getInt("audiencesize"),
                        rs.getInt("seasonid")
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

		return pastActList;
	}

	public int countActivities() throws SQLException {

		// the results of the search
		int aCount = 0;
		/*double aIndex = 0;
		int aTot = 0;*/

		PreparedStatement pstmt = null; //initialiazation
		ResultSet rs = null;

		try {
			pstmt= con.prepareStatement(STATEMENT3);
			rs = pstmt.executeQuery();

			while (rs.next()) {
						aCount =  rs.getInt("actcounter");}

		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

		return aCount;
	}

	
	public double getAudIndex() throws SQLException {

		// the results of the search
		double aIndex = 0;

		PreparedStatement pstmt = null; //initialiazation
		ResultSet rs = null;

		try {
			pstmt= con.prepareStatement(STATEMENT4);
			rs = pstmt.executeQuery();

			while (rs.next()) {
						aIndex =  rs.getDouble("audindex");}

		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

		return aIndex;
	}


	public int getTotAudience() throws SQLException {

		// the results of the search
		int aTot = 0;

		PreparedStatement pstmt = null; //initialiazation
		ResultSet rs = null;

		try {
			pstmt= con.prepareStatement(STATEMENT5);
			rs = pstmt.executeQuery();

			while (rs.next()) {
						aTot =  rs.getInt("totaudience");}

		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

		return aTot;
	}

	public List<Activity> listUpcomingFive() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Activity> upActList = new ArrayList<Activity>();

		try {
			pstmt = con.prepareStatement(STATEMENT6);
			
            Timestamp now = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(1, now);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				upActList.add(new Activity(
                        rs.getInt("activityid"), 
						rs.getString("title"), 
                        rs.getString("description"), 
                        rs.getString("type"),
                        rs.getString("location"),
                        rs.getString("dateandtime"),
                        rs.getString("privacytag"),
						rs.getInt("maxaudience"),
                        rs.getInt("audiencesize"),
                        rs.getInt("seasonid")
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

		return upActList;
	}

	public List<Activity> listActivity() throws SQLException {

		PreparedStatement pstmt = null; //initialiazation
		ResultSet rs = null;

		// the results of the search
		final List<Activity> actList = new ArrayList<Activity>();

		try {
			pstmt = con.prepareStatement(STATEMENT7);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				actList.add(new Activity(
                        rs.getInt("activityid"), 
						rs.getString("title"), 
                        rs.getString("description"), 
                        rs.getString("type"),
                        rs.getString("location"),
                        rs.getString("dateandtime"),
                        rs.getString("privacytag"),
						rs.getInt("maxaudience"),
                        rs.getInt("audiencesize"),
                        rs.getInt("seasonid")
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

		return actList;
	}

	/**
	 * Returns the total number of activities.
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

	/**
	 * Returns the total audience size.
	 * 
	 * @return a list of {@code Member} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for members.
	 */
	public int audience() throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int audience = 0;

		try {
			pstmt = con.prepareStatement(AUDIENCE);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				audience = rs.getInt("total");
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

		return audience;
	}

}
