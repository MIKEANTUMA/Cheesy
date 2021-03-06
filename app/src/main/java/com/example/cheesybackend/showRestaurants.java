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
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

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
    Query q2;
    Query q3;
    boolean isChecked = false;
    GeoLocation center;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerOptions<Restaurant> options;
    final double radiusInM = 50 * 1000;
    public Cart single_instance = null;
    ArrayList<String> names;

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

        Cart.setSingle_instance(single_instance);

        //
        findViewById(R.id.SearchTab).setOnClickListener(this::switchTab);
        findViewById(R.id.AccountTab).setOnClickListener(this::switchTab);
        findViewById(R.id.OrderTab).setOnClickListener(this::switchTab);
        nearBy = (CheckBox) findViewById(R.id.cb_nearby);
        nearBy.setOnCheckedChangeListener(this::Check);
        getLastLocation();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference rest = db.collection("restaurants");

        options = new FirestoreRecyclerOptions.Builder<Restaurant>().setQuery(rest, Restaurant.class).build();

        adapter = new RestaurantOrgAdapter(this,options);

        recyclerView.setAdapter(adapter);


        buttonSearch.setOnClickListener(v ->{
            search();
        });
    }
    public void search(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference rest = db.collection("restaurants");
        if (search.getText().toString().isEmpty()){
            options = new FirestoreRecyclerOptions.Builder<Restaurant>().setQuery(rest, Restaurant.class).build();
            adapter.notifyDataSetChanged();
            adapter.updateOptions(options);
            Toast.makeText(this, "Please enter a valid search", Toast.LENGTH_SHORT).show();
            if (isChecked){
                q3 = rest.whereIn("name",names);
                options = new FirestoreRecyclerOptions.Builder<Restaurant>()
                        .setQuery(q3, Restaurant.class)
                        .build();
                adapter.notifyDataSetChanged();
                adapter.updateOptions(options);
            }

        }
        else {
            if (isChecked && !names.isEmpty()) {
                q3 = rest.whereEqualTo("name", search.getText().toString()).whereIn("name", names);
                Query q4 = rest.whereEqualTo("location", search.getText().toString()).whereIn("name", names);
                Query q5 = rest.whereEqualTo("phoneNumber", search.getText().toString()).whereIn("name", names);
                options = new FirestoreRecyclerOptions.Builder<Restaurant>()
                        .setQuery(q3, Restaurant.class)
                        .build();
                final List<Task<QuerySnapshot>> tasks1 = new ArrayList<>();
                tasks1.add(q3.get());
                tasks1.add(q4.get());
                tasks1.add(q5.get());
                Tasks.whenAllComplete(tasks1).addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Task<?>>> task) {
                        boolean tr = false;
                        for (Task<QuerySnapshot> task2 : tasks1){
                            if (!task2.getResult().isEmpty()){
                                options = new FirestoreRecyclerOptions.Builder<Restaurant>()
                                        .setQuery(task2.getResult().getQuery(), Restaurant.class)
                                        .build();
                                adapter.notifyDataSetChanged();
                                adapter.updateOptions(options);
                                tr = true;
                            }
                            if (!tr) {
                                options = new FirestoreRecyclerOptions.Builder<Restaurant>()
                                        .setQuery(q3, Restaurant.class)
                                        .build();
                                adapter.notifyDataSetChanged();
                                adapter.updateOptions(options);
                            }
                        }
                    }
                });
            }
            else {
                Query q = rest.whereEqualTo("name", search.getText().toString());
                options = new FirestoreRecyclerOptions.Builder<Restaurant>()
                        .setQuery(q, Restaurant.class)
                        .build();
                Query q4 = rest.whereEqualTo("location", search.getText().toString());
                Query q5 = rest.whereEqualTo("phoneNumber", search.getText().toString());
                final List<Task<QuerySnapshot>> tasks1 = new ArrayList<>();
                tasks1.add(q.get());
                tasks1.add(q4.get());
                tasks1.add(q5.get());
                Tasks.whenAllComplete(tasks1).addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Task<?>>> task) {
                        boolean tr = false;
                        for (Task<QuerySnapshot> task2 : tasks1){
                            if (!task2.getResult().isEmpty()){
                                options = new FirestoreRecyclerOptions.Builder<Restaurant>()
                                        .setQuery(task2.getResult().getQuery(), Restaurant.class)
                                        .build();
                                adapter.notifyDataSetChanged();
                                adapter.updateOptions(options);
                                tr = true;
                            }
                        }
                        if(!tr) {
                            options = new FirestoreRecyclerOptions.Builder<Restaurant>()
                                    .setQuery(q, Restaurant.class)
                                    .build();
                            adapter.notifyDataSetChanged();
                            adapter.updateOptions(options);
                        }
                    }
                });
            }
        }
    }

    private void Check(CompoundButton compoundButton, boolean blue) {
        if(nearBy.isChecked()) {
            isChecked = true;
            Log.d("check", "is checked");
            getLastLocation();
            if (checkPermissions()) {
                names = new ArrayList<>();
                getLastLocation();
                final double radiusInM = 50 * 1000;
                center = new GeoLocation(lat, longg);

                List<GeoQueryBounds> bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM);

                Log.d("CENTER", center.toString());

                final List<Task<QuerySnapshot>> tasks = new ArrayList<>();


                for (GeoQueryBounds b : bounds) {
                    q2 = db.collection("restaurants")
                            .orderBy("geohash")
                            .startAt(b.startHash)
                            .endAt(b.endHash);
                    tasks.add(q2.get());

                }
                // Collect all the query results together into a single list
                Tasks.whenAllComplete(tasks)
                        .addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                            @Override
                            public void onComplete(@NonNull Task<List<Task<?>>> t) {
                                List<DocumentSnapshot> matchingDocs = new ArrayList<>();

                                for (Task<QuerySnapshot> task : tasks) {
                                    QuerySnapshot snap = task.getResult();
                                    for (DocumentSnapshot doc : snap.getDocuments()) {


                                        GeoPoint geoPoint = doc.get("geoPoint", GeoPoint.class);

                                        // We have to filter out a few false positives due to GeoHash
                                        // accuracy, but most will match
                                        GeoLocation docLocation = new GeoLocation(geoPoint.getLatitude(), geoPoint.getLongitude());
                                        double distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center);
                                        if (distanceInM <= radiusInM) {
                                            matchingDocs.add(doc);
                                            Log.d("matchig docs", doc.getId());
                                            names.add(doc.getId());

                                        }
                                    }
                                }
                                for (int i = 0; i < names.size(); i++)
                                    Log.d("NAMES", names.get(i));

                                // matchingDocs contains the results
                                // ...
                                if(!names.isEmpty()){

                                    CollectionReference rest = db.collection("restaurants");
                                    q3 = rest.whereIn("name", names);


                                    options = new FirestoreRecyclerOptions.Builder<Restaurant>()
                                            .setQuery(q3, Restaurant.class)
                                            .build();
                                    // Log.d("Name", options.getSnapshots().get(0).getName());
                                    adapter.notifyDataSetChanged();
                                    try {
                                        adapter.updateOptions(options);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    Log.d("GEOFENCE", "no restaurants near you");
                                    Toast.makeText(showRestaurants.this, "no restaurants near you", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(showRestaurants.this, "Turn location on in Account Activity", Toast.LENGTH_LONG);
            }
        }
        else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference rest = db.collection("restaurants");
            options = new FirestoreRecyclerOptions.Builder<Restaurant>().setQuery(rest, Restaurant.class).build();
            adapter.notifyDataSetChanged();
            adapter.updateOptions(options);
            isChecked = false;
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

    @Override
    public void onBackPressed() {
        //Does nothing so you can't go back to splash screen
        //without restarting app
    }

    @Override
    public void onResume() {
        super.onResume();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference rest = db.collection("restaurants");
//        options = new FirestoreRecyclerOptions.Builder<Restaurant>().setQuery(rest, Restaurant.class).build();
        adapter.notifyDataSetChanged();
        adapter.updateOptions(options);


    }






}
