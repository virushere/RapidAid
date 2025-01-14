package com.example.rapidaid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class PoliceFAQ extends Fragment {

    RecyclerView recyclerView;
    List<FAQsModel> faQsModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_police_f_a_q, container, false);

        recyclerView = view.findViewById(R.id.recylerView);

        initData();

        setRecycler();

        return view;
    }

    private void setRecycler() {
        FAQsAdapter faQsAdapter = new FAQsAdapter(faQsModelList);
        recyclerView.setAdapter(faQsAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initData() {
        faQsModelList = new ArrayList<>();
        faQsModelList.add(new FAQsModel("Is this data correct?", "The data uploaded is retrieved via google and from authorised websites of Indian government and their respective authorities so all the information provided is authenticated."));
        faQsModelList.add(new FAQsModel("I am a police officer can I login as guest user?", "Yes, everyone can login as guest user."));
        faQsModelList.add(new FAQsModel("I am guest user can I login as police/traffic driver?", "No, administrative rights are only provided to the authorized entities."));
        faQsModelList.add(new FAQsModel("Does Rapid Aid app requires my location access?", "No, we respect our users privacy hence locational gps tracking is not been used and your location is completely hidden from application and other users."));
        faQsModelList.add(new FAQsModel("Does Rapid Aid need calling permission?", "Yes, In case you're a guest user we have a call feature from which user can directly communicate with authorized entities."));
        faQsModelList.add(new FAQsModel("Are there any other permissions that can cause harm to my privacy?", "No"));
        faQsModelList.add(new FAQsModel("Are their any subscriptions in order to avail the data provided for a normal user?", "No, our app is completely free and can be used by anyone."));
        faQsModelList.add(new FAQsModel("Is their any age criteria?", "We strongly recommend our application for 15+ age group though it can be used by anyone but with great powers comes great responsibility."));
    }
}