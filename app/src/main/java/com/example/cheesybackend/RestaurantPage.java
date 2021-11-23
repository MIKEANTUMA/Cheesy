package com.example.cheesybackend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;

public class RestaurantPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);


       // getIntent().getSerializableExtra("Restaurant").

      //  Log.d("SERIALIZABLE", restaurant.toString());
       //Log.d("SERIALIZABLEeeeeeeeee", restaurant.getClass().toString());


        //Restaurant restaurant1 = new Restaurant(restaurant.getClass());
    }
}