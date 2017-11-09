package com.example.alex.allthewage;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.auth.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;


/**
 * Created by Andres Ibarra on 11/9/17.
 */

public class employer_signup extends Activity implements
        View.OnClickListener {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


    private EditText companyName;
    private EditText EmailField;
    private EditText PasswordField;
    private EditText ConfirmPasswordField;

    String enteredEmail;
    String enteredPassword;
    String enteredConfirmPassword;
    String enteredCompanyName;

    @Override
    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.employerSignupButton) {


            //clicked SignUp
            enteredCompanyName = companyName.getText().toString();
            enteredEmail = EmailField.getText().toString();
            enteredPassword = PasswordField.getText().toString();
            enteredConfirmPassword = ConfirmPasswordField.getText().toString();

            if(enteredPassword.contentEquals(enteredConfirmPassword)){
                //ATTEMPT TO SIGNUP
                signUp(enteredCompanyName, enteredEmail, enteredPassword, enteredConfirmPassword);
            }
            else{
                //PASSWORDS AREN'T THE SAME OR EMPTY
            }

        }
    }//END OF ONCLICK

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_signup);


        companyName = (EditText) findViewById(R.id.CompanyNameSignUp);
        EmailField = (EditText) findViewById(R.id.EmailSignUP);
        PasswordField = (EditText) findViewById(R.id.PasswordemployerSignUP);
        ConfirmPasswordField = (EditText) findViewById(R.id.ConfirmPassemployerSignUP);

        findViewById(R.id.employerSignupButton).setOnClickListener(this);

    }//END OF ONCREATE

    //DESCRIPTION:
    //This function will attempt to sign up a user and log them in to set up their database
    public void signUp(final String companyName, String email, String password, String cpassword){

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign up success, update UI with the signed-in user's information
                    FirebaseUser user = auth.getCurrentUser();
                    System.out.println("COULD SIGN UP");

                    //SET UP DATABASE
                    //SETTING UP NULL EMPLOYEE TO BE DELETED LATER ON
                    ref.child("EMPLOYERS").child("Companies").child(user.getUid()).child(companyName).child("eID").child("Name").setValue(" ");
                    ref.child("EMPLOYERS").child("Companies").child(user.getUid()).child(companyName).child("eID").child("Email").setValue(" ");
                    ref.child("EMPLOYERS").child("Companies").child(user.getUid()).child(companyName).child("eID").child("Phone Number").setValue(" ");
                    ref.child("EMPLOYERS").child("Companies").child(user.getUid()).child(companyName).child("eID").child("Age").setValue(0);
                    ref.child("EMPLOYERS").child("Companies").child(user.getUid()).child(companyName).child("eID").child("Social Security").setValue(" ");

                    globalVars.GlobalCompanyName = companyName;

                } else {
                    // If sign up fails, display a message to the user.
                    System.out.println("COULD NOT SIGN UP");

                }
            }
        });


    }//END OF SIGNUP

}
