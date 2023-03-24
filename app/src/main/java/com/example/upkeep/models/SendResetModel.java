package com.example.upkeep.models;

public class SendResetModel {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SendResetModel(String email) {
        this.email = email;
    }

    private String email;

}
