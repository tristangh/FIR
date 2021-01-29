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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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

public class Account extends Fragment {
    private static final String TAG = "Account error";
    private com.example.MyTreasury.Dashboard.OnFragmentInteractionListener mListener;
    private Activity activity;

    private FirebaseAuth mAuth;
    private TextView asso, school, type, purpose, link, email, status;
    private Data data;

    public DatabaseReference myRef;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    RecyclerView MembersRecycler;
    RecyclerView CategoriesRecycler;

    RecyclerView.Adapter MembersAdapter;
    RecyclerView.Adapter CategoriesAdapter;

    public static List<Data> members_list;
    public static List<Data> categories_list;


    Spinner asso_mem;
    String selectedSpinnerMem;
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
        type = v.findViewById(R.id.asso_type);
        purpose = v.findViewById(R.id.purpose);
        link = v.findViewById(R.id.link);
        MembersRecycler = v.findViewById(R.id.MembersRecycler);
        CategoriesRecycler = v.findViewById(R.id.CategoriesRecycler);

        //Firebase init
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();
        String user_id = mUser.getUid();


        myRef = FirebaseDatabase.getInstance().getReference(user_id);

        myRef.child("Members").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (members_list != null) {
                    members_list.clear();
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Data data = snapshot.getValue(Data.class);
                    members_list.add(data);

                }

                MembersAdapter = new EditAdapter(members_list, myRef.child("Members"), R.layout.flexbox);
                MembersRecycler.setAdapter(MembersAdapter);
                MembersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("Categories").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (categories_list != null) {
                    categories_list.clear();
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Data data = snapshot.getValue(Data.class);
                    categories_list.add(data);

                }

                CategoriesAdapter = new EditAdapter(categories_list, myRef.child("Categories"), R.layout.flexbox);
                CategoriesRecycler.setAdapter(CategoriesAdapter);
                CategoriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("AccountInfo")){

                    myRef.child("AccountInfo").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //check if accountInfo exists

                            //get data object
                            data = dataSnapshot.getValue(Data.class);

                            //append last data to textviews
                            if (data.assoName != null) {
                                asso.setText(data.assoName.toString());
                            }
                            if (data.school != null) {
                                school.setText(data.school.toString());
                            }
                            if (data.type != null) {
                                type.setText(data.type.toString());
                            }
                            if (data.purpose != null) {
                                purpose.setText(data.purpose.toString());
                            }
                            if (data.link != null) {
                                link.setText(data.link.toString());
                            }
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
                            Log.w("Account value listener on error", "Failed to read value.", error.toException());
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "No account information yet, please use edit button to add them.", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //email init
        email = v.findViewById(R.id.tv_email);
        email.setText(mUser.getEmail());

        //System.out.println("Data retrived " + myRef.child("link").get());

        //for user information
        //emailTv = findViewById(R.id.email_tv);
        //emailTv.setText(mAuth.getCurrentUser().getEmail());


        logoutButton = v.findViewById(R.id.btn_logout);
        editButton = v.findViewById(R.id.btn_edit);


        //log out button

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //signing out
                    mAuth.signOut();
                    Log.w(TAG, "signed out successfully");

                    Toast.makeText(getContext(), "Signed out successfully", Toast.LENGTH_SHORT).show();

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
                    i.putExtra("asso", data.assoName);
                    i.putExtra("school", data.school);
                    i.putExtra("type", data.type);
                    i.putExtra("purpose", data.purpose);
                    i.putExtra("link", data.link);
                    i.putExtra("email", mUser.getEmail().toString());


                    startActivity(i);

                } catch (Exception e) {
                }
            }
        });

        return v;

    }


    public interface OnFragmentInteractionListener {
        void OnFragmentInteractionChangeTitle(String title);
    }


}