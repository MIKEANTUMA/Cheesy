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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextemail, editTextpassword;
    private TextView pass;
    Button login;
    private DatabaseReference mDatabase;
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
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //addRestaurant();



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
                                Intent intent = new Intent(Login.this, MainActivity2.class);
                                startActivity(new Intent(Login.this, MainActivity2.class));
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


//    public void addRestaurant(){
//        String name = "mike's pizza";
//        String address = "123 street new york";
//        float rate =  3.8f;
//        String website = "http://google.com";
//
//        List<Menu.Entree> entrees = new ArrayList<Menu.Entree>();
//        entrees.add(new Menu.Entree("pie", "cheese pizza",13.99));
//        entrees.add(new Menu.Entree("Chicken Roll", "Bread roll stuffed with chicken and red sauce",10.99));
//
//
//        List<Menu.Appetizer> appetizers = new ArrayList<Menu.Appetizer>();
//        appetizers.add(new Menu.Appetizer("chicken fingers","5 fried chicken fingers", 8.99));
//        appetizers.add(new Menu.Appetizer("buffalo wings","10 buffalo wings", 8.99));
//
//        List<Menu.Drink> drinks = new ArrayList<Menu.Drink>();
//        drinks.add(new Menu.Drink("Coke",2.99));
//        drinks.add(new Menu.Drink("water", 1.50));
//
//
//        Menu menu = new Menu(entrees,drinks,appetizers);
//
//        String phoneNumber = "516-728-1827";
//
//        Restaurant pizzaPalce = new Restaurant(name,address,menu,rate,phoneNumber,website);
//        for (int i =4; i<15; i++){
//            mDatabase.child("restaurants").child("restaurant"+i).setValue(pizzaPalce);
//        }
//
//    }
}