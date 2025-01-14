package com.example.rapidaid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class DriverOngoingUpdate extends Fragment {

    Button driver_update_btn_update;
    TextInputLayout driver_update_name, driver_update_phone, driver_update_vehicle, driver_update_city, driver_update_google_maps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_ongoing_update, container, false);

        driver_update_name = view.findViewById(R.id.driver_update_name);
        driver_update_phone = view.findViewById(R.id.driver_update_phone);
        driver_update_vehicle = view.findViewById(R.id.driver_update_vehicle);
        driver_update_city = view.findViewById(R.id.driver_update_city);
        driver_update_google_maps = view.findViewById(R.id.driver_update_google_maps);
        driver_update_btn_update = view.findViewById(R.id.driver_update_btn_update);

        driver_update_btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOngoingData();
            }
        });

        return view;
    }

    private void updateOngoingData() {
        String name = driver_update_name.getEditText().getText().toString();
        String phone = driver_update_phone.getEditText().getText().toString();
        String vehicle = driver_update_vehicle.getEditText().getText().toString();
        String city = driver_update_city.getEditText().getText().toString();
        String maps = driver_update_google_maps.getEditText().getText().toString();
    }
}