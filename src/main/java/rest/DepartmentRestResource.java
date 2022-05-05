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


public final class DepartmentRestResource extends RestResource {

	/**
	 * Creates a new REST resource for managing {@code Department} 
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @param con the connection to the database.
	 */
	public DepartmentRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
		super(req, res, con);
	}


	/**
	 * Creates a new Department into the database. 
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void createDepartment() throws IOException {

		Department a  = null;
		Message m = null;

		try{

			final Department department =  Department.fromJSON(req.getInputStream()); //reads the Department from the request

			// creates a new object for accessing the database and stores the employee
			a = new CreateDepartmentDatabase(con, department).createDepartment(); // database helper class

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_CREATED);
				a.toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot create the department: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
				m = new Message("Cannot create the department: it already exists.", "E5A2", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot create the department: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}

	/**
	 * Reads an Department from the database.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */ 
 public void readDepartment() throws IOException {

		Department a  = null;
		Message m = null;

		try{
			// parse the URI path to extract the name
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("department") + 10);

			final String name = path.substring(1); // takes only the name after the "/"


			// creates a new object for accessing the database and reads the Department
			a = new ReadDepartmentDatabase(con, name).readDepartment();

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Department %s not found.",name), "E5A3", null);
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot read department: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

	}

	 
	public void updateDepartment() throws IOException {

		Department a  = null;
		Message m = null;

		try{
			// parse the URI path to extract the badge
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("department") + 10);

			final String name = path.substring(1);
			final Department updatedDep = Department.fromJSON(req.getInputStream());

			if (name.length() != updatedDep.getName().length()) {
				m = new Message(
						"Wrong request for URI /department/{name}: URI request and employee resource IDs differ.",
						"E4A7", String.format("Request URI id %d; uploaded department resource  id %d.",
											  name, updatedDep.getName()));
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				m.toJSON(res.getOutputStream());
				return;
			}

			// creates a new object for accessing the database and updates the employee
			a = new UpdateDepartmentDatabase(con, updatedDep).updateDepartment();

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Department %d not found.", updatedDep.getName()), "E5A3", null);
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
    

    

	public void deleteDepartment() throws IOException {

		Department a  = null;
		Message m = null;

		try{
			// parse the URI path to extract the badge
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("department") + 10);

			final String name = path.substring(1);

			// creates a new object for accessing the database and deletes the Department
			a = new DeleteDepartmentDatabase(con, name).deleteDepartment();

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Department %s not found.", name), "E5A3", null);
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23503")) {
				m = new Message("Cannot delete the department: other resources depend on it.", "E5A4", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot delete the department: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	} 

	public void listDepartment() throws IOException {

		List<Department> el  = null;
		Message m = null;

		try{
			// creates a new object for accessing the database and lists all the members
			el = new ListDepartmentDatabase(con).listDepartment();

			if(el != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(el).toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot list departments: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot search departments: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	} 

	 
}