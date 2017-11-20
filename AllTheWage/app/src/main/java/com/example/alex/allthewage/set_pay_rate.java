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
 * Modified by Jamine Guo on 11/5/2017
 *
 * List of things to work on:
 *      - Limit input value for only numbers (hoping to be able to assign specific keyboard instead)
 *      - Add exception for null value input click
 *      - Limit input to 2 decimal places
 *      - Getting and saving current hour/rate from "employee profile"
 *      - Change onCreate text for currentRateText to display current rate initially, 0 if nothing
 *      - Maybe include a "current hour"
 *      - Remove the underline in currentRateText
 *      - Integrate Firebase
 */

public class set_pay_rate extends AppCompatActivity {

    private double payRate;
    TextView currentRateDisplayText;
    EditText enterRateText;

    // Get Functions
    double getPayRate(){ return payRate; }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_pay_rate);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);


        currentRateDisplayText = (TextView) findViewById(R.id.currentRateDisplayText);
        enterRateText = (EditText) findViewById(R.id.enterRateText);

        // Listeners
        Button inputRatesButton = (Button) findViewById(R.id.inputRatesButton);
        inputRatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payRate = Double.parseDouble(enterRateText.getText().toString());
                // Change text after input
                currentRateDisplayText.setText("Current pay-rate is $" + payRate + " per hour");
                enterRateText.setHint("Success! Reset?");
                enterRateText.setText("");
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
