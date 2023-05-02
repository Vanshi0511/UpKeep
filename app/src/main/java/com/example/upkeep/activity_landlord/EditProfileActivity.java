package com.example.upkeep.activity_landlord;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.upkeep.ApiController;
import com.example.upkeep.R;
import com.example.upkeep.SharedPref;
import com.example.upkeep.models.EditProfileModel;
import com.example.upkeep.models.LoginModel;
import com.google.android.material.imageview.ShapeableImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView backBtn, imageLayout;
    private ShapeableImageView btnEdit;
    String username,email,password,gender,image;
    private RadioButton radioMale, radioFemale;
    private Button btnSave;
    private ActivityResultLauncher<String> launcher;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backBtn = findViewById(R.id.imgBackBtn);
        imageLayout = findViewById(R.id.imageLayout);
        btnEdit = findViewById(R.id.btnEdit);
        btnSave = findViewById(R.id.btnSave);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileModel model=new EditProfileModel(username,email,password,gender,image);

                editProfile(model);
            }
        });
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

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageLayout.setImageURI(result);
            }
        });

    }

    public void editProfile(EditProfileModel model) {
        String authToken =  "Bearer "+new SharedPref(EditProfileActivity.this).getToken();


        Call<EditProfileModel> call = ApiController.getInstance().getApiSets()
                .editProfile(authToken,model);

        call.enqueue(new Callback<EditProfileModel>() {
            @Override
            public void onResponse(Call<EditProfileModel> call, Response<EditProfileModel> response) {
                if (response.isSuccessful()) {
                    EditProfileModel model = response.body();
                    Toast.makeText(EditProfileActivity.this, "Profile edited successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfileActivity.this, "Failed to edit profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EditProfileModel> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

}