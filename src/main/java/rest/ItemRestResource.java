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


public final class ItemRestResource extends RestResource {

	
	public ItemRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
		super(req, res, con);
	}


	
	public void createItem() throws IOException {

		Item a  = null;
		Message m = null;

		try{

			final Item item =  Item.fromJSON(req.getInputStream()); 

			
			a = new CreateItemDatabase(con, item).createItem(); 

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_CREATED);
				a.toJSON(res.getOutputStream());
			} else {
				
				m = new Message("Cannot create the item: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
				m = new Message("Cannot create the item: it already exists.", "E5A2", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot create the item: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}

	 
 public void readItem() throws IOException {

		Item a  = null;
		Message m = null;

		try{
			
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("item") + 4);

			final int itemid = Integer.parseInt(path.substring(1)); 


			
			a = new ReadItemDatabase(con, itemid).readItem();

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Item %d not found.",itemid), "E5A3", null);
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot read item: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

	}

	 
	public void updateItem() throws IOException {

		Item a  = null;
		Message m = null;

		try{
			
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("item") + 4);

			final int itemid = Integer.parseInt(path.substring(1));
			final Item updatedItem = Item.fromJSON(req.getInputStream());

			if (itemid != updatedItem.getItemID()) {
				m = new Message(
						"Wrong request for URI /item/{itemid}: URI request and employee resource IDs differ.",
						"E4A7", String.format("Request URI id %d; uploaded item resource id %d.",
											  itemid, updatedItem.getItemID()));
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				m.toJSON(res.getOutputStream());
				return;
			}

			
			a = new UpdateItemDatabase(con, updatedItem).updateItem();

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Item %d not found.", updatedItem.getItemID()), "E5A3", null);
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23503")) {
				m = new Message("Cannot update the item: other resources depend on it.", "E5A4", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot update the item: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}
    

    

	public void deleteItem() throws IOException {

		Item a  = null;
		Message m = null;

		try{
			
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("item") + 4);

			final int itemid = Integer.parseInt(path.substring(1));

			
			a = new DeleteItemDatabase(con, itemid).deleteItem();

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				a.toJSON(res.getOutputStream());
			} else {
				m = new Message(String.format("Item %d not found.", itemid), "E5A3", null);
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23503")) {
				m = new Message("Cannot delete the item: other resources depend on it.", "E5A4", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot delete the item: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}



	} 
	public void listItem() throws IOException {

		List<Item> a  = null;
		Message m = null;

		try{
			// creates a new object for accessing the database and lists all the members
			a = new ListItemDatabase(con).listItem();

			if(a != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(a).toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot list items: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot search item: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	public void searchItemByTransaction()  throws IOException {

		List<Item> il  = null;
		Message m = null;

		try{
			// parse the URI path to extract the transaction id
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("transaction") + 11);

			final int transid = Integer.parseInt(path.substring(1));


			// creates a new object for accessing the database and search the employees
			il = new SearchItemByTransactionDatabase(con, transid).searchItemByTransaction();

			if(il != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(il).toJSON(res.getOutputStream());
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
	 
}