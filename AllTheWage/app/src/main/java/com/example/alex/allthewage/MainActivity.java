package com.example.alex.allthewage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button rButton; //employer button
    Button eButton; //employee button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_split);

        rButton = (Button) findViewById(R.id.employerButton);
        eButton = (Button) findViewById(R.id.employeeButton);

        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent split1 = new Intent(MainActivity.this, employer_login.class);
                startActivity(split1);
            }
        });

        eButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent split2 = new Intent(MainActivity.this, employee_login.class);
                startActivity(split2);
            }
        });

    }
}
