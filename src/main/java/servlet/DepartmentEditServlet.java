package servlet;

import database.ReadDepartmentDatabase;
import resource.Department;
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



public final class DepartmentEditServlet extends AbstractDatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {

            String name = req.getParameter("name");


            // select a single department
            Department a = null;
            Message m = null;

            try {

                // creates a new object for accessing the database and gets the departments
                a = new ReadDepartmentDatabase(getDataSource().getConnection(),name)
                        .readDepartment();

                m = new Message("Departments successfully obtained.");
                
            } catch (SQLException ex) {
                    m = new Message("Cannot obtain departments: unexpected error while accessing the database.", 
                            "E200", ex.getMessage());
            }
            // stores the department list and the message as a request attribute
            req.setAttribute("department", a);
            req.setAttribute("message", m);


            // forwards the control to the editdepartment.jsp for display
            req.getRequestDispatcher("editdepartment.jsp").forward(req, res);

        }

}