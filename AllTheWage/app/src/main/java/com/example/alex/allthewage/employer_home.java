package com.example.alex.allthewage;


import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

import java.util.UUID;

import static android.provider.MediaStore.Video.VideoColumns.LATITUDE;
import static android.provider.MediaStore.Video.VideoColumns.LONGITUDE;



/**
 * Created by Alex on 11/1/2017.
 */

public class employer_home extends AppCompatActivity {


    //GRABBING AN INSTANCE OF FIREBASEAUTH
    FirebaseAuth auth = FirebaseAuth.getInstance();

    //Getting the reference to the database
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    //Getting the reference to the employers personal location
    DatabaseReference empref = ref.child("EMPLOYERS").child("Companies").child(auth.getUid());

    private TextView welcomeMessage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_home);

        welcomeMessage = (TextView) findViewById(R.id.employerWelcome);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        //DESCRIPTION
        // Allows us to display the custom welcome message for each company depending on
        // their own company name
        empref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot child: dataSnapshot.getChildren())
               {
                   globalVars.GlobalCompanyName = child.getKey();
                   System.out.println("Welcome, " +  globalVars.GlobalCompanyName);

                   welcomeMessage.setText("Welcome, " +  globalVars.GlobalCompanyName);

               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final Button GeoFenceButton = (Button) findViewById(R.id.Geo_Fence_Button);
        final LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
        FusedLocationProviderClient location = LocationServices.getFusedLocationProviderClient(this);
        GeoFenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GoogleApiClient.ConnectionCallbacks connectionAddListener =
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                            }
                        };

                GoogleApiClient.OnConnectionFailedListener connectionFailedListener =
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(ConnectionResult connectionResult) {

                            }
                        };
            }

                public void createGeofences(double latitude, double longitude) {
                    String id = UUID.randomUUID().toString();
                    Geofence fence = new Geofence.Builder()
                            .setRequestId(id)
                            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                            .setCircularRegion(latitude, longitude, 100) // Try changing your radius
                            .setExpirationDuration(Geofence.NEVER_EXPIRE)
                            .build();
                }

        });
    }
    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == resultCode) {
            // Handle success
            return true;
        } else {
            // Handle the error
            return false;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.employeesMenu:
                Intent empIntent = new Intent(employer_home.this, employee_info.class);
                startActivity(empIntent);
                return true;
            case R.id.paychecksMenu:
                Intent paycheckIntent = new Intent(employer_home.this, paychecks.class);
                startActivity(paycheckIntent);
                return true;
            case R.id.sethoursMenu:
                Intent setHoursIntent = new Intent (employer_home.this, set_hours.class);
                startActivity(setHoursIntent);
                return true;
            case R.id.setpayrateMenu:
                Intent setPayIntent = new Intent (employer_home.this, set_pay_rate.class);
                startActivity(setPayIntent);
                return true;
            case R.id.forumMenu:
                Intent forumIntent = new Intent (employer_home.this, forum.class);
                startActivity(forumIntent);
                return true;
            case R.id.helpMenu:
                Intent helpIntent = new Intent (employer_home.this, help.class);
                startActivity(helpIntent);
                return true;
            case R.id.signoutMenu:
                auth.signOut();
                Intent signOutIntent = new Intent(employer_home.this, MainActivity.class);
                startActivity(signOutIntent);
                return true;
        }
        return true;
    }

}
