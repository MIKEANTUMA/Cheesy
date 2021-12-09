package com.example.cheesybackend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

//this activity will hold the users order history

public class Orders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        findViewById(R.id.SearchTab).setOnClickListener(this::switchTab);
        findViewById(R.id.AccountTab).setOnClickListener(this::switchTab);
        findViewById(R.id.OrderTab).setOnClickListener(this::switchTab);

        //hides the action bar
        try {
            this.getActionBar().hide();
        }
        catch (NullPointerException e) {}
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