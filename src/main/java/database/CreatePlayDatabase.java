package database;

import resource.Item;
import resource.Play;
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


public final class CreatePlayDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "INSERT INTO theater_wa_db.play ( title, description, script, duration, posterimage, rehearsalschedule) VALUES (?, ?, ?, ?, ?, ?) RETURNING *";
    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The employee to be updated in the database
     */
    private final Play play;

    /**
     * Creates a new object for updat an employee.
     *
     * @param con
     *            the connection to the database.
     * @param employee
     *            the employee to be created in the database.
     */
    public CreatePlayDatabase(final Connection con, final Play play) {
        this.con = con;
        this.play = play;
    }
    /**
     * Creates an employee in the database.
     *
     * @return the {@code Employee} object matching the badge.
     *
     * @throws SQLException
     *             if any error occurs while reading the employee.
     */
    public Play createPlay() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the created play
        Play a = null;


        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, play.getTitle());
            pstmt.setString(2, play.getDescription());
            pstmt.setString(3, play.getScript());
            pstmt.setInt(4, play.getDuration());
            pstmt.setString(5, play.getPosterimage());
            pstmt.setString(6, play.getRehearsalschedule());



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
