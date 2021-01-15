package com.example.MyTreasury;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Register extends Activity {

    Spinner association_spinner;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerview);

        association_spinner = findViewById(R.id.spinner_association);

        setSpinnerItems();

    }

    private void setSpinnerItems(){

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
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

                item = spinnerArray.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}
