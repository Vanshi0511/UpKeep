package com.example.upkeep.auth;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.upkeep.FacebookLogin;
import com.example.upkeep.GoogleLogin;
import com.example.upkeep.R;
import com.facebook.CallbackManager;


public class AccountLoginActivity extends AppCompatActivity {

    private Button googleSignInButton,fbSignInButton,emailSignInButton;
    public static CallbackManager callbackManager= CallbackManager.Factory.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);

        googleSignInButton=findViewById(R.id.googleSignInBtn);
        fbSignInButton=findViewById(R.id.fbSignInBtn);
        emailSignInButton=findViewById(R.id.btnEmail);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GoogleLogin(AccountLoginActivity.this).googleSigning();
            }
        });

        fbSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FacebookLogin(AccountLoginActivity.this,0).facebookSigning();
            }
        });

        emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AccountLoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100)
        GoogleLogin.activityResult(requestCode,resultCode,data,RESULT_OK);
        else
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
