package com.hackmoskow.mobile.domain.models;

public class UserData {
    private String sex;
    private int age;

    public UserData(String sex, int age) {
        this.sex = sex;
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }
}
