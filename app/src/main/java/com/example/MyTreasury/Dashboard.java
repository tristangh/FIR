package com.example.MyTreasury;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class Dashboard extends Fragment {
    private OnFragmentInteractionListener mListener;
    private Activity activity;
    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //change title
        if (mListener !=null) {
            mListener.OnFragmentInteractionChangeTitle("Dashboard");
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dashboard_view, container, false);


        return v;
    }

    public interface OnFragmentInteractionListener {
        void OnFragmentInteractionChangeTitle(String title);
    }

}