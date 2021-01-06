package com.example.MyTreasury;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Dashboard extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

    }

    public void onButtonClick(View v) {
        //Log.d("Dashboard error : ", String.valueOf(v.getId()));
        if (v.getId() == R.id.loginpageshow_button) {
            Intent i = new Intent(Dashboard.this, ExpenseList.class);
            startActivity(i);
        }

    }


}