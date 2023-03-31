package com.example.upkeep.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.upkeep.NewActivity;
import com.example.upkeep.R;
import com.example.upkeep.SharedPref;
import com.example.upkeep.activity_landlord.DashboardActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(new SharedPref(SplashActivity.this).getValueForIsAppOpened())
                {
                    if(new SharedPref(SplashActivity.this).getStatusOfAccountLogin().equals("unKnown"))
                        intent=new Intent(SplashActivity.this, AccountLoginActivity.class);
                    else
                        intent=new Intent(SplashActivity.this, NewActivity.class);
                }
                else
                    intent=new Intent(SplashActivity.this, SliderActivity.class);

                startActivity(intent);
                finish();
            }
        },2000);
    }
}