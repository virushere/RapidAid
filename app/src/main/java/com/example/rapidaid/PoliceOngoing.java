package com.example.rapidaid;

import static java.lang.String.valueOf;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PoliceOngoing extends Fragment {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<PoliceOngoingData> policeOngoingDataArrayList;
    PoliceOngoingDataAdapter policeOngoingDataAdapter;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userId;
    String usercity;
    DocumentReference documentReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_police_ongoing, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = view.findViewById(R.id.police_ongoing_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        documentReference = db.collection("Police").document(userId);

        policeOngoingDataArrayList = new ArrayList<PoliceOngoingData>();
        policeOngoingDataAdapter = new PoliceOngoingDataAdapter(getActivity(), policeOngoingDataArrayList);

        recyclerView.setAdapter(policeOngoingDataAdapter);

        gettingData();

        return view;
    }

    public void EventChangeListener() {

        db.collection("Ongoing").whereEqualTo("city", usercity)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                policeOngoingDataArrayList.add(dc.getDocument().toObject(PoliceOngoingData.class));
                            }

                            policeOngoingDataAdapter.notifyDataSetChanged();
                        }

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    public void gettingData() {
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                System.out.println(value);
                usercity = value.getString("city");
                System.out.println(usercity);

                EventChangeListener();
            }
        });
    }
}