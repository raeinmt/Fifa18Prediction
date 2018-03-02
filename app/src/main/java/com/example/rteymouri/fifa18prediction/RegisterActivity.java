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

public class RegisterActivity extends AppCompatActivity {

    private Button cancelRegister;
    private Button confirmRegister;
    private EditText nameField;
    private EditText emailField;
    private EditText passwordField;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cancelRegister = findViewById(R.id.cancelRegister);
        confirmRegister = findViewById(R.id.confirmRegister);
        nameField = findViewById(R.id.registerName);
        emailField = findViewById(R.id.registerEmail);
        passwordField = findViewById(R.id.registerPassword);

        mAuth = FirebaseAuth.getInstance();

        cancelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = emailField.getText().toString();
                String passwrod = passwordField.getText().toString();
                String name = nameField.getText().toString();

                //TODO: validate email and password and re-enter password
                //TODO: email must be correctly formatted and password must be at least 6 character long

                // Register the user with firebase authentication
                mAuth.createUserWithEmailAndPassword(email, passwrod).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d("FIFAA 18:","confirmRegister Done!");
                            Intent predictionsIntent = new Intent(getApplicationContext(),PredictionsActivity.class);
                            startActivity(predictionsIntent);
                            finish();

                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"Registration failed!",Toast.LENGTH_SHORT).show();
                            Log.d("FIFAA 18:","confirmRegister failed!" + task.getResult().toString());
                        }
                    }
                });


            }
        });
    }


}
