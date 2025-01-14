package com.example.rapidaid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PoliceOngoingDataAdapter extends RecyclerView.Adapter<PoliceOngoingDataAdapter.MyViewHolder> {

    Context context;
    ArrayList<PoliceOngoingData> policeOngoingDataArrayList;

    public PoliceOngoingDataAdapter(Context context, ArrayList<PoliceOngoingData> policeOngoingDataArrayList) {
        this.context = context;
        this.policeOngoingDataArrayList = policeOngoingDataArrayList;
    }

    @NonNull
    @Override
    public PoliceOngoingDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.police_ongoing_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PoliceOngoingDataAdapter.MyViewHolder holder, int position) {
        PoliceOngoingData policeOngoingData = policeOngoingDataArrayList.get(position);

        holder.police_ongoing_name.setText(policeOngoingData.name);
        holder.police_ongoing_phone.setText(policeOngoingData.phone);
        holder.police_ongoing_vehicle.setText(policeOngoingData.vehicle);
        holder.police_ongoing_city.setText(policeOngoingData.city);
        holder.police_ongoing_maps.setText(policeOngoingData.maps);

        String url = holder.police_ongoing_maps.getText().toString();

        holder.police_ongoing_btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return policeOngoingDataArrayList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {

        Button police_ongoing_btn_location;
        TextView police_ongoing_name, police_ongoing_phone, police_ongoing_vehicle, police_ongoing_city, police_ongoing_maps;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            police_ongoing_name = itemView.findViewById(R.id.police_ongoing_name);
            police_ongoing_phone = itemView.findViewById(R.id.police_ongoing_phone);
            police_ongoing_vehicle = itemView.findViewById(R.id.police_ongoing_vehicle);
            police_ongoing_city = itemView.findViewById(R.id.police_ongoing_city);
            police_ongoing_maps = itemView.findViewById(R.id.police_ongoing_maps);
            police_ongoing_btn_location = itemView.findViewById(R.id.police_ongoing_btn_location);
        }
    }
}
