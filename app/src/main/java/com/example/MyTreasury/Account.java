package com.example.MyTreasury;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Account extends Fragment {
    private static final String TAG = "Account error";
    private com.example.MyTreasury.Dashboard.OnFragmentInteractionListener mListener;
    private Activity activity;
    Button btn;

    private FirebaseAuth mAuth;
    private TextView emailTv;

    Spinner association_spinner,asso_mem;
    String item;
    Button logoutButton, editButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //change title
        if (mListener !=null) {
            mListener.OnFragmentInteractionChangeTitle("Account");
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.account_view, container, false);

        /* ------------------------- insert code */




        mAuth = FirebaseAuth.getInstance();



        //for user information
        //emailTv = findViewById(R.id.email_tv);
        //emailTv.setText(mAuth.getCurrentUser().getEmail());


        association_spinner = v.findViewById(R.id.spinner_association);
        asso_mem = v.findViewById(R.id.spinner_mem);
        logoutButton = v.findViewById(R.id.btn_logout);
        editButton = v.findViewById(R.id.btn_edit);

        setSpinnerItems();
        setSpinnerMem();


        //log out button

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //signing out
                    mAuth.signOut();
                    Log.w(TAG, "signed out successfully");

                    Toast.makeText(getContext(), "Signed out successfully", Toast.LENGTH_SHORT).show();


                    //launch Login activity

                    Intent i = new Intent(getContext(), Login.class);
                    startActivity(i);

                } catch (Exception e) {
                    // an error

                }

            }
        });


        //edit button
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(getContext(), AccountEdit.class);
                    startActivity(i);

                } catch (Exception e) {
                    // an error

                }
            }
        });



        /* -------------------------------end of code */
        //return fragment
        return v;

    }




    private void setSpinnerItems(){

        final ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Sportive");
        spinnerArray.add("Charity");
        spinnerArray.add("Others");

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(),
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

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerArray);
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











    public interface OnFragmentInteractionListener {
        void OnFragmentInteractionChangeTitle(String title);
    }

}