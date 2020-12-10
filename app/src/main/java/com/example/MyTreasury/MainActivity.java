package com.example.MyTreasury;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

    Button loginpageview_Button;
    LinearLayout loginpage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // loginpage = (LinearLayout) (R.id.loginpage.xml)


        loginpageview_Button =findViewById(R.id.loginpageshow_button);
        loginpageview_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginpageview_Button.setVisibility(View.GONE);
                loginpage.setVisibility(View.VISIBLE);


            }
        });


    }

    




}