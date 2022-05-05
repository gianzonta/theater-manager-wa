package servlet;

import database.ReadActivityDatabase;
import database.ReadPlayDatabase;
import resource.Activity;
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



public final class ActivityEditServlet extends AbstractDatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {

        if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager") && !((String) req.getSession(false).getAttribute("uGroup")).equals("Director"))
        {req.getRequestDispatcher("404.jsp").forward(req, res);}

            int activityid = Integer.parseInt(req.getParameter("activityid"));


            // select a single activity
            Activity a = null;
            Message m1 = null;

            try {

                // creates a new object for accessing the database and gets the upcoming events
                a = new ReadActivityDatabase(getDataSource().getConnection(),activityid)
                        .readActivity();

                m1 = new Message("Upcoming activities successfully obtained.");
                
            } catch (SQLException ex) {
                    m1 = new Message("Cannot obtain upcoming activities: unexpected error while accessing the database.", 
                            "E200", ex.getMessage());
            }
            // stores the activity list and the message as a request attribute
            req.setAttribute("activity", a);
            req.setAttribute("message", m1);


            Play p = null;
            Message m2 = null;


            try {

                // creates a new object for accessing the database and gets the upcoming events
                p = new ReadPlayDatabase(getDataSource().getConnection(),0)
                        .readPlayActivity(a.getActivityID());

                m2 = new Message("Related play successfully obtained.");
                
            } catch (SQLException ex) {
                    m2 = new Message("Cannot obtain related play: unexpected error while accessing the database.", 
                            "E200", ex.getMessage());
            }
            // stores the activity list and the message as a request attribute
            req.setAttribute("play", p);
            req.setAttribute("message", m2);



            // forwards the control to the activitylist.jsp for display
            req.getRequestDispatcher("activityedit.jsp").forward(req, res);

        }

}