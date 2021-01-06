package com.example.MyTreasury;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import static android.media.CamcorderProfile.get;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public ArrayList<String[]> myList;
    public Context mContext;
    public Intent mIntent;
    public Class myActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public Button mButton;
        public TextView txtDate;
        public TextView txtCause;
        public TextView txtBalance;

        public MyViewHolder(View ItemView) {
            super(ItemView);
            mButton = itemView.findViewById(R.id.mButton);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtCause = itemView.findViewById(R.id.txtCause);
            txtBalance = itemView.findViewById(R.id.txtBalance);
        }
    }

    public MyAdapter(ArrayList<String[]> list, Class activity){
        myList = list;
        myActivity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        MyViewHolder viewH = new MyViewHolder(v);
        return viewH;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String[] row = (String[]) myList.get(position);
        String date = row[0];
        String cause = row[1];
        String balance = row[2];
        String currency = row[3];
        String type = row[4];
        String sign = "+";
        if (type == "Debit"){
            sign = "-";
        }

        String amount = sign+balance+" "+currency;
        int pos = position;
        //String img_name = item_text.toLowerCase().replaceAll("\\s", "");
        holder.txtDate.setText(date);
        holder.txtCause.setText(cause);
        holder.txtBalance.setText(amount);
        holder.mButton.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  mIntent = new Intent(mContext, myActivity);
                                                  //String item_text = item.toString();
                                                  //mIntent.putExtra("Position", pos);
                                                  mContext.startActivity(mIntent);
                                              }
                                          }
        );
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mContext = recyclerView.getContext();
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}
