package com.example.upkeep.chat_fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.upkeep.R;
import com.example.upkeep.databinding.ListRecycleChatUserBinding;

import java.util.List;

public class RecyclerViewChatUserAdapter extends RecyclerView.Adapter<RecyclerViewChatUserAdapter.MyViewHolder> {

    private List<Message1> moviesList;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ListRecycleChatUserBinding binding;

        public MyViewHolder(ListRecycleChatUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public RecyclerViewChatUserAdapter(Context context, List<Message1> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListRecycleChatUserBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_recycle_chat_user,
                parent,
                false
        );
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Message1 movie = moviesList.get(position);
        if (!"lastmesg".equals(movie.getDob())) {
            holder.binding.groupTitle.setText(movie.getDob());
        }
        holder.binding.groupName.setText(movie.getGenre());
        if (!"time".equals(movie.getprice())) {
            holder.binding.lastMsgTime.setText(movie.getprice());
        }
        if (!"mesgcount".equals(movie.getiscoming()) && !"0".equals(movie.getiscoming())) {
            holder.binding.messageCount.setText(movie.getiscoming());
            holder.binding.messageCount.setVisibility(View.VISIBLE);
        }

        holder.binding.linearMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDetails.chatWith = movie.getGenre();
                Intent intent = new Intent(context, Chat.class);
                intent.putExtra("chat_image", movie.getMid());
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
        if (!"".equals(movie.getMid())) {
            Glide.with(context)
                    .load(movie.getMid())
                    .centerCrop()
                    .placeholder(R.mipmap.fan)
                    .error(R.mipmap.fan)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.binding.listImage);
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}