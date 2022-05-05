package database;

import resource.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ReadItemDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT itemid, name, description, quantity, size, historicalgenre, image, username FROM theater_wa_db.item WHERE itemid = ?";

	/**
	 * The connection to the database
	 */
	private final Connection con;
	private final int itemid;

	
	public ReadItemDatabase(final Connection con,final int itemid) {
		this.con = con;
		this.itemid = itemid;
	}

	
	public Item readItem() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		Item a = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, itemid);
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
