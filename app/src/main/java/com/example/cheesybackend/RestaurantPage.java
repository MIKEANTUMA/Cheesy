package com.example.cheesybackend;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RestaurantPage extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, LocationListener {

    TextView name;
    Button entree;
    Button drink;
    Button appetizer;
    RatingBar ratingbar;
    TextView description;
    TextView website;
    TextView phoneNumber;
    Restaurant restaurant;
    Button btnreturn;
    FloatingActionButton floatingActionButtonBack;
    private final int REQ_PERMISSION = 0;
    private Location lastLocation;
    private Address address;
    private Marker restrauantMarker;
    private LatLng restLatLng;

    private LocationRequest locationRequest;
    // Defined in mili seconds.
    private final int UPDATE_INTERVAL =  3 * 60  * 1000; // 3 minutes
    private final int FASTEST_INTERVAL = 30 * 1000;  // 30 secs
    private GoogleApiClient googleApiClient;

    private static final String TAG = RestaurantPage.class.getSimpleName();

    //    private TextView textLat, textLong;
    private MapFragment mapFragment;
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);

        name = findViewById(R.id.tv_name);
        entree = findViewById(R.id.btn_entree);
        appetizer = findViewById(R.id.btn_appetizer);
        drink = findViewById(R.id.btn_drink);
        ratingbar = findViewById(R.id.rb_rating);
        description = findViewById(R.id.tv_description);
        website = findViewById(R.id.tv_website);
        phoneNumber = findViewById(R.id.tv_phoneNumber);
        entree.setOnClickListener(this);
        appetizer.setOnClickListener(this);
        drink.setOnClickListener(this);
        floatingActionButtonBack=findViewById(R.id.floatingBtn);
        floatingActionButtonBack.setOnClickListener(this);
        double la = getIntent().getDoubleExtra("lat", 0);
        double ln = getIntent().getDoubleExtra("lng", 0);
