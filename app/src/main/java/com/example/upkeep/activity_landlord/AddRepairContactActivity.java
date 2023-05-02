package com.example.upkeep.activity_landlord;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.upkeep.ApiController;
import com.example.upkeep.R;
import com.example.upkeep.models.AddPropertyModel;
import com.example.upkeep.models.AddRepairContactModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRepairContactActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private Toolbar toolbar;
    private EditText name,email,contact;
    private AutoCompleteTextView typeOfRepair;
    private Button btnSave;

    private String name1,email1,contact1,typeOfRepair1;
    ProgressBar progressBar;

    private ArrayList<String> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repair_contact);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Repair Contact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitleTextAppearance(this,R.style.redHatFont);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        contact=findViewById(R.id.contact);
        typeOfRepair=findViewById(R.id.typeOfRepair);
        btnSave=findViewById(R.id.btnSave);
        progressBar = findViewById(R.id.progress_bar);
        arr = new ArrayList<>();
        arr.add("Fan"); arr.add("Door") ; arr.add("Light");

        ArrayAdapter<String> adapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arr);
        typeOfRepair.setAdapter(adapter);
        typeOfRepair.setThreshold(1);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name1=name.getText().toString().trim();
                email1=email.getText().toString().trim();
                contact1=contact.getText().toString().trim();
                typeOfRepair1=typeOfRepair.getText().toString().trim();

                if(TextUtils.isEmpty(name1))
                {
                    name.setError("Fields can't be empty");
                    name.requestFocus();
                }else if(TextUtils.isEmpty(contact1) || contact1.length()<10)
                {
                    contact.setError("Fields can't be empty");
                    contact.requestFocus();

                }else if(TextUtils.isEmpty(email1))
                {
                    email.setError("Fields can't be empty");
                    email.requestFocus();

                }else if(TextUtils.isEmpty(typeOfRepair1))
                {
                    typeOfRepair.setError("Fields can't be empty");
                    typeOfRepair.requestFocus();

                }else
                {
                    progressBar.setVisibility(View.VISIBLE);
                   // addRepairContactToFirebase(name1,email1,contact1,typeOfRepair1);
                    AddRepairContactModel model =new AddRepairContactModel(name1,email1,contact1,typeOfRepair1);
                    addData(model);
                }
            }
        });
    }


    private void addData(AddRepairContactModel model)
    {
        String token="";
        Call<AddRepairContactModel> call = ApiController.getInstance()
                .getApiSets().addRepairContact(token , model);

       call.enqueue(new Callback<AddRepairContactModel>() {
           @Override
           public void onResponse(Call<AddRepairContactModel> call, Response<AddRepairContactModel> response) {
               if(response.isSuccessful())
               {
                   Toast.makeText(AddRepairContactActivity.this, "Repair Contact Added", Toast.LENGTH_SHORT).show();
                   onBackPressed();
               }
           }

           @Override
           public void onFailure(Call<AddRepairContactModel> call, Throwable t) {

           }
       });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    public void addRepairContactToFirebase(String name, String email, String contact_no, String type_of_repairs) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String repairId = mDatabase.child("Repair Contact").push().getKey();


        AddRepairContactModel repaircontact =new AddRepairContactModel(name,  email, contact_no, type_of_repairs);

        mDatabase.child("Repair Contact").child(repairId).setValue(repaircontact).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Property saved successfully
                Toast.makeText(AddRepairContactActivity.this, "Repair Contact Added", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Repair Contact saved successfully");
                progressBar.setVisibility(View.GONE);
                onBackPressed();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Failed to save property
                Log.e(TAG, "Failed to save : " + e.getMessage());
            }
        });
    }

}