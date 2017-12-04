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

/*
 * Created by Andres on 11/9/2017.
 *
 *  THINGS TO DO NEXT:
 *      - PREFORM SOME TYPE OF ALERT TO LET THE USER KNOW WHETHER THEY SIGNED UP SUCCESSFULLY OR NOT
 *
 *
 *
 *
 */

public class employee_signup extends Activity implements
        View.OnClickListener {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference employerref = ref.child("EMPLOYERS").child("Companies");
    DatabaseReference employeeref = ref.child("EMPLOYEES");


    private EditText CompanyNameField;
    private EditText NameField;
    private EditText AgeField;
    private EditText PhoneNumberField;
    private EditText EmailField;
    private EditText PasswordField;
    private EditText ConfirmPasswordField;

    String enteredCompanyName;
    String enteredName;
    String enteredAge;
    String enteredPhoneNumber;
    String enteredEmail;
    String enteredPassword;
    String enteredConfirmPassword;
    String employerID = " ";


    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.employeeSignup){
            //ATTEMPT TO SIGNUP
            enteredCompanyName = CompanyNameField.getText().toString();
            enteredName = NameField.getText().toString();
            enteredAge = AgeField.getText().toString();
            enteredPhoneNumber = PhoneNumberField.getText().toString();
            enteredEmail = EmailField.getText().toString();
            enteredPassword = PasswordField.getText().toString();
            enteredConfirmPassword = ConfirmPasswordField.getText().toString();

            if(enteredPassword.contentEquals(enteredConfirmPassword)){
                //ATTEMPT TO SIGNUP
                signUp(enteredCompanyName, enteredName, enteredAge, enteredPhoneNumber, enteredEmail, enteredPassword);
            }
            else{
                //PASSWORDS AREN'T THE SAME OR EMPTY
            }


        }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_signup);

        CompanyNameField = (EditText) findViewById(R.id.companyNameET);
        NameField = (EditText) findViewById(R.id.nameET);
        AgeField = (EditText) findViewById(R.id.ageET);
        PhoneNumberField = (EditText) findViewById(R.id.phoneET);
        EmailField = (EditText) findViewById(R.id.emailET);
        PasswordField = (EditText) findViewById(R.id.passET);
        ConfirmPasswordField = (EditText) findViewById(R.id.confirmPassET);

        findViewById(R.id.employeeSignup).setOnClickListener(this);   //setting the click listner for Sign Up Button

    }


    public void signUp(final String CompanyName, final String name, final String age, final String phone, final String email, final String password){

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign up success


                    final FirebaseUser user = auth.getCurrentUser();
                    System.out.println("COULD SIGN UP");
                    employeeref = ref.child("EMPLOYEES").child(CompanyName).child(user.getUid());

                    //SET UP DATABASE
                    //SETTING UP EMPLOYEE SIDE FIRST
                    employeeref.child("Paycheck Amount").setValue(0);
                    employeeref.child("Name").setValue(name);
                    employeeref.child("Hours Worked").setValue(0);
                    employeeref.child("Pay Rate").setValue(0);


                    //SET UP DATABASE
                    //SETTING UP EMPLOYER SIDE
                    employerref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean foundIt = false;
                            //iterating through all the employerIDs
                            for(DataSnapshot employerIDs: dataSnapshot.getChildren())
                            {
                                //iterating now through all the companyNames
                                for(DataSnapshot allcompanynames: employerIDs.getChildren()){

                                    if(allcompanynames.getKey().contentEquals(CompanyName)){
                                        employerID = employerIDs.getKey();
                                        foundIt = true;
                                        break;

                                    }
                                }//end of COMPANYNAME ITERATION
                              if(foundIt){
                                  break;
                              }
                            }//end of EMPLOYERID ITERATION



                            employeeref.child("EmployerID").setValue(employerID);

                            employerref = ref.child("EMPLOYERS").child("Companies").child(employerID).child(CompanyName).child(user.getUid());
                            employerref.child("Name").setValue(name);
                            employerref.child("Age").setValue(age);
                            employerref.child("Email").setValue(email);
                            employerref.child("Phone Number").setValue(phone);
                            employerref.child("Social Security").setValue("***-**-1111");

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });//END OF ADDLISTENERFORSINGLEVALUEEVENT



                    //DONE SIGNINGUUP
                    globalVars.GlobalCompanyName = CompanyName;

                } else {
                    // If sign up fails, display a message to the user.
                    System.out.println("COULD NOT SIGN UP");

                }
            }
        });



    }


}
