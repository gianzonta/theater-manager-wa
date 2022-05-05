package database;

import resource.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ListItemDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT * FROM theater_wa_db.item";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * Creates a new object for listing all the departments.
	 * 
	 * @param con
	 *            the connection to the database.
	 */
	public ListItemDatabase(final Connection con) {
		this.con = con;
	}

	/**
	 * Lists all the members in the database.
	 * 
	 * @return a list of {@code Item} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for item.
	 */
	public List<Item> listItem() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Item> a = new ArrayList<Item>();

		try {
			pstmt = con.prepareStatement(STATEMENT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				a.add(new Item(
                        rs.getInt("itemid"), 
                        rs.getString("name"), 
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getString("size"), 
                        rs.getString("historicalgenre"),
                        rs.getString("image"), 
                        rs.getString("username") 
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

		return a;
	}
}
