package com.example.MyTreasury;

import android.annotation.SuppressLint;
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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AccountEdit extends Activity {
    Spinner association_spinner,asso_mem;
    String item;
    Button addMemberButton, updateButton, btn_addMember;
    EditText edt_asso_name,edt_school,edt_purpose,edt_link,edt_status, edt_add_mem;
    RecyclerView memberRecylerView;
    FlexboxLayoutManager layoutManager_member;
    List<String> members_list;
    public static RecyclerView.Adapter MemberAdapter;

    RecyclerView categoryRecylerView;
    List<String> categories_list;
    Button btn_addCategory;
    EditText edt_add_cat;
    FlexboxLayoutManager layoutManager_category;
    public static RecyclerView.Adapter CategoryAdapter;

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
        mDatabase = FirebaseDatabase.getInstance().getReference(user_id);


        //spinner init
        association_spinner = findViewById(R.id.spinner_association);
        asso_mem = findViewById(R.id.spinner_mem);
        memberRecylerView = findViewById(R.id.memberRecylerView);
        categoryRecylerView = findViewById(R.id.categoryRecyclerView);

        setSpinnerItems();
        setSpinnerMem();


        //button init
        addMemberButton = findViewById(R.id.addmember_button);
        updateButton = findViewById(R.id.btn_update);
        btn_addMember = findViewById(R.id.btn_addMember);
        btn_addCategory = findViewById(R.id.btn_addCategory);

        //Members recycler init
        //layoutManager = new GridLayoutManager(this, 6);
        layoutManager_member = new FlexboxLayoutManager(this, FlexDirection.ROW);
        //layoutManager_member.setFlexDirection(FlexDirection.ROW);
        memberRecylerView.setLayoutManager(layoutManager_member);
        members_list = new ArrayList<>();

        //Categories recycler init
        layoutManager_category = new FlexboxLayoutManager(this, FlexDirection.ROW);
        //layoutManager_category.setFlexDirection();
        categoryRecylerView.setLayoutManager(layoutManager_category);
        categories_list = new ArrayList<>();

        //edittext init
        edt_asso_name = findViewById(R.id.edt_asso_name);
        edt_school = findViewById(R.id.edt_school);
        edt_purpose = findViewById(R.id.edt_purpose);
        edt_link = findViewById(R.id.edt_link);
        edt_status = findViewById(R.id.edt_status);
        edt_add_mem = findViewById(R.id.edt_add_mem);
        edt_add_cat = findViewById(R.id.edt_add_cat);

        btn_addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String member = edt_add_mem.getText().toString();

                mDatabase.child("Members").push().setValue(member);
                edt_add_mem.setText("");


            }
        });

        btn_addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String category = edt_add_cat.getText().toString();

                mDatabase.child("Categories").push().setValue(category);
                edt_add_cat.setText("");


            }
        });

        mDatabase.child("Members").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (members_list != null) {
                    members_list.clear();
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String member = snapshot.getValue().toString();
                    members_list.add(member);

                }
                MemberAdapter = new EditAdapter(members_list, mDatabase.child("Members"));
                memberRecylerView.setAdapter(MemberAdapter);
                MemberAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Expense List add value listener on error", "Failed to read value.", error.toException());
            }
        });

        mDatabase.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (categories_list != null) {
                    categories_list.clear();
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String category = snapshot.getValue().toString();
                    categories_list.add(category);

                }
                CategoryAdapter = new EditAdapter(categories_list, mDatabase.child("Categories"));
                categoryRecylerView.setAdapter(CategoryAdapter);
                CategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Expense List add value listener on error", "Failed to read value.", error.toException());
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String id = mDatabase.child("AccountInfo").push().getKey();
                String date = "date actuelle";
                String assoName = edt_asso_name.getText().toString();
                String school = edt_school.getText().toString();
                String purpose = edt_purpose.getText().toString();
                String link = edt_link.getText().toString();
                String status = edt_status.getText().toString();


                Data data = new Data(id, date, assoName,school,purpose,link,status);
                //.trim()
                mDatabase.child("AccountInfo").setValue(data);
                Intent IntentBack = new Intent(AccountEdit.this, Account.class);
                IntentBack.putExtra("frgToLoad", 2);
                startActivity(IntentBack);

            }
        });


    }


//Spinner
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

                item = spinnerArray.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}
