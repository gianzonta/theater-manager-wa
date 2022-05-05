package servlet;

import database.ActListDatabase;
import database.ListMemberDatabase;
import database.ListTransactionDatabase;
import database.ListPlayDatabase;

import resource.Activity;
import resource.Message;
import resource.Member;
import resource.Transaction;
import resource.Play;


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

public final class DashboardServlet extends AbstractDatabaseServlet {

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


		// fetch activities
		List<Activity> upActList = null;
		Message m = null;

		try {

			// creates a new object for accessing the database and gets the upcoming events
			upActList = new ActListDatabase(getDataSource().getConnection())
					.listUpcomingFive();

			m = new Message("Upcoming activities successfully obtained.");
			
		} catch (SQLException ex) {
				m = new Message("Cannot obtain upcoming activities: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}
		// stores the activity list and the message as a request attribute
		req.setAttribute("uActivityList", upActList);
		req.setAttribute("uActMessage", m);


		// fetch members
		List<Member> upMemberList = null;
		Message m2 = null;

		try {

			// creates a new object for accessing the database and gets the upcoming events
			upMemberList = new ListMemberDatabase(getDataSource().getConnection())
					.lastFiveMembers();

			m = new Message("Last 5 members successfully obtained.");
			
		} catch (SQLException ex) {
				m = new Message("Cannot obtain last 5 members: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}
		// stores the members list and the message as a request attribute
		req.setAttribute("uMembersList", upMemberList);
		req.setAttribute("uMembersListMessage", m2);

		// fetch transactions
		List<Transaction> upTransactionList = null;
		Message m3 = null;

		try {

			// creates a new object for accessing the database and gets the upcoming events
			upTransactionList = new ListTransactionDatabase(getDataSource().getConnection())
					.lastFiveTransactions();

			m = new Message("Last 5 transactions successfully obtained.");
			
		} catch (SQLException ex) {
				m = new Message("Cannot obtain last 5 transactions: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}
		// stores the activity list and the message as a request attribute
		req.setAttribute("uTransactionList", upTransactionList);
		req.setAttribute("uTransactionListMessage", m3);

		// fetch plays
		List<Play> upPlayList = null;
		Message m4 = null;

		try {

			// creates a new object for accessing the database and gets the upcoming events
			upPlayList = new ListPlayDatabase(getDataSource().getConnection())
					.lastFivePlays();

			m = new Message("Last 5 plays successfully obtained.");
			
		} catch (SQLException ex) {
				m = new Message("Cannot obtain last 5 plays: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}
		// stores the activity list and the message as a request attribute
		req.setAttribute("uPlayList", upPlayList);
		req.setAttribute("uPlayListMessage", m4);

		// count members
		int countMembers = 0;
		Message m5 = null;

		try {
			// creates a new object for accessing the database and gets the count
			countMembers = new ListMemberDatabase(getDataSource().getConnection()).count();
			m5 = new Message("Members count successfully obtained.");
			
		} catch (SQLException ex) {
				m5 = new Message("Cannot obtain members count: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}
		// stores the members list and the message as a request attribute
		req.setAttribute("uMemberCount", countMembers);
		req.setAttribute("uMemberCountMessage", m5);

		// count plays
		int countPlays = 0;
		Message m6 = null;

		try {
			// creates a new object for accessing the database and gets the count
			countPlays = new ListPlayDatabase(getDataSource().getConnection()).count();
			m6 = new Message("Plays count successfully obtained.");
			
		} catch (SQLException ex) {
				m6 = new Message("Cannot obtain plays count: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}
		// stores the members list and the message as a request attribute
		req.setAttribute("uPlayCount", countPlays);
		req.setAttribute("uPlayCountMessage", m6);

		// count activities
		int countActivities = 0;
		Message m7 = null;

		try {
			// creates a new object for accessing the database and gets the count
			countActivities = new ActListDatabase(getDataSource().getConnection()).count();
			m7 = new Message("Activities count successfully obtained.");
			
		} catch (SQLException ex) {
				m7 = new Message("Cannot obtain activities count: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}
		// stores the members list and the message as a request attribute
		req.setAttribute("uActivityCount", countActivities);
		req.setAttribute("uActivityCountMessage", m7);

		// count audience size
		int countAudienceSize = 0;
		Message m8 = null;

		try {
			// creates a new object for accessing the database and gets the count
			countAudienceSize = new ActListDatabase(getDataSource().getConnection()).audience();
			m7 = new Message("Audience size count successfully obtained.");
			
		} catch (SQLException ex) {
				m7 = new Message("Cannot obtain audience size count: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}
		// stores the members list and the message as a request attribute
		req.setAttribute("uAudienceSizeCount", countAudienceSize);
		req.setAttribute("uAudienceSizeCountMessage", m8);


		// forwards the control to the dashboard.jsp for display
		req.getRequestDispatcher("dashboard.jsp").forward(req, res);

	}

}
