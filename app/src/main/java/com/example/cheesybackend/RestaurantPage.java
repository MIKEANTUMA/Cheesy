package com.example.cheesybackend;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RestaurantPage extends AppCompatActivity implements View.OnClickListener {

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
        restaurant = getIntent().getParcelableExtra("Restaurant");
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


}