//        restLatLng = getIntent().getExtras();
        restLatLng = new LatLng(la,ln);
        restaurant = getIntent().getParcelableExtra("Restaurant");

        // initialize GoogleMaps
        initGMaps();
        // create GoogleApiClient
        createGoogleApi();
        name.setText(restaurant.getName());
        name.setGravity(Gravity.CENTER);
        ratingbar.setRating(restaurant.getRating());
        description.setText(restaurant.getDescription());
        website.setText("website: "+restaurant.getWebsite());
        phoneNumber.setText("PhoneNumber: "+restaurant.getPhoneNumber());


        //hides the action bar
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e) {}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_entree:
                Intent intent = new Intent(this, entreePage.class);
                intent.putExtra("Restaurant", restaurant);
                startActivity(intent);
                Toast.makeText(this, "entree", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_appetizer:
                Intent intent1 = new Intent(this, appetizerPage.class);
                intent1.putExtra("Restaurant", restaurant);
                startActivity(intent1);
                Toast.makeText(this, "appetizer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_drink:
                Intent intent2 = new Intent(this, drinkPage.class);
                intent2.putExtra("Restaurant", restaurant);
                startActivity(intent2);
                break;
            case R.id.floatingBtn:
                Intent intent3 = new Intent(this, showRestaurants.class);
                intent3.putExtra("Restaurant", restaurant);
                startActivity(intent3);
                break;
        }
    }

    // Create GoogleApiClient instance
    private void createGoogleApi() {
        Log.d(TAG, "createGoogleApi()");
        if ( googleApiClient == null ) {
            googleApiClient = new GoogleApiClient.Builder( this )
                    .addConnectionCallbacks( this )
                    .addOnConnectionFailedListener( this )
                    .addApi( LocationServices.API )
                    .build();
        }
    }
    // Initialize GoogleMaps
    private void initGMaps(){
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // Callback called when Map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady()");
        map = googleMap;
        map.setOnMarkerClickListener(this);
    }

    // Callback called when Marker is touched
    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "onMarkerClickListener: " + marker.getPosition() );
        return false;
    }
    // GoogleApiClient.ConnectionCallbacks connected
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected()");
        getLastKnownLocation();
    }

    // GoogleApiClient.ConnectionCallbacks suspended
    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "onConnectionSuspended()");
    }

    // GoogleApiClient.OnConnectionFailedListener fail
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w(TAG, "onConnectionFailed()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Call GoogleApiClient connection when starting the Activity
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }
    // Get last known location
    private void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation()");
        if ( checkPermission() ) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if ( lastLocation != null ) {
                Log.i(TAG, "LasKnown location. " +
                        "Long: " + lastLocation.getLongitude() +
                        " | Lat: " + lastLocation.getLatitude());
                writeLastLocation();
                startLocationUpdates();
            } else {
                Log.w(TAG, "No location retrieved yet");
                startLocationUpdates();
            }
        }
        else Toast.makeText(this,"Turn on Location to use map", Toast.LENGTH_LONG).show();
    }


    private void startLocationUpdates(){
        Log.i(TAG, "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if ( checkPermission() )
            LocationServices.FusedLocationApi.requestLocationUpdates
                    (googleApiClient, locationRequest, this);
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged ["+location+"]");
        lastLocation = location;
        writeActualLocation(location);
    }

    // Write location coordinates on UI
    private void writeActualLocation(Location location) {
        markerLocation(new LatLng(location.getLatitude(), location.getLongitude()));
    }
    private Marker locationMarker;
    // Create a Location Marker
    private void markerLocation(LatLng latLng) {
        Log.i(TAG, "markerLocation("+latLng+")");
        Log.i(TAG, "markerLocation("+restLatLng+")");
        String title = "You";
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title);
        String title2 = restaurant.getName();
        // Define marker options
        MarkerOptions markerOptions2;
        markerOptions2 = new MarkerOptions()
                .position(restLatLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .title(title2);

        if ( map!=null ) {
            // Remove the anterior marker
            if ( locationMarker != null )
                locationMarker.remove();
            locationMarker = map.addMarker(markerOptions);
            // Remove last geoFenceMarker
            if (restrauantMarker != null)
                restrauantMarker.remove();

            restrauantMarker = map.addMarker(markerOptions2);
            LatLngBounds australiaBounds;

            if(locationMarker.getPosition().latitude < restrauantMarker.getPosition().latitude){
                if (locationMarker.getPosition().longitude < restrauantMarker.getPosition().longitude){
                    australiaBounds = new LatLngBounds(
                            locationMarker.getPosition(), // SW bounds
                            restrauantMarker.getPosition()  // NE bounds
                    );
                }
                else
                {
                    australiaBounds = new LatLngBounds(
                            new LatLng(locationMarker.getPosition().latitude, restrauantMarker.getPosition().longitude), // SW bounds
                            new LatLng(restrauantMarker.getPosition().latitude, locationMarker.getPosition().longitude)  // NE bounds
                    );
                }
            }
            else {
                if (locationMarker.getPosition().longitude < restrauantMarker.getPosition().longitude){
                    australiaBounds = new LatLngBounds(
                            new LatLng(restrauantMarker.getPosition().latitude, locationMarker.getPosition().longitude), // SW bounds
                            new LatLng(locationMarker.getPosition().latitude, restrauantMarker.getPosition().longitude)  // NE bounds
                    );
                }
                else
                {
                    australiaBounds = new LatLngBounds(
                            restrauantMarker.getPosition(), //NE bounds
                            locationMarker.getPosition() // SW bounds
                    );
                }
            }
            double latDist = locationMarker.getPosition().latitude - restrauantMarker.getPosition().latitude;
            if (latDist < 0.0)
                latDist = -1 * latDist;

            double lngDist = locationMarker.getPosition().longitude - restrauantMarker.getPosition().longitude;
            if (lngDist < 0.0)
                lngDist = -1 * latDist;
            double it;
            if(lngDist > latDist)
                it = lngDist;
            else
                it = latDist;
            float zoom = 6f;
            if (it < 0.015){
                zoom = 14f;
            }

            else if (it < 0.1){
                zoom = 12f;
            }

            else if (it < 1){
                zoom = 10f;
            }

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(australiaBounds.getCenter(),zoom);
            map.animateCamera(cameraUpdate);

            // Constrain the camera target to the Adelaide bounds.
            map.setLatLngBoundsForCameraTarget(australiaBounds);
        }
    }

    private void writeLastLocation() {
        writeActualLocation(lastLocation);
    }

    // Check for permission to access Location
    private boolean checkPermission() {
        Log.d(TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }

    // Asks for permission
    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(
                this,
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                REQ_PERMISSION
        );
    }


    // Verify user's response of the permission requested
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch ( requestCode ) {
            case REQ_PERMISSION: {
                if ( grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    // Permission granted
                    getLastKnownLocation();

                } else {
                    // Permission denied
                    permissionsDenied();
                }
                break;
            }
        }
    }

    // App cannot work without the permissions
    private void permissionsDenied() {
        Log.w(TAG, "permissionsDenied()");
    }

}