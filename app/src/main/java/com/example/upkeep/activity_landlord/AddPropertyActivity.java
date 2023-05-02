package com.example.upkeep.activity_landlord;

import static android.content.ContentValues.TAG;

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
import android.util.Log;
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


import com.example.upkeep.ApiController;
import com.example.upkeep.R;
import com.example.upkeep.SharedPref;
import com.example.upkeep.models.AddPropertyModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.net.UnknownHostException;
import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPropertyActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Spinner stateSpinner;
    private RelativeLayout addImage;
    private LinearLayout layout;
    private ActivityResultLauncher<String> launcher;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private Button btnAddProperty;
    private ArrayAdapter<String> arrayAdapter;
    ProgressBar progressBar;

    private EditText propertyName, totalRoom, address1, address2, city, postCode, description;

    private DatabaseReference mDatabase;
    private String propName, totRoom, add1, add2, cit, postCod, desc;
    Spinner propCapacity;

    private int imageCount = 0;
    private Uri propertyImage;
    String propertyCapacity, state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        propCapacity = findViewById(R.id.propertySpinner);
        toolbar = findViewById(R.id.toolbar);
        stateSpinner = findViewById(R.id.stateSpinner);
        addImage = findViewById(R.id.imageBtn);
        layout = findViewById(R.id.linear);
        btnAddProperty = findViewById(R.id.btnAddProperty);
        propertyName = findViewById(R.id.propertyName);
        totalRoom = findViewById(R.id.totalRoom);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        city = findViewById(R.id.city);
        postCode = findViewById(R.id.postCode);
        description = findViewById(R.id.description);
        progressBar = findViewById(R.id.progress_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Property");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitleTextAppearance(this, R.style.redHatFont);

        String[] arr = new String[]
                {"-Select state-", "Andhra Pradesh", "Karnataka", "Bihar", "Punjab", "Chhattisgarh", "Uttar Pradesh", "Haryana", "Assam", "Tamil Nadu", "Gujarat", "Odisha", "Rajasthan", "Madhya Pradesh"
                        , "Mizoram", "Tripura",
                        "Maharashtra",
                        "Jharkhand", "Goa",
                        "West Bengal",
                        "Himachal Pradesh",
                        "Telangana",
                        "Kerala",
                        "Arunachal Pradesh", "Uttarakhand", "Manipur", "Nagaland", "Meghalaya", "Sikkim", "Delhi", "Jammu & Kashmir"};

        Arrays.sort(arr);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(arrayAdapter);

        // Spinner of property capacity
        Spinner spinner = findViewById(R.id.propertySpinner);
        String[] myArray = new String[]{"-Select property capacity-", "1", "2", "3", "4", "5"};

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



        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageCount >= 1) {
                    Toast.makeText(AddPropertyActivity.this, "You can select only 1 image.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Dialog dialog =new Dialog(AddPropertyActivity.this);
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

        btnAddProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateData();
                //todo main


            }
        });


