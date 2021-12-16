package com.example.cheesybackend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//this activity will hold the users order history

public class Orders extends AppCompatActivity {
    private RecyclerView recyclerView;
    OrdersAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the
    // Firebase Realtime Database
    FirebaseRecyclerOptions<UserOrder> options;
    private FirebaseAuth mAuth;
    private DatabaseReference currentUserOrders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        //hides action bar
        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        currentUserOrders = FirebaseDatabase.getInstance().getReference().
                child("user").child(user.getUid()).child("Orders");
        recyclerView = findViewById(R.id.recyclerview_tasks);
        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        options = new FirebaseRecyclerOptions.Builder<UserOrder>().setQuery(currentUserOrders, UserOrder.class).build();
        adapter = new OrdersAdapter(this,options);
//        Intent intent = (Intent) getIntent().getSerializableExtra("adapter");
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);

        findViewById(R.id.SearchTab).setOnClickListener(this::switchTab);
        findViewById(R.id.AccountTab).setOnClickListener(this::switchTab);
        findViewById(R.id.OrderTab).setOnClickListener(this::switchTab);

    }
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();


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
}