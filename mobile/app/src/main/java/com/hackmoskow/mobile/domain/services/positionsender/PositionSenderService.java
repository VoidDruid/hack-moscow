package com.hackmoskow.mobile.domain.services.positionsender;

import android.content.ContentResolver;
import android.os.Build;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PositionSenderService {

    private ContentResolver contentResolver;

    public PositionSenderService(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void sendLocation(double latitude, double longitude, int durationInMinutes) {
        try {
            final String POST_PARAMS = "{\n" + "\"long\": " + longitude + ",\r\n" +
                    "    \"lat\": " + latitude + ",\r\n" +
                    "    \"duration\": " + durationInMinutes + "\n}";
            System.out.println(POST_PARAMS);
            URL obj = new URL("http://spacehub.su/api/location/" + getUniqId());

            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setDoOutput(true);

            OutputStream os = postConnection.getOutputStream();
            os.write(POST_PARAMS.getBytes());
            os.flush();
            os.close();

            int responseCode = postConnection.getResponseCode();
            System.out.println("POST Response Code :  " + responseCode);
            System.out.println("POST Response Message : " + postConnection.getResponseMessage());
            if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        postConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
            } else {
                System.out.println("POST NOT WORKED");
            }
        } catch (IOException e) {
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

