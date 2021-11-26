package com.example.cheesybackend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SearchRestaurant extends AppCompatActivity {

    private RecyclerView recyclerView;
    RestaurantOrgAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the
    // Firebase Realtime Database

    private ImageButton m1, m2, m3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_restaurant);
        findViewById(R.id.SearchTab).setOnClickListener(this::switchTab);
        findViewById(R.id.AccountTab).setOnClickListener(this::switchTab);
        findViewById(R.id.OrderTab).setOnClickListener(this::switchTab);



        //Michael's code for the firebase recycler view
        mbase = FirebaseDatabase.getInstance().getReference().child("restaurants");
        recyclerView = findViewById(R.id.recyclerview_tasks);
        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Restaurant> options = new FirebaseRecyclerOptions.Builder<Restaurant>().setQuery(mbase, Restaurant.class).build();
        adapter = new RestaurantOrgAdapter(this,options);
        Intent intent = (Intent) getIntent().getSerializableExtra("adapter");
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);


    }

    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
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
    }


    private void switchTab(View view) {
        switch (view.getId()){
            case R.id.SearchTab:
                startActivity(new Intent(getApplicationContext(), SearchRestaurant.class));
                break;
            case R.id.OrderTab:
                startActivity(new Intent(getApplicationContext(), Orders.class));
                break;
            case R.id.AccountTab:
                startActivity(new Intent(getApplicationContext(), Account.class));
                break;

        }
    }





}