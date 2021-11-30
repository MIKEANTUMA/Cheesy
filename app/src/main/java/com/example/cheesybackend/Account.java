package com.example.cheesybackend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Account extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.SearchTab).setOnClickListener(this::switchTab);
        findViewById(R.id.AccountTab).setOnClickListener(this::switchTab);
        findViewById(R.id.OrderTab).setOnClickListener(this::switchTab);
        findViewById(R.id.PaymentBtn).setOnClickListener(this::switchTab);
        findViewById(R.id.AddressBtn).setOnClickListener(this::switchTab);
        findViewById(R.id.personalInfoBtn).setOnClickListener(this::switchTab);
        findViewById(R.id.AboutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert= new AlertDialog.Builder(Account.this);
                alert.setMessage("This app is our semester project for BCS 421. "
                + "It's a pizza ordering app, something similar to doordash or Grubhub, "
                + "but more focused on pizza places. You can create a user account, "
                + "search restaurants, click on them and view their menu, make an order, "
                                + "and the order receipts");
                alert.setPositiveButton("Continue", (v,a)->{

                });
                alert.create().show();

            }
        });
        findViewById(R.id.SignOutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        });
    }

    private void switchTab(View view) {
        switch (view.getId()){
            case R.id.SearchTab:
                startActivity(new Intent(getApplicationContext(), SearchRestaurant.class));
                break;
            case R.id.OrderTab:
                startActivity(new Intent(getApplicationContext(), Orders.class));
                break;
            case R.id.AccountTab:
                startActivity(new Intent(getApplicationContext(), Account.class));
                break;
            case R.id.PaymentBtn:
                startActivity(new Intent(getApplicationContext(), Payment.class));
                break;
            case R.id.personalInfoBtn:
                startActivity(new Intent(getApplicationContext(), EditInfo.class));
                break;
            case R.id.AddressBtn:
                startActivity(new Intent(getApplicationContext(), EditAddress.class));
                break;
        }
    }

}