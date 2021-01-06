package com.example.MyTreasury;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpenseList extends AppCompatActivity {
    ArrayList<String[]> transactions_list;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenseslistview);

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
            /*
            Date : line_array[0]
            Cause : line_array[1]
            Balance : line_array[2]
            Currency : line_array[3]
            Type (debit, credit) : line_array[4]
            State (done, pending) : line_array[5]
            The payer (Dropdown with member list, association and Guest) : line_array[6]
            Category : line_array[7]
            Sub-category : line_array[8]
            Invoice file (image / pdf) : line_array[9]
            Comments  : line_array[10]
             */
            /*
            if (transaction_list.contains(line_array[0])) {
                System.out.println(line_array[0].toString() + " is already in the list");
            }
            else {transaction_list.add(line_array[0]);}
             */
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
