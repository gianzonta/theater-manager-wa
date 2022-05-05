package resource;


import com.fasterxml.jackson.core.*;

import java.io.*;


public class Item extends Resource {

	private final int itemID;

	private final String name;

	private final String description;
	
	private final int quantity;

	private final String size;

	private final String historicalGenre;

	private final String image;

	private final String username;

	// construction without DepartmentID
	public Item(final int itemID, final String name, final String description, final int quantity, final String size, final String historicalGenre, final String image, final String username) {
		
		this.itemID = itemID; 	
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.size = size;
		this.historicalGenre = historicalGenre;
		this.image = image;
		this.username = username;
	}
	
	public final int getItemID() {
		return itemID;
	}
	
	public final String getName() {
		return name;
	}

	public final String getDescription() {
		return description;
	}

	public final int getQuantity() {
		return quantity;
	}

	public final String getSize() {
		return size;
	}

	public final String getHistoricalGenre() {
		return historicalGenre;
	}

	public final String getImage() {
		return image;
	}

	public final String getUserName() {
		return username;
	}


	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("item");

		jg.writeStartObject();

		jg.writeNumberField("itemID", itemID);

		jg.writeStringField("name", name);

		jg.writeStringField("description", description);

		jg.writeNumberField("quantity", quantity);

		jg.writeStringField("size", size);

		jg.writeStringField("historicalGenre", historicalGenre);

		jg.writeStringField("image", image);

		jg.writeStringField("username", username);

		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush();
	}


	public static Item fromJSON(final InputStream in) throws IOException {

		// the fields read from JSON

		int jitemID = -1;
		String jname = null;
		String jdescription = null;
		int jquantity = -1;
		String jsize = null;
		String jhistoricalGenre = null;
		String jimage = null;
		String jusername = null;
	

		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "item".equals(jp.getCurrentName()) == false) {

			// there are no more events
			if (jp.nextToken() == null) {
				throw new IOException("Unable to parse JSON: no item object found.");
			}
		}

		while (jp.nextToken() != JsonToken.END_OBJECT) {

			if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

				switch (jp.getCurrentName()) {
					case "itemID":
						jp.nextToken();
						jitemID = Integer.parseInt(jp.getText());
						break;
					case "name":
						jp.nextToken();
						jname = jp.getText();
						break;
					case "description":
						jp.nextToken();
						jdescription = jp.getText();
						break;	
					case "quantity":
						jp.nextToken();
						jquantity = Integer.parseInt(jp.getText());
						break;
					case "size":
						jp.nextToken();
						jsize = jp.getText();
						break;
					case "historicalGenre":
						jp.nextToken();
						jhistoricalGenre = jp.getText();
						break;
					case "image":
						jp.nextToken();
						jimage = jp.getText();
						break;
					case "username":
						jp.nextToken();
						jusername = jp.getText();
						break;			
				}
			}
		}

		return new Item(jitemID, jname, jdescription, jquantity, jsize, jhistoricalGenre, jimage, jusername);
	}
}