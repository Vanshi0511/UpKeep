package com.example.upkeep.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.upkeep.activity_landlord.MainActivity;
import com.example.upkeep.fragments.ChatFragment;
import com.example.upkeep.fragments.ChatGroupFragment;

public class TabAdapter extends FragmentStateAdapter
{


    public TabAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0: return new ChatFragment();

            case 1: return new ChatGroupFragment();
        }
        return new ChatFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
