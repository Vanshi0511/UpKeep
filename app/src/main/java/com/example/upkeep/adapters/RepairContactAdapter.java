package com.example.upkeep.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.upkeep.R;
import com.example.upkeep.fragments.HomeFragment2;
import com.example.upkeep.models.AddRepairContactModel;
import com.example.upkeep.models.RepairFragmentModel;

import java.util.ArrayList;

public class RepairContactAdapter extends RecyclerView.Adapter<RepairContactAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AddRepairContactModel> model;
    Dialog dialog;
    public RepairContactAdapter(Context context,ArrayList<AddRepairContactModel> model)
    {
      this.context=context;
      this.model=model;

        dialog =new Dialog(context);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.repair_fragment_recycler_view,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.typeofRepair.setText(model.get(position).getType_of_repairs());
        holder.contactNo.setText(model.get(position).getContact_no());
        //holder.address.setText(model.get(position).get);
        //Glide.with(context).load(Uri.parse(model.get(position).get))
        holder.btnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag= model.get(position).isExpand();
                if(!flag) {
                    holder.expandLayout.setVisibility(View.VISIBLE);
                    model.get(position).setExpand(true);
                    holder.arrow.setImageResource(R.drawable.arrow_down);
                }
                else
                {
                    holder.expandLayout.setVisibility(View.GONE);
                    model.get(position).setExpand(false);
                    holder.arrow.setImageResource(R.drawable.right_arrow_draw);
                }
            }
        });

        holder.btnTenantContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.tenant_contact_dialog);
                ImageView imgCross  = dialog.findViewById(R.id.imgCross);
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);

                TenantContactAdapter adapter =new TenantContactAdapter(context);
                dialog.show();
                recyclerView.setAdapter(adapter);

                imgCross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        holder.btnRepairContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.repair_contact_dialog);
                ImageView imgCross  = dialog.findViewById(R.id.imgCross);
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);

                HomeFragment2.dialogStatus=2;
                TenantContactAdapter adapter =new TenantContactAdapter(context);
                recyclerView.setAdapter(adapter);
                dialog.show();

                ImageView seeAll = dialog.findViewById(R.id.imgSeeAll);
                seeAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        seeAll.setVisibility(View.GONE);
                        recyclerView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                });

                imgCross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HomeFragment2.dialogStatus=0;
                        dialog.dismiss();
                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView typeofRepair,contactNo , propertyName,propertyAddress,name;
        private CheckBox checkBox;
        private ImageView imageView,arrow,repairImage;
        private RelativeLayout btnLayout , expandLayout;
        private LinearLayout btnTenantContact, btnRepairContact;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            typeofRepair = itemView.findViewById(R.id.typeOfRepair);
            contactNo = itemView.findViewById(R.id.contactNo);
            repairImage = itemView.findViewById(R.id.image);
            propertyName = itemView.findViewById(R.id.propertyName);
            propertyAddress = itemView.findViewById(R.id.propertyAddress);
            checkBox = itemView.findViewById(R.id.markResolved);
            imageView= itemView.findViewById(R.id.imageRepair);
            arrow = itemView.findViewById(R.id.arrow);
            btnLayout= itemView.findViewById(R.id.relative1);
            expandLayout= itemView.findViewById(R.id.relative2);

            name = itemView.findViewById(R.id.name);

            btnTenantContact = itemView.findViewById(R.id.btnTenantContact);
            btnRepairContact = itemView.findViewById(R.id.btnRepairContact);
        }
    }
}
