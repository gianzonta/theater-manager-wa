package database;

import resource.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public final class CreateMemberDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO theater_wa_db.member (username, name, surname, photo, birthDate, phoneNumber, email, hiringDate, isUnipdStudent, isMemberPro, userGroup) VALUES (CAST(? AS NAMEDOM), CAST(? AS NAMEDOM), CAST(? AS NAMEDOM), ? , CAST(? AS DATE), CAST(? AS PHONENUMDOM), CAST(? AS EMAILDOM), CAST(? AS DATE), ? , ? , CAST(? AS USERTYPE)) RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The employee to be updated in the database
	 */
	private final Member member;

	/**
	 * Creates a new object for updat an employee.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param employee
	 *            the employee to be created in the database.
	 */
	public CreateMemberDatabase(final Connection con, final Member member) {
		this.con = con;
		this.member = member;
	}

	/**
	 * Creates an employee in the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public Member createMember() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the create employee
		Member a = null;


		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, member.getUsername());
			//pstmt.setString(2, hashedPsw);
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getSurname());
            pstmt.setString(4, member.getPhoto());
            pstmt.setString(5, member.getBirthDate());
            pstmt.setString(6, member.getPhoneNumber());
            pstmt.setString(7, member.getEmail());
            pstmt.setString(8, member.getHiringDate());
			pstmt.setBoolean(9, member.getIsUnipdStudent());
			pstmt.setBoolean(10, member.getIsMemberPro());
			pstmt.setString(11, member.getUserGroup());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				a = new Member(
                        rs.getString("username"), 
						rs.getString("pswHash"), 
                        rs.getString("name"), 
                        rs.getString("surname"),
                        rs.getString("photo"),
                        rs.getString("birthDate"),
                        rs.getString("phoneNumber"),
						rs.getString("email"),
                        rs.getString("hiringDate"),
						rs.getBoolean("isUnipdStudent"),
						rs.getBoolean("isMemberPro"),
						rs.getString("userGroup")
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