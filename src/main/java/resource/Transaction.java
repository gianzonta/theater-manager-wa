package resource;


import com.fasterxml.jackson.core.*;

import java.io.*;


public class Transaction extends Resource {


	private final int transID; // this value is set by the database - serial interpreted as an int

	private final String name;

	private final String description;

	private final double amount;

	private final String invoice;	// the link of the invoice (saved externally)

	private final String date; // date is presented as a pre-formatted string, will the database be good with a string? see db_pop! - time+date

	private final String username;

	private final int seasonID;

	private String relatedActivity = null;
	private int activityID;


	// construction without transactionID
	public Transaction(final String name, final String description, final double amount, final String invoice, final String date, final String username, final int seasonID) {
		this.transID = -1; //impossible ID
		this.name = name;
		this.description = description;
		this.amount = amount;
		this.invoice = invoice;
		this.date = date;
		this.username = username;
		this.seasonID = seasonID;
	}
	// construction with activityID
	public Transaction(final int transID, final String name, final String description, final double amount, final String invoice, final String date, final String username, final int seasonID) {
		this.transID = transID; //possible ID
		this.name = name;
		this.description = description;
		this.amount = amount;
		this.invoice = invoice;
		this.date = date;
		this.username = username;
		this.seasonID = seasonID;
	}

	// construction with related activity
	public Transaction(final int transID, final String name, final double amount, final String date, final String username, final String relatedActivity, final int activityID) {
		this.transID = transID;
		this.name = name;
		this.amount = amount;
		this.date = date;
		this.username = username;
		this.activityID = activityID;
		this.relatedActivity = relatedActivity;
		// ignore 
		this.description = null;
		this.invoice = null;
		this.seasonID = -1;
	}

	public final int getTransID() {
		return transID;
	}

	public final String getName() {
		return name;
	}

	public final String getDescription() {
		return description;
	}

	public final double getAmount() {
		return amount;
	}

	public final String getInvoice() {
		return invoice;
	}

	public final String getDate() {
		return date;
	}

	public final String getUsername() {
		return username;
	}

	public final int getSeasonID() {
		return seasonID;
	}

	public final String getRelatedActivity() {
		return relatedActivity;
	}

	public final int getActivityID() {
		return activityID;
	}


	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("transaction");

		jg.writeStartObject();

		jg.writeNumberField("transID", transID);

		jg.writeStringField("name", name);

		jg.writeStringField("description", description);

		jg.writeNumberField("amount", amount);

		jg.writeStringField("invoice", invoice);

		jg.writeStringField("date", date);

		jg.writeStringField("username", username);

		jg.writeNumberField("seasonID", seasonID);

		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush();
	}


	public static Transaction fromJSON(final InputStream in) throws IOException {

		// the fields read from JSON

		int jtransID = -1; //-1 is an invalid ID
		String jname = null;
		String jdescription = null;
		double jamount = -1;
		String jinvoice = null;
		String jdate = null;
		String jusername = null;
		int jseasonID = -1;

		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "transaction".equals(jp.getCurrentName()) == false) {

			// there are no more events
			if (jp.nextToken() == null) {
				throw new IOException("Unable to parse JSON: no transaction object found.");
			}
		}

		while (jp.nextToken() != JsonToken.END_OBJECT) {

			if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

				switch (jp.getCurrentName()) {
					case "transID":
						jp.nextToken();
						jtransID = Integer.parseInt(jp.getText());
						break;
					case "name":
						jp.nextToken();
						jname = jp.getText();
						break;
					case "description":
						jp.nextToken();
						jdescription = jp.getText();
						break;
					case "amount":
						jp.nextToken();
						jamount = Double.parseDouble(jp.getText());
						break;
					case "invoice":
						jp.nextToken();
						jinvoice = jp.getText();
						break;
					case "date":
						jp.nextToken();
						jdate = jp.getText();
						break;
					case "username":
						jp.nextToken();
						jusername = jp.getText();
						break;	
					case "seasonID":
						jp.nextToken();
						jseasonID = Integer.parseInt(jp.getText());
						break;						
				}
			}
		}

		return new Transaction(jtransID, jname, jdescription, jamount, jinvoice, jdate, jusername, jseasonID);
	}
}