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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditInfo extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference currentUser;
    EditText Fname, Lname, Email, DOB;
    Button changeBtn, resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e) {}

        Fname = findViewById(R.id.changeFName);
        Lname = findViewById(R.id.changeLName);
        Email = findViewById(R.id.changeEmail);
        DOB = findViewById(R.id.changeDOB);
        changeBtn = findViewById(R.id.infoChangeButton);
        resetBtn = findViewById(R.id.passwordResetBtn);
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
        resetBtn.setOnClickListener(this::PasswordReset);

    }


    private void PasswordReset(View view) {
        startActivity(new Intent(getApplicationContext(), AccountPasswordReset.class));
    }

    //TODO: when change btn press return to account class
    private void makeChanges(View view) {
       if (!isValidEmail(Email.getText().toString())){
           Email.setError("Enter a valid email");
           Email.requestFocus();
           return;
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        currentUser = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
        currentUser.child("fname").setValue(Fname.getText().toString().trim());
        currentUser.child("lname").setValue(Lname.getText().toString().trim());
        currentUser.child("dob").setValue(DOB.getText().toString().trim());
        user.updateEmail(Email.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    currentUser.child("email").setValue(Email.getText().toString().trim());
                    Log.d(TAG, "User email address updated.");
                    Toast.makeText(EditInfo.this, "User Info updated",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(EditInfo.this,"Email not updated",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public boolean isValidEmail(final String email) {

        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^([a-z0-9]+(?:[._-][a-z0-9]+)*)@([a-z0-9]+(?:[.-][a-z0-9]+)*\\.[a-z]{2,})$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();

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