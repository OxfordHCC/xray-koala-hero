package org.sociam.koalahero.xray;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Pair;

import org.sociam.koalahero.JsonParsers.JsonArrayParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class XRayJsonParser {

    public List<XRayAppInfo> readAppArray(InputStreamReader ISR) {
        JsonReader jsonReader = new JsonReader(ISR);
        List<XRayAppInfo> apps = new ArrayList<XRayAppInfo>();

        try {
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                apps.add(readApp(jsonReader));
            }
            jsonReader.endArray();
        }
        catch (IOException exc) {
            // Failed To Read.
        }
        return apps;
    }

    private XRayAppInfo readApp(JsonReader jsonReader) {
        XRayAppInfo xRayAppInfo = new XRayAppInfo();

        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {

                // Check next JSON token is a Name...
                String name = "";
                if (jsonReader.peek() == JsonToken.NAME) {
                    name = jsonReader.nextName();
                }
                if(name.equals("title")) {
                    xRayAppInfo.title = jsonReader.nextString();
                }
                else if (name.equals("app")) {
                    xRayAppInfo.app = jsonReader.nextString();
                }
                else if (name.equals("storeinfo")){
                    xRayAppInfo.appStoreInfo = readAppStoreInfo(jsonReader);
                }
                else if (name.equals("developer")) {
                    xRayAppInfo.developerInfo = parseDeveloperInfo(jsonReader);
                }
                else if(name.equals("hosts")) {
                    xRayAppInfo.hosts = readStringArray(jsonReader);
                }
                else if(name.equals("icon")) {
                    xRayAppInfo.iconURI = jsonReader.nextString();
                }
                else{
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
        }
        catch (IOException exc) {

        }
        return xRayAppInfo;
    }

    public ArrayList<String> readStringArray(JsonReader jsonReader) {
        ArrayList<String> strings = new ArrayList<String>();
        try {
            if(jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                jsonReader.beginArray();
                while(jsonReader.hasNext()) {
                    strings.add(jsonReader.nextString());
                }
                jsonReader.endArray();
            }
        }
        catch (IOException exc) {

        }
        return strings;
    }

    private Pair<Long,Long> readInstalls(JsonReader jsonReader) {
        Long min = 0L;
        Long max = 0L;
        try{
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if(name.equals("min")) {
                    min = jsonReader.nextLong();
                }
                else if(name.equals("max")) {
                    max = jsonReader.nextLong();
                }
                else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
        }
        catch (IOException exc) {

        }
        return new Pair<Long, Long>(min,max);
    }

    private XRayAppStoreInfo readAppStoreInfo(JsonReader jsonReader) {
        XRayAppStoreInfo appStoreInfo = new XRayAppStoreInfo();
        try{
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name =jsonReader.nextName();
                if(name.equals("title")) {
                    appStoreInfo.title = jsonReader.nextString();
                }
                else if(name.equals("summary")) {
                    appStoreInfo.summary = jsonReader.nextString();
                }
                else if(name.equals("storeURL")) {
                    appStoreInfo.storeURL = jsonReader.nextString();
                }
                else if (name.equals("rating")) {
                    appStoreInfo.rating = (float) jsonReader.nextDouble();
                }
                else if (name.equals("genre")) {
                    appStoreInfo.genre = jsonReader.nextString();
                }
                else if (name.equals("contentRating")) {
                    appStoreInfo.contentRating = jsonReader.nextString();
                }

                else if (name.equals("installs")) {
                    Pair<Long, Long> installs = readInstalls(jsonReader);
                    appStoreInfo.minInstalls = installs.first;
                    appStoreInfo.minInstalls = installs.second;
                }
                else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
        }
        catch (IOException exc) {

        }
        return appStoreInfo;
    }

    private DeveloperInfo parseDeveloperInfo(JsonReader jsonReader) {
        DeveloperInfo developerInfo = new DeveloperInfo();
        try{
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name =jsonReader.nextName();
                if(name.equals("emails")) {
                    developerInfo.emails = JsonArrayParser.parseStringArrayList(jsonReader);
                }
                else if(name.equals("name")) {
                    developerInfo.devName = jsonReader.nextString();
                }
                else if (name.equals("storeSite")) {
                    developerInfo.playStoreSiteURL = jsonReader.nextString();
                }
                else if(name.equals("site")) {
                    developerInfo.siteURL = jsonReader.nextString();
                }
                else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
        }
        catch (IOException exc) {

        }
        return developerInfo;
    }
}
