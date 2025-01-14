package com.example.rapidaid;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DriverCompleted extends Fragment {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<DriverCompletedData> driverCompletedDataArrayList;
    DriverCompletedDataAdapter driverCompletedDataAdapter;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_completed, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = view.findViewById(R.id.driver_completed_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        driverCompletedDataArrayList = new ArrayList<DriverCompletedData>();
        driverCompletedDataAdapter = new DriverCompletedDataAdapter(getActivity(), driverCompletedDataArrayList);

        recyclerView.setAdapter(driverCompletedDataAdapter);

        EventChangeListener();

        return view;
    }

    private void EventChangeListener() {
        db.collection("Completed").whereEqualTo("userid", userId)
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
                                driverCompletedDataArrayList.add(dc.getDocument().toObject(DriverCompletedData.class));
                            }

                            driverCompletedDataAdapter.notifyDataSetChanged();
                        }

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}