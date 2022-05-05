package database;


import resource.Play;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads an employee from the database.
 *
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */

public final class ReadPlayDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT playid, title, description, script, duration, posterimage,rehearsalschedule  FROM theater_wa_db.play WHERE playid = ?";

    private static final String STATEMENT2 = "SELECT P.playid, title, description, script, duration, posterimage,rehearsalschedule  FROM theater_wa_db.play AS P INNER JOIN theater_wa_db.schedule AS S ON S.playid = P.playid WHERE activityid = ?";
    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The badge of the employee
     */
    private final int playid;

    /**
     * Creates a new object for reading an employee.
     *
     * @param con
     *            the connection to the database.
     * @param salary
     *            the badge of the employee.
     */
    public ReadPlayDatabase(final Connection con, final int playid) {
        this.con = con;
        this.playid = playid;
    }
    public Play readPlay() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the read employee
        Play a = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, playid);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                a = new Play(
                        rs.getInt("playid"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("script"),
                        rs.getInt("duration"),
                        rs.getString("posterimage"),
                        rs.getString("rehearsalschedule")
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

    public Play readPlayActivity(int actid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the read employee
        Play a = null;

        try {
            pstmt = con.prepareStatement(STATEMENT2);
            pstmt.setInt(1, actid);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                a = new Play(
                        rs.getInt("playid"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("script"),
                        rs.getInt("duration"),
                        rs.getString("posterimage"),
                        rs.getString("rehearsalschedule")
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
