package servlet;

import database.ReadMemberDatabase;
import resource.Member;
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



public final class EditProfileServlet extends AbstractDatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {

            String memberid = req.getParameter("username");


            // select a single activity
            Member a = null;
            Message m = null;

            try {

                // creates a new object for accessing the database and gets the upcoming events
                a = new ReadMemberDatabase(getDataSource().getConnection(),memberid)
                        .readMember();

                m = new Message("User profile successfully collected.");
                
            } catch (SQLException ex) {
                    m = new Message("Cannot fetch user profile: unexpected error while accessing the database.", 
                            "E200", ex.getMessage());
            }
            // stores the activity list and the message as a request attribute
            req.setAttribute("username", a);
            req.setAttribute("message", m);


            // forwards the control to the activitylist.jsp for display
            req.getRequestDispatcher("editprofile.jsp").forward(req, res);

        }

}