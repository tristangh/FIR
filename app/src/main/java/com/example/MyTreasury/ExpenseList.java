package com.example.MyTreasury;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ExpenseList extends Fragment {
    private OnFragmentInteractionListener mListener;
    private Activity activity;

    ArrayList<String[]> transactions_list;
    List<Data> payments_list;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView txt;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //change title
        if (mListener !=null) {
            mListener.OnFragmentInteractionChangeTitle("Expenses List");
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.expenses_listview, container, false);

        txt = v.findViewById(R.id.txt);
        txt.setText("Transactions record");

        mRecyclerView = (RecyclerView) v.findViewById(R.id.mRecyclerView);

        mRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(v.getContext());

        mRecyclerView.setLayoutManager(layoutManager);

        payments_list = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String user_id = mUser.getUid();
        myRef = FirebaseDatabase.getInstance().getReference(user_id).child("Expenses");

        ChildEventListener mChildListener = new ChildEventListener() {


            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Data data = snapshot.getValue(Data.class);
                    payments_list.add(data);

                }
                mAdapter = new MyAdapter(payments_list, SingleExpense.class, myRef);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Data data = snapshot.getValue(Data.class);
                    payments_list.remove(data);
                }

                mAdapter = new MyAdapter(payments_list, SingleExpense.class, myRef);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                //
                //
                //notifyItemRemoved(position);

                //notifyItemRangeChanged(position, DataList.size());

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Expense List add value listener on error", "Failed to read value.", error.toException());
            }

        };
        myRef.addValueEventListener((ValueEventListener) mChildListener);


        System.out.println("Payment list : " + payments_list);


        return v;
    }

    public interface OnFragmentInteractionListener {
        void OnFragmentInteractionChangeTitle(String title);
    }


}