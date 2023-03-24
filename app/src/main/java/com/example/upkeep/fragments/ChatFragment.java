package com.example.upkeep.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.upkeep.R;
import com.example.upkeep.adapters.TabAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ChatFragment extends Fragment {

   TabLayout tabLayout;
   ViewPager2 viewPager;
   Context context;

    public ChatFragment(Context context) {
        // Required empty public constructor
        this.context=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        tabLayout=view.findViewById(R.id.tabLayout);
        viewPager=view.findViewById(R.id.viewPager);
        loadTabLayout();
        return view;
    }
    public void loadTabLayout()
    {
        String arr[]=new String[]{"CHAT","CHAT GROUP"};
       TabAdapter adapter=new TabAdapter(this);
       viewPager.setAdapter(adapter);

       new TabLayoutMediator(tabLayout,viewPager,(tab, position) -> tab.setText(arr[position])).attach();
    }
}