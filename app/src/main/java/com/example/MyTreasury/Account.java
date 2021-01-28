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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Account extends Fragment {
    private static final String TAG = "Account error";
    private com.example.MyTreasury.Dashboard.OnFragmentInteractionListener mListener;
    private Activity activity;
    Button btn;

    private FirebaseAuth mAuth;
    private TextView asso, school, type, purpose, link, member, status;

    private DatabaseReference myRef;


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

        //Text view init
        asso = v.findViewById(R.id.asso_name);
        school = v.findViewById(R.id.school);
        purpose = v.findViewById(R.id.purpose);
        link = v.findViewById(R.id.link);
        status = v.findViewById(R.id.status);




        //Firebase init
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String user_id = mUser.getUid();
        myRef = FirebaseDatabase.getInstance().getReference(user_id).child("AccountInfo");

        //System.out.println("Data retrived " + myRef.child("link").get());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //get data object
                Data data = dataSnapshot.getValue(Data.class);


                //append last data to textviews
                asso.setText(data.assoName.toString());
                school.setText(data.school.toString());
                purpose.setText(data.purpose.toString());
                link.setText(data.link.toString());
                status.setText(data.status.toString());



                //System.out.println("Data retrived " + data.link);


                //if adapter required :

                /*

                mAdapter = new MyAdapter(payments_list, SingleExpense.class, myRef);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                */
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Expense List add value listener on error", "Failed to read value.", error.toException());
            }
        });




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