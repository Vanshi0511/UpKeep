package com.example.upkeep.activity_tenant;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.upkeep.R;
public class SubmitMaintaineanceActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Spinner repairSpinner;
    private LinearLayout layout;
    private RelativeLayout addImage;
    private ActivityResultLauncher<String> launcher;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private Button btnSubmit;
    ProgressBar progressBar;
    private EditText name,description;
    private int imageCount = 0;
    private Uri propertyImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_maintaineance);

        addImage = findViewById(R.id.imageBtn);
        layout = findViewById(R.id.linear);
        toolbar = findViewById(R.id.toolbar);
        name = findViewById(R.id.name);
        repairSpinner = findViewById(R.id.repairSpinner);
        description = findViewById(R.id.description);
        progressBar = findViewById(R.id.progress_bar);
btnSubmit = findViewById(R.id.btnSubmit);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Submit Maintaienance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextAppearance(this, R.style.redHatFont);

        Spinner spinner = findViewById(R.id.repairSpinner);
        String[] myArray = new String[]{"-Type Of Repair-", "Leaky Faucets", "Jammed Doors", "Loose Tiles", "Stuck Drawers", "Wobbly Door Knobs"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, myArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            propertyImage = result.getData().getData();
                            insertImage();
                        }
                    }
                });
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    propertyImage = result;
                    insertImage();
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateData();
                //todo main


            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageCount >= 1) {
                    Toast.makeText(SubmitMaintaineanceActivity.this, "You can select only 1 image.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Dialog dialog = new Dialog(SubmitMaintaineanceActivity.this);
                dialog.setContentView(R.layout.image_picker_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                LinearLayout camera, gallery;
                camera = dialog.findViewById(R.id.cameraLayout);
                gallery = dialog.findViewById(R.id.galleryLayout);

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        galleryLauncher.launch(cameraIntent);
                        dialog.dismiss();
                    }
                });

                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        launcher.launch("image/*");
                        dialog.dismiss();
                    }
                });
            }
        });
    }
        public String getPath() {
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = managedQuery(propertyImage, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();

            return cursor.getString(column_index);
        }

    private void insertImage() {
        LinearLayout.LayoutParams params;
        //creating layout for image
        RelativeLayout imageLayout = new RelativeLayout(SubmitMaintaineanceActivity.this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, 200);
        imageLayout.setLayoutParams(layoutParams);

        //creating imageView
        ImageView imageView = new ImageView(SubmitMaintaineanceActivity.this);
        imageView.setBackgroundResource(R.drawable.edittext_border);
        imageView.setPadding(3, 3, 3, 3);
        imageView.setImageURI(propertyImage);

        params = new LinearLayout.LayoutParams(180, 180);
        params.setMargins(10, 10, 10, 10);
        imageView.setLayoutParams(params);


        ImageView imageCancel = new ImageView(SubmitMaintaineanceActivity.this);
        imageCancel.setImageResource(R.drawable.add_prop_cancel);
        params = new LinearLayout.LayoutParams(50, 50);
        params.setMargins(150, 0, 0, 150);
        imageCancel.setLayoutParams(params);

        //adding image to relative layout
        imageLayout.addView(imageView);
        imageLayout.addView(imageCancel);
        //adding relative layout to main linear layout
        layout.addView(imageLayout);
        imageCount++;


        imageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.removeView(imageLayout);
                imageCount--;
            }
        });

        //find the images by getChild or put the uri in arraylist.
    }

    private void validateData() {
        String titleName;
        titleName = name.getText().toString().trim();
        String desc;
        desc = description.getText().toString().trim();

        String repair;
        repair = repairSpinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(titleName)) {

            //ErrorMessage.showAlert(AddPropertyActivity.this, "Alert", "Please enter Property Name");
            name.setError("Field can't be empty");
            name.requestFocus();
        } else if (repair.equalsIgnoreCase("-Select Type Of Repair")) {
            Toast.makeText(this, "Please select repair type", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(desc)) {
            description.setError("Field can't be empty");
            description.requestFocus();
        } else {
            //todo perform a task
            progressBar.setVisibility(View.VISIBLE);
          //  AddPropertyModel model = new AddPropertyModel(propName, totRoom, propertyCapacity, add1, add2, state, cit, postCod, desc, null);
          // addData(model);
        }
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}