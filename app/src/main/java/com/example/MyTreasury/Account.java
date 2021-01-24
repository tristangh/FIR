package com.example.MyTreasury;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class Account extends Fragment {
    private com.example.MyTreasury.Dashboard.OnFragmentInteractionListener mListener;
    private Activity activity;
    Button btn;

    private FirebaseAuth mAuth;
    private TextView emailTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //change title
        if (mListener !=null) {
            mListener.OnFragmentInteractionChangeTitle("Account");
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.account_view, container, false);
        mAuth = FirebaseAuth.getInstance();

        emailTv = v.findViewById(R.id.email_tv);
        emailTv.setText(mAuth.getCurrentUser().getEmail());

        return v;
    }





    public interface OnFragmentInteractionListener {
        void OnFragmentInteractionChangeTitle(String title);
    }

}