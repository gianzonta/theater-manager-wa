package database;

import resource.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.Year;

public final class ListTransactionDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT * FROM theater_wa_db.transact";
	private static final String STATEMENT2 = "SELECT * FROM theater_wa_db.transact ORDER BY TransactionDate DESC LIMIT 5";
	private static final String INCOMINGS = "SELECT * FROM theater_wa_db.transact WHERE Amount > 0 AND date_part('year', TransactionDate) = ? ORDER BY TransactionDate DESC LIMIT 20";
	private static final String OUTGOINGS = "SELECT * FROM theater_wa_db.transact WHERE Amount < 0 AND date_part('year', TransactionDate) = ? ORDER BY TransactionDate DESC LIMIT 20";
	private static final String START_YEAR = "SELECT MIN(date_part('year', TransactionDate)) as year from theater_wa_db.transact";
	private static final String REVENUES = "SELECT SUM(Amount) as total from theater_wa_db.transact WHERE Amount > 0 AND date_part('year', TransactionDate) = ?";
	private static final String EXPENSES = "SELECT SUM(Amount) as total from theater_wa_db.transact WHERE Amount < 0 AND date_part('year', TransactionDate) = ?";
	private static final String STATEMENT3 = "SELECT T.TransID, T.Name, T.Amount, T.TransactionDate, T.Username, A.Title as related, A.ActivityID \n" + 
											 "FROM theater_wa_db.transact as T \n" +
											 "LEFT JOIN theater_wa_db.GeneratedBy as G ON T.TransID = G.TransID \n" +
											 "LEFT JOIN theater_wa_db.Activity as A ON G.ActivityID = A.ActivityID \n" +
											 "ORDER BY TransactionDate DESC LIMIT 5";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * Creates a new object for listing all the members.
	 * 
	 * @param con
	 *            the connection to the database.
	 */
	public ListTransactionDatabase(final Connection con) {
		this.con = con;
	}

	/**
	 * Lists all the members in the database.
	 * 
	 * @return a list of {@code Member} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for members.
	 */
	public List<Transaction> listTransaction() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Transaction> transactions = new ArrayList<Transaction>();

		try {
			pstmt = con.prepareStatement(STATEMENT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				transactions.add(new Transaction(
                        rs.getInt("transid"), //may need some uppercase
						rs.getString("name"), 
                        rs.getString("description"), 
                        rs.getDouble("amount"),
                        rs.getString("invoice"),
                        rs.getString("transactiondate"),
                        rs.getString("username"),
						rs.getInt("seasonid")
                        ));						
			}
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

		return transactions;
	}

	/**
	 * Returns the last 5 transactions by 'TransactionDate'.
	 * 
	 * @return a list of {@code Member} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for members.
	 */
	public List<Transaction> lastFiveTransactions() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Transaction> transactions = new ArrayList<Transaction>();

		try {
			pstmt = con.prepareStatement(STATEMENT3);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				transactions.add(new Transaction(
                        rs.getInt("TransID"),
						rs.getString("Name"), 
                        rs.getDouble("Amount"),
                        rs.getString("TransactionDate"),
                        rs.getString("Username"),
						rs.getString("related"),
						rs.getInt("ActivityID")
                        ));						
			}
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

		return transactions;
	}

	/**
	 * Returns the last 20 'incoming' transactions.
	 * 
	 * @return a list of {@code Member} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for members.
	 */
	public List<Transaction> incomings(int year) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Transaction> transactions = new ArrayList<Transaction>();

		try {
			pstmt = con.prepareStatement(INCOMINGS);
			pstmt.setInt(1, year);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				transactions.add(new Transaction(
                        rs.getInt("transid"), //may need some uppercase
						rs.getString("name"), 
                        rs.getString("description"), 
                        rs.getDouble("amount"),
                        rs.getString("invoice"),
                        rs.getString("transactiondate"),
                        rs.getString("username"),
						rs.getInt("seasonid")
                        ));						
			}
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

		return transactions;
	}

	/**
	 * Returns the last 20 'incoming' transactions.
	 * 
	 * @return a list of {@code Member} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for members.
	 */
	public List<Transaction> outgoings(int year) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Transaction> transactions = new ArrayList<Transaction>();

		try {
			pstmt = con.prepareStatement(OUTGOINGS);
			pstmt.setInt(1, year);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				transactions.add(new Transaction(
                        rs.getInt("transid"), //may need some uppercase
						rs.getString("name"), 
                        rs.getString("description"), 
                        rs.getDouble("amount"),
                        rs.getString("invoice"),
                        rs.getString("transactiondate"),
                        rs.getString("username"),
						rs.getInt("seasonid")
                        ));						
			}
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

		return transactions;
	}

	/**
	 * Returns the earliest year from all transactions.
	 * 
	 * @return a list of {@code Member} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for members.
	 */
	public int startYear() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// start with current year as default
		int year = Year.now().getValue();

		try {
			pstmt = con.prepareStatement(START_YEAR);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				year = rs.getInt("year");				
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}

		return year;
	}

	/**
	 * Returns the revenues for a selected year.
	 * 
	 * @return a list of {@code Member} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for members.
	 */
	public int revenues(int year) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int result = 0;

		try {
			pstmt = con.prepareStatement(REVENUES);
			pstmt.setInt(1, year);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result = rs.getInt("total");				
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}

		return result;
	}

	/**
	 * Returns the expenses total for a selected year.
	 * 
	 * @return an integer.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for members.
	 */
	public int expenses(int year) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int result = 0;

		try {
			pstmt = con.prepareStatement(EXPENSES);
			pstmt.setInt(1, year);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result = rs.getInt("total");				
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}

		return result;
	}
}
