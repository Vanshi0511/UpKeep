package com.example.upkeep.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {
    @SerializedName("Token")
     private Token token;
     private String msg;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public LoginResponseModel(Token token, String msg)
     {
         this.token=token;
         this.msg=msg;
     }
        public Token getToken() {
        return token;
    }

      public void setToken(Token token) {
        this.token = token;
    }

    public class Token
    {
        private String refresh , access;

        public Token(String refresh, String access) {
            this.refresh = refresh;
            this.access = access;
        }

        public String getRefresh() {
            return refresh;
        }

        public void setRefresh(String refresh) {
            this.refresh = refresh;
        }

        public String getAccess() {
            return access;
        }

        public void setAccess(String access) {
            this.access = access;
        }
    }

}
