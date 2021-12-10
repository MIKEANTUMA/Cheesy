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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountPasswordReset extends AppCompatActivity {

    private EditText email,oldPassword,newPassword;
    private Button resetBtn;
    FirebaseAuth mAuth;
    DatabaseReference currentUser;
    FirebaseDatabase mbase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_password_reset);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e) {}

        email = findViewById(R.id.email);
        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        resetBtn = findViewById(R.id.ResetPassword);
        resetBtn.setOnClickListener(this::Reset);
    }
    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private void Reset(View view) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String e = email.getText().toString().trim();
        String o = oldPassword.getText().toString().trim();
        String n = newPassword.getText().toString().trim();


        if (!isValidPassword(n)){
            newPassword.setError("Enter a valid password");
            newPassword.requestFocus();
            return;
        }
// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(e, o);

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(n).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        currentUser = FirebaseDatabase.getInstance().getReference().
                                                child("user").child(user.getUid());
                                        currentUser.child("password").setValue(n);
                                        Toast.makeText(AccountPasswordReset.this, "Password updated",
                                                Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(AccountPasswordReset.this, EditInfo.class));
                                        Log.d(TAG, "Password updated");
                                    } else {
                                        Toast.makeText(AccountPasswordReset.this, "failed to update password",
                                                Toast.LENGTH_LONG).show();
                                        Log.d(TAG, "Error password not updated");
                                    }
                                }
                            });
                        } else {
                            Log.d(TAG, "Error auth failed");
                        }
                    }
                });
    }
}