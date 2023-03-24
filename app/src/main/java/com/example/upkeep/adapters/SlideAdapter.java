package com.example.upkeep.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.upkeep.FacebookLogin;
import com.example.upkeep.auth.LoginActivity;
import com.example.upkeep.R;
import com.example.upkeep.GoogleLogin;


public class SlideAdapter extends PagerAdapter {

    private Button btnEmail;
    private Activity activity;

    public SlideAdapter(Activity activity) {
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater= (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);

        View view;
        Button googleSignInButton,fbSignInButton;
        if(position==3)
        {
            view=inflater.inflate(R.layout.activity_account_login,container,false);
            btnEmail=view.findViewById(R.id.btnEmail);
            googleSignInButton=view.findViewById(R.id.googleSignInBtn);
            fbSignInButton=view.findViewById(R.id.fbSignInBtn);

            btnEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                }
            });

            googleSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new GoogleLogin(activity).googleSigning();
                }
            });

            fbSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new FacebookLogin(activity,1).facebookSigning();
                }
            });
        }
        else
        {
            String name="upkeep";
            int id=-1;
            view = inflater.inflate(R.layout.slider_layout,container,false);


            ImageView imageView=view.findViewById(R.id.image);
            TextView textView=view.findViewById(R.id.titleName);


            switch (position)
            {
                case 0 : id=R.drawable.landload_sliding_img;
                         name="Landlord";
                         break;
                case 1 : id=R.drawable.tenant_sliding_img;
                         name="Tenant";
                         break;
                case 2 : id=R.drawable.tradesman_sliding_image;
                         name="Tradesman";
                         break;
            }
            imageView.setImageResource(id);
            textView.setText(name);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
