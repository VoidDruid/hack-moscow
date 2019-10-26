package com.hackmoskow.mobile.domain.repository;

import android.content.ContentResolver;
import android.os.Build;
import android.provider.Settings;

import com.google.gson.Gson;
import com.hackmoskow.mobile.domain.models.Places;
import com.here.android.mpa.common.GeoCoordinate;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlacesRepository {
    private List<Places> places;
    private ContentResolver contentResolver;

    public PlacesRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        places = new ArrayList<>();
        places.add(new Places(new GeoCoordinate(55.814297, 37.575042), "Росбанк", "Финансы", 10));
        places.add(new Places(new GeoCoordinate(55.815081, 37.573387), "Лондон Авто", "Автомобили", 20));
        places.add(new Places(new GeoCoordinate(55.815400, 37.574701), "Институт общественных знаний", "Обучение", 30));
        places.add(new Places(new GeoCoordinate(55.814964, 37.576771), "Дмитровское Шоссе", "Автомобили", 40));
        places.add(new Places(new GeoCoordinate(55.816991, 37.574572), "Ламповед", "Финансы", 50));
    }

    public List<Places> getPlaces(double latitude, double longitude, String categories) {
        places.clear();
        try {
            URL urlForGetRequest = new URL("http://spacehub.su/api/users/" + getUniqId() + "/search?" +
                    "lat=" + latitude + "&long=" + longitude + "&category=" + categories);
            String readLine;
            HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                // print result
                System.out.println("JSON String Result " + response.toString());
                generatePlacesFromJson(response.toString());
            } else {
                System.out.println("GET NOT WORKED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return places;
    }

    private void generatePlacesFromJson(String json) {
        try {
            json = json.replaceAll("long", "longitude");
            Gson gson = new Gson();
            JSONArray jsonArray = new JSONArray(json);
            for (int i=0; i<jsonArray.length(); i++) {
                places.add(gson.fromJson(jsonArray.getString(i), Places.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUniqId() {
        String uniqId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);

        if (uniqId == null) {
            uniqId = "35" +
                    Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                    Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                    Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                    Build.USER.length() % 10;
        }
        return uniqId;
    }
}
