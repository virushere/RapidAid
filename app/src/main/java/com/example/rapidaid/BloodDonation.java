package com.example.rapidaid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BloodDonation extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<BloodDonationData> bloodDonationDataArrayList;
    BloodDonationDataAdapter bloodDonationDataAdapter;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donation);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.blood_donation_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bloodDonationDataArrayList = new ArrayList<BloodDonationData>();
        bloodDonationDataAdapter = new BloodDonationDataAdapter(getApplicationContext(), bloodDonationDataArrayList);

        recyclerView.setAdapter(bloodDonationDataAdapter);

        EventChangeListener();
    }

    public void EventChangeListener() {

        db.collection("Blood")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                bloodDonationDataArrayList.add(dc.getDocument().toObject(BloodDonationData.class));
                            }

                            bloodDonationDataAdapter.notifyDataSetChanged();
                        }

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}