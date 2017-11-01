package com.example.alex.allthewage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Alex on 10/19/2017.
 */

public class employee_login extends Activity {

    Button login;
    Button signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_login);

        login = (Button) findViewById(R.id.LoginButton2);
        signup = (Button) findViewById(R.id.SignUpButton2);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(employee_login.this, employee_signup.class);
                startActivity(signupIntent);
            }
        });


    }
}