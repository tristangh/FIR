package com.example.MyTreasury;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                String insertedText;
                insertedText = edt_addmemb.getText().toString();
                //insert member to Firebase
                //Toast.makeText(text(), "Signed out successfully", Toast.LENGTH_SHORT).show();


            }
        });

    }
}