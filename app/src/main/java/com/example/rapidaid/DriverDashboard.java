package com.example.rapidaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DriverDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);

        Toolbar toolbar = findViewById(R.id.driver_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.driver_drawer_layout);

        NavigationView navigationView = findViewById(R.id.driver_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.driver_fragment_container, new DriverNewAid()).commit();
            navigationView.setCheckedItem(R.id.driver_nav_new);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit");
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DriverDashboard.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.driver_nav_new:
                getSupportFragmentManager().beginTransaction().replace(R.id.driver_fragment_container, new DriverNewAid()).commit();
                break;
            case R.id.driver_nav_ongoing:
                getSupportFragmentManager().beginTransaction().replace(R.id.driver_fragment_container, new DriverOngoing()).commit();
                break;
            case R.id.driver_nav_completed:
                getSupportFragmentManager().beginTransaction().replace(R.id.driver_fragment_container, new DriverCompleted()).commit();
                break;
            case R.id.driver_nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.driver_fragment_container, new DriverProfile()).commit();
                break;
            case R.id.driver_nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Alert!");
                builder.setMessage("Do you want to Logout?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.nav_share:
                String url = "https://drive.google.com/drive/folders/1PvmtHfk6tCu4-stlWEUAYJ8n_kYIY65e?usp=sharing";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                break;
            case R.id.nav_faq:
                getSupportFragmentManager().beginTransaction().replace(R.id.driver_fragment_container, new PoliceFAQ()).commit();
                break;
            case R.id.nav_feedback:
                getSupportFragmentManager().beginTransaction().replace(R.id.driver_fragment_container, new Feedback()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}