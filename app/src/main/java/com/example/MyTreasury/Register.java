package com.example.MyTreasury;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Register extends Activity {

    private Button emailCreateAccountButton;
    private EditText fieldEmail, fieldPassword;
    private FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    Spinner association_spinner;
    String selectedSpinnerType;
    EditText edt_asso_name,edt_school,edt_purpose,edt_link,edt_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerview);
        mAuth = FirebaseAuth.getInstance();

        fieldEmail = findViewById(R.id.edt_email);
        fieldPassword = findViewById(R.id.edt_password);


        //button instance
        emailCreateAccountButton = findViewById(R.id.register_button);

        //edittext init
        edt_asso_name = findViewById(R.id.edt_asso_name);
        edt_school = findViewById(R.id.edt_school);
        edt_purpose = findViewById(R.id.edt_purpose);
        edt_link = findViewById(R.id.edt_link);
        edt_status = findViewById(R.id.edt_status);

        //spinner init
        association_spinner = findViewById(R.id.spinner_association_type);
        setSpinnerType();

    }

    public void onButtonClick(View v) {
        //Log.d("Dashboard error : ", String.valueOf(v.getId()));

        String sFieldPassword = fieldPassword.getText().toString();
        String sFieldEmail = fieldEmail.getText().toString();

        //Create account fill form
        if (v.getId() == R.id.register_button) {


            if (sFieldEmail.matches("") || sFieldPassword.matches("")) {
                Toast.makeText(Register.this, "Fields can not be empty.",
                        Toast.LENGTH_SHORT).show();
            } else if (sFieldPassword.length() < 5) {
                Toast.makeText(Register.this, "Password is too short.",
                        Toast.LENGTH_SHORT).show();
            } else {

                //create the account
                createAccount(fieldEmail.getText().toString(), fieldPassword.getText().toString());




            }


        }



    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);



        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            assert user != null;
                            Toast.makeText(Register.this, "Account created :" + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();

                            //add the new info to the accountInfo


                            String user_id = user.getUid();
                            mDatabase = FirebaseDatabase.getInstance().getReference(user_id).child("AccountInfo");

                            String id = mDatabase.push().getKey();
                            String date = "date actuelle";
                            String assoName = edt_asso_name.getText().toString();
                            String school = edt_school.getText().toString();
                            String purpose = edt_purpose.getText().toString();
                            String link = edt_link.getText().toString();
                            String status = edt_status.getText().toString();
                            String type = selectedSpinnerType;


                            Data data = new Data(id, date, assoName,school,type,purpose,link);
                            //.trim()
                            mDatabase.setValue(data);

                            //Open Dashboard view
                            Intent i = new Intent(Register.this, MainActivity.class);
                            i.putExtra("frgToLoad", 1);
                            startActivity(i);


                        } else {
                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            assert task.getException().getMessage()!=null;
                            Toast.makeText(Register.this, "Authentication failed :" + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();

                            Intent i = new Intent(Register.this, Login.class);
                            i.putExtra("frgToLoad", 1);
                            startActivity(i);
                            //updateUI(null);
                        }

                    }
                });
        // [END create_user_with_email]


    }

    //Spinner
    private void setSpinnerType(){

        final ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Sportive");
        spinnerArray.add("Charity");
        spinnerArray.add("Professional");
        spinnerArray.add("Cultural");
        spinnerArray.add("Artistic");
        spinnerArray.add("International");
        spinnerArray.add("Humanitarian");
        spinnerArray.add("Playful");
        spinnerArray.add("Robotics");
        spinnerArray.add("Sciences");
        spinnerArray.add("Other");
;

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        association_spinner.setAdapter(spinnerArrayAdapter);


        association_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

                selectedSpinnerType = spinnerArray.get(position);
                Log.d(TAG, "Selected Item:" + selectedSpinnerType);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}
