package com.hackmoskow.mobile.domain.repository;

import android.content.Context;

import androidx.annotation.Nullable;

import com.hackmoskow.mobile.domain.models.UserData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserProfileRepository {
    private String storageFileName;

    public UserProfileRepository(String storageFileName, Context context) {
        this.storageFileName = context.getFilesDir() + "/" + storageFileName;
    }

    public void saveUserData(UserData userData, UserProfileRepositoryCallback callback) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(storageFileName));
            writer.write("sex: " + userData.getSex());
            writer.newLine();
            writer.write("age: " + userData.getAge());
            writer.flush();
            writer.close();
            callback.onUserDataSaved();
        } catch (IOException e) {
            e.printStackTrace();
            callback.onUserDataNotSaved();
        }
    }

    @Nullable
    public UserData getUserData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(storageFileName));
            String sex = reader.readLine().split(": ")[1];
            int age = Integer.parseInt(reader.readLine().split(": ")[1]);
            return new UserData(sex, age);
        } catch (Exception e) {
            return null;
        }
    }
}
