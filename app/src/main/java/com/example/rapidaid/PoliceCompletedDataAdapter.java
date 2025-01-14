package com.example.rapidaid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PoliceCompletedDataAdapter extends RecyclerView.Adapter<PoliceCompletedDataAdapter.MyViewHolder> {

    Context context;
    ArrayList<PoliceCompletedData> policeCompletedDataArrayList;

    public PoliceCompletedDataAdapter(Context context, ArrayList<PoliceCompletedData> policeCompletedDataArrayList) {
        this.context = context;
        this.policeCompletedDataArrayList = policeCompletedDataArrayList;
    }

    @NonNull
    @Override
    public PoliceCompletedDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.police_completed_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PoliceCompletedDataAdapter.MyViewHolder holder, int position) {
        PoliceCompletedData policeCompletedData = policeCompletedDataArrayList.get(position);

        holder.police_completed_name.setText(policeCompletedData.name);
        holder.police_completed_phone.setText(policeCompletedData.phone);
        holder.police_completed_vehicle.setText(policeCompletedData.vehicle);
        holder.police_completed_city.setText(policeCompletedData.city);
        holder.police_completed_maps.setText(policeCompletedData.maps);
    }

    @Override
    public int getItemCount() {
        return policeCompletedDataArrayList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView police_completed_name, police_completed_phone, police_completed_vehicle, police_completed_city, police_completed_maps;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            police_completed_name = itemView.findViewById(R.id.police_completed_name);
            police_completed_phone = itemView.findViewById(R.id.police_completed_phone);
            police_completed_vehicle = itemView.findViewById(R.id.police_completed_vehicle);
            police_completed_city = itemView.findViewById(R.id.police_completed_city);
            police_completed_maps = itemView.findViewById(R.id.police_completed_maps);
        }
    }
}
