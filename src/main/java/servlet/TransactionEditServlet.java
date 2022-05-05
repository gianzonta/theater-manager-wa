package servlet;

import database.ReadTransactionDatabase;
import resource.Transaction;
import resource.*;


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



public final class TransactionEditServlet extends AbstractDatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {

            if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager"))
				{req.getRequestDispatcher("404.jsp").forward(req, res);}

            int transid = Integer.parseInt(req.getParameter("transid"));


            // select a single activity
            Transaction tra = null;
            Message m = null;

            try {

                // creates a new object for accessing the database and gets the upcoming events
                tra = new ReadTransactionDatabase(getDataSource().getConnection(),transid)
                        .readTransaction();

                m = new Message("Transaction successfully obtained");
                
            } catch (SQLException ex) {
                    m = new Message("Cannot obtain upcoming activities: unexpected error while accessing the database.", 
                            "E200", ex.getMessage());
            }
            // stores the activity list and the message as a request attribute
            req.setAttribute("transaction", tra);
            req.setAttribute("message", m);


            // forwards the control to the activitylist.jsp for display
            req.getRequestDispatcher("transactionedit.jsp").forward(req, res);

        }

}