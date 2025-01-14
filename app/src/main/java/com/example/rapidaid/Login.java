package com.example.rapidaid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    String text;
    TextView tv_redirect_signup, tv_forgot_password;
    Button btn_login, btn_guest;
    TextInputLayout input_login_email, input_login_password;
    ProgressBar progressBar;

    //Firebase Instances
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Firebase References
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        spinner = findViewById(R.id.spinner_work);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.work, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        tv_redirect_signup = findViewById(R.id.tv_redirect_signup);
        tv_forgot_password = findViewById(R.id.tv_forgot_password);
        btn_login = findViewById(R.id.btn_login);
        input_login_email = findViewById(R.id.input_login_email);
        input_login_password = findViewById(R.id.input_login_password);
        btn_guest = findViewById(R.id.btn_guest);
        progressBar = findViewById(R.id.pb_progress);

        tv_redirect_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });

        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
            }
        });

        btn_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GuestUser.class);
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = input_login_email.getEditText().getText().toString();
                String password = input_login_password.getEditText().getText().toString();

                if (!email.isEmpty()) {
                    input_login_email.setError(null);
                    input_login_email.setErrorEnabled(false);
                    if (!password.isEmpty()) {
                        input_login_password.setError(null);
                        input_login_password.setErrorEnabled(false);
                        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            progressBar.setVisibility(View.VISIBLE);
                            mAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                if (user.isEmailVerified()) {
                                                    loginBasedOnUser();
                                                } else {
                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(Login.this, "Email ID not verified. Check your mail for verification link!", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(Login.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            input_login_email.setError("Please enter valid email");
                        }
                    } else {
                        input_login_password.setError("Please enter password");
                    }
                } else {
                    input_login_email.setError("Please enter email");
                }
            }
        });
    }

    private void loginBasedOnUser() {
        try {
            DocumentReference documentReference;
            userId = mAuth.getCurrentUser().getUid();
            System.out.println(userId);
            if (text.equals("Police")) {
                documentReference = db.collection("Police").document(userId);
            } else {
                documentReference = db.collection("Driver").document(userId);
            }

            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                    System.out.println(value.getString("work") == "Police");

                    String newText = value.getString("work").toString();

                    if (newText.equals("Police")) {
                        progressBar.setVisibility(View.GONE);
                        Intent i = new Intent(getApplicationContext(), PoliceDashboard.class);
                        startActivity(i);
                        finish();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Intent i = new Intent(getApplicationContext(), DriverDashboard.class);
                        startActivity(i);
                        finish();
                    }
                }
            });
        } catch (Exception e) {
            progressBar.setVisibility(View.GONE);
            System.out.println(e.getMessage());
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}