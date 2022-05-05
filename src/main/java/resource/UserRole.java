package resource;

import com.fasterxml.jackson.core.*;

import java.io.*;


public class UserRole extends Resource {

	private final String username;

	private final int seasonID;

	private final int playID;

	private final String name; // name of the department

	private final String role;

	// constructor
	public UserRole(final String username, final int seasonID, final int playID, final String name, final String role) {

		this.username = username;
		this.seasonID = seasonID;
		this.playID = playID;
		this.name = name;
		this.role = role;
	}

	public final int getSeasonID() {
		return seasonID;
	}

	public final String getName() {
		return name;
	}

	public final String getUsername() {
		return username;
	}

	public final int getPlayID() {
		return playID;
	}

	public final String getRole() {
		return role;
	}


	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("userrole");

		jg.writeStartObject();

		jg.writeStringField("username", username);

		jg.writeNumberField("seasonID", seasonID);

		jg.writeNumberField("playID", playID);

		jg.writeStringField("name", name);

		jg.writeStringField("role", role);

		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush();
	}


	public static UserRole fromJSON(final InputStream in) throws IOException {

		// the fields read from JSON

		String jusername = null;
		int jseasonID = -1;
		int jplayID = -1;
		String jname = null;
		String jrole = null;

		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "userrole".equals(jp.getCurrentName()) == false) {

			// there are no more events
			if (jp.nextToken() == null) {
				throw new IOException("Unable to parse JSON: no UserRole object found.");
			}
		}

		while (jp.nextToken() != JsonToken.END_OBJECT) {

			if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

				switch (jp.getCurrentName()) {
					case "playID":
						jp.nextToken();
						jplayID = Integer.parseInt(jp.getText());
						break;
					case "name":
						jp.nextToken();
						jname = jp.getText();
						break;
					case "username":
						jp.nextToken();
						jusername = jp.getText();
						break;
					case "seasonID":
						jp.nextToken();
						jseasonID = Integer.parseInt(jp.getText());
						break;
					case "role":
						jp.nextToken();
						jrole = jp.getText();
						break;					
				}
			}
		}

		return new UserRole(jusername, jseasonID, jplayID, jname, jrole);
	}
}