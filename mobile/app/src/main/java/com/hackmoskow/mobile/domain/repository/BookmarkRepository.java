package com.hackmoskow.mobile.domain.repository;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.hackmoskow.mobile.domain.models.Event;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkRepository {
    private List<Event> events;
    private String storageFileName;

    public BookmarkRepository(String storageFileName, Context context) {
        this.storageFileName = context.getFilesDir() + "/" + storageFileName;
        events = new ArrayList<>();
        loadEvents();
    }

    private void loadEvents() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(storageFileName));
            StringBuilder stringBuilder = new StringBuilder();
            while (reader.ready()) {
                stringBuilder.append(reader.readLine());
            }
            System.out.println(stringBuilder.toString());
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                events.add(gson.fromJson(jsonArray.getString(i), Event.class));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Event> getEvents() {
        events.add(new Event(1, "title", "type", "descr", 2, 3));
        return events;
    }

    public void saveEvent(Event event) {
        events.add(event);
        saveData();
    }

    public void remove(Event event) {
        events.remove(event);
        saveData();
    }

    private void saveData() {
        try {
            JsonWriter writer = new JsonWriter(new FileWriter(storageFileName));
            writer.beginArray();
            for (Event event : events) {
                writer.beginObject();
                writer.name("id").value(event.getId());
                writer.name("title").value(event.getTitle());
                writer.name("type").value(event.getType());
                writer.name("description").value(event.getDescription());
                writer.name("longitude").value(event.getLongitude());
                writer.name("lat").value(event.getLat());
                writer.endObject();
            }
            writer.endArray();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
