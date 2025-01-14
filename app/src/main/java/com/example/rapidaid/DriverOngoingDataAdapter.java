package com.example.rapidaid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DriverOngoingDataAdapter extends RecyclerView.Adapter<DriverOngoingDataAdapter.MyViewHolder> {

    Context context;
    ArrayList<DriverOngoingDataModel> driverOngoingDataArrayList;
    DriverOngoingDataInterface driverOngoingDataInterface;

    public DriverOngoingDataAdapter(Context context, ArrayList<DriverOngoingDataModel> driverOngoingDataArrayList) {
        this.context = context;
        this.driverOngoingDataArrayList = driverOngoingDataArrayList;
    }

    @NonNull
    @Override
    public DriverOngoingDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.driver_ongoing_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverOngoingDataAdapter.MyViewHolder holder, int position) {
        DriverOngoingDataModel driverOngoingData = driverOngoingDataArrayList.get(position);

        holder.driver_ongoing_name.setText(driverOngoingData.name);
        holder.driver_ongoing_phone.setText(driverOngoingData.phone);
        holder.driver_ongoing_vehicle.setText(driverOngoingData.vehicle);
        holder.driver_ongoing_city.setText(driverOngoingData.city);
        holder.driver_ongoing_maps.setText(driverOngoingData.maps);

        holder.driver_ongoing_btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.driver_ongoing_name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Once completed can't undo!");
                builder.setCancelable(false);

                builder.setPositiveButton("Complete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        //FirebaseFirestore.getInstance().collection("Ongoing").document(position)
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference documentReference = db.collection("Completed").document();

                        String name = driverOngoingData.name;
                        String phone = driverOngoingData.phone;
                        String vehicle = driverOngoingData.vehicle;
                        String city = driverOngoingData.city;
                        String maps = driverOngoingData.maps;
                        String userId = mAuth.getCurrentUser().getUid();
                        String status = "Completed";

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
                                Toast.makeText(context, "Aid Completed", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Aid Complete Failure", Toast.LENGTH_SHORT).show();
                            }
                        });

                        driverOngoingDataInterface.onClickDataDelete(driverOngoingData);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        holder.driver_ongoing_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.driver_ongoing_name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be undo!");
                builder.setCancelable(false);

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //FirebaseFirestore.getInstance().collection("Ongoing").document(position)
                        driverOngoingDataInterface.onClickDataDelete(driverOngoingData);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return driverOngoingDataArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView driver_ongoing_name, driver_ongoing_phone, driver_ongoing_vehicle, driver_ongoing_city, driver_ongoing_maps;
        Button driver_ongoing_btn_complete, driver_ongoing_btn_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            driver_ongoing_name = itemView.findViewById(R.id.driver_ongoing_name);
            driver_ongoing_phone = itemView.findViewById(R.id.driver_ongoing_phone);
            driver_ongoing_vehicle = itemView.findViewById(R.id.driver_ongoing_vehicle);
            driver_ongoing_city = itemView.findViewById(R.id.driver_ongoing_city);
            driver_ongoing_maps = itemView.findViewById(R.id.driver_ongoing_maps);
            driver_ongoing_btn_complete = itemView.findViewById(R.id.driver_ongoing_btn_complete);
            driver_ongoing_btn_delete = itemView.findViewById(R.id.driver_ongoing_btn_delete);
        }
    }
}
