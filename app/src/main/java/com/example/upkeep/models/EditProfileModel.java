package com.example.upkeep.models;

import java.util.ArrayList;

public class EditProfileModel {
String username;
String email;
String password;
String gender;
String image;

    public EditProfileModel(String username, String email, String password, String gender, String image) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.image = image;
    }

    public EditProfileModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
