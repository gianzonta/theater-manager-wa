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

import resource.Activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;


public final class UpdateActivityDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "UPDATE theater_wa_db.activity SET title = ?, description = ?, type = ?, location = ?, dateandtime = ?, privacytag = CAST(? AS PRIVACY), maxaudience = ?, audiencesize = ? WHERE activityid = ? RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;


	private final Activity activity;


	public UpdateActivityDatabase(final Connection con, final Activity activity) {
		this.con = con;
		this.activity = activity;
	}


	public Activity updateActivity() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the updated employee
		Activity a = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, activity.getTitle());
			pstmt.setString(2, activity.getDescription());
			pstmt.setString(3, activity.getType());
			pstmt.setString(4, activity.getLocation());

			Timestamp ts = Timestamp.valueOf(activity.getDate());
            pstmt.setTimestamp(5, ts);
            pstmt.setString(6, activity.getPrivacyTag());
            pstmt.setInt(7, activity.getMaxAudience());
            pstmt.setInt(8, activity.getAudienceSize());

            pstmt.setInt(9, activity.getActivityID());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				a = new Activity(
                        rs.getInt("activityID"), 
						rs.getString("title"), 
                        rs.getString("description"), 
                        rs.getString("type"),
                        rs.getString("location"),
                        rs.getString("dateandtime"),
                        rs.getString("privacytag"),
						rs.getInt("maxaudience"),
                        rs.getInt("audiencesize"),
                        rs.getInt("seasonid")
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
