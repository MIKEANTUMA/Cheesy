package com.example.cheesybackend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

public class appetizerPage extends AppCompatActivity implements View.OnClickListener {

    Restaurant restaurant;
    private RecyclerView recyclerView;
    Button entree;
    Button drink;
    Button appetizer;
    TextView name;
    ImageButton checkout;
    ImageButton btnreturn;
    ImageButton cartBtn;
    private Cart cart = Cart.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appetizer_page);

        //hides action bar
        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        name = findViewById(R.id.tv_name);

        restaurant = getIntent().getParcelableExtra("Restaurant");
        name.setText(restaurant.getName());
        recyclerView = findViewById(R.id.recyclerview_items);
        ArrayList<Appetizer> list = restaurant.getMenu().getAppetizer();
        AppetizerOrgAdapter adapter = new AppetizerOrgAdapter(this,list);
        recyclerView.setAdapter(adapter);
        entree = findViewById(R.id.btn_entree);
        appetizer = findViewById(R.id.btn_appetizer);
        drink = findViewById(R.id.btn_drink);
        entree.setOnClickListener(this);
        appetizer.setOnClickListener(this);
        drink.setOnClickListener(this);

        btnreturn=findViewById(R.id.returnbtn);
        btnreturn.setOnClickListener(this::switchTab);
        cartBtn=findViewById(R.id.cartBtn);
        cartBtn.setOnClickListener(this::switchTab);
        checkout = findViewById(R.id.checkoutBtn);
        checkout.setOnClickListener(this::switchTab);
    }

    private void switchTab(View view) {
        switch (view.getId()){
            case R.id.returnbtn:
                startActivity(new Intent(getApplicationContext(), showRestaurants.class));
                break;
            case R.id.cartBtn:
//                TODO: set new cart activity
                startActivity(new Intent(getApplicationContext(), Orders.class));
                break;
            case R.id.checkoutBtn:
                try {
                    Log.d("entreepage CHECKOUT", "checkout");
                    cart.checkOut(this,restaurant);
                } catch (JSONException e) {
                    Log.d("entreepage CHECKOUT", "sad face :(");
                    e.printStackTrace();
                }                break;
        }
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
                Toast.makeText(this, "drink", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.btn_checkout:
//                try {
//                    Log.d("appetizer CHECKOUT", "checkout");
//                    cart.checkOut(this,restaurant);
//                } catch (JSONException e) {
//                    Log.d("appetizer CHECKOUT", "sad face :(");
//                    e.printStackTrace();
//                }
//                break;
//            case R.id.btn_return:
//                Intent intent3 = new Intent(this, showRestaurants.class);
//                intent3.putExtra("Restaurant", restaurant);
//                startActivity(intent3);
//                break;
        }
    }
}