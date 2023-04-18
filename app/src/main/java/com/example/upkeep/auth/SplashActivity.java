package com.example.upkeep.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.upkeep.PincodeRentActivity;
import com.example.upkeep.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent intent;
                intent=new Intent(SplashActivity.this, PincodeRentActivity.class);
//                if(new SharedPref(SplashActivity.this).getValueForIsAppOpened())
//                {
//                    if(new SharedPref(SplashActivity.this).getStatusOfAccountLogin().equals("unKnown"))
//                        intent=new Intent(SplashActivity.this, AccountLoginActivity.class);
//                    else
//                        intent=new Intent(SplashActivity.this, NewActivity.class);
//                }
//                else
//                    intent=new Intent(SplashActivity.this, SliderActivity.class);

                startActivity(intent);
                finish();
            }
        },2000);
    }
}