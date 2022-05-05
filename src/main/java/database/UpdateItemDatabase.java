package database;

import resource.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;


public final class UpdateItemDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "UPDATE theater_wa_db.item SET name = ?, description = ?, quantity = ?, size = ?, historicalgenre = ?, image = ? WHERE itemid = ? RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;


	private final Item item;


	public UpdateItemDatabase(final Connection con, final Item item) {
		this.con = con;
		this.item = item;
	}


	public Item updateItem() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the updated employee
		Item a = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			
			pstmt.setString(1, item.getName());
			pstmt.setString(2, item.getDescription());
			pstmt.setInt(3, item.getQuantity());
			pstmt.setString(4, item.getSize());
			pstmt.setString(5, item.getHistoricalGenre());
			pstmt.setString(6, item.getImage());
			pstmt.setInt(7, item.getItemID());


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
