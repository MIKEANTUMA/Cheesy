package com.example.cheesybackend;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;


public class showRestaurants extends AppCompatActivity  {

    private RecyclerView recyclerView;
   // RestaurantOrgAdapter adapter; // Create Object of the Adapter class
   // RestaurantOrgAdapter adapter1;
    DatabaseReference mbase; // Create object of the
    // Firebase Realtime Database
    EditText search;
    ImageButton buttonSearch;

    String s ="";
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    CheckBox nearBy;
    double longg;
    double lat;

    boolean isChecked = false;
    GeoLocation center;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerAdapter adapter1;
    final double radiusInM = 50 * 1000;

    FirestoreRecyclerOptions<Restaurant> options1;
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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mbase = FirebaseDatabase.getInstance().getReference().child("restaurants");
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerview_tasks);
        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data

        Intent intent = (Intent) getIntent().getSerializableExtra("adapter");
        // Connecting Adapter class with the Recycler view*/


        //
        findViewById(R.id.SearchTab).setOnClickListener(this::switchTab);
        findViewById(R.id.AccountTab).setOnClickListener(this::switchTab);
        findViewById(R.id.OrderTab).setOnClickListener(this::switchTab);
        nearBy = (CheckBox) findViewById(R.id.cb_nearby);
        nearBy.setOnCheckedChangeListener(this::Check);
        getLastLocation();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference rest = db.collection("restaurants");
        Query q = rest.orderBy("name");
        FirestoreRecyclerOptions<Restaurant> options = new FirestoreRecyclerOptions.Builder<Restaurant>()
                .setQuery(q, Restaurant.class)
                .build();



        adapter = new FirestoreRecyclerAdapter<Restaurant, restaurantsViewholder>(options) {
            @NonNull
            @Override
            public restaurantsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_restraunt, parent, false);
                return new restaurantsViewholder(view);
            }


            @Override
            protected void onBindViewHolder(@NonNull restaurantsViewholder holder, int position, @NonNull Restaurant model) {


                holder.restaurantName.setText(model.getName());
                Log.d("menu", model.getMenu().getAppetizer().toString());
                // Add address from model class (here
                // "restaurant.class")to appropriate view in Card
                // view (here "person.xml")
                holder.address.setText(model.getLocation());

                // Add WebsiteLink from model class (here
                // "restaurant.class")to appropriate view in Card
                // view (here "recylerview_restaurant.xml")
                holder.WebsiteLink.setText(model.getWebsite());

                holder.PhoneNumber.setText(model.getPhoneNumber());
                holder.ratingBar.setRating(model.getRating());
                holder.restaurantName.setOnClickListener(v -> {
                    Log.d("PIZZZZAAAAA", "YOU CLICKED MY NAME");
                    Intent intent = new Intent(showRestaurants.this, RestaurantPage.class);
                    Restaurant restaurant = new Restaurant(model.getName(),model.getLocation(),model.getMenu(),model.getRating(),
                            model.getPhoneNumber(),model.getWebsite(),model.getDescription());
                    intent.putExtra("Restaurant", restaurant);
                    showRestaurants.this.startActivity(intent);

                });
                holder.PhoneNumber.setOnClickListener(v -> {
                    Log.d("PIZZZZAAAAA", "YOU CLICKED MY PHONE NUMBER");
                });
            }
        };

        recyclerView.setAdapter(adapter);








        buttonSearch.setOnClickListener(v -> {
            if (search.getText().toString().isEmpty()){
                Toast.makeText(this, "Please enter a valid search", Toast.LENGTH_SHORT).show();

            }
            if(isChecked){
                getLastLocation();
                final double radiusInM = 50 * 1000;
                center = new GeoLocation(40.720610,-73.539570);

                List<GeoQueryBounds> bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM);

                Log.d("CENTER", center.toString());
                ArrayList<String> geo = new ArrayList<>();
                for (GeoQueryBounds b : bounds) {

                    geo.add(b.startHash +b.endHash);
                    Log.d("GEOHASH CODE", b.startHash.toString() + b.endHash.toString());
                }


                Query q2 = db.collection("restaurants").whereEqualTo("geohash", "dr5xwsfp95");

                FirestoreRecyclerOptions<Restaurant> options1 = new FirestoreRecyclerOptions.Builder<Restaurant>()
                        .setQuery(q2, Restaurant.class)
                        .build();


                recyclerView.removeAllViews();
                adapter = new FirestoreRecyclerAdapter<Restaurant, restaurantsViewholder>(options1) {
                    @NonNull
                    @Override
                    public restaurantsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_restraunt, parent, false);
                        return new restaurantsViewholder(view);
                    }


                    @Override
                    protected void onBindViewHolder(@NonNull restaurantsViewholder holder, int position, @NonNull Restaurant model) {


                        holder.restaurantName.setText(model.getName());
                        Log.d("menu", model.getMenu().getAppetizer().toString());
                        // Add address from model class (here
                        // "restaurant.class")to appropriate view in Card
                        // view (here "person.xml")
                        holder.address.setText(model.getLocation());

                        // Add WebsiteLink from model class (here
                        // "restaurant.class")to appropriate view in Card
                        // view (here "recylerview_restaurant.xml")
                        holder.WebsiteLink.setText(model.getWebsite());

                        holder.PhoneNumber.setText(model.getPhoneNumber());
                        holder.ratingBar.setRating(model.getRating());
                        holder.restaurantName.setOnClickListener(v -> {
                            Log.d("PIZZZZAAAAA", "YOU CLICKED MY NAME");
                            Intent intent = new Intent(showRestaurants.this, RestaurantPage.class);
                            Restaurant restaurant = new Restaurant(model.getName(),model.getLocation(),model.getMenu(),model.getRating(),
                                    model.getPhoneNumber(),model.getWebsite(),model.getDescription());
                            intent.putExtra("Restaurant", restaurant);
                            showRestaurants.this.startActivity(intent);

                        });
                        holder.PhoneNumber.setOnClickListener(v -> {
                            Log.d("PIZZZZAAAAA", "YOU CLICKED MY PHONE NUMBER");
                        });
                    }
                };

                recyclerView = findViewById(R.id.recyclerview_tasks);
                recyclerView.setAdapter(adapter);



            }
        });
    }

    private void Check(CompoundButton compoundButton, boolean b) {
        if(nearBy.isChecked()){
            isChecked = true;
            Log.d("check","is checked");
            getLastLocation();
        }
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
        getLastLocation();
        try {
            //Log.d("RESTARUANT", adapter.getItem(0).getName());
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


    }



    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            longg = location.getLongitude();
                            lat = location.getLatitude();

                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            longg = mLastLocation.getLongitude();
            lat = mLastLocation.getLatitude();

            //latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
            //longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }












}
