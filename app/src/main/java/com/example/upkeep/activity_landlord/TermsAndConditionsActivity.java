package com.example.upkeep.activity_landlord;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.upkeep.R;

public class TermsAndConditionsActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        textView=findViewById(R.id.textTerms);
        textView.setText("TERMS & CONDITIONS");

        backBtn=findViewById(R.id.imgBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}