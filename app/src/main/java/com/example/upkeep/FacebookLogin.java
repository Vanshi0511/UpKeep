package com.example.upkeep;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.example.upkeep.auth.AccountLoginActivity;
import com.example.upkeep.activity_landlord.DashboardActivity;
import com.example.upkeep.auth.SliderActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

public class FacebookLogin
{
    private Activity activity;
    private int check;

    public FacebookLogin(Activity activity ,int check)
    {
       this.activity=activity;
       this.check=check;
    }
    public void  facebookSigning()
    {
        CallbackManager callbackManager;
        if(check==1)
            callbackManager= SliderActivity.callbackManager;
        else
            callbackManager= AccountLoginActivity.callbackManager;

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


                        AccessToken accessToken = AccessToken.getCurrentAccessToken();

                        GraphRequest request = GraphRequest.newMeRequest(
                                accessToken,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted( JSONObject object, GraphResponse response) {

                                        try {
                                            // todo - data of user from facebook
                                            /*
                                           String username =  object.getString("name");
                                           String profileImage = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                           String email = object.getString("email");
                                           String token = accessToken.getToken();

                                             */

                                            SharedPref pref =new SharedPref(activity,"Facebook");
                                            pref.setStatusOfAccountLogin();
                                            pref.setValueForAppIsOpened();
                                            //pref.setToken();

                                            Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(activity, NewActivity.class);
                                            activity.startActivity(intent);
                                            activity.finishAffinity();
                                        } catch (Exception e)
                                        {
                                            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,link,picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(activity, "Failed to login", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(activity, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile","email"));
    }


}
