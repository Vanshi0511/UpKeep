package com.example.upkeep.models;

import com.google.gson.annotations.SerializedName;

public class RegisterResponseModel {
    private String msg;
    private String code;



    public RegisterResponseModel(String msg) {
        this.msg = msg;
       // this.token = token;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
