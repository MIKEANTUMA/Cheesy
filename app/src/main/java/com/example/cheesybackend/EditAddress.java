package com.example.cheesybackend;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditAddress extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference currentUser;
    EditText Address, Zipcode;
    Button changeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e) {}
        Address = findViewById(R.id.changeAddress);
        Zipcode = findViewById(R.id.changeZipcode);
        changeBtn = findViewById(R.id.infoChangeButton);
        findViewById(R.id.SearchTab).setOnClickListener(this::switchTab);
        findViewById(R.id.AccountTab).setOnClickListener(this::switchTab);
        findViewById(R.id.OrderTab).setOnClickListener(this::switchTab);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        currentUser = FirebaseDatabase.getInstance().getReference().
                child("user").child(user.getUid());
        currentUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User g = dataSnapshot.getValue(User.class);
                Address.setText(g.getAddress());
                Zipcode.setText(g.getZipcode());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase Error", "Couldn't grab user");
            }

        });
        changeBtn.setOnClickListener(this::makeChanges);
    }

    //TODO: when change btn pressed return to account class
    private void makeChanges(View view) {

        FirebaseUser user = mAuth.getCurrentUser();
        currentUser = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
        currentUser.child("address").setValue(Address.getText().toString().trim());
        currentUser.child("zipcode").setValue(Zipcode.getText().toString().trim());
        Toast.makeText(EditAddress.this, "Address updated",
                Toast.LENGTH_LONG).show();
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