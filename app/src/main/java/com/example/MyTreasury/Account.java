package com.example.MyTreasury;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;


public class Account extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView emailTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_view);

        emailTv = findViewById(R.id.email_tv);
        emailTv.setText(mAuth.getCurrentUser().getEmail());

    }

    public void onButtonClick(View v) {
        //Log.d("Dashboard error : ", String.valueOf(v.getId()));
        //sign out button
        if (v.getId() == R.id.logout_button) {


            try {
                //signing out
                mAuth.signOut();
                Log.w(TAG, "signed out successfully");

                Toast.makeText(Account.this, "Signed out successfully",
                        Toast.LENGTH_SHORT).show();

                //launch Login activity
                Intent i = new Intent(Account.this, Login.class);
                startActivity(i);

            } catch (Exception e){
                // an error

            }

        }

    }
}