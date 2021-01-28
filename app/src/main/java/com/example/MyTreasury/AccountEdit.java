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

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AccountEdit extends Activity {
    Spinner association_spinner,asso_mem;
    Button addMemberButton, updateButton, btn_addMember;
    EditText edt_asso_name,edt_school,edt_purpose,edt_link,edt_status, edt_add_mem;
    RecyclerView memberRecylerView;
    FlexboxLayoutManager layoutManager_member;
    List<Data> members_list;
    public static RecyclerView.Adapter MemberAdapter;
    String selectedSpinnerType;

    RecyclerView categoryRecylerView;
    List<Data> categories_list;
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




        //button init
        addMemberButton = findViewById(R.id.addmember_button);
        updateButton = findViewById(R.id.btn_update);
        btn_addMember = findViewById(R.id.btn_addMember);
        btn_addCategory = findViewById(R.id.btn_addCategory);

        //Members recycler init
        //layoutManager = new GridLayoutManager(this, 6);
        layoutManager_member = new FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP);
        //layoutManager_member.setFlexDirection(FlexDirection.ROW);
        memberRecylerView.setLayoutManager(layoutManager_member);
        members_list = new ArrayList<Data>();

        //Categories recycler init
        layoutManager_category = new FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP);
        //layoutManager_category.setFlexDirection();
        categoryRecylerView.setLayoutManager(layoutManager_category);
        categories_list = new ArrayList<Data>();

        //edittext init
        edt_asso_name = findViewById(R.id.edt_asso_name);
        edt_school = findViewById(R.id.edt_school);
        edt_purpose = findViewById(R.id.edt_purpose);
        edt_link = findViewById(R.id.edt_link);
        edt_status = findViewById(R.id.edt_status);
        edt_add_mem = findViewById(R.id.edt_add_mem);
        edt_add_cat = findViewById(R.id.edt_add_cat);

        getAndSetIncomingIntent();

        setSpinnerType();
        //setSpinnerMem();


        btn_addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_member = mDatabase.child("Members").push().getKey();
                String member_name = edt_add_mem.getText().toString();
                Data member_data = new Data(id_member, member_name);
                mDatabase.child("Members").child(id_member).setValue(member_data);
                //mDatabase.child("Members").push().setValue(member_name);
                edt_add_mem.setText("");

            }
        });

        btn_addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_category = mDatabase.child("Categories").push().getKey();
                String cat_name = edt_add_cat.getText().toString();
                Data cat_data = new Data(id_category, cat_name);
                mDatabase.child("Categories").child(id_category).setValue(cat_data);
                //mDatabase.child("Members").push().setValue(member_name);
                edt_add_mem.setText("");


            }
        });

        mDatabase.child("Members").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (members_list != null) {
                    members_list.clear();
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Data data = snapshot.getValue(Data.class);
                    members_list.add(data);
                    //String member = snapshot.getValue().toString();
                    //String id = mDatabase.child("Members").push().getKey();
                    //members_list.add(member);

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

                    Data data = snapshot.getValue(Data.class);
                    categories_list.add(data);
                    //String category = snapshot.getValue().toString();
                    //categories_list.add(category);


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


                String id_account = mDatabase.child("AccountInfo").push().getKey();
                String date = "date actuelle";
                String assoName = edt_asso_name.getText().toString();
                String school = edt_school.getText().toString();
                String type = selectedSpinnerType;
                String purpose = edt_purpose.getText().toString();
                String link = edt_link.getText().toString();
                String status = edt_status.getText().toString();

                Data account_data = new Data(id_account, date, assoName,school,type, purpose,link,status);
                //.trim()
                mDatabase.child("AccountInfo").setValue(account_data);

                Intent IntentBack = new Intent(AccountEdit.this, MainActivity.class);
                IntentBack.putExtra("frgToLoad", 3);
                startActivity(IntentBack);

            }
        });


    }


    //get intents from account
    private void getAndSetIncomingIntent(){
        if (getIntent().hasExtra("asso")) {
            Log.d(TAG, getIntent().getStringExtra("asso"));
            edt_asso_name.setText(getIntent().getStringExtra("asso"));
        }
        if (getIntent().hasExtra("school")) {
            edt_school.setText(getIntent().getStringExtra("school"));
        }
        if (getIntent().hasExtra("purpose")) {
            edt_purpose.setText(getIntent().getStringExtra("purpose"));
        }
        if (getIntent().hasExtra("link")) {
            edt_link.setText(getIntent().getStringExtra("link"));
        }
        if (getIntent().hasExtra("status")) {
            edt_status.setText(getIntent().getStringExtra("status"));
        }
        if (getIntent().hasExtra("type")) {
            selectedSpinnerType = getIntent().getStringExtra("type");
        }

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



}



