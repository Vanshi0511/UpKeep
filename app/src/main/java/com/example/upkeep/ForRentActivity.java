package com.example.upkeep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ForRentActivity extends AppCompatActivity {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 102;
    List<Place.Field> fields;
    private TextView locationTv;
    private ImageButton currentLocationImgBtn;
    GpsTracker mGPS;
    FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_rent2);


        locationTv = findViewById(R.id.location_tv);
        currentLocationImgBtn = findViewById(R.id.current_location_img_btn);

        try {
            mGPS = new GpsTracker(ForRentActivity.this);
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(ForRentActivity.this);

                if (mGPS.getLocation() != null) {
                    try {
                        Geocoder geocoder = new Geocoder(ForRentActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(mGPS.getLocation().getLatitude(), mGPS.getLocation().getLongitude(), 1);
                        SavedData.saveAddress(addresses.get(0).getLocality());
                        locationTv.setText("" + addresses.get(0).getLocality());

                    } catch (Exception e) {

                    }
                }
                getLocation();

        } catch (Exception e) {
        }

        if (ContextCompat.checkSelfPermission(ForRentActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ForRentActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ForRentActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        locationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(ForRentActivity.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });
        if (mGPS.getLocation() != null) {
            try {
                Geocoder geocoder = new Geocoder(ForRentActivity.this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(mGPS.getLocation().getLatitude(), mGPS.getLocation().getLongitude(), 1);
                // SavedData.saveAddress(addresses.get(0).getAddressLine(0));
                SavedData.saveAddress(addresses.get(0).getLocality());
                locationTv.setText("" + addresses.get(0).getAddressLine(0));

            } catch (Exception e) {
            }
        }

        currentLocationImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGPS.getLocation() != null) {
                    try {
                        Geocoder geocoder = new Geocoder(ForRentActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(mGPS.getLocation().getLatitude(), mGPS.getLocation().getLongitude(), 1);
                        // SavedData.saveAddress(addresses.get(0).getAddressLine(0));
                        SavedData.saveAddress(addresses.get(0).getLocality());
                        locationTv.setText("" + addresses.get(0).getAddressLine(0));

                        ForRentActivity.this.recreate();
                    } catch (Exception e) {
                    }
                }
            }
        });
    }

    public void getLocation() {

        long UPDATE_INTERVAL_IN_MILLISECONDS = 60 * 1000; // 5*1000 = 5000 = 60 second
        final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2; // 5*1000/2 = 2500 = 2.5 second


        locationRequest = new LocationRequest()
                .setInterval(UPDATE_INTERVAL_IN_MILLISECONDS).setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);



        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(ForRentActivity.this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {

                for (Location location : locationResult.getLocations()) {
                    try {
                        SavedData.saveLatitude(String.valueOf(location.getLatitude()));
                        SavedData.saveLongitude(String.valueOf(location.getLongitude()));
                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(ForRentActivity.this, Locale.getDefault());
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        String addresss = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        locationTv.setText(addresss);


                        SavedData.saveAddress(addresss);
                        ErrorMessage.E("city" + city + "<addresss>" + addresss);
                    } catch (Exception ew) {
                        ErrorMessage.E("city Exception>>" + ew.toString());
                    }

                }
            }
        };
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                locationTv.setText(place.getName());

                SavedData.saveLatitude(String.valueOf(place.getLatLng().latitude));
                SavedData.saveLongitude(String.valueOf(place.getLatLng().longitude));
                SavedData.saveAddress(place.getName());
                ErrorMessage.E("Address" + place.getAddress());

                System.out.println("===Home LAT==="+SavedData.getLatitude());
                System.out.println("===Home LONG==="+SavedData.getLongitude());

                try {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(ForRentActivity.this, Locale.getDefault());
                    addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String addresss = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    locationTv.setText(addresss);

                    SavedData.saveAddress(addresss);
                } catch (Exception e) {
                }

                System.out.println("===Home LAT A==="+SavedData.getLatitude());
                System.out.println("===Home LONG A==="+SavedData.getLongitude());
            }
        } catch (Exception e) {
            ErrorMessage.E("Exception other" + e.toString());
        }
    }
}
