package com.example.alex.allthewage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

/**
 * Created by Alex on 11/1/2017.
 */

public class paychecks extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paychecks);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.paycheck_choices, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.homeMenu:
                Intent empIntent = new Intent(paychecks.this, employer_home.class);
                startActivity(empIntent);
                return true;
            case R.id.employeesMenu:
                Intent paycheckIntent = new Intent(paychecks.this, employee_info.class);
                startActivity(paycheckIntent);
                return true;
            case R.id.sethoursMenu:
                Intent setHoursIntent = new Intent (paychecks.this, set_hours.class);
                startActivity(setHoursIntent);
                return true;
            case R.id.setpayrateMenu:
                Intent setPayIntent = new Intent (paychecks.this, set_pay_rate.class);
                startActivity(setPayIntent);
                return true;
            case R.id.forumMenu:
                Intent forumIntent = new Intent (paychecks.this, forum.class);
                startActivity(forumIntent);
                return true;
            case R.id.helpMenu:
                Intent helpIntent = new Intent (paychecks.this, help.class);
                startActivity(helpIntent);
                return true;
            case R.id.signoutMenu:
                Intent signOutIntent = new Intent(paychecks.this, MainActivity.class);
                startActivity(signOutIntent);
                return true;
        }
        return true;
    }
}
