package com.example.upkeep.chat_fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.upkeep.R;
import com.example.upkeep.activity_landlord.MainActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;

    public static String tag_to_redirect ="";
    public ChatFragment(MainActivity mainActivity) {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_chat,container,false);
        viewPager = (ViewPager) rootview.findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        setupViewPager(viewPager);

        tag_to_redirect =    getArguments().getString("tag");


        tabLayout = (TabLayout)rootview. findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        Log.i("tagvalue","tag "+ viewPager.getCurrentItem());
        get_fragment_From_viewpageradapter();
        return rootview;
    }

    private void get_fragment_From_viewpageradapter()
    {
        Fragment page = getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + viewPager.getCurrentItem());
                    /*
                       this method is to get Fragment from ViewPager adapter.
                       i have commented this code because i am using above code to get Fragment from ViewPager.
                    */
        //Fragment page = myPagerAdapter.getItem(mViewPager.getCurrentItem());
        if (page != null){
            switch (viewPager.getCurrentItem())
            {
                case 0:
                    OneFragment oneFragment = (OneFragment) page;
                    Log.i("fragment","one fragment "+oneFragment);
                    //  oneFragment.print(); //this is public method of Fragment OneFragment.
                    break;
                case 1:
                    TwoFragment twoFragment = (TwoFragment) page;
                    Log.i("fragment","two fragment "+twoFragment);
                    //  twoFragment.print();
                    break;
            }

        }
    }
    private void setupViewPager(ViewPager viewPager)
    {

        adapter.addFragment(new OneFragment(), "CHAT");
        adapter.addFragment(new TwoFragment(), "CHAT GROUP");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
