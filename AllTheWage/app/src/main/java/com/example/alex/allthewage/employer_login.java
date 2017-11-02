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
/**
 * Created by Alex on 10/19/2017.
 */

public class employer_login extends Activity implements
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

        if(i == R.id.LoginButton1) {
            email = EmailField.getText().toString();
            password = PasswordField.getText().toString();

            if(!email.isEmpty() && !password.isEmpty())
            {
                signIn(email, password);

            }
        }   //END OF IF LOGIN_BUTTON

    }//END OF ONCLICK

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            System.out.println("COULD LOG IN");
                            Intent employerHomeIntent = new Intent(employer_login.this, employer_home.class);
                            startActivity(employerHomeIntent);



                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("COULD NOT LOG IN");

                        }
                    }
                });



    }//END OF SIGN UP



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_login);

        findViewById(R.id.LoginButton1).setOnClickListener(this);   //setting the click listner for Login Button

        EmailField = (EditText) findViewById(R.id.employerEmailID);
        PasswordField = (EditText) findViewById(R.id.employerPasswordID);

        mAuth = FirebaseAuth.getInstance();    //initializing Firebase Auth to be used to login

        //DUMMY CALL TO SIGN IN BECAUSE FOR SOME REASON IT DOESN'T WORK ON THE FIRST TRY
        mAuth.signInWithEmailAndPassword(" ", " ");




    }//END OF ONCREATE



}  //END OF FILE

