package com.example.cheesybackend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class entreePage extends AppCompatActivity {
    Restaurant restaurant;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entree_page);


        restaurant = getIntent().getParcelableExtra("Restaurant");
        recyclerView = findViewById(R.id.recyclerview_items);
        ArrayList<Entree> list = restaurant.getMenu().getEntree();
        Log.d("ENTREE", list.get(0).getName());
        MenuOrgAdapter adapter = new MenuOrgAdapter(this,list);
        recyclerView.setAdapter(adapter);

        Log.d("MENUORGADAPTER", String.valueOf(adapter.getItemCount()));
    }



}