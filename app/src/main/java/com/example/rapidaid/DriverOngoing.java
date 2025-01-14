package com.example.rapidaid;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DriverOngoing extends Fragment implements DriverOngoingDataInterface {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<DriverOngoingDataModel> driverOngoingDataArrayList;
    DriverOngoingDataAdapter driverOngoingDataAdapter;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_ongoing, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = view.findViewById(R.id.driver_ongoing_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        driverOngoingDataArrayList = new ArrayList<DriverOngoingDataModel>();
        driverOngoingDataAdapter = new DriverOngoingDataAdapter(getActivity(), driverOngoingDataArrayList);

        recyclerView.setAdapter(driverOngoingDataAdapter);
        driverOngoingDataAdapter.driverOngoingDataInterface = this;

        EventChangeListener();

        return view;
    }

    private void EventChangeListener() {
        db.collection("Ongoing").whereEqualTo("userid", userId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                String id = dc.getDocument().getId();
                                DriverOngoingData driverOngoingData = dc.getDocument().toObject(DriverOngoingData.class);
                                DriverOngoingDataModel driverOngoingDataModel = new DriverOngoingDataModel(id, driverOngoingData.name, driverOngoingData.phone, driverOngoingData.vehicle, driverOngoingData.city, driverOngoingData.maps);
                                driverOngoingDataArrayList.add(driverOngoingDataModel);
                            }

                            driverOngoingDataAdapter.notifyDataSetChanged();
                        }

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onClickDataDelete(DriverOngoingDataModel driverOngoingData) {
        System.out.println("Test Data: " + driverOngoingData.id);
        FirebaseFirestore deletedb = FirebaseFirestore.getInstance();
        deletedb.collection("Ongoing").document(driverOngoingData.id).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Test", "Value deleted");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}