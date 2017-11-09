package com.example.alex.allthewage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.*;

//FIREBASE AUTH
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Alex on 10/19/2017.
 */

public class employee_login extends Activity implements
        View.OnClickListener{

    private EditText EmailField;
    private EditText PasswordField;

    String email;
    String password;

    //DECLARING FIREBASE AUTH VARIABLE
    private FirebaseAuth mAuth;



    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.LoginButton2) {
            email = EmailField.getText().toString();
            password = PasswordField.getText().toString();

            if(!email.isEmpty() && !password.isEmpty())
            {
                signIn(email, password);

            }
        }   //END OF IF LOGIN_BUTTON
        if(i == R.id.SignUpButton2) {
            signUp();



        }

    }//END OF ONCLICK



    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("COULD LOG IN");
                            Intent employerHomeIntent = new Intent(employee_login.this, employee_home.class);
                            startActivity(employerHomeIntent);

                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("COULD NOT LOG IN");

                        }
                    }
                });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_login);



        findViewById(R.id.LoginButton2).setOnClickListener(this);   //setting the click listner for Login Button
        findViewById(R.id.SignUpButton2).setOnClickListener(this);   //setting the click listner for Login Button


        EmailField = (EditText) findViewById(R.id.employeeEmailID);
        PasswordField = (EditText) findViewById(R.id.employeePasswordID);

       mAuth = FirebaseAuth.getInstance();    //initializing Firebase Auth to be used to login


        //DUMMY CALL TO SIGN IN BECAUSE FOR SOME REASON IT DOESN'T WORK ON THE FIRST TRY
        mAuth.signInWithEmailAndPassword(" ", " ");

    }


    public void signUp(){
        Intent employerSignUpIntent = new Intent(employee_login.this, employee_signup.class);
        startActivity(employerSignUpIntent);
    }
}