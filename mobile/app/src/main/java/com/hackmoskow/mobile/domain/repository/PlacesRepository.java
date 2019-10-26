package com.hackmoskow.mobile.domain.repository;

import android.content.ContentResolver;
import android.os.Build;
import android.provider.Settings;

import com.hackmoskow.mobile.domain.models.Places;
import com.here.android.mpa.common.GeoCoordinate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlacesRepository {
    private List<Places> places;
    private ContentResolver contentResolver;

    public PlacesRepository() {
        places = new ArrayList<>();
        places.add(new Places(new GeoCoordinate(55.814297, 37.575042), "Росбанк", "Финансы", 10));
        places.add(new Places(new GeoCoordinate(55.815081, 37.573387), "Лондон Авто", "Автомобили", 20));
        places.add(new Places(new GeoCoordinate(55.815400, 37.574701), "Институт общественных знаний", "Обучение", 30));
        places.add(new Places(new GeoCoordinate(55.814964, 37.576771), "Дмитровское Шоссе", "Автомобили", 40));
        places.add(new Places(new GeoCoordinate(55.816991, 37.574572), "Ламповед", "Финансы", 50));
    }

    public List<Places> getPlaces(double latitude, double longitude) {
//        try {
//            URL urlForGetRequest = new URL("https://http://spacehub.su/api/users/" + getUniqId() + "/search");
//            String readLine;
//            HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setRequestProperty("lat", String.valueOf(latitude));
//            connection.setRequestProperty("long", String.valueOf(longitude));
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                BufferedReader in = new BufferedReader(
//                        new InputStreamReader(connection.getInputStream()));
//                StringBuffer response = new StringBuffer();
//                while ((readLine = in .readLine()) != null) {
//                    response.append(readLine);
//                } in .close();
//                // print result
//                System.out.println("JSON String Result " + response.toString());
//            } else {
//                System.out.println("GET NOT WORKED");
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return places;
    }

    private List<Places> getPlacesFromJson(String json) {
        return null;
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
