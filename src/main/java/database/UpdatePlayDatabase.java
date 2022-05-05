package database;
import resource.Activity;
import resource.Play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;


public final class UpdatePlayDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE theater_wa_db.play SET title = ?, description = ?, script = ?, duration = ?, posterimage = ?, rehearsalschedule = ? WHERE playid = ? RETURNING *";

    /**
     * The connection to the database
     */
    private final Connection con;


    private final Play play;

    public UpdatePlayDatabase(final Connection con, final Play play) {
        this.con = con;
        this.play = play;
    }

    public Play updatePlay() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the updated employee
        Play a = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, play.getTitle());
            pstmt.setString(2, play.getDescription());
            pstmt.setString(3, play.getScript());
            pstmt.setInt(4, play.getDuration());
            pstmt.setString(5, play.getPosterimage());
            pstmt.setString(6, play.getRehearsalschedule());

            pstmt.setInt(7, play.getPlayid());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                a = new Play(
                        rs.getInt("playID"),
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
