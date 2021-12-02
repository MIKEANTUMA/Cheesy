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

public class entreePage extends AppCompatActivity implements View.OnClickListener {
    Restaurant restaurant;
    private RecyclerView recyclerView;
    Button entree;
    Button drink;
    Button appetizer;
    Button btnreturn;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entree_page);

        name = findViewById(R.id.tv_name);

        restaurant = getIntent().getParcelableExtra("Restaurant");
        name.setText(restaurant.getName());
        recyclerView = findViewById(R.id.recyclerview_items);
        ArrayList<Entree> list = restaurant.getMenu().getEntree();
        Log.d("ENTREE", list.get(0).getName());
        EntreeOrgAdapter adapter = new EntreeOrgAdapter(this,list);
        recyclerView.setAdapter(adapter);
        entree = findViewById(R.id.btn_entree);
        appetizer = findViewById(R.id.btn_appetizer);
        drink = findViewById(R.id.btn_drink);
        entree.setOnClickListener(this);
        appetizer.setOnClickListener(this);
        drink.setOnClickListener(this);
        btnreturn=findViewById(R.id.btn_return);
        btnreturn.setOnClickListener(this);

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
                break;
            case R.id.btn_return:
                Intent intent3 = new Intent(this, showRestaurants.class);
                intent3.putExtra("Restaurant", restaurant);
                startActivity(intent3);
                break;

        }
    }

}