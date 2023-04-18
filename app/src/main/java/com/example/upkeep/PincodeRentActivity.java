package com.example.upkeep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upkeep.activity_landlord.GetStartedActivity;
import com.example.upkeep.auth.AccountLoginActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PincodeRentActivity extends AppCompatActivity {
    TextInputLayout pincodeLayout;
    EditText pincode;
    private TextView alreadyHavingRoomTextView;
    Button cancelButton, saveButton;
    private FusedLocationProviderClient fusedLocationClient;

    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode_rent);


        pincode = findViewById(R.id.pincode);
        pincodeLayout = findViewById(R.id.pincodeLayout);
        alreadyHavingRoomTextView = findViewById(R.id.alreadyHavingRoomTextView);

        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);
        initGoogleMapLocation();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(pincode.getText().toString())) {
                    Toast.makeText(PincodeRentActivity.this, "Please enter pincode", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(PincodeRentActivity.this, CurrentLocationActivity.class);
                    startActivity(intent);

                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pincode.setText("");
            }
        });

        alreadyHavingRoomTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PincodeRentActivity.this, AccountLoginActivity.class);
                startActivity(intent);
            }
        });

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

        System.out.println(" ADDRESS" + address);
        System.out.println(" CITY" + city);
        System.out.println("PINCODE " + postalCode);
        pincode.setText(postalCode);

    }


}