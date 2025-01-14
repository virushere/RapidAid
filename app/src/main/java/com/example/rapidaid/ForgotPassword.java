package com.example.rapidaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForgotPassword extends AppCompatActivity {

    Button btn_reset_password;
    TextInputLayout input_reset_email;
    ProgressBar progressBar;
    String email;

    //Firebase Instances
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btn_reset_password = findViewById(R.id.btn_reset_password);
        input_reset_email = findViewById(R.id.input_rest_email);
        progressBar = findViewById(R.id.pb_progress);

        mAuth = FirebaseAuth.getInstance();
        
        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData() {
        email = input_reset_email.getEditText().getText().toString();
        if (email.isEmpty()) {
            input_reset_email.setError("Email ID Required");
        } else {
            forgotPass();
        }
    }

    private void forgotPass() {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Check your email for password reset link...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                } else {
                    Toast.makeText(ForgotPassword.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPassword.this, "Please Try Again! Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}