package com.example.upkeep.models;

import java.util.ArrayList;

public class EditProfileResponseModel {


    public String status;
    public int code;
    public String message;
    public ArrayList<Object> data;

    public EditProfileResponseModel(String status, int code, String message, ArrayList<Object> data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public EditProfileResponseModel() {
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Object> getData() {
        return data;
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }

}
