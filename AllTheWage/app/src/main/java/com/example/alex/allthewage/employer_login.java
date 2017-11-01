package com.example.alex.allthewage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Alex on 10/19/2017.
 */

public class employer_login extends Activity{

    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_login);

        login = (Button) findViewById(R.id.LoginButton1); //# 1 buttons are for employer

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent employerHomeIntent = new Intent(employer_login.this, employer_home.class);
                startActivity(employerHomeIntent);
            }
        });

    }
}
