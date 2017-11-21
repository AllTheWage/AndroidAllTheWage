package com.example.alex.allthewage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Alex on 11/1/2017.
 * Modified by Jamine Guo on 11/5/2017.
 *
 * List of things to work on:
 *      - Limit input value for only numbers (hoping to be able to assign specific keyboard instead)
 *      - Add exception for null value input click
 *      - Limit input to 2 decimal places
 *      - Remove the "hour" and "hours" distinction?
 *      - Getting and saving current hour/rate from "employee profile"
 *      - Change onCreate text for currentHourText to display current hour initially, 0 if nothing
 *      - Maybe include a "current rate"
 *      - Remove the underline in currentHourText
 *      - Integrate Firebase
 *      - May have a major rehaul depending on what team wants on the geolocation implementation
 *          - Change this to more of an hour counter based on geolocation?
 *          - Or manually input hours?
 */

public class set_hours extends AppCompatActivity {

    private double hours;
    TextView currentHourDisplayText;
    EditText enterHourText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_hours);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        currentHourDisplayText = (TextView) findViewById(R.id.currentHourDisplayText);
        enterHourText = (EditText) findViewById(R.id.enterRateText);

        // Listeners
        Button inputHourButton = (Button) findViewById(R.id.inputHoursButton);
        inputHourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hours = Double.parseDouble(enterHourText.getText().toString());
                // Change text after input
                if(hours == 1.0)
                    currentHourDisplayText.setText("Current set hours is " + hours + " hour");
                else
                    currentHourDisplayText.setText("Current set hours is " + hours + " hours");
                enterHourText.setHint("Success! Reset?");
                enterHourText.setText("");
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

