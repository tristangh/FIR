package com.example.MyTreasury;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddExpense extends AppCompatActivity {
    EditText enter_type,enter_status,enter_member,enter_category,enter_link,
            enter_account_name,enter_description,enter_balance,enter_device,enter_address,enter_parent_cat;
    TextView enter_date;

    Button button;
    RelativeLayout rel_start_date;
    String startDate;
    Calendar myCalendar = Calendar.getInstance();
    String url = "https://script.google.com/macros/s/AKfycbzFHvaFjsZqWWPAAaCfsc79NJkyEKD7pUs0fewvivog-vAUUd-BFIPV/exec";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet);

        enter_type = findViewById(R.id.enter_type);
        enter_date = findViewById(R.id.enter_date);
        enter_status = findViewById(R.id.enter_status);
        enter_member = findViewById(R.id.enter_member);
        enter_category = findViewById(R.id.enter_category);
        enter_link = findViewById(R.id.enter_link);
        enter_account_name = findViewById(R.id.enter_account_name);
        enter_description = findViewById(R.id.enter_description);
        enter_balance = findViewById(R.id.enter_balance);
        enter_device = findViewById(R.id.enter_device);
        enter_address = findViewById(R.id.enter_address);
        enter_parent_cat = findViewById(R.id.enter_parent_cat);
        button = findViewById(R.id.button);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData(enter_type.getText().toString().trim(),enter_status.getText().toString().trim(),enter_member.getText().toString().trim(),enter_category.getText().toString().trim(),enter_link.getText().toString().trim(),
                        enter_account_name.getText().toString().trim(),enter_description.getText().toString().trim(),enter_balance.getText().toString().trim(),enter_device.getText().toString().trim(),enter_address.getText().toString().trim(),
                        enter_parent_cat.getText().toString().trim(),enter_date.getText().toString().trim());
            }
        });

        setStartDate();
    }


    private void setStartDate(){
        Calendar startSelectionDate = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.FRANCE);
        startDate = sdf.format(startSelectionDate.getTime());
        enter_date.setText(sdf.format(startSelectionDate.getTime()));

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
        enter_date.setText(sdf.format(myCalendar.getTime()));
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
                empty();

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
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
 //       stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue addQueue = Volley.newRequestQueue(AddExpense.this);
        addQueue.add(stringRequest);
    }
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


}