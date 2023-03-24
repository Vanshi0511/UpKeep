package com.example.upkeep.activity_landlord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.upkeep.R;

public class GetStartedActivity extends AppCompatActivity {

    private TextView textTerms;
    private ImageView imgBackBtn;
    private RelativeLayout myProperties,repairContacts,banking;
    private Button btnComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        textTerms=findViewById(R.id.textTerms);
        myProperties=findViewById(R.id.myProperties);
        repairContacts=findViewById(R.id.repairContacts);
        banking=findViewById(R.id.banking);
        btnComplete=findViewById(R.id.btnComplete);


        textTerms.setText("TERMS & CONDITIONS");
        textTerms.setPaintFlags(textTerms.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        textTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GetStartedActivity.this, TermsAndConditionsActivity.class);
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

        myProperties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GetStartedActivity.this, AddPropertyActivity.class);
                startActivity(intent);
            }
        });

        repairContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GetStartedActivity.this, AddRepairContactActivity.class);
                startActivity(intent);
            }
        });

        banking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GetStartedActivity.this, AddPaymentActivity.class);
                startActivity(intent);
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GetStartedActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}