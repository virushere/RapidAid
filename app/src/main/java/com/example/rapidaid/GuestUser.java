package com.example.rapidaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

public class GuestUser extends AppCompatActivity {

    GridLayout mainGridLayout;
    CardView card_blood_donation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_user);

        card_blood_donation = findViewById(R.id.card_blood_donation);

        mainGridLayout = findViewById(R.id.mainGridLayout);
        setSingleEvent(mainGridLayout);

        card_blood_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BloodDonation.class));
            }
        });
    }

    private void setSingleEvent(GridLayout mainGridLayout) {
        for (int i=0;i<mainGridLayout.getChildCount();i++)
        {
            CardView cardview =(CardView) mainGridLayout.getChildAt(i);
            final int finalI = i;
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalI==0)
                    {
                        Intent intent = new Intent(getApplicationContext(), Police.class);
                        startActivity(intent);
                    }
                    else  if(finalI==1)
                    {
                        Intent intent = new Intent(getApplicationContext(), Ambulance.class);
                        startActivity(intent);
                    }
                    else  if(finalI==2)
                    {
                        Intent intent = new Intent(getApplicationContext(), Firestation.class);
                        startActivity(intent);
                    }
                    else  if(finalI==3)
                    {
                        Intent intent = new Intent(getApplicationContext(), NDRF.class);
                        startActivity(intent);
                    }
                    else  if(finalI==4)
                    {
                        Intent intent = new Intent(getApplicationContext(), NDMA.class);
                        startActivity(intent);
                    }
                    else  if(finalI==5)
                    {
                        Intent intent = new Intent(getApplicationContext(), MissingComplaint.class);
                        startActivity(intent);
                    }
                    else  if(finalI==6)
                    {
                        Intent intent = new Intent(getApplicationContext(), AirAmbulance.class);
                        startActivity(intent);
                    }
                    else  if(finalI==7)
                    {
                        Intent intent = new Intent(getApplicationContext(), Railway.class);
                        startActivity(intent);
                    }
                    else  if(finalI==8)
                    {
                        Intent intent = new Intent(getApplicationContext(), LPGLeak.class);
                        startActivity(intent);
                    }
                    else  if(finalI==9)
                    {
                        Intent intent = new Intent(getApplicationContext(), CyberCrime.class);
                        startActivity(intent);
                    }
                    else  if(finalI==10)
                    {
                        Intent intent = new Intent(getApplicationContext(), Kisan.class);
                        startActivity(intent);
                    }
                    else  if(finalI==11)
                    {
                        Intent intent = new Intent(getApplicationContext(), SeniorCitizen.class);
                        startActivity(intent);
                    }
                    else  if(finalI==12)
                    {
                        Intent intent = new Intent(getApplicationContext(), Tourist.class);
                        startActivity(intent);
                    }
                    else  if(finalI==13)
                    {
                        Intent intent = new Intent(getApplicationContext(), Womenhelpline.class);
                        startActivity(intent);
                    }
                    else  if(finalI==14)
                    {
                        Intent intent = new Intent(getApplicationContext(), Antipoison.class);
                        startActivity(intent);
                    }
                    else  if(finalI==15)
                    {
                        Intent intent = new Intent(getApplicationContext(), Aids.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}