package com.example.alex.allthewage;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 11/1/2017.
 * Modified by Jamine Guo on 12/8/2017.
 *
 * List of things to work on:
 *      - Lock EditText lines
 */

public class employee_info extends AppCompatActivity {

    private List<String> IDs;
    private List<String> names;
    Spinner employeeSpinner;
    ArrayAdapter employeeAdapter;
    EditText employeeID;
    EditText employeeName;
    EditText employeeAge;
    EditText employeeMail;
    EditText employeeNum;

    // variables for/from selected employee from spinner
    private String selectedName;
    private String selectedID;
    private String selectedAge;
    private String selectedMail;
    private String selectedNum;

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("EMPLOYERS").child("Companies").child(mAuth.getUid()).child(globalVars.GlobalCompanyName);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_info);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        employeeID = (EditText) findViewById(R.id.employeeID);
        employeeName = (EditText) findViewById(R.id.employeeName);
        employeeAge = (EditText) findViewById(R.id.employeeAge);
        employeeMail = (EditText) findViewById(R.id.employeeMail);
        employeeNum = (EditText) findViewById(R.id.employeeNum);

        // access employers database and implement spinner
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {

                        // collectName((Map<String, Object>) dataSnapshot.getValue());
                        names = new ArrayList<String>();
                        names.add("None"); // Initial selection
                        IDs = new ArrayList<String>();
                        IDs.add("Employee ID"); // Initial selection

                        // Iterate through employee IDs and retrieve names
                        for(DataSnapshot employee : dataSnapshot.getChildren()){
                            String name = employee.child("Name").getValue(String.class);
                            String ID = employee.getKey();
                            names.add(name);
                            IDs.add(ID);
                        }

                        // Spinner implementation
                        employeeSpinner = (Spinner) findViewById(R.id.employeeSpinner);
                        employeeAdapter = new ArrayAdapter(employee_info.this, android.R.layout.simple_spinner_dropdown_item, names);
                        employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        employeeSpinner.setAdapter(employeeAdapter);

                        // Spinner selection
                        employeeSpinner.setOnItemSelectedListener(
                                new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        // STORE INFORMATION
                                        selectedName = (String) parent.getItemAtPosition(position);
                                        if (selectedName == "None") {
                                            selectedName = "Employee Name";
                                            selectedID = "Employee ID";
                                            selectedAge = "Employee Age";
                                            selectedMail = "Employee Email";
                                            selectedNum = "Employee Phone Number";
                                        }
                                        else {
                                            selectedID = IDs.get(position);
                                            DataSnapshot employee = dataSnapshot.child(selectedID);
                                            selectedAge = employee.child("Age").getValue().toString();
                                            selectedMail = employee.child("Email").getValue().toString();
                                            selectedNum = employee.child("Phone Number").getValue().toString();
                                        }

                                        //Change Display Information
                                        employeeID.setText(selectedID);
                                        employeeName.setText(selectedName);
                                        employeeAge.setText(selectedAge);
                                        employeeMail.setText(selectedMail);
                                        employeeNum.setText(selectedNum);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                }
                        );
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }




    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()) {
            case R.id.homeMenu:
                Intent empIntent = new Intent(employee_info.this, employer_home.class);
                startActivity(empIntent);
                return true;
            case R.id.paychecksMenu:
                Intent paycheckIntent = new Intent(employee_info.this, paychecks.class);
                startActivity(paycheckIntent);
                return true;
            case R.id.sethoursMenu:
                Intent setHoursIntent = new Intent (employee_info.this, set_hours.class);
                startActivity(setHoursIntent);
                return true;
            case R.id.setpayrateMenu:
                Intent setPayIntent = new Intent (employee_info.this, set_pay_rate.class);
                startActivity(setPayIntent);
                return true;
            case R.id.forumMenu:
                Intent forumIntent = new Intent (employee_info.this, forum.class);
                startActivity(forumIntent);
                return true;
            case R.id.helpMenu:
                Intent helpIntent = new Intent (employee_info.this, help.class);
                startActivity(helpIntent);
                return true;
            case R.id.signoutMenu:
                Intent signOutIntent = new Intent(employee_info.this, MainActivity.class);
                startActivity(signOutIntent);
                return true;
        }
        return true;
    }

}

