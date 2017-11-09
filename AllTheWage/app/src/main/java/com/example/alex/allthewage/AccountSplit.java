package com.example.alex.allthewage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//FIREBASE AUTH
import com.google.firebase.auth.FirebaseAuth;


public class AccountSplit extends Activity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_split);

        //SINGING OUT ANY USER IN CASE THERE
        //IS STILL SOMEONE LOGGED IN
        mAuth.signOut();
        globalVars.GlobalCompanyName = " ";


    }
}

