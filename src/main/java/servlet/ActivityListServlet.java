/*
 * Copyright 2018 University of Padua, Italy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package servlet;

import database.ActListDatabase;
import resource.Activity;
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
public final class ActivityListServlet extends AbstractDatabaseServlet {

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

		if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager") && !((String) req.getSession(false).getAttribute("uGroup")).equals("Director"))
			{req.getRequestDispatcher("404.jsp").forward(req, res);}

		// data model 1 - upcoming
		List<Activity> upActList = null;
		Message m = null;

		try {

			// creates a new object for accessing the database and gets the upcoming events
			upActList = new ActListDatabase(getDataSource().getConnection())
					.listUpcomingAct();

			m = new Message("Upcoming activities successfully obtained.");
			
		} catch (SQLException ex) {
				m = new Message("Cannot obtain upcoming activities: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}
		// stores the activity list and the message as a request attribute
		req.setAttribute("uActivityList", upActList);
		req.setAttribute("uActMessage", m);

		
		//data model 2 - 
		List<Activity> pastActList = null;
		m = null; // re-initialization

		try {

			// creates a new object for accessing the database and gets the upcoming events
			pastActList = new ActListDatabase(getDataSource().getConnection())
					.listPastAct(0,10); //past plays database, it selects n rows with offset o - in this preview page offset = 0, rows = 10

			m = new Message("Past activities successfully obtained.");
			
		} catch (SQLException ex) {
				m = new Message("Cannot obtain past activities: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}
		// stores the past activity list and the message as a request attribute
		req.setAttribute("pActivityList", pastActList);
		req.setAttribute("pActMessage", m);

		//-------------- COUNTER
		int cAct = 0;
		m = null;

		try {

			// creates a new object for accessing the database and gets the upcoming events
			cAct = new ActListDatabase(getDataSource().getConnection())
					.countActivities(); //past plays database, it selects n rows with offset o - in this preview page offset = 0, rows = 10

			m = new Message("Past activities successfully obtained.");
			
		} catch (SQLException ex) {
				m = new Message("Cannot obtain activities stats: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}

		req.setAttribute("cActivity", cAct);
		req.setAttribute("cActMessage", m);


		//-------------- AUDIENCE INDEX
		double aiAct = 0;
		m = null;

		try {

			// creates a new object for accessing the database and gets the upcoming events
			aiAct = new ActListDatabase(getDataSource().getConnection())
					.getAudIndex(); //past plays database, it selects n rows with offset o - in this preview page offset = 0, rows = 10

			m = new Message("Past activities successfully obtained.");
			
		} catch (SQLException ex) {
				m = new Message("Cannot obtain activities stats: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}

		req.setAttribute("aiActivity", aiAct);
		req.setAttribute("aiActMessage", m);


		//-------------- TOT AUDIENCE
		int tAct = 0;
		m = null;

		try {
			// creates a new object for accessing the database and gets the upcoming events
			tAct = new ActListDatabase(getDataSource().getConnection())
					.getTotAudience(); //past plays database, it selects n rows with offset o - in this preview page offset = 0, rows = 10

			m = new Message("total audience successfully obtained.");
			
		} catch (SQLException ex) {
				m = new Message("Cannot obtain total audience: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}

		req.setAttribute("tActivity", tAct);
		req.setAttribute("tActMessage", m);


		// forwards the control to the activitylist.jsp for display
		req.getRequestDispatcher("activitylist.jsp").forward(req, res);

	}

}
