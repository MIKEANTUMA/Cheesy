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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditInfo extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference currentUser;
    EditText Fname, Lname, Email, DOB;
    Button changeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        Fname = findViewById(R.id.changeFName);
        Lname = findViewById(R.id.changeLName);
        Email = findViewById(R.id.changeEmail);
        DOB = findViewById(R.id.changeDOB);
        changeBtn = findViewById(R.id.infoChangeButton);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        currentUser = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
        currentUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User g = dataSnapshot.getValue(User.class);
                Fname.setText(g.getFname());
                Lname.setText(g.getLname());
                Email.setText(g.getEmail());
                DOB.setText(g.getDob());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase Error", "Couldn't grab user");
            }

        });
        findViewById(R.id.SearchTab).setOnClickListener(this::switchTab);
        findViewById(R.id.AccountTab).setOnClickListener(this::switchTab);
        findViewById(R.id.OrderTab).setOnClickListener(this::switchTab);
        changeBtn.setOnClickListener(this::makeChanges);
    }

    private void makeChanges(View view) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        currentUser = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
        currentUser.child("fname").setValue(Fname.getText().toString());
        currentUser.child("lname").setValue(Lname.getText().toString());
        currentUser.child("email").setValue(Email.getText().toString());
        currentUser.child("dob").setValue(DOB.getText().toString());
        user.updateEmail(Email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User email address updated.");
                }
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
        }
    }
}