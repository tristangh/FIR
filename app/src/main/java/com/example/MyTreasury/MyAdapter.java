package com.example.MyTreasury;

import android.content.Context;

import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public ArrayList<String[]> myList;
    public Context mContext;
    public Intent mIntent;
    public Class myActivity;
    public List<Data> DataList;
    public DatabaseReference database_ref;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public Button openButton;
        public TextView txtDate;
        public TextView txtCause;
        public TextView txtAmount;
        public Button deleteButton;
        public Layout rowLayout;

        public MyViewHolder(View ItemView) {
            super(ItemView);
            openButton = itemView.findViewById(R.id.openButton);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtCause = itemView.findViewById(R.id.txtCause);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }
    }

    public MyAdapter(List <Data> DataList, Class activity, DatabaseReference database_ref){
        myActivity = activity;
        this.DataList = DataList;
        this.database_ref = database_ref;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        MyViewHolder viewH = new MyViewHolder(v);
        return viewH;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final MyViewHolder viewHolder = (MyViewHolder)holder;

        Data data = DataList.get(position);

        final String id = data.getId();
        final String date = data.getDate();
        final String cause = data.getCause();
        final String amount = data.getAmount();
        final String state = data.getState();
        final String payer = data.getPayer();
        final String cat = data.getCat();
        final String subcat = data.getSubcat();
//        final String img = row[9];
  //      final String comments = row[10];
        viewHolder.txtDate.setText(date);
        viewHolder.txtAmount.setText(amount);
        viewHolder.txtCause.setText(cause);

        viewHolder.openButton.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  mIntent = new Intent(mContext, myActivity);

                                                  mIntent.putExtra("Date", date);
                                                  mIntent.putExtra("Cause", cause);
                                                  mIntent.putExtra("Amount", amount);
                                                  mIntent.putExtra("State", state);
                                                  mIntent.putExtra("Payer", payer);
                                                  mIntent.putExtra("Category", cat);
                                                  mIntent.putExtra("Sub-category", subcat);
                                                  //mIntent.putExtra("Invoice file", img);
                                                  //mIntent.putExtra("Comments", comments);
                                                  mContext.startActivity(mIntent);
                                              }
                                          }
        );
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database_ref.child(id).removeValue();
                DataList.remove(position);
                //
                notifyItemRemoved(position);

                notifyItemRangeChanged(position, DataList.size());


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
        return DataList.size();
    }
}
