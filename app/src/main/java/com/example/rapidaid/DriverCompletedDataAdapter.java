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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DriverCompletedDataAdapter extends RecyclerView.Adapter<DriverCompletedDataAdapter.MyViewHolder> {

    Context context;
    ArrayList<DriverCompletedData> driverCompletedDataArrayList;

    public DriverCompletedDataAdapter(Context context, ArrayList<DriverCompletedData> driverCompletedDataArrayList) {
        this.context = context;
        this.driverCompletedDataArrayList = driverCompletedDataArrayList;
    }

    @NonNull
    @Override
    public DriverCompletedDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.driver_completed_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverCompletedDataAdapter.MyViewHolder holder, int position) {
        DriverCompletedData driverCompletedData = driverCompletedDataArrayList.get(position);

        holder.driver_completed_name.setText(driverCompletedData.name);
        holder.driver_completed_phone.setText(driverCompletedData.phone);
        holder.driver_completed_vehicle.setText(driverCompletedData.vehicle);
        holder.driver_completed_city.setText(driverCompletedData.city);
        holder.driver_completed_maps.setText(driverCompletedData.maps);
    }

    @Override
    public int getItemCount() {
        return driverCompletedDataArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView driver_completed_name, driver_completed_phone, driver_completed_vehicle, driver_completed_city, driver_completed_maps;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            driver_completed_name = itemView.findViewById(R.id.driver_completed_name);
            driver_completed_phone = itemView.findViewById(R.id.driver_completed_phone);
            driver_completed_vehicle = itemView.findViewById(R.id.driver_completed_vehicle);
            driver_completed_city = itemView.findViewById(R.id.driver_completed_city);
            driver_completed_maps = itemView.findViewById(R.id.driver_completed_maps);
        }
    }
}
