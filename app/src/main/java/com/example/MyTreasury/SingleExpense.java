package com.example.MyTreasury;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingleExpense extends AppCompatActivity {
    String id;
    String date;
    String cause;
    String balance;
    String state;
    String payer;
    String cat;
    String subcat;
    String comments;
    String img_id_str = "facture_1.jpg";

    TextView txt_date;
    TextView txt_cause;
    TextView txt_balance;
    TextView txt_state;
    TextView txt_payer;
    TextView txt_cat;
    TextView txt_subcat;
    TextView txt_com;
    ImageView img_invoice;

    Button button_delete;
    FirebaseAuth mAuth;
    private DatabaseReference database_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleexepense_view);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String user_id = mUser.getUid();
        database_ref = FirebaseDatabase.getInstance().getReference(user_id).child("Expenses");

        getIncomingIntent();
        txt_date = findViewById(R.id.txt_date);
        txt_cause = findViewById(R.id.txt_cause);
        txt_balance = findViewById(R.id.txt_balance);
        txt_state = findViewById(R.id.txt_state);
        txt_payer = findViewById(R.id.txt_payer);
        txt_cat = findViewById(R.id.txt_cat);
        txt_subcat = findViewById(R.id.txt_subcat);
        txt_com = findViewById(R.id.txt_com);
        img_invoice = findViewById(R.id.img_invoice);
        button_delete = findViewById(R.id.button_delete);

        txt_date.setText(date);
        txt_cause.setText(cause);
        txt_balance.setText(balance);
        txt_state.setText(state);
        txt_payer.setText(payer);
        txt_cat.setText(cat);
        txt_subcat.setText(subcat);
        //txt_com.setText(comments);
        //img_invoice.setImageResource(Integer.parseInt(img_id_str));

        img_invoice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent MyIntent = new Intent(SingleExpense.this, Invoice.class);
                MyIntent.putExtra("Image id", img_id_str);
                startActivity(MyIntent);
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database_ref.child(id).removeValue();
                Intent IntentBack = new Intent(SingleExpense.this, ExpenseList.class);
                startActivity(IntentBack);
                //DataList.remove(position);

                //notifyItemRemoved(position);

                //notifyItemRangeChanged(position, DataList.size());
            }
        });

    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("Id")) {
            id = getIntent().getStringExtra("Id");
        }
        if (getIntent().hasExtra("Date")) {
            date = getIntent().getStringExtra("Date");
        }
        if (getIntent().hasExtra("Cause")) {
            cause = getIntent().getStringExtra("Cause");
        }
        if (getIntent().hasExtra("Amount")) {
            balance = getIntent().getStringExtra("Amount");
        }
        if (getIntent().hasExtra("State")) {
            state = getIntent().getStringExtra("State");
        }
        if (getIntent().hasExtra("Payer")) {
            payer = getIntent().getStringExtra("Payer");
        }
        if (getIntent().hasExtra("Category")) {
            cat = getIntent().getStringExtra("Category");
        }
        if (getIntent().hasExtra("Sub-category")) {
            subcat = getIntent().getStringExtra("Sub-category");
        }/*
        if (getIntent().hasExtra("Invoice file")) {
            img_id_str = getIntent().getStringExtra("Invoice file");
        }
        if (getIntent().hasExtra("Comments")) {
            comments = getIntent().getStringExtra("Comments");
        }
        */

    }
}


