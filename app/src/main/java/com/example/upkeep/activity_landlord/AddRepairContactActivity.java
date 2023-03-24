package com.example.upkeep.activity_landlord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.upkeep.ApiController;
import com.example.upkeep.R;
import com.example.upkeep.models.AddRepairContactModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRepairContactActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText name,email,contact;
    private AutoCompleteTextView typeOfRepair;
    private Button btnSave;

    private String name1,email1,contact1,typeOfRepair1;

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
}