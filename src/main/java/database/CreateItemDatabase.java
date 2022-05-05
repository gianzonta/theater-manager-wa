package database;

import resource.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

/**
 * Creates an employee in the database.
 * 
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class CreateItemDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO theater_wa_db.item (name, description, quantity, size, historicalgenre, image, username) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The employee to be updated in the database
	 */
	private final Item item;

	/**
	 * Creates a new object for updat an employee.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param employee
	 *            the employee to be created in the database.
	 */
	public CreateItemDatabase(final Connection con, final Item item) {
		this.con = con;
		this.item = item;
	}

	/**
	 * Creates an employee in the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public Item createItem() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the create employee
		Item a = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, item.getName());
			pstmt.setString(2, item.getDescription());
			pstmt.setInt(3, item.getQuantity());
			pstmt.setString(4, item.getSize());
			pstmt.setString(5, item.getHistoricalGenre());
			pstmt.setString(6, item.getImage());
			pstmt.setString(7, item.getUserName());


			rs = pstmt.executeQuery();

			if (rs.next()) {
				a = new Item(
						rs.getInt("itemID"),
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