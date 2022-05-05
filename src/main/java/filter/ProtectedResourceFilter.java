/*
 * Copyright 2020 University of Padua, Italy
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

package filter;

import servlet.AbstractDatabaseServlet;
import resource.Member;
import resource.Message;
import database.ReadMemberDatabase;
 
import java.sql.Connection;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Arrays;
import java.util.List;
import java.lang.NullPointerException;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Checks for successful authentication to allow for accessing protected resources.
 *
 * @author Edoardo Casarin
 * @version 1.00
 * @since 1.00
 */
public class ProtectedResourceFilter extends AbstractDatabaseServlet implements Filter {

    /**
     * The Base64 Decoder
     */
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    /**
     * The name of the user attribute in the session
     */
    private static final String USER_ATTRIBUTE = "user";

    /**
     * The configuration for the filter
     */
    private FilterConfig config = null;

    /**
     * The connection pool to the database.
    */
    private DataSource ds;

    /**
        Excluded pages
     */
    private List<String> excludedUrls;

    String uName;
    String uSurname;
    String uPhoto;
    String uGroup;


    @Override
    public void init(final FilterConfig config) throws ServletException {

        String excludePattern = config.getInitParameter("excludedUrls");
        excludedUrls = Arrays.asList(excludePattern.split(","));


        if (config == null) {
            throw new ServletException("Filter configuration cannot be null.");
        }
        this.config = config;

        /*
        Here we could pass configuration parameters to the filter, if needed.
         */
        
        // the JNDI lookup context
        InitialContext cxt;

        try {
            cxt = new InitialContext();
            ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/db-theater-wa");
        } catch (NamingException e) {
            ds = null;

            throw new ServletException(
                    String.format("Impossible to access the connection pool to the database: %s", e.getMessage()));
        }

        uName = null;
        uSurname = null; 
        uPhoto = null;
        uGroup = null;
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
                         final FilterChain chain) throws IOException, ServletException {
                
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
                throw new ServletException("Only HTTP requests/responses are allowed.");
            }
        // Safe to downcast at this point.
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;

        String path = req.getServletPath();

        if(!excludedUrls.contains(path))
        {  

            final HttpSession session = req.getSession(false);

            // if we do not have a session, try to authenticate the user
            if (session == null) {
                if (!authenticateUser(req, res)) {
                    return;
                }
            }
            else {

                final String user = (String) session.getAttribute(USER_ATTRIBUTE);

                // there might exist a session but without any user in it
                if (user == null || user.trim().isEmpty()) { //user.isBlank() -.trim().isEmpty()
                    // invalidate the session
                    session.invalidate();

                    // try to authenticate the user
                    if (!authenticateUser(req, res)) {
                        return;
                    }
                }
            }
            // throw new ServletException("Entered restricted part of the site.");

        }
        // the user is properly authenticated and in session, continue the processing
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        config = null;
        ds = null;
    }

    private boolean authenticateUser(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException {

        // get the authorization information
        final String auth = req.getHeader("Authorization");

        // if there is no authorization information, send the authentication challenge again
        if (auth == null || auth.trim().isEmpty()) {
            sendAuthenticationChallenge(req,res);

            return false;
        }

        // if it is not HTTP Basic authentication, send the authentication challenge again
        if (!auth.toUpperCase().startsWith("BASIC ")) {
            sendAuthenticationChallenge(req,res);
            return false;
        }

        // perform Base64 decoding
        final String pair = new String(DECODER.decode(auth.substring(6)));

        // userDetails[0] is the username; userDetails[1] is the password
        final String[] userDetails = pair.split(":", 2);

        // if the user is successfully authenticated, create a Session and store the user there
        if (checkUserCredentials(userDetails[0], userDetails[1], req, res)) {
            // create a  new session
            HttpSession session = req.getSession(true);

            session.setAttribute(USER_ATTRIBUTE, userDetails[0]);

            session.setAttribute("uName", uName);

            session.setAttribute("uSurname", uSurname);

            session.setAttribute("uPhoto", uPhoto);

            session.setAttribute("uGroup", uGroup);

            return true;
        }

        // as a fallback, always send the authentication challenge again
        sendAuthenticationChallenge(req, res);

        return false;
    }

    /**
     * Sends the authentication challenge.
     *
     * @param res the HTTP servlet response.
     * @throws IOException if anything goes wrong while sending the authentication challenge.
     */
    private void sendAuthenticationChallenge(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException {

        /*res.setHeader("WWW-Authenticate", "Basic realm=ruzzantetms");

        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);*/

        //res.sendRedirect("login.jsp");
        req.getRequestDispatcher("login.jsp").forward(req, res);
    }

    /**
     * Performs the actual authentication based on the provided user credentials.
     *
     * @param username the username.
     * @param password the password.
     * @return {@code true} if the user has been successfully authenticated; {@code false} otherwise.
     */
    private boolean checkUserCredentials(String username, String password, HttpServletRequest req, HttpServletResponse res) throws IOException {
        
        //  Accessing a database, by using the provided username and password.
        
        Member mem = null; 
        Message m = null;
         try{
			// creates a new object for accessing the database and reads the member

            Connection con = ds.getConnection();
			
            ReadMemberDatabase readMem = new ReadMemberDatabase(con, username);

            mem = readMem.readMember();

            //mem = new Member("user","psw","g","z","fsegfs","2020-10-21","1646","nue@nfei.it","2020-10-05",false,false,"Actor");

            if (mem == null || mem.getUsername() == null || mem.getUsername().trim().isEmpty()) //integrity check
                return false;

            uName = mem.getName();
            uSurname = mem.getSurname();
            uPhoto = mem.getPhoto();
            uGroup = mem.getUserGroup();

            String psw = mem.getPswHash();

            String encInPsw = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password); 
            //String encInPsw = password;

            if (mem.getUsername() != null) {
                if (encInPsw.equalsIgnoreCase(psw)) { 

                    return true;
                }
                else 
                    return false;                        
		    }       
        }        
        // catch (Throwable t) exception in case of database error
        catch (SQLException t) {
			m = new Message("Cannot read member: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		} 
        /*
        catch (NullPointerException t) {
			m = new Message("Cannot read member: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			// m.toJSON(res.getOutputStream());
		}                    
        catch (IOException t) {
			m = new Message("Cannot read member: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			// m.toJSON(res.getOutputStream());
		} 
        */

        // 1st trial
        /*
        if(username.equals("thezongia"))
            if(password.equalsIgnoreCase("ok"))
                return true;        
        */     

        return false;
    }
}