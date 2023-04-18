package com.example.upkeep.chat_fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Shared_Preference {

        Context context_value ;
        SharedPreferences sharedPreferences;

        public Shared_Preference(Context context)
        {
            context_value = context;

        }
        public void save_shared_pref(String key, String value)
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context_value);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key,value);
            editor.commit();
        }
        public void save_shared_inf(String key, int value)
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context_value);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key,value);
            editor.commit();
        }

        public void save_shared_pref_for_int(String key, int value)
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context_value);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key,value);

            editor.commit();
        }

        public String get_shared_pref(String key)
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context_value);
            String get_key = sharedPreferences.getString(key,"1");
            return get_key;
        }

        public int get_shared_int(String key)
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context_value);
            int get_key = sharedPreferences.getInt(key,0);
            return get_key;
        }


        public String get_shared_pref_key(String key)
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context_value);
            String get_key = sharedPreferences.getString(key,"0");
            return get_key;
        }

        public void clear_data()
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context_value);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }


}
