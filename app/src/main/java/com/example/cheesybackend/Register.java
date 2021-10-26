package com.example.cheesybackend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText editFirstName,editLastName,editEmail,editDoB,editAddress,editZipCode,editPassword;
    private Button btn;
    private FirebaseAuth mAuth;
    private ProgressBar progress;
    private String email;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editFirstName = findViewById(R.id.editTextFirstName);
        editLastName = findViewById(R.id.editTextLastName);
        editEmail = findViewById(R.id.editTextEmail);
        editDoB = findViewById(R.id.editTextDoB);
        editAddress = findViewById(R.id.editTextAddress);
        editZipCode = findViewById(R.id.editTextZipCode);

        editPassword = findViewById(R.id.editTextPassword);
        btn = findViewById(R.id.create_account_btn);
        progress = findViewById(R.id.progressCircle);
        btn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_account_btn:
                registerUser();
        }
    }


    public void registerUser(){
        email = editEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        if(editFirstName.getText().toString().isEmpty()){
            editFirstName.setError("First name is required");
            editFirstName.requestFocus();
            return;
        }
        if(editLastName.getText().toString().isEmpty()){
            editLastName.setError("Last name is required");
            editLastName.requestFocus();
            return;
        }
        if(editEmail.getText().toString().isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }

        if(editPassword.getText().toString().isEmpty()){
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }
        if(!isValidEmail(editEmail.getText().toString())){
            editEmail.setError("Enter a valid email");
            editEmail.requestFocus();
            return;
        }
        if(!isValidPassword(editPassword.getText().toString())){
            editPassword.setError("Enter a valid password");
            editPassword.requestFocus();
            return;
        }
//        progress.setVisibility(View.VISIBLE);



        User user = new User(editFirstName.getText().toString(),
                editLastName.getText().toString(),
                editEmail.getText().toString(),
                editDoB.getText().toString(),
                editAddress.getText().toString(),
                editZipCode.getText().toString(),
                editPassword.getText().toString());



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser userr = mAuth.getCurrentUser();
                            FirebaseDatabase.getInstance().getReference("user").child(userr.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "User added", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Register.this, "Authentication failed.",
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

}