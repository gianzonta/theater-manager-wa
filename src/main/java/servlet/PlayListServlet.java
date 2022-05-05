package servlet;

import database.ListPlayDatabase;
import resource.Play;
import resource.Message;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Searches employees by their salary.
 *
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */

public final class PlayListServlet extends AbstractDatabaseServlet{

    /**
     * Searches employees by their salary.
     *
     * @param req
     *            the HTTP request from the client.
     * @param res
     *            the HTTP response from the server.
     *
     * @throws ServletException
     *             if any error occurs while executing the servlet.
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        List<Play> plays = null;
        Message m = null;

        try {

            // creates a new object for accessing the database and gets the upcoming events
            plays = new ListPlayDatabase(getDataSource().getConnection())
                    .listPlay();

            m = new Message("Upcoming plays successfully obtained.");

        } catch (SQLException ex) {
            m = new Message("Cannot obtain upcoming plays: unexpected error while accessing the database.",
                    "E200", ex.getMessage()); 
        }
        // stores the play list and the message as a request attribute
        req.setAttribute("uPlayList", plays);
        req.setAttribute("uPlayMessage", m);


        // forwards the control to the playlist.jsp for display
        req.getRequestDispatcher("playlist.jsp").forward(req, res);

    }



}
