package com.example.upkeep;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.upkeep.activity_landlord.DashboardActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


public class GoogleLogin
{
    private static int GOOGLE_REC_CODE=100;
    private static Activity activity;

    public GoogleLogin(Activity activity) {
        this.activity =activity;
    }


    public void googleSigning()
    {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);

        if(account==null)
        {
            GoogleSignInOptions googleSignInOptions =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(activity.getString(R.string.server_client_id))
                    .requestEmail()
                    .build();

            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(activity,googleSignInOptions);

            Intent signInIntent= googleSignInClient.getSignInIntent();
            activity.startActivityForResult(signInIntent,GOOGLE_REC_CODE);
        }
        else
            Toast.makeText(activity, "Already exist", Toast.LENGTH_SHORT).show();
    }

    public static void activityResult(int requestCode, int resultCode, Intent data,int RESULT_OK) {

        if (requestCode == GOOGLE_REC_CODE) {

            if(resultCode==RESULT_OK){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);}
        }
    }

    private static void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);



            // todo - data of user from google
            /*
            String username = account.getDisplayName();
            String email = account.getEmail();
            String token = account.getIdToken();
            Uri profileImage = account.getPhotoUrl();
            */

            Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show();

            SharedPref pref =new SharedPref(activity,"Google");

            pref.setStatusOfAccountLogin();
            pref.setValueForAppIsOpened();
            //pref.setToken();

            Intent intent=new Intent(activity, DashboardActivity.class);
            activity.startActivity(intent);
            activity.finishAffinity();

        } catch (ApiException e) {

            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}