//        propCapacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (!parent.getItemAtPosition(position).toString().equals("Property Capacity") ){
//                        propertyCapacity = parent.getItemAtPosition(position).toString();
//                        if(propCapacity.equals("Property Capacity")){
//                            ErrorMessage.T(AddPropertyActivity.this, "Please select Property Capacity");
//
//                        }
//                }
//                //  (
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                ErrorMessage.T(AddPropertyActivity.this, "Please select Property Capacity");
//            }
//        });
    }

    private void validateData() {
        propName = propertyName.getText().toString().trim();

        totRoom = totalRoom.getText().toString().trim();
        add1 = address1.getText().toString().trim();
        add2 = address2.getText().toString().trim();
        //stat=state.getText().toString().trim();
        cit = city.getText().toString().trim();
        postCod = postCode.getText().toString().trim();
        desc = description.getText().toString().trim();

        state = stateSpinner.getSelectedItem().toString();
        propertyCapacity = propCapacity.getSelectedItem().toString();

        if (TextUtils.isEmpty(propName)) {

            //ErrorMessage.showAlert(AddPropertyActivity.this, "Alert", "Please enter Property Name");
            propertyName.setError("Field can't be empty");
            propertyName.requestFocus();
        } else if (propertyCapacity.equalsIgnoreCase("-Select property capacity-")) {
            Toast.makeText(this, "Please select property capacity", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(totRoom)) {
            totalRoom.setError("Field can't be empty");
            totalRoom.requestFocus();

        } else if (TextUtils.isEmpty(add1)) {
            address1.setError("Field can't be empty");
            address1.requestFocus();
        } else if(TextUtils.isEmpty(add2)){
            address2.setError("Field can't be empty");
            address2.requestFocus();
        } else if (state.equalsIgnoreCase("-Select state-")) {
            Toast.makeText(this, "Please select state", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cit)) {
            city.setError("Field can't be empty");
            city.requestFocus();
        } else if (TextUtils.isEmpty(postCod)) {
            postCode.setError("Field can't be empty");
            postCode.requestFocus();

        } else if (TextUtils.isEmpty(desc)) {
            description.setError("Field can't be empty");
            description.requestFocus();
        } else {
            //todo perform a task
            progressBar.setVisibility(View.VISIBLE);
            //addPropertyToFirebase(propName, totRoom, propertyCapacity, add1, add2, state, cit, postCod, desc, null);



            AddPropertyModel model = new AddPropertyModel(propName, totRoom, propertyCapacity, add1, add2, state, cit, postCod, desc, null);
            addData(model);
        }
    }


    private void addData(AddPropertyModel model) {

        RequestBody propertyName = RequestBody.create(MediaType.parse("multipart/form_data"), model.getPropertyName());
        RequestBody totalRoom = RequestBody.create(MediaType.parse("multipart/form_data"), model.getTotalRoom());
        RequestBody propertyCapacity = RequestBody.create(MediaType.parse("multipart/form_data"), model.getPropertyCapacity());
        RequestBody address1 = RequestBody.create(MediaType.parse("multipart/form_data"), model.getAddress1());
        RequestBody address2 = RequestBody.create(MediaType.parse("multipart/form_data"), model.getAddress2());
        RequestBody state = RequestBody.create(MediaType.parse("multipart/form_data"), model.getState());
        RequestBody city = RequestBody.create(MediaType.parse("multipart/form_data"), model.getCity());
        RequestBody postCode = RequestBody.create(MediaType.parse("multipart/form_data"), model.getPostCode());
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form_data"), model.getDescription());

        MultipartBody.Part image = null;
        File file = null;
        try {
            //  file = new File(model.getImage()); //FILE PATH
            //System.out.println("============ FILE PATH =========== "+model.getImage());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (file != null) {
            RequestBody imageFile = RequestBody.create(MediaType.parse("multipart/form_data"), file);
            image = MultipartBody.Part.createFormData("image", file.getName(), imageFile);
        }

//        if (propertyImage == null) {
//            Toast.makeText(this, "Insert image also", Toast.LENGTH_SHORT).show();
//            progressBar.setVisibility(View.GONE);
//        } else {
//            File file = new File(getPath());
//            if (file != null) {
//                RequestBody imageFile = RequestBody.create(MediaType.parse("multipart/form_data"), file);
//                image = MultipartBody.Part.createFormData("image", file.getName(), imageFile);
//            }


            Call<AddPropertyModel> call = ApiController.getInstance() // need a URL
                    .getApiSets().addProperty("Bearer "+new SharedPref(AddPropertyActivity.this).getToken(),propertyName, totalRoom, propertyCapacity, address1, address2, state, postCode, city, description, image);



            call.enqueue(new Callback<AddPropertyModel>() {
                @Override
                public void onResponse(Call<AddPropertyModel> call, Response<AddPropertyModel> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AddPropertyActivity.this, "Property Added", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AddPropertyActivity.this, response.message(), Toast.LENGTH_SHORT).show();}
                }

                @Override
                public void onFailure(Call<AddPropertyModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    if (t instanceof UnknownHostException)
                        Toast.makeText(AddPropertyActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            });

        }
    //}


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
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
        RelativeLayout imageLayout = new RelativeLayout(AddPropertyActivity.this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, 200);
        imageLayout.setLayoutParams(layoutParams);

        //creating imageView
        ImageView imageView = new ImageView(AddPropertyActivity.this);
        imageView.setBackgroundResource(R.drawable.edittext_border);
        imageView.setPadding(3, 3, 3, 3);
        imageView.setImageURI(propertyImage);

        params = new LinearLayout.LayoutParams(180, 180);
        params.setMargins(10, 10, 10, 10);
        imageView.setLayoutParams(params);


        ImageView imageCancel = new ImageView(AddPropertyActivity.this);
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
    public void addPropertyToFirebase(String propertyName, String totalRoom, String propertyCapacity, String address1, String address2, String state, String city, String postCode, String description, String image) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Generate a unique key for the property
        String propertyId = mDatabase.child("properties").push().getKey();

        // Create a new property model object
        AddPropertyModel property = new AddPropertyModel(propertyName, totalRoom, propertyCapacity, address1, address2, state, city, postCode, description, image);

        // Save the property to the database
        mDatabase.child("properties").child(propertyId).setValue(property).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Property saved successfully
                Toast.makeText(AddPropertyActivity.this, "Property saved successfully", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Property saved successfully");
                progressBar.setVisibility(View.GONE);
                onBackPressed();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Failed to save property
                Log.e(TAG, "Failed to save property: " + e.getMessage());
            }
        });
    }

}
