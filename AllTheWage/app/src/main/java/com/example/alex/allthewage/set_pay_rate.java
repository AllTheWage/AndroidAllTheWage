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
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 11/1/2017.
 * Modified by Jamine Guo on 11/5/2017
 *
 * List of things to work on:
 *      - Add exception for null value input click
 *      - Limit input to 2 decimal places
 *      - Remove the underline in currentRateText
 *      - Integrate push notifications instead of current "success!" msg
 *      - Add an undo feature?
 *      - Lock EditText lines
 */

public class set_pay_rate extends AppCompatActivity {

    private double payRate;
    private List<String> names;
    private List<String> IDs;
    TextView currentRateDisplayText;
    EditText enterRateText;
    Spinner nameSpinner;
    ArrayAdapter nameAdapter;

    // variables for/from selected employee from spinner
    private String selectedName;
    private String selectedRate;
    private String selectedHours;
    private String selectedID;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("EMPLOYEES").child(globalVars.GlobalCompanyName);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_pay_rate);
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
                        nameSpinner = (Spinner) findViewById(R.id.nameSpinner);
                        nameAdapter = new ArrayAdapter(set_pay_rate.this, android.R.layout.simple_spinner_dropdown_item, names);
                        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        nameSpinner.setAdapter(nameAdapter);

                        // Spinner selection
                        nameSpinner.setOnItemSelectedListener(
                                new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        // STORE INFORMATION
                                        selectedName = (String) parent.getItemAtPosition(position);
                                        if (selectedName == "None") {
                                            selectedRate = "0";
                                            selectedHours = "0";
                                            selectedID = "0";
                                            currentRateDisplayText.setText("Current set rate is ...");
                                        }
                                        else {
                                            selectedID = IDs.get(position);
                                            DataSnapshot employee = dataSnapshot.child(selectedID);
                                            selectedRate = employee.child("Pay Rate").getValue().toString();
                                            selectedHours = employee.child("Hours Worked").getValue().toString();

                                            //Change Display Information
                                            currentRateDisplayText.setText("Current pay-rate is $" + selectedRate + " per hour");
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


        currentRateDisplayText = (TextView) findViewById(R.id.currentRateDisplayText);
        enterRateText = (EditText) findViewById(R.id.enterRateText);

        // Listeners
        Button inputRatesButton = (Button) findViewById(R.id.inputRatesButton);
        inputRatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedName == "None"){
                    enterRateText.setHint("Select an employee");
                    enterRateText.setText("");
                }
                else {
                    // input new info
                    payRate = Double.parseDouble(enterRateText.getText().toString());
                    ref.child(selectedID).child("Pay Rate").setValue(payRate);
                    ref.child(selectedID).child("Paycheck Amount").setValue(payRate * Double.parseDouble(selectedHours));

                    // Change display after input
                    // currentRateDisplayText.setText("Current pay-rate is $" + payRate + " per hour");
                    // nameSpinner.setSelection(nameAdapter.getPosition(selectedName)); // doesn't seem to do anything because of OnItemSelected Listener
                    enterRateText.setHint("Inputted successfully!");
                    enterRateText.setText("");
                }
            }
        });

        Button setHourButton = (Button) findViewById(R.id.setHourButton);
        setHourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setHoursIntent = new Intent (set_pay_rate.this, set_hours.class);
                startActivity(setHoursIntent);
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
                Intent empIntent = new Intent(set_pay_rate.this, employee_info.class);
                startActivity(empIntent);
                return true;
            case R.id.paychecksMenu:
                Intent paycheckIntent = new Intent(set_pay_rate.this, paychecks.class);
                startActivity(paycheckIntent);
                return true;
            case R.id.sethoursMenu:
                Intent setHoursIntent = new Intent (set_pay_rate.this, set_hours.class);
                startActivity(setHoursIntent);
                return true;
            case R.id.homeMenu:
                Intent setPayIntent = new Intent (set_pay_rate.this, employer_home.class);
                startActivity(setPayIntent);
                return true;
            case R.id.forumMenu:
                Intent forumIntent = new Intent (set_pay_rate.this, forum.class);
                startActivity(forumIntent);
                return true;
            case R.id.helpMenu:
                Intent helpIntent = new Intent (set_pay_rate.this, help.class);
                startActivity(helpIntent);
                return true;
            case R.id.signoutMenu:
                Intent signOutIntent = new Intent(set_pay_rate.this, MainActivity.class);
                startActivity(signOutIntent);
                return true;
        }
        return true;
    }
}
