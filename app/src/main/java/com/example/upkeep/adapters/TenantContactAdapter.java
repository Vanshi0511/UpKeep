package com.example.upkeep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upkeep.R;
import com.example.upkeep.fragments.HomeFragment2;

public class TenantContactAdapter extends RecyclerView.Adapter<TenantContactAdapter.ViewHolder> {

    private Context context;
    public  TenantContactAdapter(Context context)
    {
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tenant_contacts_recycler_view,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(HomeFragment2.dialogStatus==1)
            holder.drawReceivedAwait.setImageResource(R.drawable.info_drawable);
        if(HomeFragment2.dialogStatus==2)
        {
            holder.chat.setImageResource(R.drawable.message);
            holder.drawReceivedAwait.setImageResource(R.drawable.fan);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView drawReceivedAwait , chat , call;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            drawReceivedAwait = itemView.findViewById(R.id.drawReceivedAwait);
            chat = itemView.findViewById(R.id.imgChat);
            call = itemView.findViewById(R.id.imgCall);
        }
    }
}
