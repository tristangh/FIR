package com.example.MyTreasury;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpenseList extends AppCompatActivity {
    ArrayList<String[]> transactions_list;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView txt;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenseslistview);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ExpenseData");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                Log.d("Expense List add value listener on data change", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Expense List add value listener on error", "Failed to read value.", error.toException());
            }
        });

        InputStream InputStreamReader = null;
        Scanner in = null;

        transactions_list = new ArrayList<String[]>();
        String line;

        InputStreamReader = (getResources().openRawResource(R.raw.database));
        in = new Scanner(InputStreamReader);
        in.useDelimiter("\\n");

        while (in.hasNext()) {
            line = in.next();
            System.out.println(line);
            String[] line_array = line.split(",");
            //singleRow_into_list
            transactions_list.add(line_array);
        }
        in.close();
        transactions_list.remove(0);
        txt = findViewById(R.id.txt);

        //System.out.println(transaction_list);
        //Toast.makeText(BandActivity.this, transaction_list.get(0), Toast.LENGTH_LONG).show();
        //String data = transactions_list.get(0).toString() + "\n" + transactions_list.get(1).toString() + "\n" + transactions_list.get(2).toString() + "\n";

        txt.setText("Transactions record");

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        mRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(transactions_list, SingleExpense.class);
        mRecyclerView.setAdapter(mAdapter);
    }


}
