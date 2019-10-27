package com.hackmoskow.mobile.domain.repository;

import com.google.gson.Gson;
import com.hackmoskow.mobile.domain.models.Places;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CategoriesRepository {
    private List<Category> categories;

    public CategoriesRepository() {
        this.categories = new ArrayList<>();
    }

    public List<Category> getCategories() {
        categories.clear();
        try {
            URL urlForGetRequest = new URL("http://spacehub.su/api/categories");
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
                generateCategories(response.toString());
            } else {
                System.out.println("GET NOT WORKED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    private void generateCategories(String json) {
        try {
            Gson gson = new Gson();
            JSONArray jsonArray = new JSONArray(json);
            for (int i=0; i<jsonArray.length(); i++) {
                categories.add(gson.fromJson(jsonArray.getString(i), Category.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class Category {
        private String id;
        private String title;

        public Category(String id, String title) {
            this.id = id;
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }
}
