package com.example.upkeep.chat_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.upkeep.R;
import com.example.upkeep.activity_landlord.MainActivity;
import com.example.upkeep.fragments.HomeFragment;
import com.example.upkeep.fragments.RepairFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



    public class Chat extends AppCompatActivity
    {
        LinearLayout layout,left_linear_layout;
        ImageView sendButton;
        EditText messageArea;
        ScrollView scrollView;
        Firebase reference1, reference2,reference3;
        TextView username_text;
        String username="",token="";
        Shared_Preference shared_preference;
        ImageView image_back_chat,image_user;
        Dbhelper dbhelper;
        LinearLayout linear_online;
        TextView online_text;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);
            layout = (LinearLayout) findViewById(R.id.layout2);
            left_linear_layout = (LinearLayout) findViewById(R.id.left_linear_layout);
            // layout_2 = (RelativeLayout)findViewById(R.id.layout2);
            sendButton = (ImageView)findViewById(R.id.sendButton);
            messageArea = (EditText)findViewById(R.id.messageArea);

            linear_online =(LinearLayout)findViewById(R.id.linear_online);
            online_text =(TextView) findViewById(R.id.online_text);

            linear_online.setVisibility(View.VISIBLE);

            scrollView = (ScrollView)findViewById(R.id.scrollView);
            image_back_chat = (ImageView)findViewById(R.id.image_back_chat);
            image_user = (ImageView)findViewById(R.id.image_user);
            shared_preference = new Shared_Preference(getApplicationContext());
            username = shared_preference.get_shared_pref(Key_Value_Sharef_pref.USER_NAME);
            token = shared_preference.get_shared_pref(Key_Value_Sharef_pref.TOKEN);
            username_text = (TextView)findViewById(R.id.username_text);
            username_text.setText(UserDetails.chatWith);
            dbhelper = new Dbhelper(this);
            UserDetails.username = username;
            if(getIntent().getStringExtra("chat_image") != null)
            {
                String chat_image =   getIntent().getStringExtra("chat_image");
                if(!(chat_image.equals("")))
                {
                    Glide.with(getApplicationContext())
                            .load(chat_image)
                            .fitCenter()
                            .placeholder(R.mipmap.fan)
                            .error(R.mipmap.fan);
                }
            }
            Log.i("username","gdfg "+UserDetails.username +" "+UserDetails.chatWith);
            Firebase.setAndroidContext(this);
            reference1 = new Firebase("https://upkeep-200807.firebaseio.com/" + UserDetails.username + "_" + UserDetails.chatWith);
            reference2 = new Firebase("https://upkeep-200807.firebaseio.com/" + UserDetails.chatWith + "_" + UserDetails.username);
            reference3 = new Firebase("https://upkeep-200807.firebaseio.com/Online_User");
            dbhelper.updataDataread_count(UserDetails.chatWith,"0");

            image_back_chat.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    register_user_on_firebase(UserDetails.username,"Offline");
                    dbhelper.updataDataread_count(UserDetails.chatWith,"0");
                    if(ChatFragment.tag_to_redirect.equals("home"))
                    {
                        Intent intent=new Intent(Chat.this,HomeFragment.class);
                        intent.putExtra("tag","Chat");
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent=new Intent(Chat.this,RepairFragment.class);
                        intent.putExtra("tag","Chat");
                        startActivity(intent);
                        finish();
                    }
                }
            });


            sendButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String messageText = messageArea.getText().toString();
                    // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String currentDateandTime = sdf.format(new Date());
                    Log.i("username_chat","chat "+UserDetails.chatWith+" "+messageText+" "+currentDateandTime);
                    dbhelper.updataData(UserDetails.chatWith,messageText,currentDateandTime);

                    if(!messageText.equals(""))
                    {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("message", messageText);
                        map.put("user", UserDetails.username);
                        map.put("email", UserDetails.username);
                        map.put("time", currentDateandTime);
                        map.put("read_count", "0");
                        String Key = reference1.push().getKey();
                        Log.i("jsonresopnsekey","key "+Key);
                        reference1.push().setValue(map);
                        reference2.push().setValue(map);
                        messageArea.setText("");
                    }
                }
            });
            Log.i("jsonresponse","chat "+" " +UserDetails.chatWith+" "+UserDetails.username);
            register_user_on_firebase(UserDetails.username,"Online");
            //  get_online_data(UserDetails.chatWith);
            reference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("jsonresponechat6","chat data "+dataSnapshot+" ");

                    // List<String> cities2 = new ArrayList<String>();
                    ArrayList<ArrayList<String>> arrayLists = new ArrayList<ArrayList<String>>();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        ArrayList<String> cities = new ArrayList<String>();
                        Map map =  (postSnapshot.getValue(Map.class));
                        String read_count = map.get("read_count").toString();

                        if(read_count.equals("0"))
                        {
                            cities.add(postSnapshot.getKey().toString());
                            cities.add(read_count);
                            arrayLists.add(cities);
                        }
                    }
                    Log.i("jsonresponechat6","chat cities "+arrayLists+" ");
                    update_message_data(arrayLists);
                }
                @Override
                public void onCancelled(FirebaseError firebaseError)
                {
                }
            });

            reference3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    Log.i("online_data6","chat data "+dataSnapshot+" ");

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        Map map =  (postSnapshot.getValue(Map.class));
                        String online_status = map.get("online_status").toString();
                        String key_value = postSnapshot.getKey().toString();
                        if(key_value.equals(UserDetails.chatWith))
                        {
                            online_text.setText(online_status);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            reference2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Map map = dataSnapshot.getValue(Map.class);
                    Log.i("jsonresponechat3","reference "+s+" "+map);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            reference1.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    Log.i("jsonresponechat2", "reference " + previousChildName + " " + map);
                    String message = map.get("message").toString();
                    String userName = map.get("user").toString();
                    String time = map.get("time").toString();
                    if (userName.equals(UserDetails.username)) {
                        addMessageBox(message, 1, time);
                    } else {
                        dbhelper.updataData(userName, message, time);
                        addMessageBox(message, 2, time);
                    }
                    map.put("read_count", "1");
                    //map.put("user", user);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            //  reference1.removeValue();
            //  reference2.removeValue();
        }

        private void update_message_data(ArrayList<ArrayList<String>> arrayLists)
        {
            for(int i = 0 ;i<arrayLists.size();i++)
            {
                if (!(TextUtils.isEmpty(arrayLists.get(i).get(0))))
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("read_count", "1");
                    reference1.child(arrayLists.get(i).get(0)).updateChildren(map);
                }
            }
        }

        private void get_online_data(final String chatWith)
        {
            final String url = "https://upkeep-200807.firebaseio.com/Online_User.json";

            StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>(){
                @Override
                public void onResponse(String s)
                {
                    //  Log.i("jsonresponse","res erro "+s);
                    if(s.equals("null"))
                    {
                        online_text.setText("Offline");
                    }
                    else
                    {
                        try
                        {
                            JSONObject obj = new JSONObject(s);
                            if(obj.has(chatWith))
                            {
                                JSONObject obj_line = obj.getJSONObject(chatWith);
                                String online_status = obj_line.getString("online_status");
                                online_text.setText(online_status);
                                Log.i("jsonresponse","found data");
                            }
                            //Log.i("jsonresponse","res erro "+obj);
                        }
                        catch (JSONException e)
                        {
                            // Log.i("jsonresponse","res erro "+e);
                            e.printStackTrace();
                        }
                    }
                }

            },new com.android.volley.Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError volleyError)
                {
                    //  System.out.println("" + volleyError );
                }
            });
            RequestQueue rQueue = Volley.newRequestQueue(this);
            rQueue.add(request);
        }


        private void register_user_on_firebase(final String user, final String status) {
            String url = "https://upkeep-200807.firebaseio.com/Online_User/" + user + ".json";
            StringRequest request = new StringRequest(Request.Method.PUT, url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("register", "user register");
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("register", "error: " + error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("online_status", status);
                    return params;
                }
            };
            RequestQueue rQueue = Volley.newRequestQueue(this);
            rQueue.add(request);
        }

        public void addMessageBox(String message, int type,String time)
        {
            TextView textView = new TextView(this);
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout linearLayout1 = new LinearLayout(this);
            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
            TextView textView1 = new TextView(this);
            textView.setText(message);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams lp_main = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //  LinearLayout.LayoutParams lp_main = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1f);
            lp_main.weight = 1.0f;
            lp2.weight = 1.0f;
            // lp2.setMargins(10,10,10,10);
            //  textView.setPadding(10,10,10,10);
            LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp4.setMargins(10,10,10,10);
            linearLayout.setPadding(5,5,5,5);
            textView.setTextSize(18);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            textView1.setText(time);
            textView1.setTextSize(10);
            if(type == 1)
            {
                lp2.gravity = Gravity.RIGHT;
                linearLayout.setBackgroundResource(R.drawable.ract_button);
                lp_main.gravity = Gravity.RIGHT;
                lp4.gravity = Gravity.RIGHT;
                textView.setLayoutParams(lp2);
                textView1.setPadding(0,0,80,0);
                linearLayout1.setLayoutParams(lp_main);
                linearLayout.setLayoutParams(lp4);
                textView.setTextColor(getResources().getColor(R.color.white));
                textView1.setTextColor(getResources().getColor(R.color.white));
                linearLayout.addView(textView);
                LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp3.gravity = Gravity.LEFT;
                textView1.setLayoutParams(lp3);
                linearLayout.addView(textView1);
                linearLayout1.addView(linearLayout);
            }
            else
            {
                lp2.gravity = Gravity.LEFT;
                lp4.gravity = Gravity.LEFT;
                textView1.setPadding(80,0,0,0);
                lp_main.gravity = Gravity.LEFT;
                textView.setLayoutParams(lp2);
                linearLayout1.setLayoutParams(lp_main);
                linearLayout.setLayoutParams(lp4);
                linearLayout.setBackgroundResource(R.drawable.gray_background);
                linearLayout.addView(textView);
                LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp3.gravity = Gravity.RIGHT;
                textView1.setLayoutParams(lp3);
                linearLayout.addView(textView1);
                linearLayout1.addView(linearLayout);
            }

            layout.addView(linearLayout1);
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.fullScroll(scrollView.FOCUS_DOWN);
                }
            });

        }

        @Override
        protected void onPause() {
            super.onPause();
            register_user_on_firebase(UserDetails.username,"Offline");
            dbhelper.updataDataread_count(UserDetails.chatWith,"0");
        }

        @Override
        protected void onResume() {
            super.onResume();
            register_user_on_firebase(UserDetails.username,"Online");
            dbhelper.updataDataread_count(UserDetails.chatWith,"0");
        }

        public void onBackPressed()
        {
            register_user_on_firebase(UserDetails.username,"Offline");
            dbhelper.updataDataread_count(UserDetails.chatWith,"0");
            if(ChatFragment.tag_to_redirect.equals("home"))
            {
                Intent intent=new Intent(Chat.this, HomeFragment.class);
                intent.putExtra("tag","Chat");
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent=new Intent(Chat.this, RepairFragment.class);
                intent.putExtra("tag","Chat");
                startActivity(intent);
                finish();
            }
        }
    }

