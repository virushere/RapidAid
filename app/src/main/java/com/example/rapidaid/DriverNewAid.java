package com.example.rapidaid;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DriverNewAid extends Fragment {

    Button driver_new_btn_send;
    TextInputLayout driver_new_name, driver_new_phone, driver_new_vehicle, driver_new_city, driver_new_google_maps;
    ProgressBar progressBar;

    //Firebase Instances
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    DocumentReference documentReference;

    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_new_aid, container, false);

        //Firebase References
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        //documentReference = db.collection("Ongoing").document(userId).collection("Aid").document();
        documentReference = db.collection("Ongoing").document();

        driver_new_btn_send = view.findViewById(R.id.driver_new_btn_send);
        driver_new_name = view.findViewById(R.id.driver_new_name);
        driver_new_phone = view.findViewById(R.id.driver_new_phone);
        driver_new_vehicle = view.findViewById(R.id.driver_new_vehicle);
        driver_new_city = view.findViewById(R.id.driver_new_city);
        driver_new_google_maps = view.findViewById(R.id.driver_new_google_maps);
        progressBar = view.findViewById(R.id.pb_progress);

        driver_new_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = driver_new_name.getEditText().getText().toString();
                String phone = driver_new_phone.getEditText().getText().toString();
                String vehicle = driver_new_vehicle.getEditText().getText().toString();
                String city = driver_new_city.getEditText().getText().toString();
                String maps = driver_new_google_maps.getEditText().getText().toString();
                String status = "Ongoing";

                addDataToFirestore(name, phone, vehicle, city, maps, status, userId);
            }
        });

        return view;
    }

    private void addDataToFirestore(String name, String phone, String vehicle, String city, String maps, String status, String userId) {
        Map<String, Object> userStore = new HashMap<>();
        userStore.put("name", name);
        userStore.put("phone", phone);
        userStore.put("vehicle", vehicle);
        userStore.put("city", city);
        userStore.put("maps", maps);
        userStore.put("status", status);
        userStore.put("userid", userId);

        documentReference.set(userStore).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "New Aid Created", Toast.LENGTH_SHORT).show();
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
}