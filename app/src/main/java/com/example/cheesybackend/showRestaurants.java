package com.example.cheesybackend;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class showRestaurants extends AppCompatActivity {

    private RecyclerView recyclerView;
    RestaurantOrgAdapter adapter; // Create Object of the Adapter class
    RestaurantOrgAdapter adapter1;
    DatabaseReference mbase; // Create object of the
    // Firebase Realtime Database
    EditText search;
    ImageButton buttonSearch;
    FirebaseRecyclerOptions<Restaurant> options;
    String s ="";
    DatabaseReference rest;
    GoogleApiClient googleApiClient;
    Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_restaurants);
        search = findViewById(R.id.et_searchbar);
        buttonSearch = findViewById(R.id.img_btn_search);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e) {}

        createGoogleApi();

        mbase = FirebaseDatabase.getInstance().getReference().child("restaurants");
        recyclerView = findViewById(R.id.recyclerview_tasks);
        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        options = new FirebaseRecyclerOptions.Builder<Restaurant>().setQuery(mbase, Restaurant.class).build();
        adapter = new RestaurantOrgAdapter(this,options);
        Intent intent = (Intent) getIntent().getSerializableExtra("adapter");
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);
        rest = FirebaseDatabase.getInstance().getReference().child("restaurants").child("restaurant01");


        //
        findViewById(R.id.SearchTab).setOnClickListener(this::switchTab);
        findViewById(R.id.AccountTab).setOnClickListener(this::switchTab);
        findViewById(R.id.OrderTab).setOnClickListener(this::switchTab);
        buttonSearch.setOnClickListener(v -> {
            if (search.getText().toString().isEmpty()){
                options = new FirebaseRecyclerOptions.Builder<Restaurant>().setQuery(mbase, Restaurant.class).build();
                adapter.updateOptions(options);

            }
            else {
                String s = search.getText().toString();
                Log.d("S", s);
                Query query = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("restaurants")
                        .orderByChild("latitude")
                        .startAt(s);

                options = new FirebaseRecyclerOptions.Builder<Restaurant>()
                        .setQuery(query, Restaurant.class)
                        .build();
                adapter.updateOptions(options);
            }
        });
    }

    private void createGoogleApi() {

        if ( googleApiClient == null ) {
            googleApiClient = new GoogleApiClient.Builder( this )
                    .addApi( LocationServices.API )
                    .build();
        }
    }
    @SuppressLint("MissingPermission")
    private void findLocation() {
        if (checkPermission())
                myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

    }


    private void switchTab(View view) {
        switch (view.getId()){
            case R.id.SearchTab:
                startActivity(new Intent(getApplicationContext(), showRestaurants.class));
                break;
            case R.id.OrderTab:
                startActivity(new Intent(getApplicationContext(), Orders.class));
                break;
            case R.id.AccountTab:
                startActivity(new Intent(getApplicationContext(), Account.class));
                break;
        }
    }
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
        googleApiClient.connect();

        try {
            Log.d("RESTARUANT", adapter.getItem(0).getName());
        }catch(Exception e){
            Log.d("KEY", e.getMessage());
        }

    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
        googleApiClient.disconnect();


    }
    // Check for permission to access Location
    private boolean checkPermission() {
        Log.d("TAG", "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }



}
