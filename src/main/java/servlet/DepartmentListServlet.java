package servlet;

import database.ListDepartmentDatabase;
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

public final class DepartmentListServlet extends AbstractDatabaseServlet {

	/**
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


		// fetch departments
		List<Department> upDepList = null;
		Message m = null;

		try {

			// creates a new object for accessing the database and gets the departments
			upDepList = new ListDepartmentDatabase(getDataSource().getConnection())
					.listDepartment();

			m = new Message("Departments successfully obtained.");
			
		} catch (SQLException ex) {
				m = new Message("Cannot obtain departments: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}
		// stores the department list and the message as a request attribute
		req.setAttribute("uDepartmentList", upDepList);
		req.setAttribute("uDepMessage", m);

		// forwards the control to the departmentlist.jsp for display
		req.getRequestDispatcher("departmentlist.jsp").forward(req, res);

	}

}
