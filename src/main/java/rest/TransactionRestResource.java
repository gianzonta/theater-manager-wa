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


public final class TransactionRestResource extends RestResource {

	/**
	 * Creates a new REST resource for managing {@code Employee} resources. - CONSTRUCTOR
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @param con the connection to the database.
	 */
	public TransactionRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
		super(req, res, con);
	}


	/**
	 * Creates a new ACTIVITY into the database. 
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void createTransaction() throws IOException {

		Transaction newT  = null;
		Message m = null;

		try{

			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager") && !((String) req.getSession(false).getAttribute("uGroup")).equals("Director"))
				{throw new ForbiddenException();}

				
			final Transaction trans =  Transaction.fromJSON(req.getInputStream()); //reads the activity from the request

			// creates a new object for accessing the database and stores the employee
			newT= new CreateTransactionDatabase(con, trans).createTransaction(); // database helper class, newT has the returned database class

			if(newT != null) {
				res.setStatus(HttpServletResponse.SC_CREATED);
				newT.toJSON(res.getOutputStream());
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
	 public void readTransaction() throws IOException {

		Transaction transact  = null;
		Message m = null;

		try{
			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager") && !((String) req.getSession(false).getAttribute("uGroup")).equals("Director"))
				{throw new ForbiddenException();}
			// parse the URI path to extract the badge
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("transaction") + 11);

			final int transid = Integer.parseInt(path.substring(1)); // takes only the numbers, after the "/"


			// creates a new object for accessing the database and reads the employee
			transact = new ReadTransactionDatabase(con, transid).readTransaction();

			if(transact != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				transact.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Activity %d not found.", transid), "E5A3", null);
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
	public void updateTransaction() throws IOException {

		Transaction transact  = null;
		Message m = null;

		try{
			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager") && !((String) req.getSession(false).getAttribute("uGroup")).equals("Director"))
				{throw new ForbiddenException();}

			// parse the URI path to extract the badge
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("transaction") + 11);

			final int transid = Integer.parseInt(path.substring(1)); //id taken from uri
			final Transaction updatedTrans = Transaction.fromJSON(req.getInputStream()); // transaction taken from request jason

			if (transid != updatedTrans.getTransID()) {
				m = new Message(
						"Wrong request for URI /transaction/{transid}: URI request and transaction resource IDs differ.",
						"E4A7", String.format("Request URI id %d; uploaded transaction resource  id %d.",
											  transid, updatedTrans.getTransID()));
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				m.toJSON(res.getOutputStream());
				return;
			}

			// creates a new object for accessing the database and updates the employee
			transact = new UpdateTransactionDatabase(con, updatedTrans).updateTransaction();

			if(transact != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				transact.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Activity %d not found.", updatedTrans.getTransID()), "E5A3", null);
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
    


	public void deleteTransaction() throws IOException {

		Transaction transact  = null;
		Message m = null;

		try{
			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager") && !((String) req.getSession(false).getAttribute("uGroup")).equals("Director"))
				{throw new ForbiddenException();}

			// parse the URI path to extract the badge
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("transaction") + 11);

			final int transid = Integer.parseInt(path.substring(1));


			// creates a new object for accessing the database and deletes the employee
			transact = new DeleteTransactionDatabase(con, transid).deleteTransaction();

			if(transact != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				transact.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Transaction %d not found.", transid), "E5A3", null);
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
	public void searchTransactionByActivity()  throws IOException {

		List<Transaction> tl  = null;
		Message m = null;

		try{
			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager") && !((String) req.getSession(false).getAttribute("uGroup")).equals("Director"))
				{throw new ForbiddenException();}
			

			// parse the URI path to extract the salary
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("activity") + 8);

			final int activityid = Integer.parseInt(path.substring(1));


			// creates a new object for accessing the database and search the employees
			tl = new SearchTransactionByActivityDatabase(con, activityid).searchTransactionByActivity();

			if(tl != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(tl).toJSON(res.getOutputStream());
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
	public void listTransaction() throws IOException,ForbiddenException {

		List<Transaction> el  = null;
		Message m = null;

		try{

			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager") && !((String) req.getSession(false).getAttribute("uGroup")).equals("Director"))
				{throw new ForbiddenException();}

			// creates a new object for accessing the database and lists all the employees
			el = new ListTransactionDatabase(con).listTransaction();

			if(el != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(el).toJSON(res.getOutputStream());
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

	/**
	 * Creates a transaction -> activity relation - REST uri formatting: content/transaction/activity/{activityid} method: POST
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */ 
	public void createTransactionActivityRelation()  throws IOException {
	
		Transaction te  = null; // the replied transaction, same as sent if everithin is good ((I don't know if it is of any use)
		Message m = null;

		try{
			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager") && !((String) req.getSession(false).getAttribute("uGroup")).equals("Director"))
				{throw new ForbiddenException();}
			// parse the URI path to extract the activity
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("activity") + 8);

			final int activityid = Integer.parseInt(path.substring(1)); // gets the activityid

			final Transaction trans =  Transaction.fromJSON(req.getInputStream()); // gets the full transaction

			// creates a new object for accessing the database and creates the relation having the two actid-transid variables
			te = new CreateTransactionActivityRelationDatabase(con, activityid, trans).createTransactionActivityRelation();

			if(te != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				te.toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot search transactions: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
				m = new Message("Cannot create the relation: it already exists.", "E5A2", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot create the activity: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	} 

	public void deleteTransactionActivityRelation()  throws IOException {
	
		Transaction te  = null; // the replied transaction, same as sent if everithin is good ((I don't know if it is of any use)
		Message m = null;

		try{
			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager") && !((String) req.getSession(false).getAttribute("uGroup")).equals("Director"))
				{throw new ForbiddenException();}
			// parse the URI path to extract the activity
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("activity") + 8);

			final int activityid = Integer.parseInt(path.substring(1)); // gets the activityid

			final Transaction trans =  Transaction.fromJSON(req.getInputStream()); // gets the full transaction

			// creates a new object for accessing the database and creates the relation having the two actid-transid variables
			te = new DeleteTransactionActivityRelationDatabase(con, activityid, trans).deleteTransactionActivityRelation();

			if(te != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				te.toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot delete transaction-activity relation: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
				m = new Message("Cannot create the relation: it already exists.", "E5A2", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot create the activity: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}


	public void createTransactionItemRelation()  throws IOException {
	
		Transaction te  = null; // the replied transaction, same as sent if everithin is good ((I don't know if it is of any use)
		Message m = null;

		try{
			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager") && !((String) req.getSession(false).getAttribute("uGroup")).equals("Director"))
				{throw new ForbiddenException();}
			// parse the URI path to extract the activity
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("item") + 4);

			final int itemid = Integer.parseInt(path.substring(1)); // gets the activityid

			final Transaction trans =  Transaction.fromJSON(req.getInputStream()); // gets the full transaction from client

			// creates a new object for accessing the database and creates the relation having the two actid-transid variables
			te = new CreateTransactionItemRelationDatabase(con, itemid, trans).createTransactionItemRelation();

			if(te != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				te.toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot create transaction-item relation: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
				m = new Message("Cannot create the relation: it already exists.", "E5A2", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot create the relation: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}

	public void deleteTransactionItemRelation()  throws IOException {
	
		Transaction te  = null; // the replied transaction, same as sent if everithin is good ((I don't know if it is of any use)
		Message m = null;

		try{

			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager") && !((String) req.getSession(false).getAttribute("uGroup")).equals("Director"))
				{throw new ForbiddenException();}
			// parse the URI path to extract the activity
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("item") + 4);

			final int itemid = Integer.parseInt(path.substring(1)); // gets the activityid

			final Transaction trans =  Transaction.fromJSON(req.getInputStream()); // gets the full transaction

			// creates a new object for accessing the database and creates the relation having the two actid-transid variables
			te = new DeleteTransactionItemRelationDatabase(con, itemid, trans).deleteTransactionItemRelation();

			if(te != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				te.toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot delete transaction-item relation: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
				m = new Message("Cannot delete the relation: it already exists.", "E5A2", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot delete the relation: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}
}