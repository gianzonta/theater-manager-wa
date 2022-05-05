package resource;


import com.fasterxml.jackson.core.*;

import java.io.*;


public class Department extends Resource {


	private final String name;

	private final String description;


	// construction without DepartmentID
	public Department(final String name, final String description) {
		this.name = name;
		this.description = description;
	}
	

	
	public final String getName() {
		return name;
	}

	public final String getDescription() {
		return description;
	}

	


	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("department");

		jg.writeStartObject();

		jg.writeStringField("name", name);

		jg.writeStringField("description", description);

		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush();
	}


	public static Department fromJSON(final InputStream in) throws IOException {

		// the fields read from JSON

		
		String jname = null;
		String jdescription = null;

		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "department".equals(jp.getCurrentName()) == false) {

			// there are no more events
			if (jp.nextToken() == null) {
				throw new IOException("Unable to parse JSON: no department object found.");
			}
		}

		while (jp.nextToken() != JsonToken.END_OBJECT) {

			if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

				switch (jp.getCurrentName()) {
					case "name":
						jp.nextToken();
						jname = jp.getText();
						break;
					case "description":
						jp.nextToken();
						jdescription = jp.getText();
						break;			
				}
			}
		}

		return new Department(jname, jdescription);
	}
}