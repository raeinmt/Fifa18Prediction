package com.example.rteymouri.fifa18prediction;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button registerButton;
    private Button loginButton;
    private EditText emailField;
    private EditText passwordField;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerButton = findViewById(R.id.register);
        loginButton = findViewById(R.id.login);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent to a registerActivity
                Log.d("FIFA 18","Register clicked");
                Intent registerActivity = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(registerActivity);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Check if email and password is valid
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                Log.d("FIFA 18:","email " + email);
                Log.d("FIFA 18:","password " + password);
                // TODO: Check if the user/pass exists on firebase
                // TODO: If yes, intent to resultsActivity
                // TODO: If no, create a toast with error message
                if (email.isEmpty() || password.isEmpty()) {
                    Log.d("FIFA 18:","Email and password cannot be empty");
                }
                else {

                    // Add firebase validation
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent predictionsActivity = new Intent(getApplicationContext(), PredictionsActivity.class);
                                startActivity(predictionsActivity);
                            }

                            else  {
                                Toast.makeText(LoginActivity.this,"Login failed!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser !=null) {
            Intent predictionsActivity = new Intent(getApplicationContext(), PredictionsActivity.class);
            startActivity(predictionsActivity);
        }
    }
}
