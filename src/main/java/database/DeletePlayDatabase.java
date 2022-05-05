package database;
import resource.Activity;
import resource.Play;

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
public class DeletePlayDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM theater_wa_db.play WHERE playid = ? RETURNING *";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The badge of the employee
     */
    private final int playid;

    /**
     * Creates a new object for deleting an employee.
     *
     * @param con
     *            the connection to the database.
     * @param playid
     *            the badge of the employee.
     */

    public DeletePlayDatabase(final Connection con, final int playid) {
        this.con = con;
        this.playid = playid;
    }

    /**
     * Deletes an employee from the database.
     *
     * @return the {@code Employee} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the employee.
     */
    public Play deletePlay() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the deleted play
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
}
