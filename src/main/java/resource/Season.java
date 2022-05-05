package resource;


import com.fasterxml.jackson.core.*;

import java.io.*;


public class Season extends Resource {


	private int seasonID; // this value is set by the database - serial interpreted as an int

	private String period; // will be later interpreted as whished

	private String startDate;

	private String endDate;

	private double revenues;

	private double expenses;

	private double initialFund;


	// construction with activityID
	public Season(int seasonID, String period, double revenues, double expenses, double initialFund) {
		this.seasonID = seasonID; //possible ID
		this.period = period;
		
		period = period.substring(1,period.length()); //excluded exernal parentesis

		String[] dates = period.split( "," );

		this.startDate = dates[0];
		this.endDate = dates[1];

		this.revenues = revenues;
		this.expenses = expenses;
		this.initialFund = initialFund;
	}

	public int getSeasonID() {
		return seasonID;
	}

	public String getPeriod() {
		return period;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public double getRevenues() {
		return revenues;
	}

	public double getExpenses() {
		return expenses;
	}

	public double getInitialFund() {
		return initialFund;
	}

	public void addRevenues(double rev) {
		revenues = revenues + rev;
	}

	public void removeRevenues(double rev) {
		revenues = revenues - rev;
	}

	public void addExpenses(double exp) {
		expenses = expenses + exp;
	}

	public void removeExpenses(double exp) {
		expenses = expenses - exp;
	}

	@Override // methods are there but are wrong!
	public final void toJSON(final OutputStream out) throws IOException {
		
		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("season");

		jg.writeStartObject();

		jg.writeNumberField("seasonID", seasonID);

		jg.writeStringField("period", period);

		jg.writeNumberField("revenues", revenues);

		jg.writeNumberField("expenses", expenses);

		jg.writeNumberField("initialFund", initialFund);

		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush(); 
	}


	public static Transaction fromJSON(final InputStream in) throws IOException {
		/* 
		// the fields read from JSON

		
		String jseasonID = -1; 
		String jperiod = "";

		Double jrevenues = 0;
		Double jexpenses = 0;
		Double jinitialFund = 0;

		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "season".equals(jp.getCurrentName()) == false) {

			// there are no more events
			if (jp.nextToken() == null) {
				throw new IOException("Unable to parse JSON: no season object found.");
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
		
		return new Transaction(jtransID, jname, jdescription, jamount, jinvoice, jdate, jusername, jseasonID); */
		return null;
	} 
}