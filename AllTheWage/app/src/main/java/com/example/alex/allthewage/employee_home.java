package com.example.alex.allthewage;

/**
 * Created by Andres Ibarra on 11/2/17.
 */

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
import android.widget.TextView;

import com.google.firebase.auth.*;
import com.google.firebase.database.*;

public class employee_home extends AppCompatActivity  {


    //GRABBING AN INSTANCE OF FIREBASEAUTH
    FirebaseAuth auth = FirebaseAuth.getInstance();

    //Getting the reference to the database
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    //Getting the reference to the employers personal location
    DatabaseReference empref = ref.child("EMPLOYEES");

    private TextView welcomeMessage;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.employee_home_toolbar);
        setSupportActionBar(toolbar);

        welcomeMessage = (TextView) findViewById(R.id.employeeWelcome);

        //DESCRIPTION
        // Allows us to display the custom welcome message for each company depending on
        // their own company name
        empref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean foundit = false;

                for(DataSnapshot CompanyNames: dataSnapshot.getChildren())
                {

                    for(DataSnapshot userIDs: CompanyNames.getChildren()){
                        if(userIDs.getKey().contentEquals(auth.getUid())){
                            globalVars.GlobalCompanyName = CompanyNames.getKey();
                            foundit = true;
                            break;

                        }

                    }
                    if(foundit)
                        break;

                }

                //FIXING THE DATABASE REFERENCE
                empref = ref.child("EMPLOYEES").child(globalVars.GlobalCompanyName).child(auth.getUid()).child("Name");

                empref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        welcomeMessage.setText("Welcome, " + dataSnapshot.getValue().toString()+ "!");
                        globalVars.GlobalEmployeeName = dataSnapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });//END OF GETTING EMPLOYEE NAME
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });//END OF DISPLAYING COMPANY NAME


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.employeemenu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.paychecksMenu:
                Intent paycheckIntent = new Intent(employee_home.this, paychecks.class);
                startActivity(paycheckIntent);
                return true;
            case R.id.forumMenu:
                Intent forumIntent = new Intent(employee_home.this, forum.class);
                startActivity(forumIntent);
                return true;
            case R.id.requestsMenu:
                Intent requestsIntent = new Intent(employee_home.this, requests.class);
                startActivity(requestsIntent);
                return true;
            case R.id.helpMenu:
                Intent helpIntent = new Intent(employee_home.this, help.class);
                startActivity(helpIntent);
                return true;


            //NEED TO DECIDE WHAT TO PUT IN THE EMPLOYEE MENU!!!
            case R.id.employeesMenu:

                return true;
            case R.id.signoutMenu:
                Intent signOutIntent = new Intent(employee_home.this, MainActivity.class);
                startActivity(signOutIntent);
                return true;
        }


        return true;
    }
}
