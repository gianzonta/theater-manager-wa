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


public final class MemberRestResource extends RestResource {

	/**
	 * Creates a new REST resource for managing {@code Member} resources. - CONSTRUCTOR
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @param con the connection to the database.
	 */
	public MemberRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
		super(req, res, con);
	}


	/**
	 * Creates a new Member into the database. 
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */


	public void createMember() throws IOException,ForbiddenException {

		Member a  = null;
		Message m = null;

		try{
			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager"))
				{throw new ForbiddenException();}
			final Member member =  Member.fromJSON(req.getInputStream()); //reads the member from the request

			// creates a new object for accessing the database and stores the member
			a = new CreateMemberDatabase(con, member).createMember(); // database helper class

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_CREATED);
				a.toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot create the new member: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
				m = new Message("Cannot create the new member: it already exists.", "E5A2", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else 
			if (t instanceof ForbiddenException) {
				m = new Message("You don't have permission for this operation");
				res.setStatus(HttpServletResponse.SC_FORBIDDEN);
				m.toJSON(res.getOutputStream());
			}
			
			else 		
			
			{

				m = new Message("Cannot create the new member: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}

	/**
	 * Reads a member from the database.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */ 
	 public void readMember() throws IOException {

		Member a  = null;
		Message m = null;

		try{
			// parse the URI path to extract the username
			String path = req.getRequestURI();
			path = path.substring(path.indexOf("member") + 6);

			final String username = path.substring(1); // takes only the username after the "/"


			// creates a new object for accessing the database and reads the member
			a = new ReadMemberDatabase(con, username).readMember();

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Member %d not found.", username), "E5A3", null);
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot read member: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

	}

/*
	/**
	 * Updates a member in the database.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */ 

	 
	public void updateMember() throws IOException {

		Member a  = null;
		Message m = null;

		try{

			
			// parse the URI path to extract the badge
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("member") + 6);

			final String username = path.substring(1);
			final Member updatedMember = Member.fromJSON(req.getInputStream());


			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager") && !((String) req.getSession(false).getAttribute("user")).equals(username))
				{throw new Exception("Restricted operation: you don't have the rights to do this!");}
			
			if (!username.equals(updatedMember.getUsername())) {
				m = new Message("Wrong request for URI /member/{username}: URI request and member username differ.","E4A7", String.format("Request URI id %d; uploaded member resource  id %d.",username, updatedMember.getUsername()));
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				m.toJSON(res.getOutputStream());
				return;
			}

			System.out.println("first error (if) passed on");

			// creates a new object for accessing the database and updates the member
			a = new UpdateMemberDatabase(con, updatedMember).updateMember();

			System.out.println("database requested item");

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Member %d not found.", updatedMember.getUsername()), "E5A3", null);
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23503")) {
				m = new Message("Cannot update the member: other resources depend on it.", "E5A4", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot update the member: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}
    

	public void deleteMember() throws IOException {

		Member a  = null;
		Message m = null;

		try{

			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager"))
			{throw new ForbiddenException();}
			// parse the URI path to extract the badge
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("member") + 6);

			final String username = path.substring(1);

			// creates a new object for accessing the database and deletes the member
			a = new DeleteMemberDatabase(con, username).deleteMember();

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Member %d not found.", username), "E5A3", null);
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23503")) {
				m = new Message("Cannot delete the member: other resources depend on it.", "E5A4", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);

			} else
			if (t instanceof ForbiddenException) {
				m = new Message("You don't have permission for this operation");
				res.setStatus(HttpServletResponse.SC_FORBIDDEN);
				m.toJSON(res.getOutputStream());
			} else
			
			 {
				m = new Message("Cannot delete the member: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			}
		}
	} 

	/**
	 * Lists all the members.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */ 
	public void listMember() throws IOException,ForbiddenException {

		List<Member> el  = null;
		Message m = null;

		try{
			if (!((String) req.getSession(false).getAttribute("uGroup")).equals("Company Manager"))
			{throw new ForbiddenException();}
			// creates a new object for accessing the database and lists all the members
			el = new ListMemberDatabase(con).listMember();

			if(el != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(el).toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot list members: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) 
		{
			if (t instanceof ForbiddenException) {
				m = new Message("You don't have permission for this operation");
				res.setStatus(HttpServletResponse.SC_FORBIDDEN);
				m.toJSON(res.getOutputStream());
			} else
			{
			m = new Message("Cannot search member: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
			}
		}
	} 


	/**
	 * Reads the user roles of a member from the database.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */ 
	public void getRolesByUsername() throws IOException {

		List<UserRole> el  = null;
		Message m = null;

		String path = req.getRequestURI();
		path = path.substring(path.indexOf("member") + 6);
		final String username = path.substring(1,path.lastIndexOf("/userrole"));
		
		try{
			// creates a new object for accessing the database and lists all the user roles of a member
			el = new ListUserRoleDatabase(con).listbyUsername(username);

			if(el != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(el).toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Unexpected error: cannot list userroles for username: "+username, "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot search userrole: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	} 


	public void createUserRole() throws IOException {

		UserRole a  = null;
		Message m = null;

		try{
			final UserRole userrole =  UserRole.fromJSON(req.getInputStream()); //reads the member from the request

			// creates a new object for accessing the database and stores the member
			a = new CreateUserRoleDatabase(con, userrole).createUserRole(); // database helper class

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_CREATED);
				a.toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot insert the new user role: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
				m = new Message("Cannot create the new user role: it already exists.", "E5A2", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot create the new user role: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}

	public void deleteUserRole() throws IOException {

		UserRole a  = null;
		Message m = null;

		try{

			final UserRole userrole =  UserRole.fromJSON(req.getInputStream());
			a = new DeleteUserRoleDatabase(con, userrole).deleteUserRole();

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("User Role %d not found."), "E5A3", null);
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23503")) {
				m = new Message("Cannot delete the user role: other resources depend on it.", "E5A4", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot delete the user role: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	} 

	public void listIsPartOf() throws IOException {

		String path = req.getRequestURI();
		path = path.substring(path.indexOf("member") + 6);
		final String username = path.substring(1,path.lastIndexOf("/ispartof"));

		String el  = null;
		Message m = null;
		OutputStream out = res.getOutputStream();
		try{
			// creates a new object for accessing the database and lists all the members
			el = new IsPartOfDatabase(con,username).listIsPartOf();
			if(el != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				out.write(el.getBytes());
				out.flush();
			//	res.getWriter().close();
			} else {
				// it should not happen
				m = new Message("Cannot list department assignations: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(out);
			}
		} catch (Throwable t) {
			m = new Message("Unexpected error: cannot search member: "+username, "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(out);
		}

	}


	public void updateIsPartOf() throws IOException {

		String path = req.getRequestURI();
		path = path.substring(path.indexOf("member") + 6);
		final String username = path.substring(1,path.lastIndexOf("/ispartof"));
		Message m = null;

		try{

			final String name =  req.getReader().readLine();
			new IsPartOfDatabase(con,username).createIsPartOf(name);
			
/*
			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				//a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("User Role %d not found."), "E5A3", null);
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				m.toJSON(res.getOutputStream());
			}*/
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23503")) {
				m = new Message("Cannot delete the user role: other resources depend on it.", "E5A4", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot delete the user role: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	} 
}







