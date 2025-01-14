package com.example.rapidaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    String text;
    TextView tv_redirect_signup;
    Button btn_login;
    TextInputLayout input_register_name, input_register_email, input_register_phone, input_vehicle_number, input_city_name, input_register_password, input_register_confirm_password;
    ProgressBar progressBar;

    //Firebase Instances
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = findViewById(R.id.spinner_work);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.work, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        tv_redirect_signup = findViewById(R.id.tv_redirect_signup);
        btn_login = findViewById(R.id.btn_login);
        input_register_name = findViewById(R.id.input_register_name);
        input_register_email = findViewById(R.id.input_register_email);
        input_register_phone = findViewById(R.id.input_register_phone);
        input_vehicle_number = findViewById(R.id.input_vehicle_number);
        input_city_name = findViewById(R.id.input_city_name);
        input_register_password = findViewById(R.id.input_register_password);
        input_register_confirm_password = findViewById(R.id.input_register_confirm_password);
        progressBar = findViewById(R.id.pb_progress);

        //Firebase References
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tv_redirect_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = input_register_name.getEditText().getText().toString();
                String email = input_register_email.getEditText().getText().toString();
                String phone = input_register_phone.getEditText().getText().toString();
                String vehicle = input_vehicle_number.getEditText().getText().toString();
                String city = input_city_name.getEditText().getText().toString();
                String password = input_register_password.getEditText().getText().toString();
                String confirm_password = input_register_confirm_password.getEditText().getText().toString();
                String work = text;

                if (!name.isEmpty()) {
                    input_register_name.setError(null);
                    input_register_name.setErrorEnabled(false);
                    if (!email.isEmpty()) {
                        input_register_email.setError(null);
                        input_register_email.setErrorEnabled(false);
                        if (!phone.isEmpty() && input_register_phone.getEditText().getText().toString().length()==10) {
                            input_register_phone.setError(null);
                            input_register_phone.setErrorEnabled(false);
                            if (!password.isEmpty() && input_register_password.getEditText().getText().toString().length()>8 && isValidPassword(password)) {
                                input_register_password.setError(null);
                                input_register_password.setErrorEnabled(false);
                                if (!confirm_password.isEmpty()) {
                                    input_register_confirm_password.setError(null);
                                    input_register_confirm_password.setErrorEnabled(false);
                                    if (password.equals(confirm_password)) {
                                        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                            progressBar.setVisibility(View.VISIBLE);
                                            mAuth.createUserWithEmailAndPassword(email, password)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                User user = new User(name, email, phone, vehicle, city, work);

                                                                addDataToFireStore(name, email, phone, vehicle, city, work);

//                                                                FirebaseDatabase.getInstance().getReference("Users")
//                                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                    @Override
//                                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                                        if (task.isSuccessful()) {
//                                                                            Toast.makeText(Register.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
//                                                                            addDataToFireStore(name, email, phone, vehicle, city, work);
//                                                                        } else {
//                                                                            Toast.makeText(Register.this, "Failed to register!", Toast.LENGTH_SHORT).show();
//                                                                            progressBar.setVisibility(View.GONE);
//                                                                        }
//                                                                    }
//                                                                });
                                                            } else {
                                                                Toast.makeText(Register.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                                                                progressBar.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    });

                                        } else {
                                            input_register_email.setError("Please enter valid email");
                                        }
                                    } else {
                                        Toast.makeText(Register.this, "Confirmation Password does not match", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    input_register_confirm_password.setError("Please enter confirmation password");
                                }
                            } else {
                                input_register_password.setError("Password must contain minimum 8 characters at least 1 Alphabet, 1 Number and 1 Special Character");
                            }
                        } else {
                            input_register_phone.setError("Phone number be of exact 10 digits");
                        }
                    } else {
                        input_register_email.setError("Please enter email");
                    }
                } else {
                    input_register_name.setError("Please enter name");
                }
            }
        });
    }

    private void addDataToFireStore(String name, String email, String phone, String vehicle, String city, String work) {
        //CollectionReference collectionReference;
        DocumentReference documentReference;
        userId = mAuth.getCurrentUser().getUid();
        Map<String, Object> userStore = new HashMap<>();

        if (work.equals("Police")) {
            documentReference = db.collection("Police").document(userId);

            userStore.put("name", name);
            userStore.put("email", email);
            userStore.put("phone", phone);
            userStore.put("police_id", vehicle);
            userStore.put("city", city);
            userStore.put("work", work);
        } else {
            documentReference = db.collection("Driver").document(userId);

            userStore.put("name", name);
            userStore.put("email", email);
            userStore.put("phone", phone);
            userStore.put("vehicle", vehicle);
            userStore.put("city", city);
            userStore.put("work", work);
        }

        //UsersClass usersClass = new UsersClass(name, email, phone, vehicle, city, work);

        documentReference.set(userStore).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.GONE);

                FirebaseUser user = mAuth.getCurrentUser();
                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressBar.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Email Failure", e.getMessage());
                    }
                });

//                if (work.equals("Police")) {
//                    Intent i = new Intent(getApplicationContext(), PoliceDashboard.class);
//                    startActivity(i);
//                    finish();
//                } else {
//                    Intent i = new Intent(getApplicationContext(), DriverDashboard.class);
//                    startActivity(i);
//                    finish();
//                }

                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(Register.this, "Failed to add data \n" + e, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}