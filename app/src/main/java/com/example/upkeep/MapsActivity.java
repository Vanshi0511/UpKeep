package com.example.upkeep;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.app.Activity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements LocationListener {
    Button SelectButton;
    String address;
        double latitude,longitude;
    GoogleMap map;
    LatLng mCenterLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        SelectButton = findViewById(R.id.SelectButton);

        Bundle bundle = getIntent().getExtras();
        address = bundle.getString("address");
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");

        System.out.println(" latitude======" + latitude);
        System.out.println(" longitude=====" + longitude);
        
        SelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("address", address);
                bundle.putDouble("latitude", mCenterLatLong.latitude);
                bundle.putDouble("longitude", mCenterLatLong.longitude);

                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getApplicationContext());
        ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                map = googleMap;

                LatLng currentLocation = new LatLng(latitude, longitude);

                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                map.setMyLocationEnabled(true);
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                if (currentLocation != null) {
                    map.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                    map.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
                }

                map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
                        mCenterLatLong = cameraPosition.target;
                        map.clear();
                        try {
                            Location mLocation = new Location("");
                            mLocation.setLatitude(mCenterLatLong.latitude);
                            mLocation.setLongitude(mCenterLatLong.longitude);
                            getLocationAddress();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                map.addMarker(new MarkerOptions()
                        .position(new LatLng(0, 0))
                        .title("Marker")
                        .draggable(true)
                        .snippet("Hello")
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                map.setOnMarkerDragListener(new OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {
                        Toast.makeText(MapsActivity.this, "Dragging Start", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        LatLng position = marker.getPosition();
                        Toast.makeText(MapsActivity.this, "Lat " + position.latitude + " Long " + position.longitude, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {
                        System.out.println("Dragging");
                    }
                });
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


    }


    public boolean onMarkerClick(final Marker marker) {

        if (marker.equals(map)) {
            // handle click here
            // map.getMyLocation();
            System.out.println("Clicked");
            double lat = map.getMyLocation().getLatitude();
            System.out.println("Lat" + lat);
            Toast.makeText(MapsActivity.this,
                    "Current location " + map.getMyLocation().getLatitude(),
                    Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    private void getLocationAddress() {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(mCenterLatLong.latitude, mCenterLatLong.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses != null && addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

      //  address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        System.out.println(" ADDRESS======" + address);
        System.out.println(" CITY=====" + city);
        System.out.println("PINCODE====== " + postalCode);


    }

}