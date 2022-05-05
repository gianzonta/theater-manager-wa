package database;

import resource.Transaction;
import resource.Season;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Deletes an employee from the database.
 * 
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class DeleteTransactionDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT1 = "DELETE FROM theater_wa_db.transact WHERE transid = ? RETURNING *";

	private static final String STATEMENT2 = "SELECT * FROM theater_wa_db.season WHERE seasonid = ?";
	
	private static final String STATEMENT_R = "UPDATE theater_wa_db.season SET revenues = ? WHERE seasonid = ? RETURNING *";

	private static final String STATEMENT_E = "UPDATE theater_wa_db.season SET expenses = ? WHERE seasonid = ? RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The badge of the employee
	 */
	private final int transid;


	public DeleteTransactionDatabase(final Connection con, final int transid) {
		this.con = con;
		this.transid = transid;
	}

	/**
	 * Deletes an employee from the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public Transaction deleteTransaction() throws SQLException {


		// 1 delete activity
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;

		// the deleted activity
		Transaction delTransact = null;

		try {
			pstmt1 = con.prepareStatement(STATEMENT1);
			pstmt1.setInt(1, transid);

			rs1 = pstmt1.executeQuery();

			if (rs1.next()) {
				delTransact = new Transaction(
                        rs1.getInt("transid"), //may need some uppercase
						rs1.getString("name"), 
                        rs1.getString("description"), 
                        rs1.getDouble("amount"),
                        rs1.getString("invoice"),
                        rs1.getString("transactiondate"),
                        rs1.getString("username"),
						rs1.getInt("seasonid")
                        );
			}
		} finally {
			if (rs1 != null) {
				rs1.close();
			}

			if (pstmt1 != null) {
				pstmt1.close();
			}

		}
			
		// 2. read the season from database

		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;

		Season selectedSeason = null;

		try {
			pstmt2 = con.prepareStatement(STATEMENT2);
			pstmt2.setInt(1, delTransact.getSeasonID());
			rs2 = pstmt2.executeQuery();

			if (rs2.next()) {
					selectedSeason = new Season(
                        rs2.getInt("seasonid"), 
						rs2.getString("period"), 
                        rs2.getDouble("revenues"), 
                        rs2.getDouble("expenses"),
                        rs2.getDouble("initialFund")
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


		
		// 3. update the season from database

		String column = null;

		if (delTransact.getAmount() >= 0) {
			column = "revenues";
			selectedSeason.removeRevenues(delTransact.getAmount());
		} else {
			column = "expenses";
			selectedSeason.removeExpenses(-delTransact.getAmount());
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


		return delTransact;
	}
}
