package resource;

import com.fasterxml.jackson.core.*;

import java.io.*;

public class Member extends Resource {

    private final String username;
	private final String pswHash; // consider changing to byte[]
	private final String name;
	private final String surname;
	private final String photo; // consider changing to byte[]
	private final String birthDate; // consider changing to proper Date java data type
	private final String phoneNumber;
	private final String email;
	private final String hiringDate; // consider changing to proper Date java data type
	private final boolean isUnipdStudent;
	private final boolean isMemberPro;
	private final String userGroup;


	public Member(final String username, final String pswHash, final String name, final String surname, final String photo, final String birthDate, final String phoneNumber, final String email, final String hiringDate, final boolean isUnipdStudent, final boolean isMemberPro, final String userGroup){
        this.username = username;
        this.pswHash =pswHash;
        this.name = name;
        this.surname = surname;
        this.photo = photo;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.hiringDate = hiringDate;
        this.isUnipdStudent = isUnipdStudent;
        this.isMemberPro = isMemberPro;
        this.userGroup = userGroup;
	}


	public final String getUsername() {
		return username;
	}


	public final String getPswHash() {
		return pswHash;
	}


	public final String getName() {
		return name;
	}


	public final String getSurname() {
		return surname;
    }


	public final String getPhoto () {
		return photo;
    }

    	public final String getBirthDate() {
		return birthDate;
    }

	public final String getPhoneNumber() {
		return phoneNumber;
    }

	public final String getEmail() {
		return email;
    }

	public final String getHiringDate() {
		return hiringDate;
    }

	public final boolean getIsUnipdStudent() {
		return isUnipdStudent;
    }

	public final boolean getIsMemberPro() {
		return isMemberPro;
    }

	public final String getUserGroup() {
		return userGroup;
    }


	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("member");

		jg.writeStartObject();

        jg.writeStringField("username", username);

        jg.writeStringField("pswHash", pswHash); //consider changing to blob

        jg.writeStringField("name", name);
 
        jg.writeStringField("surname", surname);

        jg.writeStringField("photo", photo); //consider changing to blob
        
        jg.writeStringField("birthDate", birthDate);    //consider changing to proper date

        jg.writeStringField("phoneNumber", phoneNumber);

        jg.writeStringField("email", email);

        jg.writeStringField("hiringDate", hiringDate);  //consider changing to proper date

        jg.writeBooleanField("isUnipdStudent", isUnipdStudent);
        
        jg.writeBooleanField("isMemberPro", isMemberPro);

		jg.writeStringField("userGroup", userGroup);

		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush();
	}

	/**
	 * Creates a {@code Member} from its JSON representation.
	 *
	 * @param in the input stream containing the JSON document.
	 *
	 * @return the {@code Employee} created from the JSON representation.
	 *
	 * @throws IOException if something goes wrong while parsing.
	 */
	public static Member fromJSON(final InputStream in) throws IOException {

		// the fields read from JSON
        String jusername = null;
        String jpswHash = null; // consider changing to byte[]
        String jname = null;
        String jsurname = null;
        String jphoto = null; // consider changing to byte[]
        String jbirthDate = null; // consider changing to proper Date java data type
        String jphoneNumber = null;
        String jemail = null;
        String jhiringDate = null; // consider changing to proper Date java data type
        boolean jisUnipdStudent = false;
        boolean jisMemberPro = false;
        String juserGroup = null;

		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "member".equals(jp.getCurrentName()) == false) {

			// there are no more events
			if (jp.nextToken() == null) {
				throw new IOException("Unable to parse JSON: no member object found.");
			}
		}

		while (jp.nextToken() != JsonToken.END_OBJECT) {

			if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
                
				switch (jp.getCurrentName()) {
					case "username":
						jp.nextToken();
						jusername = jp.getText();
						break;
					case "pswHash":
						jp.nextToken();
						jpswHash = jp.getText();
						break;
					case "name":
						jp.nextToken();
						jname = jp.getText();
						break;
					case "surname":
						jp.nextToken();
						jsurname = jp.getText();
                        break;
                    case "photo":
						jp.nextToken();
						jphoto = jp.getText();
                        break;
                    case "birthDate":
						jp.nextToken();
						jbirthDate = jp.getText();
                        break;
                    case "phoneNumber":
						jp.nextToken();
						jphoneNumber = jp.getText();
                        break;
                    case "email":
						jp.nextToken();
						jemail = jp.getText();
                        break;
                    case "hiringDate":
						jp.nextToken();
						jhiringDate = jp.getText();
                        break;
                    case "isUnipdStudent":
						jp.nextToken();
						jisUnipdStudent = (jp.getText().equals("true"));
                        break;
                    case "isMemberPro":
						jp.nextToken();
						jisMemberPro = (jp.getText().equals("true"));
                        break;
                    case "userGroup":
						jp.nextToken();
						juserGroup = jp.getText();
                        break;                  
				}
			}
		}

		return new Member(jusername, jpswHash, jname, jsurname, jphoto, jbirthDate, jphoneNumber, jemail, jhiringDate, jisUnipdStudent, jisMemberPro, juserGroup);
	}
}
