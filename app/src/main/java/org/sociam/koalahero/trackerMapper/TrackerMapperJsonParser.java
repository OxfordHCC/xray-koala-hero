package org.sociam.koalahero.trackerMapper;

import android.util.JsonReader;
import android.util.JsonToken;

import org.sociam.koalahero.JsonParsers.JsonArrayParser;

import java.io.IOException;
import java.util.ArrayList;

public class TrackerMapperJsonParser {

    public ArrayList<TrackerMapperCompany> parseCompanies(JsonReader jsonReader) {
        ArrayList<TrackerMapperCompany> companies = new ArrayList<>();
        try {
            if(jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                jsonReader.beginArray();
                while(jsonReader.hasNext()) {
                    companies.add(this.parseCompany(jsonReader));
                }
                jsonReader.endArray();
            }
        }
        catch (IOException exc) {

        }
        return companies;

    }

    public TrackerMapperCompany parseCompany(JsonReader jsonReader) {
        TrackerMapperCompany company = new TrackerMapperCompany();
        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {

                // Check next JSON token is a Name...
                String name = "";
                if (jsonReader.peek() == JsonToken.NAME) {
                    name = jsonReader.nextName();
                }

                //{"hostName":"facebook.com","hostID":4696,"companyName":"Facebook","companyID":2846}

                if(name.equals("hostName")) {
                    company.hostName = jsonReader.nextString();
                }
                else if (name.equals("hostID")) {
                    company.hostID = jsonReader.nextInt();
                }
                else if (name.equals("companyName")){
                    company.companyName = jsonReader.nextString();
                }
                else if(name.equals("hosts")) {
                    company.companyID = jsonReader.nextInt();
                }
                else if(name.equals("categories")) {
                    company.categories = JsonArrayParser.parseStringArrayList(jsonReader);
                }
                else if(name.equals("locale")) {
                    company.locale = jsonReader.nextString();
                }
                else{
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
        }
        catch (IOException exc) {

        }
        return company;
    }



}
