package com.example.MyTreasury;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SingleExpense extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.espenses_singleexpenseview);

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

    }

    private void getIncomingIntent(){
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


