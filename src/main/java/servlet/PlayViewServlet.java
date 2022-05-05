package servlet;

import database.ReadPlayDatabase;
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


public final class PlayViewServlet extends AbstractDatabaseServlet{


    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int playid = Integer.parseInt(req.getParameter("playid"));


        // select a single play
        Play a = null;
        Message m = null;

        try {

            // creates a new object for accessing the database and gets the upcoming events
            a = new ReadPlayDatabase(getDataSource().getConnection(),playid)
                    .readPlay();

            m = new Message("Upcoming play successfully obtained.");

        } catch (SQLException ex) {
            m = new Message("Cannot obtain upcoming plays: unexpected error while accessing the database.",
                    "E200", ex.getMessage());
        }
        // stores the activity list and the message as a request attribute
        req.setAttribute("play", a);
        req.setAttribute("message", m);


        // forwards the control to the activitylist.jsp for display
        req.getRequestDispatcher("playview.jsp").forward(req, res);

    }


}
