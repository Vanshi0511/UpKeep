package com.example.upkeep.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upkeep.ApiController;
import com.example.upkeep.R;
import com.example.upkeep.SharedPref;
import com.example.upkeep.adapters.HomePropertyAdapter;
import com.example.upkeep.adapters.RepairContactAdapter;
import com.example.upkeep.models.AddPropertyModel;
import com.example.upkeep.models.AddRepairContactModel;
import com.example.upkeep.models.RepairFragmentModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RepairFragment extends Fragment {

    List<AddRepairContactModel> repairContactModelList = new ArrayList<>() ;
    private Activity activity;
    private ProgressBar progressBar;
    private TextView tvNoRepairFound;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    public RepairFragment(Activity activity) {
        this.activity = activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize database reference
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Repair Contact");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repair, container, false);


        // Add value event listener to database reference
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called when the data in the database changes
                // You can retrieve the data using the dataSnapshot parameter
                // For example:
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AddRepairContactModel model = snapshot.getValue(AddRepairContactModel.class);
                    repairContactModelList.add(model);
                    // etc.
                }
                setListWithAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return view;

}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        tvNoRepairFound = view.findViewById(R.id.tvNoData);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recyclerView);

        //getRepairContactFromServer();
    }

    private void getRepairContactFromServer() {
        String authToken =  "Bearer "+new SharedPref(getActivity()).getToken();
        Call<List<AddRepairContactModel>> call = ApiController.getInstance().getApiSets().getRepairContacts(authToken);

        call.enqueue(new Callback<List<AddRepairContactModel>>() {
            @Override
            public void onResponse(Call<List<AddRepairContactModel>> call, Response<List<AddRepairContactModel>> response) {
                if (response.isSuccessful()) {
                    List<AddRepairContactModel> repairContactModelList = response.body();
                    if (repairContactModelList.size() == 0)
                        tvNoRepairFound.setVisibility(View.VISIBLE);
                    else {
                        progressBar.setVisibility(View.GONE);
                        //setListWithAdapter((ArrayList) repairContactModelList);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AddRepairContactModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (t instanceof UnknownHostException)
                    Toast.makeText(getActivity(), "No Internet Found", Toast.LENGTH_SHORT).show();
                Log.d("failure ", t.getMessage());
                tvNoRepairFound.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setListWithAdapter() {
        RepairContactAdapter adapter = new RepairContactAdapter(getContext(), (ArrayList<AddRepairContactModel>) repairContactModelList);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }
}