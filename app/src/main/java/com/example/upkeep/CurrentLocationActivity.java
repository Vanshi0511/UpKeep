package com.example.upkeep;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.upkeep.auth.SignUpActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentLocationActivity extends AppCompatActivity {
    private TextView locationTv;
    private ImageButton currentLocationImgBtn;
    private FusedLocationProviderClient fusedLocationClient;
    private Button signUpButton;
    private double latitude, longitude;
String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        signUpButton = findViewById(R.id.signUpButton);
        locationTv = findViewById(R.id.location_tv);
        currentLocationImgBtn = findViewById(R.id.current_location_img_btn);
        currentLocationImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(" latitude======" + latitude);
                System.out.println(" longitude=====" + longitude);
                Bundle bundle = new Bundle();
                bundle.putString("address", address);
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude",longitude);
                Intent intent = new Intent(CurrentLocationActivity.this, MapsActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 100);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CurrentLocationActivity.this, SignUpActivity.class);
                startActivity(in);
                finish();
            }
        });
        initGoogleMapLocation();
    }


    private void initGoogleMapLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    100);

            System.out.println("========= PERMISSION NOT GRANTED ===============");
        } else {

            System.out.println("========= PERMISSION GRANTED ===============");

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    System.out.println("======== In CALLBACK =========");

                    if (location != null) {

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        System.out.println("+++++++++++++++++ LATITUDE " + latitude + " +++++++++LONGITUDE " + longitude);


                        LatLng currentLocation = new LatLng(latitude, longitude);

//                        if (mMap != null) {
//                            mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
//                            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
//                        }
                        getLocationAddress();
                    }
                }
            });
        }

    }

    private void getLocationAddress() {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        System.out.println(" ADDRESS===" + address);
        System.out.println(" CITY===" + city);
        System.out.println("PINCODE=== " + postalCode);
        locationTv.setText(address);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            Intent intent = data;
            address = intent.getStringExtra("address");
            locationTv.setText(address);
        }
    }
}