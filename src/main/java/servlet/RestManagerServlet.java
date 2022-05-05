package servlet;

import resource.*;
import rest.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


public final class RestManagerServlet extends AbstractDatabaseServlet {

	/**
	 * The JSON MIME media type
	 */
	private static final String JSON_MEDIA_TYPE = "application/json";

	/**
	 * The JSON UTF-8 MIME media type
	 */
	private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

	/**
	 * The any MIME media type
	 */
	private static final String ALL_MEDIA_TYPE = "*/*";

	@Override
	protected final void service(final HttpServletRequest req, final HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType(JSON_UTF_8_MEDIA_TYPE);
		final OutputStream out = res.getOutputStream();

		try {
			// if the request method and/or the MIME media type are not allowed, return.
			// Appropriate error message sent by {@code checkMethodMediaType}
			if (!checkMethodMediaType(req, res)) {
				return;
			}

			// if the requested resource was an Employee, delegate its processing and return
			if (processActivity(req, res)) {
				return;
			}

			if (processTransaction(req, res)) {
				return;
			}

			if (processMember(req, res)) {
				return;
			}

			if (processDepartment(req, res)) {
				return;
			}

			if (processItem(req, res)) {
				return;
			}
			if (processPlay(req, res)) {
				return;
			}

            // add here any other process methods, for any other entities

			// if none of the above process methods succeeds, it means an unknow resource has been requested
			final Message m = new Message("Unknown resource requested.", "E4A6",
										  String.format("Requested resource is %s.", req.getRequestURI()));
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			m.toJSON(out);
		} finally {
			// ensure to always flush and close the output stream
			out.flush();
			out.close();
		}
	}

	/**
	 * Checks that the request method and MIME media type are allowed.
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @return {@code true} if the request method and the MIME type are allowed; {@code false} otherwise.
	 *
	 * @throws IOException if any error occurs in the client/server communication.
	 */
	private boolean checkMethodMediaType(final HttpServletRequest req, final HttpServletResponse res)
			throws IOException {

		final String method = req.getMethod();
		final String contentType = req.getHeader("Content-Type");
		final String accept = req.getHeader("Accept");
		final OutputStream out = res.getOutputStream();

		Message m = null;

		if(accept == null) { // general check of what the client wants back
			m = new Message("Output media type not specified.", "E4A1", "Accept request header missing.");
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			m.toJSON(out);
			return false;
		}

		if(!accept.contains(JSON_MEDIA_TYPE) && !accept.equals(ALL_MEDIA_TYPE)) {
			m = new Message("Unsupported output media type. Resources are represented only in application/json.",
							"E4A2", String.format("Requested representation is %s.", accept));
			res.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			m.toJSON(out);
			return false;
		}

		switch(method) {
			case "GET":
			case "DELETE":
				// nothing to do
				break;

			case "POST":
			case "PUT":
				if(contentType == null) {
					m = new Message("Input media type not specified.", "E4A3", "Content-Type request header missing.");
					res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					m.toJSON(out);
					return false;
				}

				if(!contentType.contains(JSON_MEDIA_TYPE)) {
					m = new Message("Unsupported input media type. Resources are represented only in application/json.",
									"E4A4", String.format("Submitted representation is %s.", contentType));
					res.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
					m.toJSON(out);
					return false;
				}

				break;
			default:
				m = new Message("Unsupported operation.",
								"E4A5", String.format("Requested operation %s.", method));
				res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				m.toJSON(out);
				return false;
		}

		return true;
	}


