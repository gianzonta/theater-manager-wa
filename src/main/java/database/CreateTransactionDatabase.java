package database;

import resource.Transaction;
import resource.Season;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.text.*;
import java.util.Date;
import java.util.Iterator;


public final class CreateTransactionDatabase {

	/**
	 * The SQL statement to be executed
	 */

	private static final String STATEMENT1 = "SELECT * FROM theater_wa_db.season";

	private static final String STATEMENT2 = "INSERT INTO theater_wa_db.transact ( name, description, amount, invoice, transactiondate, username, seasonid) VALUES (CAST(? AS NAMEDOM), ?, ?, ?, CAST(? AS DATE), CAST(? AS NAMEDOM), ?) RETURNING *";

	private static final String STATEMENT_R = "UPDATE theater_wa_db.season SET revenues = ? WHERE seasonid = ? RETURNING *";

	private static final String STATEMENT_E = "UPDATE theater_wa_db.season SET expenses = ? WHERE seasonid = ? RETURNING *";
	/**
	 * The connection to the database
	 */
	private final Connection con;


	private final Transaction transaction;


	public CreateTransactionDatabase(final Connection con, final Transaction transaction) {
		this.con = con;
		this.transaction = transaction;
	}

	/**
	 * Creates an employee in the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public Transaction createTransaction() throws SQLException {



		// the created employee
		

		//1. search for corresponding season (based on date)

		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;

		final List<Season> seasonList = new ArrayList<Season>();

		try {
			pstmt1 = con.prepareStatement(STATEMENT1);
			rs1 = pstmt1.executeQuery();

			while (rs1.next()) {
						seasonList.add(new Season(
                        rs1.getInt("seasonid"), 
						rs1.getString("period"), 
                        rs1.getDouble("revenues"), 
                        rs1.getDouble("expenses"),
                        rs1.getDouble("initialFund")
                        ));
			}
		} finally {
			if (rs1 != null) {
				rs1.close();
			}

			if (pstmt1 != null) {
				pstmt1.close();
			}

		}

		Season selectedSeason = null;
		int i = 0;
		while (i < seasonList.size()){
			Season s = seasonList.get(i);
			if (transactionInSeason(transaction, s))
			{
				selectedSeason = s;
			}
			i++;
		}

		//selectedSeason = seasonList.get(0);

		//2. insert transaction

		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;

		Transaction newTransact = null; // the resulting new transaction

		try {
			pstmt2 = con.prepareStatement(STATEMENT2);
			pstmt2.setString(1, transaction.getName());
			pstmt2.setString(2, transaction.getDescription());
			pstmt2.setDouble(3, transaction.getAmount());
			pstmt2.setString(4, transaction.getInvoice());

			//Timestamp ts = Timestamp.valueOf(transaction.getDate());
            pstmt2.setString(5, transaction.getDate());
            pstmt2.setString(6, transaction.getUsername());
            pstmt2.setInt(7, selectedSeason.getSeasonID());

			rs2 = pstmt2.executeQuery();

			if (rs2.next()) {
				newTransact = new Transaction(
                        rs2.getInt("transid"), //may need some uppercase
						rs2.getString("name"), 
                        rs2.getString("description"), 
                        rs2.getDouble("amount"),
                        rs2.getString("invoice"),
                        rs2.getString("transactiondate"),
                        rs2.getString("username"),
						rs2.getInt("seasonid")
                        );
			}
		} finally {
			if (rs2 != null) {
				rs2.close();
			}

			if (pstmt2 != null) {
				pstmt2.close();
			}

		}

		//3. update selected season balance

		String column = null;

		if (transaction.getAmount() >= 0) {
			column = "revenues";
			selectedSeason.addRevenues(transaction.getAmount());
		} else {
			column = "expenses";
			selectedSeason.addExpenses(-1*transaction.getAmount());
		}

		PreparedStatement pstmt3 = null;
		ResultSet rs3 = null;

		try {

			if (column == "revenues") { //updated value taken from only the updated cell
				pstmt3 = con.prepareStatement(STATEMENT_R);
				pstmt3.setDouble(1, selectedSeason.getRevenues());
			} else {
				pstmt3 = con.prepareStatement(STATEMENT_E);
				pstmt3.setDouble(1, selectedSeason.getExpenses());
			}

			pstmt3.setInt(2, selectedSeason.getSeasonID());

			rs3 = pstmt3.executeQuery();


		} finally {
			if (rs2 != null) {
				rs2.close();
			}

			if (pstmt2 != null) {
				pstmt2.close();
			}

			con.close();
		}

		return newTransact;
	}


	public boolean transactionInSeason(Transaction t, Season s)  {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date src = null;
		Date start = null;
		Date end = null;

		try {
			src = sdformat.parse(t.getDate());
			start = sdformat.parse(s.getStartDate());
			end = sdformat.parse(s.getEndDate());
		} catch (Exception e)
		{
			System.out.println("Error parsing date");
		}
		return (src.compareTo(start) > 0) && (src.compareTo(end) < 0);
	}
}