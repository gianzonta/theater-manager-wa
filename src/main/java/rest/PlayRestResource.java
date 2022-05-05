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


public final class PlayRestResource extends RestResource {

    /**
     * Creates a new REST resource for managing {@code Employee} resources. - CONSTRUCTOR
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public PlayRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(req, res, con);
    }

    /**
     * Creates a new PLAY into the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void createPlay() throws IOException {

        Play a  = null;
        Message m = null;

        try{

            final Play play =  Play.fromJSON(req.getInputStream()); //reads the play from the request

            // creates a new object for accessing the database and stores the employee
            a = new CreatePlayDatabase(con, play).createPlay(); // database helper class

            if(a != null) {
                res.setStatus(HttpServletResponse.SC_CREATED);
                a.toJSON(res.getOutputStream());
            } else {
                // it should not happen
                m = new Message("Cannot create the play: unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        } catch (Throwable t) {
            if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
                m = new Message("Cannot create the play: it already exists.", "E5A2", t.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            } else {
                m = new Message("Cannot create the play: unexpected error.", "E5A1", t.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        }
    }
    /**
     * Reads a play from the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void readPlay() throws IOException {

        Play a  = null;
        Message m = null;

        try{
            // parse the URI path to extract the badge
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("play") + 4);

            final int playid = Integer.parseInt(path.substring(1)); // takes only the numbers, after the "/"


            // creates a new object for accessing the database and reads the employee
            a = new ReadPlayDatabase(con, playid).readPlay();

            if(a != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                a.toJSON(res.getOutputStream());
            } else {
                m = new Message(String.format("Play %d not found.", playid), "E5A3", null);
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                m.toJSON(res.getOutputStream());
            }
        } catch (Throwable t) {
            m = new Message("Cannot read play: unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }

    }

/**
 * Updates a play in the database.
 *
 * @throws IOException
 *             if any error occurs in the client/server communication.
 */
public void updatePlay() throws IOException {

    Play a  = null;
    Message m = null;

    try{
        // parse the URI path to extract the badge
        String path = req.getRequestURI();
        path = path.substring(path.indexOf("play") + 4);

        final int playid = Integer.parseInt(path.substring(1));
        final Play updatedPlay = Play.fromJSON(req.getInputStream());

        if (playid != updatedPlay.getPlayid()) {
            m = new Message(
                    "Wrong request for URI /play/{playid}: URI request and employee resource IDs differ.",
                    "E4A7", String.format("Request URI id %d; uploaded play resource  id %d.",
                    playid, updatedPlay.getPlayid()));
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            m.toJSON(res.getOutputStream());
            return;
        }

        // creates a new object for accessing the database and updates the employee
        a = new UpdatePlayDatabase(con, updatedPlay).updatePlay();

        if(a != null) {
            res.setStatus(HttpServletResponse.SC_OK);
            a.toJSON(res.getOutputStream());
        } else {
            m = new Message(String.format("Play %d not found.", updatedPlay.getPlayid()), "E5A3", null);
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            m.toJSON(res.getOutputStream());
        }
    } catch (Throwable t) {
        if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23503")) {
            m = new Message("Cannot update the play: other resources depend on it.", "E5A4", t.getMessage());
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            m.toJSON(res.getOutputStream());
        } else {
            m = new Message("Cannot update the play: unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }
}



    public void deletePlay() throws IOException {

        Play a  = null;
        Message m = null;

        try{
            // parse the URI path to extract the badge
            String path = req.getRequestURI();
            path = path.substring(path.indexOf("play") + 4);

            final int playid = Integer.parseInt(path.substring(1));


            // creates a new object for accessing the database and deletes the employee
            a = new DeletePlayDatabase(con, playid).deletePlay();

            if(a != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                a.toJSON(res.getOutputStream());
            } else {
                m = new Message(String.format("Play %d not found.", playid), "E5A3", null);
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                m.toJSON(res.getOutputStream());
            }
        } catch (Throwable t) {
            if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23503")) {
                m = new Message("Cannot delete the Play: other resources depend on it.", "E5A4", t.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            } else {
                m = new Message("Cannot delete the Play: unexpected error.", "E5A1", t.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        }
    }




    /**
 * Lists all the employees.
 *
 * @throws IOException
 *             if any error occurs in the client/server communication.
 */
public void LastFivePlaces() throws IOException {

    List<Play> al  = null;
    Message m = null;

    try{
        // creates a new object for accessing the database and lists all the employees
        al = new ListPlayDatabase(con).lastFivePlays();

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


}