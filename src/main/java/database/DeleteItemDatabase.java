package database;

import resource.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public final class DeleteItemDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "DELETE FROM theater_wa_db.item WHERE itemid = ? RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The badge of the employee
	 */
	private final int itemid;

	/**
	 * Creates a new object for deleting an employee.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param username
	 *            the badge of the employee.
	 */
	public DeleteItemDatabase(final Connection con, final int itemid) {
		this.con = con;
		this.itemid = itemid;
	}

	/**
	 * Deletes an employee from the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public Item deleteItem() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the deleted Department
		Item a = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, itemid);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				a = new Item(
                        rs.getInt("itemid"), 
                        rs.getString("name"), 
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getString("size"), 
                        rs.getString("historicalgenre"),
                        rs.getString("image"), 
                        rs.getString("username") 
                        );
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

		return a;
	}
}
