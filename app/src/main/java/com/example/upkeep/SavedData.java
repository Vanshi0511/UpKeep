package com.example.upkeep;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SavedData {


    static SharedPreferences prefs;
        private static final String Latitude = "latitude";
        private static final String Longitude = "Longitude";
        private static final String Address = "Address";
    public static SharedPreferences getInstance() {
        if (prefs == null) {
            //code
        }
        return prefs;
    }
        public static void saveLatitude(String latitude) {
            SharedPreferences.Editor editor = getInstance().edit();
            editor.putString(Latitude, latitude);
            editor.apply();
        }

        public static String getLatitude() {
            return getInstance().getString(Latitude, "");
        }
        public static String getLongitude() {
            return getInstance().getString(Longitude, "");
        }

        public static void saveLongitude(String mobile) {
            SharedPreferences.Editor editor = getInstance().edit();
            editor.putString(Longitude, mobile);
            editor.apply();
        }
        public static String getAddress() {
            return getInstance().getString(Address, "");
        }

        public static void saveAddress(String mobile) {
            SharedPreferences.Editor editor = getInstance().edit();
            editor.putString(Address, mobile);
            editor.apply();
        }
    }