	/**
	 * Checks whether the request if for an {@link Employee} resource and, in case, processes it.
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @return {@code true} if the request was for an {@code Employee}; {@code false} otherwise.
	 *
	 * @throws IOException if any error occurs in the client/server communication.
	 */
	private boolean processActivity(HttpServletRequest req, HttpServletResponse res) throws IOException {

		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();

		String path = req.getRequestURI();
		Message m = null;

		// the requested resource was not an employee - all our entities are stored in the "content" directory
		if(path.lastIndexOf("content/activity") <= 0) { 
			return false;
		}

		try {
			// strip everyhing until after the /employee
			path = path.substring(path.lastIndexOf("activity") + 8);

			// the request URI is: /employee
			// if method GET, list employees
			// if method POST, create employee
			if (path.length() == 0 || path.equals("/")) {

				switch (method) {
					case "GET":
						new ActivityRestResource(req, res, getDataSource().getConnection()).listActivity();
						break;
					case "POST":
						new ActivityRestResource(req, res, getDataSource().getConnection()).createActivity();
						break;
					default:
						m = new Message("Unsupported operation for URI /activity.",
										"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}  else {
				// the request URI is: /activity/play/{playid} - gets the activities related to the specified transaction
				if (path.contains("play")) {
					path = path.substring(path.lastIndexOf("play") + 4);

					if (path.length() == 0 || path.equals("/")) {
						m = new Message("Wrong format for URI /activity/play/{playid}: no {playid} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					} else {
						switch (method) {
							case "GET": // OBATAINS A LIST OF RELATED ACTIVITIES

								// check that the parameter is actually an integer
								try {
									Integer.parseInt(path.substring(1));

									new ActivityRestResource(req, res, getDataSource().getConnection()).searchActivityByPlay();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI /activity/play/{playid}: {playid} is not an integer.",
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;

							case "POST": // CREATES ACTIVITY-PLAY RELATION

								// check that the parameter is actually an integer
								try {
									Integer.parseInt(path.substring(1));

									new ActivityRestResource(req, res, getDataSource().getConnection()).createActivityPlayRelation();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI /activity/play/{playid}: {playid} is not an integer.",
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;
							
							case "DELETE": // DELETE ACTIVITY-PLAY RELATION

								// check that the parameter is actually an integer
								try {
									Integer.parseInt(path.substring(1));

									//new ActivityRestResource(req, res, getDataSource().getConnection()).deleteActivityPlayRelation();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI /activity/play/{playid}: {playid} is not an integer.",
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;

							default:
								m = new Message("Unsupported operation for URI /activity/play/{playid}.", "E4A5",
												String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					} 
				} 
			else {
				// the request URI is: /activity/transaction/{transid} - gets the activities related to the specified transaction
				if (path.contains("transaction")) {
					path = path.substring(path.lastIndexOf("transaction") + 11);

					if (path.length() == 0 || path.equals("/")) {
						m = new Message("Wrong format for URI /activity/transaction/{transid}: no {transid} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					} else {
						switch (method) {
							case "GET":

								// check that the parameter is actually an integer
								try {
									Integer.parseInt(path.substring(1));

									new ActivityRestResource(req, res, getDataSource().getConnection()).searchActivityByTransaction();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI /activity/transaction/{transid}: {transid} is not an integer.",
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;
							default:
								m = new Message("Unsupported operation for URI /employee/salary/{salary}.", "E4A5",
												String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					} 
				} 
				else { //MARK
					// the request URI is: /activity/{badge}
					try {

						// check that the parameter is actually an integer
						Integer.parseInt(path.substring(1));

						switch (method) {
							case "GET":
								new ActivityRestResource(req, res, getDataSource().getConnection()).readActivity();
								break;
							case "PUT":
								new ActivityRestResource(req, res, getDataSource().getConnection()).updateActivity();
								break;
							case "DELETE":
								new ActivityRestResource(req, res, getDataSource().getConnection()).deleteActivity();
								break;
							default:
								m = new Message("Unsupported operation for URI /activity/{activityid}.",
												"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
						}
					} catch (NumberFormatException e) {
						m = new Message("Wrong format for URI /activity/{activityid}: {activityid} is not an integer.",
										"E4A7", e.getMessage());
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
				} 
			}
		}} catch(Throwable t) {
			m = new Message("Unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

		return true;

	}


	private boolean processTransaction(HttpServletRequest req, HttpServletResponse res) throws IOException {

		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();

		String path = req.getRequestURI();
		Message m = null;

		// the requested resource was not an employee - all our entities are stored in the "content" directory
		if(path.lastIndexOf("content/transaction") <= 0) { 
			return false;
		}

		try {
			// strip everyhing until after the /employee
			path = path.substring(path.lastIndexOf("transaction") + 11);

			// the request URI is: /employee
			// if method GET, list employees
			// if method POST, create employee
			if (path.length() == 0 || path.equals("/")) {

				switch (method) {
					case "GET":
						new TransactionRestResource(req, res, getDataSource().getConnection()).listTransaction();
						break;
					case "POST":
						new TransactionRestResource(req, res, getDataSource().getConnection()).createTransaction();
						break;
					default:
						m = new Message("Unsupported operation for URI /activity.",
										"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}   
			
			else {
				// the request URI is: /transaction/activity/{activityid}
				if (path.contains("activity")) {

					path = path.substring(path.lastIndexOf("activity") + 8);

					if (path.length() == 0 || path.equals("/")) {
						m = new Message("Wrong format for URI /transaction/activity/{activityid}: no {activityid} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					} else {
						switch (method) {
							case "GET":

								// check that the parameter is actually an integer
								try {
									Integer.parseInt(path.substring(1));

									new TransactionRestResource(req, res, getDataSource().getConnection()).searchTransactionByActivity();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI transaction/activity/{activityid}: {activityid} is not an integer.",
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;
							
							case "POST": // CREATES A NEW TRANSACTION->ACTIVIY relation - sending the transaction information and the connected activity in the URI
								try {
									Integer.parseInt(path.substring(1));

									new TransactionRestResource(req, res, getDataSource().getConnection()).createTransactionActivityRelation();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI transaction/activity/{activityid}: {activityid} is not an integer.",
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;

							case "DELETE": // CREATES A NEW TRANSACTION->ACTIVIY relation - sending the transaction information and the connected activity in the URI
								try {
									Integer.parseInt(path.substring(1));

									new TransactionRestResource(req, res, getDataSource().getConnection()).deleteTransactionActivityRelation();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI transaction/activity/{activityid}: {activityid} is not an integer.",
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;
							
							default:
								m = new Message("Unsupported operation for URI transaction/activity/{activityid}.", "E4A5",
												String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					} 
				} 	else {
				// the request URI is: /transaction/item/{itemid}
				if (path.contains("item")) {

					path = path.substring(path.lastIndexOf("item") + 4);

					if (path.length() == 0 || path.equals("/")) {
						m = new Message("Wrong format for URI /transaction/item/{itemid}: no {itemid} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					} else {
						switch (method) {
							/*case "GET":

								// check that the parameter is actually an integer
								try {
									Integer.parseInt(path.substring(1));

									new TransactionRestResource(req, res, getDataSource().getConnection()).searchTransactionByActivity();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI transaction/activity/{activityid}: {activityid} is not an integer.",
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break; */
							
							case "POST": // CREATES A NEW TRANSACTION->ITEM relation
								try {
									Integer.parseInt(path.substring(1));

									new TransactionRestResource(req, res, getDataSource().getConnection()).createTransactionItemRelation();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI transaction/item/{itemid}: {itemid} is not an integer.",
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;

							case "DELETE": // DELETES A NEW TRANSACTION->ITEM relation 
								try {
									Integer.parseInt(path.substring(1));

									new TransactionRestResource(req, res, getDataSource().getConnection()).deleteTransactionItemRelation();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI transaction/item/{itemid}: {itemid} is not an integer.",
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;
							
							default:
								m = new Message("Unsupported operation for URI transaction/item/{itemid}.", "E4A5",
												String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					} 
				} else {
					// the request URI is: /activity/{badge}
					try {

						// check that the parameter is actually an integer
						Integer.parseInt(path.substring(1));

						switch (method) {
							case "GET":
								new TransactionRestResource(req, res, getDataSource().getConnection()).readTransaction();
								break;
							case "PUT":
								new TransactionRestResource(req, res, getDataSource().getConnection()).updateTransaction();
								break;
							case "DELETE":
								new TransactionRestResource(req, res, getDataSource().getConnection()).deleteTransaction();
								break;
							default:
								m = new Message("Unsupported operation for URI /transaction/{transid}.",
												"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
						}
					} catch (NumberFormatException e) {
						m = new Message("Wrong format for URI /transaction/{transid}: {transid} is not an integer.",
										"E4A7", e.getMessage());
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
				} 
			
		}}} catch(Throwable t) {
			m = new Message("Unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

		return true;

	}



        /**
     * Checks whether the request if for an {@link Member} resource and, in case, processes it.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return {@code true} if the request was for an {@code Member}; {@code false} otherwise.
     *
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean processMember(HttpServletRequest req, HttpServletResponse res) throws IOException {

		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();

		String path = req.getRequestURI();
		Message m = null;

		// the requested resource was not an member
		if(path.indexOf("content/member") <= 0) {
			return false;
		}

		try {
			// strip everyhing until after the /member
			path = path.substring(path.indexOf("member") + 6);

			// the request URI is: /member
			// if method GET, list members
			// if method POST, create member
			if (path.length() == 0 || path.equals("/")) {

				switch (method) {
					case "GET":
						new MemberRestResource(req, res, getDataSource().getConnection()).listMember();
						break;
					case "POST":
						new MemberRestResource(req, res, getDataSource().getConnection()).createMember();
						break;
		
					default:
						m = new Message("Unsupported operation for URI /member.",
										"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			} else 
			
				{
					path = path.substring(1);
					if (path.lastIndexOf('/') <= 0 || path.indexOf('/') == path.length()-1)
					// the request URI is: /member/{username}
					{

						switch (method) {
							case "GET":
								new MemberRestResource(req, res, getDataSource().getConnection()).readMember();
								break;
							case "PUT":
								new MemberRestResource(req, res, getDataSource().getConnection()).updateMember();
								break;
							case "DELETE":
								new MemberRestResource(req, res, getDataSource().getConnection()).deleteMember();
								break;
						
							default:
								m = new Message("Unsupported operation for URI /member/{username}.",
												"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
						}
					}
					else
					{
						if (path.lastIndexOf("/userrole") > 0 )
						{

							switch (method) {
								case "GET":
									new MemberRestResource(req, res, getDataSource().getConnection()).getRolesByUsername();
									break;
								case "POST":
									new MemberRestResource(req, res, getDataSource().getConnection()).createUserRole();
									break;
							
								case "DELETE":
									new MemberRestResource(req, res, getDataSource().getConnection()).deleteUserRole();
									break;
							
								default:
									m = new Message("Unsupported operation for URI /member/{username}/userrole.",
													"E4A5", String.format("Requested operation %s.", method));
									res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
									m.toJSON(res.getOutputStream());
							}
						}

						if (path.lastIndexOf("/ispartof") > 0 )
						{

							switch (method) {
								case "GET":
									new MemberRestResource(req, res, getDataSource().getConnection()).listIsPartOf();
									break;
								case "PUT":
									new MemberRestResource(req, res, getDataSource().getConnection()).updateIsPartOf();
									break;
							
								default:
									m = new Message("Unsupported operation for URI /member/{username}/ispartof.",
													"E4A5", String.format("Requested operation %s.", method));
									res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
									m.toJSON(res.getOutputStream());
							}
						}

					}
				}
			
			
		} catch(Throwable t) {
			m = new Message("Unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

		return true;

	}

private boolean processDepartment(HttpServletRequest req, HttpServletResponse res) throws IOException {

		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();

		String path = req.getRequestURI();
		Message m = null;

		// the requested resource was not an employee - all our entities are stored in the "content" directory
		if(path.lastIndexOf("content/department") <= 0) { 
			return false;
		}

		try {
			// strip everyhing until after the /employee
			path = path.substring(path.lastIndexOf("department") + 10);

			// the request URI is: /employee
			// if method GET, list employees
			// if method POST, create employee
			if (path.length() == 0 || path.equals("/")) {

				switch (method) {
					case "GET":
						new DepartmentRestResource(req, res, getDataSource().getConnection()).listDepartment(); 
						break;
					case "POST":
						new DepartmentRestResource(req, res, getDataSource().getConnection()).createDepartment();
						break;
					default:
						m = new Message("Unsupported operation for URI /department.",
										"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}  else {
					// the request URI is: /activity/{badge}
					try {

						// check that the parameter is actually an integer
						path.substring(1);

						switch (method) {
							case "GET":
								new DepartmentRestResource(req, res, getDataSource().getConnection()).readDepartment();
								break;
							case "PUT":
								new DepartmentRestResource(req, res, getDataSource().getConnection()).updateDepartment();
								break;
							case "DELETE":
								new DepartmentRestResource(req, res, getDataSource().getConnection()).deleteDepartment();
								break;
							default:
								m = new Message("Unsupported operation for URI /department/{name}.",
												"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
						}
					} catch (NumberFormatException e) {
						m = new Message("Wrong format for URI /department/{department}: {name} is not an integer.",
										"E4A7", e.getMessage());
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
				} 
			
		} catch(Throwable t) {
			m = new Message("Unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

		return true;

	}
	private boolean processItem(HttpServletRequest req, HttpServletResponse res) throws IOException {

		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();

		String path = req.getRequestURI();
		Message m = null;

		// the requested resource was not an employee - all our entities are stored in the "content" directory
		if(path.lastIndexOf("content/item") <= 0) { 
			return false;
		}

		try {
			// strip everyhing until after the /employee
			path = path.substring(path.lastIndexOf("item") + 4);

			// the request URI is: /employee
			// if method GET, list employees
			// if method POST, create employee
			if (path.length() == 0 || path.equals("/")) {

				switch (method) {
					case "GET":
						new ItemRestResource(req, res, getDataSource().getConnection()).listItem(); // class to be implemented
						break;
					case "POST":
						new ItemRestResource(req, res, getDataSource().getConnection()).createItem();
						break;
					default:
						m = new Message("Unsupported operation for URI /item.",
										"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}  else {
				// the request URI is: /activity/transaction/{transid} - gets the activities related to the specified transaction
				if (path.contains("transaction")) {
					path = path.substring(path.lastIndexOf("transaction") + 11);

					if (path.length() == 0 || path.equals("/")) {
						m = new Message("Wrong format for URI /item/transaction/{transid}: no {transid} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					} else {
						switch (method) {
							case "GET":

								// check that the parameter is actually an integer
								try {
									Integer.parseInt(path.substring(1));

									new ItemRestResource(req, res, getDataSource().getConnection()).searchItemByTransaction();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI /item/transaction/{transid}: {transid} is not an integer.",
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;
							default:
								m = new Message("Unsupported operation for URI /employee/salary/{salary}.", "E4A5",
												String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					} 
				} else { //MARK
					// the request URI is: /activity/{badge}
					try {

						// check that the parameter is actually an integer
						Integer.parseInt(path.substring(1));

						switch (method) {
							case "GET":
								new ItemRestResource(req, res, getDataSource().getConnection()).readItem();
								break;
							case "PUT":
								new ItemRestResource(req, res, getDataSource().getConnection()).updateItem();
								break;
							case "DELETE":
								new ItemRestResource(req, res, getDataSource().getConnection()).deleteItem();
								break;
							default:
								m = new Message("Unsupported operation for URI /item/{itemid}.",
												"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
						}
					} catch (NumberFormatException e) {
						m = new Message("Wrong format for URI /item/{itemid}: {itemid} is not an integer.",
										"E4A7", e.getMessage());
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
				} 
			}
		} catch(Throwable t) {
			m = new Message("Unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

		return true;

	}




	private boolean processPlay(HttpServletRequest req, HttpServletResponse res) throws IOException {

		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();

		String path = req.getRequestURI();
		Message m = null;

		// the requested resource was not an employee - all our entities are stored in the "content" directory
		if(path.lastIndexOf("content/play") <= 0) {
			return false;
		}

		try {
			// strip everyhing until after the /employee
			path = path.substring(path.indexOf("play") + 4);

			// the request URI is: /employee
			// if method GET, list employees
			// if method POST, create employee
			if (path.length() == 0 || path.equals("/")) {

				switch (method) {
					case "GET":
						new PlayRestResource(req, res, getDataSource().getConnection()).LastFivePlaces();
						break;
					case "POST":
						new PlayRestResource(req, res, getDataSource().getConnection()).createPlay();
						break;
					default:
						m = new Message("Unsupported operation for URI /play.",
								"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}  else {
				// the request URI is: /activity/{badge}
				try {

					// check that the parameter is actually an integer
					path.substring(1);

					switch (method) {
						case "GET":
							new PlayRestResource(req, res, getDataSource().getConnection()).readPlay();
							break;
						case "PUT":
							new PlayRestResource(req, res, getDataSource().getConnection()).updatePlay();
							break;
						case "DELETE":
							new PlayRestResource(req, res, getDataSource().getConnection()).deletePlay();
							break;
						default:
							m = new Message("Unsupported operation for URI /play/{playid}.",
									"E4A5", String.format("Requested operation %s.", method));
							res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
							m.toJSON(res.getOutputStream());
					}
				} catch (NumberFormatException e) {
					m = new Message("Wrong format for URI /play/{playid}: {playid} is not an integer.",
							"E4A7", e.getMessage());
					res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					m.toJSON(res.getOutputStream());
				}
			}

		} catch(Throwable t) {
			m = new Message("Unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

		return true;

	}



	//End
}