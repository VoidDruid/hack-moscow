package com.hackmoskow.mobile.domain.services.positonsender;

import com.hackmoskow.mobile.domain.services.UniqIdService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PositionSenderService {

    public void sendLocation(double latitude, double longitude, int durationInMinutes) {
        try {
            final String POST_PARAMS = "{\n" + "\"long\": " + longitude + ",\r\n" +
                    "    \"lat\": " + latitude + ",\r\n" +
                    "    \"duration\": " + durationInMinutes + "\n}";
            System.out.println(POST_PARAMS);
            URL obj = new URL("/api/users/" + UniqIdService.android_id + "/location");

            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setDoOutput(true);

//            OutputStream os = postConnection.getOutputStream();
//            os.write(POST_PARAMS.getBytes());
//            os.flush();
//            os.close();
//
//            int responseCode = postConnection.getResponseCode();
//            System.out.println("POST Response Code :  " + responseCode);
//            System.out.println("POST Response Message : " + postConnection.getResponseMessage());
//            if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        postConnection.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//                System.out.println(response.toString());
//            } else {
//                System.out.println("POST NOT WORKED");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

