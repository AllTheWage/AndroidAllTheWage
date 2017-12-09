package com.example.alex.allthewage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 11/1/2017.
 * Modified by Jamine Guo on 11/5/2017.
 *
 * List of things to work on:
 *      - Add exception for null value input click
 *      - Limit input to 2 decimal places
 *      - Remove the underline in currentHourText
 *      - Maybe do something like "Current paycheck amount"
 *      - Incorporation involving geolocation feature?
 *      - Integrate push notifications instead of current "success!" msg
 *      - Add an undo feature?
 *      - Lock EditText lines
 */

public class set_hours extends AppCompatActivity {

    private double hours;
    private List<String> names;
    private List<String> IDs;
    TextView currentHourDisplayText;
    EditText enterHourText;
    Spinner nameSpinner2;
    ArrayAdapter nameAdapter2;

    // variables for/from selected employee from spinner
    private String selectedName;
    private String selectedRate;
    private String selectedHours;
    private String selectedID;

     DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("EMPLOYEES").child(globalVars.GlobalCompanyName);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_hours);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // access employees database and implement spinner
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        // collectName((Map<String, Object>) dataSnapshot.getValue());
                        names = new ArrayList<String>();
                        names.add("None"); // Initial selection
                        IDs = new ArrayList<String>();
                        IDs.add("None"); // Initial selection

                        // Iterate through employee IDs and retrieve names
                        for(DataSnapshot employee : dataSnapshot.getChildren()){
                            String name = employee.child("Name").getValue(String.class);
                            String ID = employee.getKey();
                            names.add(name);
                            IDs.add(ID);
                        }

                        // Spinner implementation
                        nameSpinner2 = (Spinner) findViewById(R.id.nameSpinner2);
                        nameAdapter2 = new ArrayAdapter(set_hours.this, android.R.layout.simple_spinner_dropdown_item, names);
                        nameAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        nameSpinner2.setAdapter(nameAdapter2);

                        // Spinner selection
                        nameSpinner2.setOnItemSelectedListener(
                                new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        // STORE INFORMATION
                                        selectedName = (String) parent.getItemAtPosition(position);
                                        if (selectedName == "None") {
                                            selectedRate = "0";
                                            selectedHours = "0";
                                            selectedID = "0";
                                            currentHourDisplayText.setText("Current hours worked is ...");
                                        }
                                        else {
                                            selectedID = IDs.get(position);
                                            DataSnapshot employee = dataSnapshot.child(selectedID);
                                            selectedRate = employee.child("Pay Rate").getValue().toString();
                                            selectedHours = employee.child("Hours Worked").getValue().toString();

                                            //Change Display Information
                                            currentHourDisplayText.setText("Current hours worked is " + selectedHours + " hours");
                                        }
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

        currentHourDisplayText = (TextView) findViewById(R.id.currentHourDisplayText);
        enterHourText = (EditText) findViewById(R.id.enterRateText);

        // Listeners
        Button addHoursButton = (Button) findViewById(R.id.addHoursButton);
        addHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedName == "None"){
                    enterHourText.setHint("Select an employee");
                    enterHourText.setText("");
                }
                else {
                    // input new info into firebase
                    hours = Double.parseDouble(enterHourText.getText().toString()) + Double.parseDouble(selectedHours);
                    ref.child(selectedID).child("Hours Worked").setValue(hours);
                    ref.child(selectedID).child("Paycheck Amount").setValue(hours * Double.parseDouble(selectedRate));

                    // Change text after input
                    enterHourText.setHint("Added successfully!");
                    enterHourText.setText("");
                }
            }
        });

        Button changeHoursButton = (Button) findViewById(R.id.changeHoursButton);
        changeHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedName == "None"){
                    enterHourText.setHint("Select an employee");
                    enterHourText.setText("");
                }
                else {
                    // input new info into firebase
                    hours = Double.parseDouble(enterHourText.getText().toString());
                    ref.child(selectedID).child("Hours Worked").setValue(hours);
                    ref.child(selectedID).child("Paycheck Amount").setValue(hours * Double.parseDouble(selectedRate));

                    // Change text after input
                    enterHourText.setHint("Changed successfully!");
                    enterHourText.setText("");
                }
            }
        });

        Button setRateButton = (Button) findViewById(R.id.setRateButton);
        setRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setPayIntent = new Intent (set_hours.this, set_pay_rate.class);
                startActivity(setPayIntent);
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
                Intent empIntent = new Intent(set_hours.this, employee_info.class);
                startActivity(empIntent);
                return true;
            case R.id.paychecksMenu:
                Intent paycheckIntent = new Intent(set_hours.this, paychecks.class);
                startActivity(paycheckIntent);
                return true;
            case R.id.homeMenu:
                Intent setHoursIntent = new Intent (set_hours.this, employer_home.class);
                startActivity(setHoursIntent);
                return true;
            case R.id.setpayrateMenu:
                Intent setPayIntent = new Intent (set_hours.this, set_pay_rate.class);
                startActivity(setPayIntent);
                return true;
            case R.id.forumMenu:
                Intent forumIntent = new Intent (set_hours.this, forum.class);
                startActivity(forumIntent);
                return true;
            case R.id.helpMenu:
                Intent helpIntent = new Intent (set_hours.this, help.class);
                startActivity(helpIntent);
                return true;
            case R.id.signoutMenu:
                Intent signOutIntent = new Intent(set_hours.this, MainActivity.class);
                startActivity(signOutIntent);
                return true;
        }
        return true;
    }
}

