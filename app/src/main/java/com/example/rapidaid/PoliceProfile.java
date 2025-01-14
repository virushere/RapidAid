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

public class PoliceProfile extends Fragment {

    TextInputLayout police_profile_name, police_profile_email, police_profile_phone, police_profile_vehicle_number, police_profile_city_name;
    Button police_btn_profile_update;
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
        View view = inflater.inflate(R.layout.fragment_police_profile, container, false);

        police_profile_name = view.findViewById(R.id.police_profile_name);
        police_profile_email = view.findViewById(R.id.police_profile_email);
        police_profile_phone = view.findViewById(R.id.police_profile_phone);
        police_profile_vehicle_number = view.findViewById(R.id.police_profile_vehicle_number);
        police_profile_city_name = view.findViewById(R.id.police_profile_city_name);
        police_btn_profile_update = view.findViewById(R.id.police_btn_profile_update);
        progressBar = view.findViewById(R.id.pb_progress);

        //Firebase References
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        documentReference = db.collection("Police").document(userId);

        //Fetching Values for Profile
        gettingData();

        police_btn_profile_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                name = police_profile_name.getEditText().getText().toString();
                email = police_profile_email.getEditText().getText().toString();
                phone = police_profile_phone.getEditText().getText().toString();
                vehicle = police_profile_vehicle_number.getEditText().getText().toString();
                city = police_profile_city_name.getEditText().getText().toString();
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
        userStore.put("police_id", vehicle);
        userStore.put("city", city);
        userStore.put("work", "Police");

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
                String police_id = value.getString("police_id").toString();
                String city = value.getString("city").toString();

                police_profile_name.getEditText().setText(name);
                police_profile_email.getEditText().setText(email);
                police_profile_phone.getEditText().setText(phone);
                police_profile_vehicle_number.getEditText().setText(police_id);
                police_profile_city_name.getEditText().setText(city);
            }
        });
    }
}