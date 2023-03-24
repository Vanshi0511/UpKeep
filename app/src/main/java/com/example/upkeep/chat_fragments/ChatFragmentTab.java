package com.example.upkeep.chat_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.upkeep.ErrorMessage;
import com.example.upkeep.NetworkUtil;
import com.example.upkeep.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragmentTab extends Fragment {


    public ChatFragmentTab() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_chat_child, container, false);
    }

//    private void SendMessageToServer(String _id) {
//
//
//        if (NetworkUtil.isNetworkAvailable(getContext())) {
//            JsonObject paramObject;
//            try {
//                paramObject = new JsonObject();
//                paramObject.addProperty("_id", _id);
//                paramObject.addProperty("message", message);
//                paramObject.addProperty("senderName", landlord);
//
//                // Log.d("param",paramObject.toString());
//
//                Call<ResponseBody> call = AppConfig.api_Interface().send_message(paramObject);
//                call.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                      //  ErrorMessage.E("sendToken" + response.code());
//                        if (response.isSuccessful()) {
//
//                            try {
//
//                                try {
//                                    JSONObject obj = new JSONObject(response.body().string());
//                                   // ErrorMessage.E("sendToken" + obj.toString());
//                                    Gson gson = new Gson();
//                                    if (obj.getString("status").equals("true")) {
//
//                                        binding.messageEtv.setText("");
//
//                                        AppUtil.hideSoftKeyboard(ChatMainActivity.this);
//                                    } else {
//                                       // ErrorMessage.T(ChatMainActivity.this, obj.getString("message"));
//                                    }
//
//
//                                } catch (Exception e) {
//                                   // ErrorMessage.E("Exception" + e.toString());
//                                }
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                               // ErrorMessage.E("Exception" + e.toString());
//
//                            }
//
//
//                        } else {
//                          ErrorMessage.E("sendToken else is working");
//                            try {
//                                JSONObject obj = new JSONObject(response.errorBody().string());
//                               ErrorMessage.T(getContext(), obj.getString("message"));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        // ErrorMessage.T(getActivity(), "Response Fail");
//                        System.out.println("============update profile fail  :" + t.toString());
//
//                    }
//                });
//            } catch (Exception e) {
//            }
//        } else {
//          ErrorMessage.T(getContext(),getContext().getResources().getString(R.string.no_internet));
//        }
//
//    }

}