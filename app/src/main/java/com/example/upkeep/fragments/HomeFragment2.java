package com.example.upkeep.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.upkeep.R;
import com.example.upkeep.adapters.RepairRequestAdapter;
import com.example.upkeep.adapters.TenantContactAdapter;


public class HomeFragment2 extends Fragment {

  private RecyclerView recyclerViewTenantContact , recyclerViewRepairRequest , recyclerViewDialog;
  private LinearLayout btnReceived , btnAwaiting;

  private ViewPager2 viewPager;

    public static int dialogStatus=0;
    public HomeFragment2() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imgCross;
        TextView textAwaitReceived,textPayment;

        recyclerViewTenantContact = view.findViewById(R.id.recyclerTenantContact);
        recyclerViewRepairRequest= view.findViewById(R.id.recyclerRepairRequest);
        btnAwaiting = view.findViewById(R.id.btnAwaiting);
        btnReceived = view.findViewById(R.id.btnReceived);

        viewPager = view.findViewById(R.id.viewPager);

        TenantContactAdapter adapter =new TenantContactAdapter(getContext());
        recyclerViewTenantContact.setAdapter(adapter);

        RepairRequestAdapter adapter1 = new RepairRequestAdapter(getContext());
        recyclerViewRepairRequest.setAdapter(adapter1);

        Dialog dialog =new Dialog(getContext());
        dialog.setContentView(R.layout.await_receive_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        imgCross = dialog.findViewById(R.id.imgCross);
        textAwaitReceived = dialog.findViewById(R.id.textAwaitReceived);
        textPayment = dialog.findViewById(R.id.textPayment);

        recyclerViewDialog = dialog.findViewById(R.id.recyclerAwaitReceived);
        recyclerViewDialog.setAdapter(adapter);

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogStatus=0;
                dialog.dismiss();
            }
        });

        btnReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textAwaitReceived.setText("Received");
                textPayment.setTextColor(getResources().getColor(R.color.black));
                textPayment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.received_drawable,0,0,0);

                dialogStatus=1;
                dialog.show();
            }
        });

        btnAwaiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textAwaitReceived.setText("Awaiting");
                textPayment.setTextColor(getResources().getColor(R.color.red));
                textPayment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.await_drawable,0,0,0);

                dialogStatus=1;
                dialog.show();
            }
        });
    }
}