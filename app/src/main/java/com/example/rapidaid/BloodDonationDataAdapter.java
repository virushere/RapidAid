package com.example.rapidaid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BloodDonationDataAdapter extends RecyclerView.Adapter<BloodDonationDataAdapter.MyViewHolder> {

    Context context;
    ArrayList<BloodDonationData> bloodDonationDataArrayList;

    public BloodDonationDataAdapter(Context context, ArrayList<BloodDonationData> bloodDonationDataArrayList) {
        this.context = context;
        this.bloodDonationDataArrayList = bloodDonationDataArrayList;
    }

    @NonNull
    @Override
    public BloodDonationDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.blood_donation_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodDonationDataAdapter.MyViewHolder holder, int position) {
        BloodDonationData bloodDonationData = bloodDonationDataArrayList.get(position);

        holder.blood_donation_location.setText(bloodDonationData.location);
        holder.blood_donation_city.setText(bloodDonationData.city);

        String maps = bloodDonationData.link;

        holder.btn_blood_donation_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(maps));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bloodDonationDataArrayList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {

        ImageButton btn_blood_donation_maps;
        TextView blood_donation_location, blood_donation_city;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            blood_donation_location = itemView.findViewById(R.id.blood_donation_location);
            blood_donation_city = itemView.findViewById(R.id.blood_donation_city);
            btn_blood_donation_maps = itemView.findViewById(R.id.btn_blood_donation_maps);
        }
    }
}
