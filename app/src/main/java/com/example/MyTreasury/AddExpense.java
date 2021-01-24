package com.example.MyTreasury;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddExpense extends AppCompatActivity {

    public EditText input_date;
    public EditText input_cause;
    public EditText input_amount;
    public Spinner spinnerCurrency;
    public RadioButton radioCredit;
    public RadioButton radioDebit;
    public Spinner spinnerState;
    public Spinner spinnerPayer;
    public Spinner spinnerCategory;
    public Spinner spinnerSubCategory;
    public EditText input_comments;
    public Button load_button;
    public Button save_button;

    RelativeLayout rel_start_date;
    String startDate;
    Calendar myCalendar = Calendar.getInstance();

    public  DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexpenseview);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String user_id = mUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference(user_id).child("Expenses");

        input_date = findViewById(R.id.input_date);
        input_cause = findViewById(R.id.input_cause);
        input_amount = findViewById(R.id.input_amount);
        spinnerCurrency = findViewById(R.id.spinnerCurrency);
        spinnerCurrency = findViewById(R.id.spinnerCurrency);
        radioCredit = findViewById(R.id.radioCredit);
        radioDebit = findViewById(R.id.radioDebit);
        spinnerState = findViewById(R.id.spinnerState);
        spinnerPayer = findViewById(R.id.spinnerPayer);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerSubCategory = findViewById(R.id.spinnerSubCategory);
        input_comments = findViewById(R.id.input_comments);
        load_button = findViewById(R.id.load_button);
        save_button = findViewById(R.id.save_button);

/*
        rel_start_date = findViewById(R.id.rel_start_date);
        rel_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myCalendar = Calendar.getInstance();

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.FRANCE);
                    myCalendar.setTime(sdf.parse(startDate));
                }catch (Exception e){
                    e.printStackTrace();
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddExpense.this, dateFrom, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
          });
 */


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = mDatabase.push().getKey();
                String date = "test";//input_date.getText().toString();
                String cause = "test";//input_cause.getText().toString();
                String amount = "test";//input_amount.getText().toString();
                String currency = "test";//spinnerCurrency.getSelectedItem().toString();
                String type = "test";
                String state = "test";
                String payer = "test";
                String category = "test";
                String subcategory = "test";
                String comments = "test";//input_comments.getText().toString();
                String img_id = "test";

                Data data = new Data(id, date, cause, amount, currency, type,state, payer, category, subcategory, comments, img_id);
                //.trim()
                mDatabase.child(id).setValue(data);
                Intent IntentBack = new Intent(AddExpense.this, MainActivity.class);
                startActivity(IntentBack);

            }
        });

        //setStartDate();
    }

/*
    private void setStartDate(){
        Calendar startSelectionDate = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.FRANCE);
        startDate = sdf.format(startSelectionDate.getTime());
        input_date.setText(sdf.format(startSelectionDate.getTime()));

    }



    DatePickerDialog.OnDateSetListener dateFrom = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabelFrom();
        }

    };

    private void updateLabelFrom() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.FRANCE);
        startDate = sdf.format(myCalendar.getTime());
        input_date.setText(sdf.format(myCalendar.getTime()));
    }

    public void insertData(final String enter_type, final String enter_status, final String enter_member,
                           final String enter_category, final String enter_link, final String enter_account_name, final String enter_description,
                           final String enter_balance, final String enter_device, final String enter_address,
                           final String enter_parent_cat, final String enter_date){
        final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Toast.makeText(AddExpense.this,response,Toast.LENGTH_LONG).show();
                //empty();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(AddExpense.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("action","submitData");
                params.put("EnterType",enter_type);
                params.put("EnterStatus",enter_status);
                params.put("EnterMember",enter_member);
                params.put("EnterCategory",enter_category);
                params.put("EnterLink",enter_link);
                params.put("EnterAccountNumber",enter_account_name);
                params.put("EnterDescription",enter_description);
                params.put("EnterBalance",enter_balance);
                params.put("EnterDevice",enter_device);
                params.put("EnterAddress",enter_address);
                params.put("EnterParentCat",enter_parent_cat);
                params.put("EnterDate",enter_date);
                return params;
            }
        };

//       int socketTimeOut = 50000;
//       RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//       stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue addQueue = Volley.newRequestQueue(AddExpense.this);
        addQueue.add(stringRequest);
    }

 */
    /*
    public void empty(){
        enter_type.setText("");
        enter_status.setText("");
        enter_member.setText("");
        enter_category.setText("");
        enter_link.setText("");
        enter_account_name.setText("");
        enter_description.setText("");
        enter_balance.setText("");
        enter_device.setText("");
        enter_address.setText("");
        enter_parent_cat.setText("");
    }

     */


}