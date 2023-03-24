package com.example.upkeep.models;

import okhttp3.MultipartBody;

public class RegisterModel {
    private String username;
    private String email;
    private String password;
    private String confirm_password;


    public String getType_of_user() {
        return type_of_user;
    }

    public void setType_of_user(String type_of_user) {
        this.type_of_user = type_of_user;
    }

    private String type_of_user;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String phone_number;
    private String gender;

    public RegisterModel(String username, String email, String password, String confirm_password, String phone_number, String gender,String type_of_user) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        this.gender=gender;
        this.phone_number=phone_number;
        this.type_of_user = type_of_user;

    }

    public RegisterModel() {
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

    public String getConfirmPassword() {
        return confirm_password;
    }

    public void setConfirmPassword(String confirm_password) {
        this.confirm_password = confirm_password;
    }
}
