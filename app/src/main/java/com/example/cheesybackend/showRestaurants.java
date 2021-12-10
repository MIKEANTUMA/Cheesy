package com.example.cheesybackend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


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


        mbase = FirebaseDatabase.getInstance().getReference().child("restaurants");
        recyclerView = findViewById(R.id.recyclerview_tasks);
        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        options = new FirebaseRecyclerOptions.Builder<Restaurant>().setQuery(mbase, Restaurant.class).build();
        adapter = new RestaurantOrgAdapter(this,options);
        Intent intent = (Intent) getIntent().getSerializableExtra("adapter");
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);

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
                        .orderByChild("name")
                        .startAt(s);

                options = new FirebaseRecyclerOptions.Builder<Restaurant>()
                        .setQuery(query, Restaurant.class)
                        .build();
                adapter.updateOptions(options);
            }
        });
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





}
