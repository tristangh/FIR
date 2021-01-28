package com.example.MyTreasury;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.List;

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.MyViewHolder> {

    List<String> DataList;
    public Class myActivity;
    public DatabaseReference database_ref;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public Button delButton;
        public TextView txt_name;

        public MyViewHolder(View ItemView) {
            super(ItemView);
            delButton = itemView.findViewById(R.id.delButton);
            txt_name = itemView.findViewById(R.id.txt_name);;
        }
    }

    public EditAdapter(List<String> DataList, Class activity, DatabaseReference database_ref) {
        this.myActivity = activity;
        this.DataList = DataList;
        this.database_ref = database_ref;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_edit, parent, false);
        EditAdapter.MyViewHolder viewH = new EditAdapter.MyViewHolder(v);
        return viewH;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final EditAdapter.MyViewHolder viewHolder = (EditAdapter.MyViewHolder)holder;

        final String noun = DataList.get(position);

        viewHolder.txt_name.setText(noun);
        viewHolder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query queryRef = database_ref.equalTo(noun);

                queryRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                        snapshot.getRef().setValue(null);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //ExpenseList.mAdapter.notifyDataSetChanged();
                //ExpenseList.mAdapter.notifyItemRemoved(Integer.parseInt(position));
                //ExpenseList.mAdapter.notifyItemRangeChanged(Integer.parseInt(position), ExpenseList.mAdapter.getItemCount());
                DataList.remove(position);


            }

        });

    }

    @Override
    public int getItemCount() {
        return DataList.size();
    }



}