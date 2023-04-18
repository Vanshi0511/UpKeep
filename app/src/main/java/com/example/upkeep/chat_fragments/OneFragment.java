package com.example.upkeep.chat_fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.upkeep.R;

    public class OneFragment extends Fragment
    {
        RecyclerView recyclerview_chat_user;
        List<Message1> message1List = new ArrayList<>();
        ProgressDialog progressdialog;
        ListView usersList;
        TextView noUsersText;
        ArrayList<String> al = new ArrayList<>();
        int totalUsers = 0;
        ProgressDialog pd;
        Shared_Preference shared_preference;
        String username1 ="",token="";
        Dbhelper dbhelper;
        RecyclerView_chat_user_adapter recyclerView_chat_user_adapter;
        public OneFragment()
        {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            if (getArguments() != null)
            {
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            // Inflate the layout for this fragment
            View rootview = inflater.inflate(R.layout.fragment_one, container, false);
            shared_preference = new Shared_Preference(getContext());
            username1 = shared_preference.get_shared_pref(Key_Value_Sharef_pref.USER_NAME);
            token = shared_preference.get_shared_pref(Key_Value_Sharef_pref.TOKEN);
            progressdialog = new ProgressDialog(getContext());
            dbhelper = new Dbhelper(getContext());
            //dbhelper.droptable();
            dbhelper.createatable();
            recyclerview_chat_user = (RecyclerView)rootview.findViewById(R.id.recyclerview_chat_user);
            recyclerView_chat_user_adapter = new RecyclerView_chat_user_adapter(getActivity(),message1List);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerview_chat_user.setLayoutManager(linearLayoutManager);
            recyclerview_chat_user.setItemAnimator(new DefaultItemAnimator());
            Log.i("chat_screne_recyc","username "+username1);
       /* for(int i =0;i<15 ;i++)
        {
            Message1 Message1 = new Message1("1", "2","3","4","last");
            message1List.add(Message1);
        }*/
            recyclerview_chat_user.setAdapter(recyclerView_chat_user_adapter);


    /* UserDetails.username = username;
        usersList = (ListView)rootview. findViewById(R.id.usersList);
        noUsersText = (TextView) rootview.findViewById(R.id.noUsersText);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        pd.show();
        getting_list();*/

            ArrayList<ArrayList<String>> get_all_data  =   dbhelper.getAllData();
            Log.i("join_res_resu","result "+get_all_data);
            get_chat_list_from_server();
            return rootview;
        }

        private void get_chat_list_from_server()
        {
            show_progress("Getting Data");
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JSONObject jsonObject = new JSONObject();
            try
            {
                jsonObject.put("key1", "value1");
                jsonObject.put("key2", "value2");
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AppConfig.GET_CHAT_LIST, jsonObject, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        hide_progress();
                        Log.i("join_req","res "+response);
                        JSONObject jsonObject_resullt = null;
                        try
                        {
                            jsonObject_resullt = response.getJSONObject("result");
                            String status = jsonObject_resullt.getString("status");
                            if(status.equals("true"))
                            {
                                JSONObject jsonarray_info = jsonObject_resullt.getJSONObject("info");
                                if(jsonarray_info.has("landlord"))
                                {
                                    JSONObject landlord_jsonobject = jsonarray_info.getJSONObject("landlord");
                                    String id = landlord_jsonobject.getString("id");
                                    String username = landlord_jsonobject.getString("username");
                                    String email = landlord_jsonobject.getString("email");
                                    String image ="";
                                    if(landlord_jsonobject.has("image"))
                                    {
                                        image = landlord_jsonobject.getString("image");
                                    }
                                    ArrayList<String> arrayList =  dbhelper.getNote(email);

                                    if(username1.equals(username)){}
                                    else
                                    {
                                        if(arrayList.size()>0)
                                        {
                                            Log.i("join_req_res","res user already saved");
                                            Message1 Message1 = new Message1(id,arrayList.get(0),arrayList.get(1),image,arrayList.get(2),arrayList.get(3),arrayList.get(4));
                                            message1List.add(Message1);
                                        }
                                        else
                                        {
                                            dbhelper.insertNote(username,email,"lastmesg","mesgcount","time");
                                            Message1 Message1 = new Message1(id,username,email,image,"lastmesg","time","mesgcount");
                                            message1List.add(Message1);
                                        }
                                    }

                                }
                                if(jsonarray_info.has("tenants"))
                                {
                                    JSONArray tenants_jsonArray = jsonarray_info.getJSONArray("tenants");
                                    for(int i =0;i<tenants_jsonArray.length(); i++)
                                    {
                                        JSONObject tenant_jsonobject = tenants_jsonArray.getJSONObject(i);
                                        String id = tenant_jsonobject.getString("id");
                                        String username = tenant_jsonobject.getString("username");
                                        String email = tenant_jsonobject.getString("email");
                                        String image="";
                                        if(tenant_jsonobject.has("image"))
                                        {
                                            image = tenant_jsonobject.getString("image");
                                        }
                                        ArrayList<String> arrayList =  dbhelper.getNote(email);
                                        if(username1.equals(username)){}
                                        else
                                        {
                                            if(arrayList.size()>0)
                                            {
                                                Log.i("join_req_res","res user already saved");
                                                Message1 Message1 = new Message1(id,arrayList.get(0),arrayList.get(1),image,arrayList.get(2),arrayList.get(3),arrayList.get(4));
                                                message1List.add(Message1);
                                            }
                                            else
                                            {
                                                dbhelper.insertNote(username,email,"lastmesg","mesgcount","time");
                                                Message1 Message1 = new Message1(id,username,email,image,"lastmesg","time","mesgcount");
                                                message1List.add(Message1);
                                            }
                                        }
                                    }
                                }
                                recyclerView_chat_user_adapter.notifyDataSetChanged();
                            }
                            else
                            {
                                String message= jsonObject_resullt.get("message").toString();
                                Toast.makeText(getContext(),""+message,Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // do something...
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError
                    {
                        final Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", token);
                        return headers;
                    }
                };
                requestQueue.add(jsonObjectRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void show_progress(String mesgg)
        {
            progressdialog.setMessage(mesgg);
            progressdialog.show();
        }

        private void hide_progress()
        {
            progressdialog.dismiss();
        }

}
