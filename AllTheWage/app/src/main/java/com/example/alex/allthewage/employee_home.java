package com.example.alex.allthewage;

/**
 * Created by andiba on 11/2/17.
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

public class employee_home extends AppCompatActivity  {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.employee_home_toolbar);
        setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.employeemenu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){

            //NEED TO DECIDE WHAT TO PUT IN THE EMPLOYEE MENU!!!
            case R.id.employeesMenu:
                System.out.println("you clicked the employees tab");
                return true;
            case R.id.signoutMenu:
                Intent signOutIntent = new Intent(employee_home.this, MainActivity.class);
                startActivity(signOutIntent);
                return true;
        }


        return true;
    }
}
