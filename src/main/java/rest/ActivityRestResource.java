package rest;

import database.*;
import resource.*;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public final class ActivityRestResource extends RestResource {

	/**
	 * Creates a new REST resource for managing {@code Employee} resources. - CONSTRUCTOR
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @param con the connection to the database.
	 */
	public ActivityRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
		super(req, res, con);
	}


	/**
	 * Creates a new ACTIVITY into the database. 
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void createActivity() throws IOException {

		Activity a  = null;
		Message m = null;

		try{

			final Activity activity =  Activity.fromJSON(req.getInputStream()); //reads the activity from the request

			// creates a new object for accessing the database and stores the employee
			a = new CreateActivityDatabase(con, activity).createActivity(); // database helper class

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_CREATED);
				a.toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot create the activity: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
				m = new Message("Cannot create the activity: it already exists.", "E5A2", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot create the activity: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}

	/**
	 * Reads an activity from the database.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */ 
	 public void readActivity() throws IOException {

		Activity a  = null;
		Message m = null;

		try{
			// parse the URI path to extract the badge
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("activity") + 8);

			final int activityid = Integer.parseInt(path.substring(1)); // takes only the numbers, after the "/"


			// creates a new object for accessing the database and reads the employee
			a = new ReadActivityDatabase(con, activityid).readActivity();

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Activity %d not found.", activityid), "E5A3", null);
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot read activity: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

	}

	/**
	 * Updates an activity in the database.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */ 
	public void updateActivity() throws IOException {

		Activity a  = null;
		Message m = null;

		try{
			// parse the URI path to extract the badge
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("activity") + 8);

			final int activityid = Integer.parseInt(path.substring(1));
			final Activity updatedAct = Activity.fromJSON(req.getInputStream());

			if (activityid != updatedAct.getActivityID()) {
				m = new Message(
						"Wrong request for URI /activity/{activityid}: URI request and employee resource IDs differ.",
						"E4A7", String.format("Request URI id %d; uploaded activity resource  id %d.",
											  activityid, updatedAct.getActivityID()));
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				m.toJSON(res.getOutputStream());
				return;
			}

			// creates a new object for accessing the database and updates the employee
			a = new UpdateActivityDatabase(con, updatedAct).updateActivity();

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Activity %d not found.", updatedAct.getActivityID()), "E5A3", null);
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23503")) {
				m = new Message("Cannot update the employee: other resources depend on it.", "E5A4", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot update the employee: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}
    


	public void deleteActivity() throws IOException {

		Activity a  = null;
		Message m = null;

		try{
			// parse the URI path to extract the badge
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("activity") + 8);

			final int activityid = Integer.parseInt(path.substring(1));


			// creates a new object for accessing the database and deletes the employee
			a = new DeleteActivityDatabase(con, activityid).deleteActivity();

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Activity %d not found.", activityid), "E5A3", null);
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23503")) {
				m = new Message("Cannot delete the activity: other resources depend on it.", "E5A4", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot delete the activity: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	} 

	/**
	 * Searches employees by their salary.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */ /*
	public void searchEmployeeBySalary()  throws IOException {

		List<Employee> el  = null;
		Message m = null;

		try{
			// parse the URI path to extract the salary
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("salary") + 6);

			final int salary = Integer.parseInt(path.substring(1));


			// creates a new object for accessing the database and search the employees
			el = new SearchEmployeeBySalaryDatabase(con, salary).searchEmployeeBySalary();

			if(el != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(el).toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot search employee: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot search employee: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	} */
	/**
	 * Searches transactions by related activity - REST uri formatting: content/transaction/activity/{activityid}
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */ 
	public void searchActivityByTransaction()  throws IOException {

		List<Activity> al  = null;
		Message m = null;

		try{
			// parse the URI path to extract the transaction id
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("transaction") + 11);

			final int transid = Integer.parseInt(path.substring(1));


			// creates a new object for accessing the database and search the employees
			al = new SearchActivityByTransactionDatabase(con, transid).searchActivityByTransaction();

			if(al != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(al).toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot search transactions: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot search transactions: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	} 
	/**
	 * Lists all the employees.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */ 
	public void listActivity() throws IOException { 

		List<Activity> al  = null;
		Message m = null;

		try{
			// creates a new object for accessing the database and lists all the employees
			al = new ActListDatabase(con).listActivity();

			if(al != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(al).toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot list employees: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot search employee: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	

	public void searchActivityByPlay()  throws IOException {

		List<Activity> al  = null;
		Message m = null;

		try{
			// parse the URI path to extract the transaction id
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("play") + 4);

			final int playid = Integer.parseInt(path.substring(1)); // deletes the "/"


			// creates a new object for accessing the database and search the employees
			al = new SearchActivityByPlayDatabase(con, playid).searchActivityByPlay();

			if(al != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(al).toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot search activities: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot search activities: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	public void createActivityPlayRelation() throws IOException {

		Activity a  = null;
		Message m = null;

		try{

			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("play") + 4);

			final int playid = Integer.parseInt(path.substring(1)); // gets the playid

			final Activity activity =  Activity.fromJSON(req.getInputStream()); //reads the activity from the request

			// creates a new object for accessing the database and stores the employee
			a = new CreateActivityPlayRelationDatabase(con, activity, playid).createActivityPlayRelation(); // database helper class

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_CREATED);
				a.toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot create the relation with play: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
				m = new Message("Cannot create the relation with play: it already exists.", "E5A2", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot create the relation with play: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}
}