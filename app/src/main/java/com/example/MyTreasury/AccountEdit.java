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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AccountEdit extends Activity {
    Spinner association_spinner,asso_mem;
    String selectedSpinnerType;
    Button addMemberButton, updateButton;
    EditText edt_asso_name,edt_school,edt_purpose,edt_link,edt_status;
    RecyclerView memberRecylcer;
    RecyclerView.LayoutManager layoutManager;

    //firebase
    public DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_editview);

        //Firebase init
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String user_id = mUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference(user_id).child("AccountInfo");


        //spinner init
        association_spinner = findViewById(R.id.spinner_association);
        asso_mem = findViewById(R.id.spinner_mem);
        memberRecylcer = findViewById(R.id.memberRecylcer);

        setSpinnerType();
        setSpinnerMem();


        //button init
        addMemberButton = findViewById(R.id.addmember_button);
        updateButton = findViewById(R.id.btn_update);

        //recycler init
        layoutManager = new LinearLayoutManager(this);
        memberRecylcer.setLayoutManager(layoutManager);

        //edittext init
        edt_asso_name = findViewById(R.id.edt_asso_name);
        edt_school = findViewById(R.id.edt_school);
        edt_purpose = findViewById(R.id.edt_purpose);
        edt_link = findViewById(R.id.edt_link);
        edt_status = findViewById(R.id.edt_status);




        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(AccountEdit.this, AddMember.class);
                startActivity(i);

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String id = mDatabase.push().getKey();
                String date = "date actuelle";
                String assoName = edt_asso_name.getText().toString();
                String school = edt_school.getText().toString();
                String type = selectedSpinnerType;
                String purpose = edt_purpose.getText().toString();
                String link = edt_link.getText().toString();
                String status = edt_status.getText().toString();


                Data data = new Data(id, date, assoName,school,type,purpose,link,status);
                //.trim()
                mDatabase.setValue(data);
                Intent IntentBack = new Intent(AccountEdit.this, Account.class);
                IntentBack.putExtra("frgToLoad", 2);
                startActivity(IntentBack);

            }
        });


    }


    //Spinner
    private void setSpinnerType(){

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
               // ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

                selectedSpinnerType = spinnerArray.get(position);
                Log.d(TAG, "Selected Item:" + selectedSpinnerType);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void setSpinnerMem(){

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

                selectedSpinnerType = spinnerArray.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}
