package com.example.upkeep.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upkeep.ApiController;
import com.example.upkeep.SharedPref;
import com.example.upkeep.activity_landlord.AddPropertyActivity;
import com.example.upkeep.R;
import com.example.upkeep.adapters.HomePropertyAdapter;
import com.example.upkeep.models.AddPropertyModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private Activity activity;
    private TextView tvNoData;
    private FloatingActionButton floatingActionButton;
    private ProgressBar progressBar;

    public HomeFragment(Activity activity) {
        this.activity=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerProperty);
        floatingActionButton = view.findViewById(R.id.floatingBtn);
        tvNoData = view.findViewById(R.id.tvNoData);
        progressBar = view.findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(activity, AddPropertyActivity.class);
                startActivity(intent);
            }
        });

        getPropertyFromServer();
    }
    private void getPropertyFromServer(){
        String authToken = "Bearer "+new SharedPref(getActivity()).getToken();
        Call<List<AddPropertyModel>> call = ApiController.getInstance().getApiSets().getProperty(authToken);

        call.enqueue(new Callback<List<AddPropertyModel>>() {
            @Override
            public void onResponse(Call<List<AddPropertyModel>> call, Response<List<AddPropertyModel>> response) {
               if(response.isSuccessful()){
                   List<AddPropertyModel> propertyModelList = response.body();
                   if(propertyModelList.size()==0)
                       tvNoData.setVisibility(View.VISIBLE);
                   else{
                       progressBar.setVisibility(View.GONE);
                       setListWithAdapter((ArrayList)propertyModelList);}
               }else{
                   progressBar.setVisibility(View.GONE);
                   Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<List<AddPropertyModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if(t instanceof UnknownHostException)
                    Toast.makeText(getActivity(), "No Internet Found", Toast.LENGTH_SHORT).show();
                Log.d("failure ",t.getMessage());
                tvNoData.setVisibility(View.VISIBLE);
            }
        });
    }
    private void setListWithAdapter(ArrayList<AddPropertyModel> list){
        HomePropertyAdapter adapter = new HomePropertyAdapter(getContext(),activity,list);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }
}