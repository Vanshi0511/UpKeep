package com.example.upkeep.activity_landlord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.upkeep.R;
import com.example.upkeep.SharedPref;
import com.example.upkeep.activity_tenant.GetStartedTenantActivity;

public class DashboardActivity extends AppCompatActivity {

    private LinearLayout amLandlord;
    private LinearLayout amTenant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        amLandlord=findViewById(R.id.amLandlord);
        amTenant=findViewById(R.id.amTenant);

        new SharedPref(DashboardActivity.this).setValueForAppIsOpened();

        amLandlord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, GetStartedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        amTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, GetStartedTenantActivity.class);
                startActivity(intent);  // TODO for tenant
                finish();
            }
        });

    }
}