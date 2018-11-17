package com.example.lnthe54.foodshare.view.activity;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.lnthe54.foodshare.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.List;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, LocationListener,
        GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Location mLocation;
    private Geocoder mGeocoder;
    private Marker mMarker;
    private Polyline mPolyline;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= 21){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrangeOpacity));
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
                break;
            }
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mGeocoder = new Geocoder(this);

        initMaps();
    }

    @SuppressLint("MissingPermission")
    private void initMaps() {
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (mMarker != null) {
            mMarker.remove();
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        onMapClick(latLng);
        mMarker = drawMarker(latLng, "Position", getLocationName(latLng), BitmapDescriptorFactory.HUE_ORANGE);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private Marker drawMarker(LatLng latLng, String title, String snippet, float hue) {
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        options.snippet(snippet);
        options.title(title);
        options.icon(BitmapDescriptorFactory.defaultMarker(hue));
        return mMap.addMarker(options);
    }

    private String getLocationName(LatLng latLng) {
        String name = "Unknown";
        try {
            List<Address> addresses = mGeocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            name = addresses.get(0).getAddressLine(0);
        } catch (Exception e) {

        }
        return name;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mLocation == null) {
            LatLng lng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraPosition position = new CameraPosition(lng, 17, 0, 1);

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        }

        mLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
