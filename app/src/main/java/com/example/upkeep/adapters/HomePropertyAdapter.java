package com.example.upkeep.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.upkeep.activity_landlord.MainActivity;
import com.example.upkeep.R;
import com.example.upkeep.fragments.HomeFragment2;
import com.example.upkeep.models.AddPropertyModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class HomePropertyAdapter extends RecyclerView.Adapter<HomePropertyAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<AddPropertyModel> list;

    public HomePropertyAdapter(Context context,Activity activity , ArrayList<AddPropertyModel> list )
    {
       this.context=context;
       this.activity=activity;
       this.list =list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_fragment_recycler_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.propertyName.setText(list.get(position).getPropertyName());
        holder.propertyAddress.setText(list.get(position).getAddress1());

        Glide.with(context).load(Uri.parse(list.get(position).getImage())).into(holder.propertyImage);
        //click listener
        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment2 homeFragment2= new HomeFragment2();
                ((MainActivity)activity).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, homeFragment2)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView propertyName, propertyAddress ;
        private ShapeableImageView propertyImage;
        private CardView cardLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            propertyName = itemView.findViewById(R.id.propertyName);
            propertyAddress = itemView.findViewById(R.id.propertyAddress);
            propertyImage = itemView.findViewById(R.id.propertyImage);

            cardLayout= itemView.findViewById(R.id.cardLayout);
        }
    }
}
