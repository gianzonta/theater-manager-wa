package resource;


import com.fasterxml.jackson.core.*;

import java.io.*;


public class Activity extends Resource {


	private final int activityID; // this value is set by the database

	private final String title;

	private final String description;

	private final String type;

	private final String location;	

	private final String date; // date is presented as a pre-formatted string, will the database be good with a string? see db_pop! - time+date

	private final String privacyTag;

	private final int maxAudience;

	private final int audienceSize;

	private final int seasonID;

	// construction without activityID
	public Activity(final String title, final String description, final String type, final String location, final String date, final String privacyTag, final int maxAudience, final int audienceSize, final int seasonID) {
		this.activityID = 0;
		this.title = title;
		this.description = description;
		this.type = type;
		this.location = location;
		this.date = date;
		this.privacyTag = privacyTag;
		this.maxAudience = maxAudience;
		this.audienceSize = audienceSize;
		this.seasonID = seasonID;
	}
	// construction with activityID
	public Activity(final int activityID, final String title, final String description, final String type, final String location, final String date, final String privacyTag, final int maxAudience, final int audienceSize, final int seasonID) {
		this.activityID = activityID;
		this.title = title;
		this.description = description;
		this.type = type;
		this.location = location;
		this.date = date;
		this.privacyTag = privacyTag;
		this.maxAudience = maxAudience;
		this.audienceSize = audienceSize;
		this.seasonID = seasonID;
	}

	public final int getActivityID() {
		return activityID;
	}

	public final String getTitle() {
		return title;
	}

	public final String getDescription() {
		return description;
	}

	public final String getType() {
		return type;
	}

		public final String getLocation() {
		return location;
	}

		public final String getDate() {
		return date;
	}

		public final String getPrivacyTag() {
		return privacyTag;
	}

		public final int getMaxAudience() {
		return maxAudience;
	}

		public final int getAudienceSize() {
		return audienceSize;
	}	

		public final int getSeasonID() {
		return seasonID;
	}	




	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("activity");

		jg.writeStartObject();

		jg.writeNumberField("activityID", activityID);

		jg.writeStringField("title", title);

		jg.writeStringField("description", description);

		jg.writeStringField("type", type);

		jg.writeStringField("location", location);

		jg.writeStringField("date", date);

		jg.writeStringField("privacyTag", privacyTag);

		jg.writeNumberField("maxAudience", maxAudience);

		jg.writeNumberField("audienceSize", audienceSize);

		jg.writeNumberField("seasonID", seasonID);

		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush();
	}


	public static Activity fromJSON(final InputStream in) throws IOException {

		// the fields read from JSON

		int jactivityID = -1;
		String jtitle = null;
		String jdescription = null;
		String jtype = null;
		String jlocation = null;
		String jdate = null;
		String jprivacyTag = null;
		int jmaxAudience = -1;
		int jaudienceSize = -1;
		int jseasonID = -1;

		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "activity".equals(jp.getCurrentName()) == false) {

			// there are no more events
			if (jp.nextToken() == null) {
				throw new IOException("Unable to parse JSON: no activity object found.");
			}
		}

		while (jp.nextToken() != JsonToken.END_OBJECT) {

			if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

				switch (jp.getCurrentName()) {
					case "activityID":
						jp.nextToken();
						jactivityID = Integer.parseInt(jp.getText());
						break;
					case "title":
						jp.nextToken();
						jtitle = jp.getText();
						break;
					case "description":
						jp.nextToken();
						jdescription = jp.getText();
						break;
					case "type":
						jp.nextToken();
						jtype = jp.getText();
						break;
					case "location":
						jp.nextToken();
						jlocation = jp.getText();
						break;
					case "date":
						jp.nextToken();
						jdate = jp.getText();
						break;
					case "privacyTag":
						jp.nextToken();
						jprivacyTag = jp.getText();
						break;	
					case "maxAudience":
						jp.nextToken();
						jmaxAudience = Integer.parseInt(jp.getText());
						break;	
					case "audienceSize":
						jp.nextToken();
						jaudienceSize = Integer.parseInt(jp.getText());
						break;
					case "seasonID":
						jp.nextToken();
						jseasonID = Integer.parseInt(jp.getText());
						break;						
				}
			}
		}

		return new Activity(jactivityID, jtitle, jdescription, jtype, jlocation, jdate, jprivacyTag, jmaxAudience, jaudienceSize, jseasonID);
	}
}