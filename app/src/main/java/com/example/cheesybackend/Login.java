package com.example.cheesybackend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
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

import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextemail, editTextpassword;
    private TextView pass;
    Button login;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    ImageView fingerPrint;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hide action bar
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e) {}

        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        editTextemail = findViewById(R.id.email);
        editTextpassword = findViewById(R.id.password);
        pass = findViewById(R.id.viewpassword);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        fingerPrint = findViewById(R.id.fingerPrintImg);

        //checks to see if shared preference has user login info
        //if info is there scanner btn appears and finger print login
        //is available
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        boolean hasLogin = sharedPreferences.getBoolean("hasLogin", false);
        if (hasLogin){
            fingerPrint.setVisibility(View.VISIBLE);
        }
        finger();
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
        //if email is empty
        if (email.isEmpty()) {
            editTextemail.setError("Email is required");
            editTextemail.requestFocus();
            return;
        }

        //if email is not valid. checks using regex
        if(!isValidEmail(editTextemail.getText().toString())){
            editTextemail.setError("Enter a valid email");
            editTextemail.requestFocus();
            return;
        }


        //if password Edittext is not visible checks email
        //input against firebase
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
            if (password.isEmpty()) {
                editTextpassword.setError("password is required");
                editTextpassword.requestFocus();
            }

            if(!isValidPassword(editTextpassword.getText().toString())){
                editTextpassword.setError("Enter a valid password");
                editTextpassword.requestFocus();
                return;
            }

            performAuth(email, password);
//            String message = "";
//            mAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//
//
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(Login.this, showRestaurants.class));
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Log.w(message, "sign in fail", task.getException());
//                                Toast.makeText(Login.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
        }
    }

    private void performAuth(String email, String password) {
        String message = "";
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //creates shared preference for login info to be used in finger print biometric
                            SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.putBoolean("hasLogin",true);
                            editor.apply();


                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, showRestaurants.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(message, "sign in fail", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
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

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    @Override
    public void onBackPressed() {
        //Does nothing so you can't go back to splash screen
        //without restarting app
    }

    public void finger(){

        // creating a variable for our BiometricManager
        // and checks if our user can use biometric sensor or not
        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {

            // this means we can use biometric sensor
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("SUCCESS", "finger: FUNCTION SUCCESS");
                break;

            // this means that the device doesn't have fingerprint sensor
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(Login.this, "fingerprint scanner not available", Toast.LENGTH_LONG).show();
                fingerPrint.setVisibility(View.GONE);
                break;

            // this means that biometric sensor is not available
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(Login.this, "finger print scanner not available", Toast.LENGTH_LONG).show();
                fingerPrint.setVisibility(View.GONE);
                break;

            // this means that the device doesn't contain your fingerprint
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(Login.this, "Register a finger print for biometric login", Toast.LENGTH_LONG).show();
                fingerPrint.setVisibility(View.GONE);
                break;
        }

        Executor executor = ContextCompat.getMainExecutor(this);

        // result of authentication
        final BiometricPrompt biometricPrompt = new BiometricPrompt(Login.this, executor,
                new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                String email = sharedPreferences.getString("email", "");
                String password = sharedPreferences.getString("password", "");
                performAuth(email, password);
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });


        // BIOMETRIC DIALOG
        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("GFG")
                .setDescription("Use your fingerprint to login ").setNegativeButtonText("Cancel").build();
        fingerPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);

            }
        });
    }

}