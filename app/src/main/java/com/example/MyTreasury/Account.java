package com.example.MyTreasury;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class Account extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView emailTv;

    Spinner association_spinner,asso_mem;
    String item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_view);

        //for user information
        //emailTv = findViewById(R.id.email_tv);
        //emailTv.setText(mAuth.getCurrentUser().getEmail());


        association_spinner = findViewById(R.id.spinner_association);
        asso_mem = findViewById(R.id.spinner_mem);

        setSpinnerItems();
        setSpinnerMem();

    }

    private void setSpinnerItems(){

        final ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Sportive");
        spinnerArray.add("Charity");
        spinnerArray.add("Others");

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        association_spinner.setAdapter(spinnerArrayAdapter);


        association_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

                item = spinnerArray.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void setSpinnerMem() {

        final ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Nithya");
        spinnerArray.add("Tristan");
        spinnerArray.add("Amine");
        spinnerArray.add("Paul");
        spinnerArray.add("Jean");

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        asso_mem.setAdapter(spinnerArrayAdapter);


        asso_mem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

                item = spinnerArray.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }




    public void onButtonClick(View v) {
        //Log.d("Dashboard error : ", String.valueOf(v.getId()));


        //sign out button


        if (v.getId() == R.id.btn_logout) {


            try {
                //signing out
                mAuth.signOut();
                Log.w(TAG, "signed out successfully");

                Toast.makeText(Account.this, "Signed out successfully",
                        Toast.LENGTH_SHORT).show();

                //launch Login activity
                Intent i = new Intent(Account.this, Login.class);
                startActivity(i);

            } catch (Exception e) {
                // an error

            }

        }

        if (v.getId() == R.id.btn_edit) {


            try {
                Intent i = new Intent(Account.this, AccountEdit.class);
                startActivity(i);

            } catch (Exception e) {
                // an error

            }


        }
    }
}