package com.example.MyTreasury;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMember extends AppCompatActivity {

    Button addMemberButton;
    EditText edt_addmemb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member);

        addMemberButton = findViewById(R.id.btn_addMember);
        edt_addmemb = findViewById(R.id.edt_add_mem);

        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edt_addmemb.getText();
                //insert member to Firebase


            }
        });

    }
}