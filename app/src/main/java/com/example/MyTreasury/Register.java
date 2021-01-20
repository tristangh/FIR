package com.example.MyTreasury;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class Register extends Activity {

    private Button emailCreateAccountButton;
    private EditText fieldEmail, fieldPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerview);
        mAuth = FirebaseAuth.getInstance();

        fieldEmail = findViewById(R.id.edt_email);
        fieldPassword = findViewById(R.id.edt_password);


        //button instance
        emailCreateAccountButton = findViewById(R.id.register_button);

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
                createAccount(fieldEmail.getText().toString(), fieldPassword.getText().toString());

            }


        }



    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        /*
        if (!validateForm()) {
            return;
        }
        */


        //showProgressBar();

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

                            //Open Dashboard view
                            Intent i = new Intent(Register.this, MainActivity.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            assert task.getException().getMessage()!=null;
                            Toast.makeText(Register.this, "Authentication failed :" + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }


}
