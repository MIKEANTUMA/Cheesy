package com.example.cheesybackend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class ViewRestaurant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_restaurant);
         getIntent().getSerializableExtra("Restaurant");
        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("Restaurant");
        Log.d("SERIALIZABLEeeeeeeeee", restaurant.getClass().toString());
        Menu menu = restaurant.getMenu();
        List<Menu.Appetizer> app = menu.getAppetizer();
//        Log.d("Appitizer", app.get(0).getName());
        Toast.makeText(ViewRestaurant.this, "This is " + menu.getAppetizer().get(0).getName(),
                Toast.LENGTH_LONG).show();

    }
}