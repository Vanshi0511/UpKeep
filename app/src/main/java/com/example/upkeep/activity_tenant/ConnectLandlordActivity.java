package com.example.upkeep.activity_tenant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.upkeep.R;
import com.example.upkeep.adapters.ConnectLandlordAdapter;
import com.example.upkeep.adapters.HomePropertyAdapter;

public class ConnectLandlordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Activity activity;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_landlord);

      toolbar = findViewById(R.id.toolbarconnect);
        recyclerView = findViewById(R.id.recyclerConnect);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Connect Landlord");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitleTextAppearance(this,R.style.redHatFont);

        recyclerView.setLayoutManager(new LinearLayoutManager(ConnectLandlordActivity.this));
        ConnectLandlordAdapter adapter = new ConnectLandlordAdapter(ConnectLandlordActivity.this,activity);
        recyclerView.setAdapter(adapter);


    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}