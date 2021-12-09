package com.example.cheesybackend;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class Account extends AppCompatActivity  {
    private FirebaseAuth mAuth;
    private final String TAG = "MainActivity";
    private final int REQUEST_LOCATION_PERMISSIONS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e) {}

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.SearchTab).setOnClickListener(this::switchTab);
        findViewById(R.id.AccountTab).setOnClickListener(this::switchTab);
        findViewById(R.id.OrderTab).setOnClickListener(this::switchTab);
        findViewById(R.id.AddressBtn).setOnClickListener(this::switchTab);
        findViewById(R.id.personalInfoBtn).setOnClickListener(this::switchTab);
        findViewById(R.id.LocateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasLocationPermission())
                    findLocation();
            }
        });
        findViewById(R.id.AboutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(Account.this);
                alert.setMessage("This app is our semester project for BCS 421. "
                        + "It's a pizza ordering app, something similar to doordash or Grubhub, "
                        + "but more focused on pizza places. You can create a user account, "
                        + "search restaurants, click on them and view their menu, make an order, "
                        + "and the order receipts");
                alert.setPositiveButton("Continue", (v, a) -> {

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
        switch (view.getId()) {
            case R.id.SearchTab:
                startActivity(new Intent(getApplicationContext(), showRestaurants.class));
                break;
            case R.id.OrderTab:
                startActivity(new Intent(getApplicationContext(), Orders.class));
                break;
            case R.id.AccountTab:
                startActivity(new Intent(getApplicationContext(), Account.class));
                break;
            case R.id.personalInfoBtn:
                startActivity(new Intent(getApplicationContext(), EditInfo.class));
                break;
            case R.id.AddressBtn:
                startActivity(new Intent(getApplicationContext(), EditAddress.class));
                break;
        }
    }
    @SuppressLint("MissingPermission")
    private void findLocation() {
        FusedLocationProviderClient client =
                LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation()
                .addOnSuccessListener(this, location -> Log.d(TAG, "location = " + location));
    }
    private boolean hasLocationPermission() {
            // Request fine location permission if not already granted
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)  == PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                        REQUEST_LOCATION_PERMISSIONS);
                return false;
            }
            return true;
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Find the location when permission is granted
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                findLocation();
            }
        }
    }

}