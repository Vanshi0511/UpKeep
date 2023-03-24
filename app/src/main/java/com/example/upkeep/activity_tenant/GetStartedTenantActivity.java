package com.example.upkeep.activity_tenant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.upkeep.R;
import com.example.upkeep.activity_landlord.AddPaymentActivity;
import com.example.upkeep.activity_landlord.GetStartedActivity;
import com.example.upkeep.activity_landlord.TermsAndConditionsActivity;

public class GetStartedTenantActivity extends AppCompatActivity {
    private TextView textTerms;
    private ImageView imgBackBtn;
    private RelativeLayout banking,connectLandlord;
    private androidx.appcompat.widget.AppCompatButton btnCompleteTenant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started2);

        textTerms=findViewById(R.id.textTerms);
        banking=findViewById(R.id.banking);
        connectLandlord=findViewById(R.id.connectLandlord);
        btnCompleteTenant=findViewById(R.id.btnCompleteTenant);

        textTerms.setText("TERMS & CONDITIONS");
        textTerms.setPaintFlags(textTerms.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        textTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GetStartedTenantActivity.this, TermsAndConditionsActivity.class);
                startActivity(intent);

            }
        });
        imgBackBtn=findViewById(R.id.imgBackBtn);
        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        banking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(GetStartedTenantActivity.this, AddPaymentActivity.class);
                startActivity(intent);

            }
        });
        connectLandlord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(GetStartedTenantActivity.this, ConnectLandlordActivity.class);
                startActivity(intent);

            }
        });
        btnCompleteTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GetStartedTenantActivity.this, MainTenantActivity.class);
                startActivity(intent);

            }
        });
    }
}