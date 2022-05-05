package resource;


import com.fasterxml.jackson.core.*;
import java.io.*;

public class Play extends Resource {

    private final int playid;
    private final String title;
    private final String description;
    private final String script;
    private final int duration;
    private final String posterimage;
    private final String rehearsalschedule;

    // construction without playID



    public Play(final String title, final String description, final String script, final int duration, final String posterimage, final String reharsalschedule) {
        this.playid = 0;
        this.title = title;
        this.description = description;
        this.script = script;
        this.duration = duration;
        this.posterimage = posterimage;
        this.rehearsalschedule = reharsalschedule;
    }

    public Play(final int playid, final String title, final String description, final String script,final int duration,final String posterimage,final String rehearsalschedule) {
        this.playid = playid;
        this.title = title;
        this.description = description;
        this.script = script;
        this.duration = duration;
        this.posterimage = posterimage;
        this.rehearsalschedule = rehearsalschedule;
    }

    public final int getPlayid() { return playid; }

    public final String getTitle() { return title; }

    public final String getDescription() {
        return description;
    }

    public final String getScript() {
        return script;
    }

    public final int getDuration() {
        return duration;
    }

    public final String getPosterimage() { return posterimage; }

    public final String getRehearsalschedule() { return rehearsalschedule; }

    @Override
    public final void toJSON(final OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("play");

        jg.writeStartObject();

        jg.writeNumberField("playID", playid);

        jg.writeStringField("title", title);

        jg.writeStringField("description", description);

        jg.writeStringField("script", script);

        jg.writeNumberField("duration", duration);

        jg.writeStringField("posterimage", posterimage);

        jg.writeStringField("rehearsalschedule", rehearsalschedule);

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();

    }


    public static Play fromJSON(final InputStream in) throws IOException {

        // the fields read from JSON

        int jplayid = -1; //-1 is an invalid ID
        String jtitle = null;
        String jdescription = null;
        String jscript = null;
        int jduration = -1;
        String jposterimage = null;
        String jreharsalschedule= null;


        final JsonParser jp = JSON_FACTORY.createParser(in);

        // while we are not on the start of an element or the element is not
        // a token element, advance to the next element (if any)
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "play".equals(jp.getCurrentName()) == false) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no play object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {

            if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                switch (jp.getCurrentName()) {
                    case "playid":
                        jp.nextToken();
                        jplayid = Integer.parseInt(jp.getText());
                        break;
                    case "title":
                        jp.nextToken();
                        jtitle = jp.getText();
                        break;
                    case "description":
                        jp.nextToken();
                        jdescription = jp.getText();
                        break;
                    case "script":
                        jp.nextToken();
                        jscript = jp.getText();
                        break;
                    case "duration":
                        jp.nextToken();
                        jduration = Integer.parseInt(jp.getText());
                        break;
                    case "posterimage":
                        jp.nextToken();
                        jposterimage = jp.getText();
                        break;
                    case "rehearsalschedule":
                        jp.nextToken();
                        jreharsalschedule = jp.getText();
                        break;

                }
            }
        }

        return new Play(jplayid, jtitle, jdescription, jscript, jduration, jposterimage, jreharsalschedule);
    }
}
