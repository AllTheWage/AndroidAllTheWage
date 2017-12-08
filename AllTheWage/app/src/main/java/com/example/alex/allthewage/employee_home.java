package com.example.alex.allthewage;

/**
 * Created by Andres Ibarra on 11/2/17.
 */

import android.app.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.Toast;

import com.google.firebase.auth.*;
import com.google.firebase.database.*;

import org.w3c.dom.Text;

public class employee_home extends AppCompatActivity  {


    //GRABBING AN INSTANCE OF FIREBASEAUTH
    FirebaseAuth auth = FirebaseAuth.getInstance();

    //Getting the reference to the database
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    //Getting the reference to the employers personal location
    DatabaseReference empref = ref.child("EMPLOYEES");
    DatabaseReference paycheckRef = ref.child("EMPLOYEES");
    DatabaseReference hourRef = ref.child("EMPLOYEES");
    private TextView welcomeMessage;

    // Alex and Morgan implement the request button
    DatabaseReference req = ref.child("REQUESTS");
    private EditText reason;
    private EditText info;
    private EditText shift;
    private Button submitButton;
    private TextView tPaycheck;
    private TextView hWorked;


    String reasonString;
    String xInfo;
    String shiftString;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.employee_home_toolbar);
        setSupportActionBar(toolbar);
        reason = (EditText) findViewById(R.id.requestReasonET);
        info = (EditText) findViewById(R.id.ExtraInfoET);
        shift = (EditText) findViewById(R.id.requestShiftET);
        submitButton = (Button) findViewById(R.id.submitRequestButton);
        tPaycheck = (TextView) findViewById(R.id.employeeTotalPaycheckTV);
        hWorked = (TextView) findViewById(R.id.employeeHoursWorkedTV);

        welcomeMessage = (TextView) findViewById(R.id.employeeWelcome);

        //DESCRIPTION
        // Allows us to display the custom welcome message for each company depending on
        // their own company name
        empref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            // this will be called to retrieve data from firebase
            public void onDataChange(DataSnapshot dataSnapshot) {
                //we will set this equal to true if
                //we find the company name on the employer side
                boolean foundit = false;

                //first for loop which will search iteratively through all the company names
                //in the employee side of the reference
                for(DataSnapshot CompanyNames: dataSnapshot.getChildren())
                {
                    //this will search to see if the user id of the current logged in user is in the
                    //employee side of the database
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
                paycheckRef = ref.child("EMPLOYEES").child(globalVars.GlobalCompanyName).child(auth.getUid()).child("Paycheck Amount");
                hourRef = ref.child("EMPLOYEES").child(globalVars.GlobalCompanyName).child(auth.getUid()).child("Hours Worked");

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


                // getting employee total paycheck
                paycheckRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        tPaycheck.setText("Total Paycheck: $" + dataSnapshot.getValue().toString());
                        globalVars.GlobalEmployeeName = dataSnapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });//END OF GETTING EMPLOYEE TOTAL PAYCHECK

                // getting employee total HOURS WORKED
                hourRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        hWorked.setText("Hours Worked: " + dataSnapshot.getValue().toString());
                        globalVars.GlobalEmployeeName = dataSnapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });//END OF GETTING EMPLOYEE TOTAL HOURS WORKED



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });//END OF DISPLAYING COMPANY NAME




        // Alex and Morgan
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reasonString = reason.getText().toString();
                xInfo = info.getText().toString();
                shiftString = shift.getText().toString();
                info.setText("");           //reset the edit texts
                reason.setText("");
                shift.setText("");

                req = ref.child("REQUESTS").child(globalVars.GlobalCompanyName).child(auth.getUid());
                //send info to database
                req.child("Extra Information").setValue(xInfo);
                req.child("Reason").setValue(reasonString);
                req.child("Shift").setValue(shiftString);

                Toast.makeText(getApplicationContext(), "Request Submitted", Toast.LENGTH_SHORT);

            }
        });



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
