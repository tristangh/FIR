package com.example.MyTreasury;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import static android.content.ContentValues.TAG;

public class Login extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private Button emailSignInButton;
    private EditText fieldEmail, fieldPassword;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginview);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        emailSignInButton = findViewById(R.id.btn_addMember);


        fieldEmail = findViewById(R.id.edt_email);
        fieldPassword = findViewById(R.id.edt_password);


        // Buttons
        //emailSignInButton.setOnClickListener(this);
        //emailCreateAccountButton.setOnClickListener((View.OnClickListener) this);

//        mBinding.signOutButton.setOnClickListener(this);
//        mBinding.verifyEmailButton.setOnClickListener(this);
//        mBinding.reloadButton.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void onButtonClick(View v) {
        //Log.d("Dashboard error : ", String.valueOf(v.getId()));

        String sFieldPassword = fieldPassword.getText().toString();
        String sFieldEmail = fieldEmail.getText().toString();


        //Login account fill form
        if (v.getId() == R.id.btn_addMember) {

            if (sFieldEmail.matches("") || sFieldPassword.matches("")) {
                Toast.makeText(Login.this, "Fields can not be empty.",
                        Toast.LENGTH_SHORT).show();
            } else {
                signIn(fieldEmail.getText().toString(), fieldPassword.getText().toString());
                //Intent i = new Intent(Login.this, Login.class);
                //startActivity(i);
            }
        }

        if (v.getId() == R.id.btn_register) {


            Intent i = new Intent(Login.this, Register.class);
            startActivity(i);


        }

    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        /*if (!validateForm()) {
            return;
        }

        showProgressBar();*/

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(Login.this, "Login successfull.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            //Open Dashboard view
                            Intent i = new Intent(Login.this, Dashboard.class);
                            startActivity(i);

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                            // [START_EXCLUDE]
                            //checkForMultiFactorFailure(task.getException());
                            // [END_EXCLUDE]

                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            //    mBinding.status.setText(R.string.auth_failed);
                        }
                        //hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }


}
