package com.example.cheesybackend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextemail, editTextpassword;
    private TextView pass;
    Button login;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        editTextemail = findViewById(R.id.email);
        editTextpassword = findViewById(R.id.password);
        pass = findViewById(R.id.viewpassword);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                userLogin();
                break;
        }

    }

    private void userLogin() {

        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        if(editTextpassword.getVisibility()==View.GONE){
            mAuth.fetchSignInMethodsForEmail(email)
                    .addOnCompleteListener(this,new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean check = !task.getResult().getSignInMethods().isEmpty();
                            if(!check)
                                //if email can't be found in database user is sent to register activity
                                startActivity(new Intent(Login.this,Register.class));
                            else {
                                //password field appear
                                editTextpassword.setVisibility(View.VISIBLE);
                                pass.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
        else {

            if (email.isEmpty()) {
                editTextemail.setError("Email is required");
                editTextemail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                editTextpassword.setError("password is required");
                editTextpassword.requestFocus();
            }
            String message = "";
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, SearchRestraunt.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(message, "sign in fail", task.getException());
                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}