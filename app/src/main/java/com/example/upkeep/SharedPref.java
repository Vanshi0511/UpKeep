package com.example.upkeep;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private SharedPreferences sharedPreferences;

    private String status;
    private Activity activity;

    public SharedPref(Activity activity,String status) {
        this.status=status;
        this.activity=activity;
    }
    public SharedPref(Activity activity)
    {
        this.activity=activity;
    }


    public void setStatusOfAccountLogin()
    {
        sharedPreferences= activity.getSharedPreferences("UserLoggedAccount",activity.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("userAccount",status).apply();
    }

    public String getStatusOfAccountLogin()
    {
        sharedPreferences= activity.getSharedPreferences("UserLoggedAccount",activity.MODE_PRIVATE);
        String account=sharedPreferences.getString("userAccount","unKnown");
        return account;
    }

    public boolean getValueForIsAppOpened()
    {
        sharedPreferences= activity.getSharedPreferences("AppOpened",activity.MODE_PRIVATE);
        boolean flag= sharedPreferences.getBoolean("isAppOpened",false);
        return flag;
    }
    public void setValueForAppIsOpened()
    {
        sharedPreferences= activity.getSharedPreferences("AppOpened",activity.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean("isAppOpened",false))
        {
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putBoolean("isAppOpened",true).apply();
        }
    }

    public void setToken(String token)
    {
        sharedPreferences = activity.getSharedPreferences("UserLoggedAccount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("token",token).apply();
    }

    public String getToken()
    {
        sharedPreferences = activity.getSharedPreferences("UserLoggedAccount", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token","unKnown");
    }

}
