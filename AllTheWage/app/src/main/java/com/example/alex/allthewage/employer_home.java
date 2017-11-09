package com.example.alex.allthewage;


import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.*;
import com.google.firebase.database.*;


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
