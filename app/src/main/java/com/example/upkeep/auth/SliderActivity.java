package com.example.upkeep.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.upkeep.GoogleLogin;
import com.example.upkeep.NewActivity;
import com.example.upkeep.R;
import com.example.upkeep.activity_landlord.DashboardActivity;
import com.example.upkeep.adapters.SlideAdapter;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


public class SliderActivity extends AppCompatActivity {

    private static ViewPager viewPager;
    private SlideAdapter slideAdapter;
    public  RadioButton radioLandlord,radioTenant,radioTradesman;

    public static CallbackManager callbackManager = CallbackManager.Factory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider_activity);

        radioLandlord=findViewById(R.id.landlordRadio);
        radioTenant=findViewById(R.id.tenantRadio);
        radioTradesman=findViewById(R.id.tradesmanRadio);

        GoogleSignInAccount account = com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null)
        { navigateToDashboard(); }

        viewPager=findViewById(R.id.viewPager);
        slideAdapter=new SlideAdapter(SliderActivity.this);
        viewPager.setAdapter(slideAdapter);

        radioLandlord.setChecked(true);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if(position==0)
                    radioLandlord.setChecked(true);
                if(position==1)
                    radioTenant.setChecked(true);
                if(position==2)
                    radioTradesman.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        radioLandlord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        radioTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        radioTradesman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });

    }


    public void navigateToDashboard()
    {
        Intent intent=new Intent(SliderActivity.this, NewActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode!=100)  //for facebook
            callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==100)  //for google
           GoogleLogin.activityResult(requestCode,resultCode,data,RESULT_OK);
    }

}