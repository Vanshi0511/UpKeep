package com.example.upkeep.activity_landlord;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.upkeep.R;
import com.google.android.material.imageview.ShapeableImageView;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView backBtn , imageLayout;
    private ShapeableImageView btnEdit;
    private RadioButton radioMale,radioFemale;
    private Button btnSave;
    private ActivityResultLauncher<String> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backBtn=findViewById(R.id.imgBackBtn);
        imageLayout=findViewById(R.id.imageLayout);
        btnEdit=findViewById(R.id.btnEdit);
        btnSave=findViewById(R.id.btnSave);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
            }
        });

        launcher= registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
               imageLayout.setImageURI(result);
            }
        });
    }
}