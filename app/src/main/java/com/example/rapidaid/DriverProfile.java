package com.example.rapidaid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class DriverProfile extends Fragment {

    TextInputLayout driver_profile_name, driver_profile_email, driver_profile_phone, driver_profile_vehicle_number, driver_profile_city_name;
    Button driver_btn_profile_update;
    ProgressBar progressBar;
    String name, email, phone, vehicle, city;

    //Firebase Instances
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    DocumentReference documentReference;

    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_profile, container, false);

        driver_profile_name = view.findViewById(R.id.driver_profile_name);
        driver_profile_email = view.findViewById(R.id.driver_profile_email);
        driver_profile_phone = view.findViewById(R.id.driver_profile_phone);
        driver_profile_vehicle_number = view.findViewById(R.id.driver_profile_vehicle_number);
        driver_profile_city_name = view.findViewById(R.id.driver_profile_city_name);
        driver_btn_profile_update = view.findViewById(R.id.driver_btn_profile_update);
        progressBar = view.findViewById(R.id.pb_progress);

        //Firebase References
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        documentReference = db.collection("Driver").document(userId);

        //Fetching Values for Profile
        gettingData();

        driver_btn_profile_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                name = driver_profile_name.getEditText().getText().toString();
                email = driver_profile_email.getEditText().getText().toString();
                phone = driver_profile_phone.getEditText().getText().toString();
                vehicle = driver_profile_vehicle_number.getEditText().getText().toString();
                city = driver_profile_city_name.getEditText().getText().toString();
                updateValues();
            }
        });

        return view;
    }

    private void updateValues() {
        Map<String, Object> userStore = new HashMap<>();
        userStore.put("name", name);
        userStore.put("email", email);
        userStore.put("phone", phone);
        userStore.put("vehicle", vehicle);
        userStore.put("city", city);
        userStore.put("work", "Driver");

        documentReference.set(userStore).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(getActivity(), "Failed to add data \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gettingData() {
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                System.out.println(value);

                String name = value.getString("name").toString();
                String email = value.getString("email").toString();
                String phone = value.getString("phone").toString();
                String vehicle = value.getString("vehicle").toString();
                String city = value.getString("city").toString();

                driver_profile_name.getEditText().setText(name);
                driver_profile_email.getEditText().setText(email);
                driver_profile_phone.getEditText().setText(phone);
                driver_profile_vehicle_number.getEditText().setText(vehicle);
                driver_profile_city_name.getEditText().setText(city);
            }
        });
    }
}