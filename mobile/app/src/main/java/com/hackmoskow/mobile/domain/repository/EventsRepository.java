package com.hackmoskow.mobile.domain.repository;

import com.google.gson.Gson;
import com.hackmoskow.mobile.domain.models.Event;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EventsRepository {
    private List<Event> events;

    public EventsRepository() {
        this.events = new ArrayList<>();
    }

    public List<Event> getEvents(String category) {
        events.clear();
        try {
            StringBuilder stringBuilder = new StringBuilder("http://spacehub.su/api/org/event");
            if (!category.isEmpty()) {
                stringBuilder.append("?category=").append(category);
            }
            URL urlForGetRequest = new URL(stringBuilder.toString());
            String readLine;
            HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                // print result
                System.out.println("JSON String Result " + response.toString());
                generateEvents(response.toString());
            } else {
                System.out.println("GET NOT WORKED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    private void generateEvents(String json) {
        try {
            Gson gson = new Gson();
            json = json.replaceAll("long","longitude");
            JSONArray jsonArray = new JSONArray(json);
            for (int i=0; i<jsonArray.length(); i++) {
                events.add(gson.fromJson(jsonArray.getString(i), Event.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
