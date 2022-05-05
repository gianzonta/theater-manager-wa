/*
 * Copyright 2018 University of Padua, Italy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package database;

import resource.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import org.apache.commons.codec.digest.DigestUtils;


public final class UpdateMemberDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String ADMINEDITSTATEMENT = "UPDATE theater_wa_db.member SET name = CAST(? AS NAMEDOM), surname = CAST(? AS NAMEDOM), birthDate = CAST(? AS DATE), phoneNumber = CAST(? AS PHONENUMDOM), email = CAST(? AS EMAILDOM), hiringDate = CAST(? AS DATE), isUnipdStudent = ?, isMemberPro = ?, userGroup = CAST(? AS USERTYPE) WHERE username = ? RETURNING *";
	private static final String USEREDITSTATEMENT = "UPDATE theater_wa_db.member SET photo = ?, phoneNumber = CAST(? AS PHONENUMDOM), email = CAST(? AS EMAILDOM) WHERE username = ? RETURNING *";
	private static final String CHANGEPSWSTATEMENT = "UPDATE theater_wa_db.member SET pswHash = ? WHERE username = ? RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;


	private final Member member;


	public UpdateMemberDatabase(final Connection con, final Member member) {
		this.con = con;
		this.member = member;
	}


	public Member updateMember() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the updated employee
		Member a = null;

		if (member.getName() != null) {
		try {
			pstmt = con.prepareStatement(ADMINEDITSTATEMENT);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getSurname());
            pstmt.setString(3, member.getBirthDate());
            pstmt.setString(4, member.getPhoneNumber());
            pstmt.setString(5, member.getEmail());
            pstmt.setString(6, member.getHiringDate());
			pstmt.setBoolean(7, member.getIsUnipdStudent());
			pstmt.setBoolean(8, member.getIsMemberPro());
			pstmt.setString(9, member.getUserGroup());
			pstmt.setString(10, member.getUsername());
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
	} else {if (member.getPswHash() == null) {
		{
		try {
			pstmt = con.prepareStatement(USEREDITSTATEMENT);
            pstmt.setString(1, member.getPhoto());
            pstmt.setString(2, member.getPhoneNumber());
            pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getUsername());
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
		}
	}
	else {

		try {
			String encInPsw = org.apache.commons.codec.digest.DigestUtils.sha256Hex(member.getPswHash()); 
			
			pstmt = con.prepareStatement(CHANGEPSWSTATEMENT);
			pstmt.setString(1, encInPsw);
			pstmt.setString(2, member.getUsername());
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
		}

	}
		return a;
	}
}
