package com.example.cheesybackend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class drinkPage extends AppCompatActivity implements View.OnClickListener {


    Restaurant restaurant;
    private RecyclerView recyclerView;
    Button entree;
    Button drink;
    Button appetizer;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appetizer_page);


        restaurant = getIntent().getParcelableExtra("Restaurant");
        recyclerView = findViewById(R.id.recyclerview_items);
        ArrayList<Drink> list = restaurant.getMenu().getDrinks();
        Log.d("DRINK", list.get(0).getName());
        DrinkOrgAdapter adapter = new DrinkOrgAdapter(this,list);
        recyclerView.setAdapter(adapter);
        entree = findViewById(R.id.btn_entree);
        appetizer = findViewById(R.id.btn_appetizer);
        drink = findViewById(R.id.btn_drink);
        entree.setOnClickListener(this);
        appetizer.setOnClickListener(this);
        drink.setOnClickListener(this);

        Log.d("MENUORGADAPTER", String.valueOf(adapter.getItemCount()));
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
            case R.id.btn_checkout:
                //Checkout(this);
        }
    }
}