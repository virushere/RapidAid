package com.example.rapidaid;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends Fragment {

    TextInputLayout input_feedback_name, input_feedback_email, input_feedback_message;
    ProgressBar progressBar;
    Button btn_feedback_send;

    //Firebase Instances
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        input_feedback_name = view.findViewById(R.id.input_feedback_name);
        input_feedback_email = view.findViewById(R.id.input_feedback_email);
        input_feedback_message = view.findViewById(R.id.input_feedback_message);
        btn_feedback_send = view.findViewById(R.id.btn_feedback_send);
        progressBar = view.findViewById(R.id.pb_progress);

        //Firebase References
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btn_feedback_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = input_feedback_name.getEditText().getText().toString();
                String email = input_feedback_email.getEditText().getText().toString();
                String message = input_feedback_message.getEditText().getText().toString();

                if (!name.isEmpty()) {
                    if (!email.isEmpty()) {
                        if (!message.isEmpty()) {
                            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                progressBar.setVisibility(View.VISIBLE);
                                InertValue(name, email, message);
                            } else {
                                input_feedback_email.setError("Please enter a valid email");
                            }
                        } else {
                            input_feedback_message.setError("Please enter message");
                        }
                    } else {
                        input_feedback_email.setError("Please enter email");
                    }
                } else {
                    input_feedback_name.setError("Please enter name");
                }

            }
        });

        return view;
    }

    private void InertValue(String name, String email, String message) {
        DocumentReference documentReference;
        Map<String, Object> feedback = new HashMap<>();

        documentReference = db.collection("Feedback").document();

        feedback.put("name", name);
        feedback.put("email", email);
        feedback.put("message", message);

        documentReference.set(feedback).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.GONE);
                input_feedback_name.getEditText().setText("");
                input_feedback_email.getEditText().setText("");
                input_feedback_message.getEditText().setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(getActivity(), "Failed to upload feedback\n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}