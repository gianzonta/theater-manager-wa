package servlet;

import database.ListTransactionDatabase;
import resource.Message;
import resource.Transaction;

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
import java.time.Year;

public final class FinancialDashboardServlet extends AbstractDatabaseServlet {

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

		// fetch incoming transactions
		List<Transaction> incomingTransactions = null;
		Message m1 = null;
        int currentYear = Year.now().getValue();

        int year = (req.getParameter("year") != null) ? Integer.parseInt(req.getParameter("year")) : currentYear;
		req.setAttribute("selectedYear", year);

		try {
			incomingTransactions = new ListTransactionDatabase(getDataSource().getConnection()).incomings(year);
			m1 = new Message("Incoming transactions successfully obtained.");
			
		} catch (SQLException ex) {
				m1 = new Message("Cannot obtain incoming transactions: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}
		req.setAttribute("incomingTransactions", incomingTransactions);
		req.setAttribute("incomingTransactionsMessage", m1);

		// fetch outgoing transactions
		List<Transaction> outgoingTransactions = null;
		Message m2 = null;

		try {
			outgoingTransactions = new ListTransactionDatabase(getDataSource().getConnection()).outgoings(year);
			m2 = new Message("Outgoing transactions successfully obtained.");
			
		} catch (SQLException ex) {
				m2 = new Message("Cannot obtain outgoing transactions: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}
		req.setAttribute("outgoingTransactions", outgoingTransactions);
		req.setAttribute("outgoingTransactionsMessage", m2);

        // get startYear
		int startYear = currentYear;
		Message m3 = null;

		try {
			startYear = new ListTransactionDatabase(getDataSource().getConnection()).startYear();
			m3 = new Message("startYear successfully obtained.");
			
		} catch (SQLException ex) {
				m3 = new Message("Cannot obtain startYear: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}

        int rangeSize = currentYear - startYear + 1;
        int[] range = new int[rangeSize];

        for (int i = 0; i < rangeSize; i++) {
            range[i] = currentYear - i;
        }

		req.setAttribute("range", range);

        // fetch revenues total
		int revenues = 0;
		Message m4 = null;

		try {
			revenues = new ListTransactionDatabase(getDataSource().getConnection()).revenues(year);
			m4 = new Message("Revenues total successfully obtained.");
			
		} catch (SQLException ex) {
				m4 = new Message("Cannot obtain revenues total: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}

		req.setAttribute("revenuesTotal", revenues);

        // fetch expenses total
		int expenses = 0;
		Message m5 = null;

		try {
			expenses = new ListTransactionDatabase(getDataSource().getConnection()).expenses(year);
			m5 = new Message("Expenses total successfully obtained.");
			
		} catch (SQLException ex) {
				m5 = new Message("Cannot obtain expenses total: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}

		req.setAttribute("expensesTotal", expenses);

        int total = revenues - expenses;

		req.setAttribute("total", total);

		// forwards the control to the financial.jsp for display
		req.getRequestDispatcher("financial.jsp").forward(req, res);

	}

}
