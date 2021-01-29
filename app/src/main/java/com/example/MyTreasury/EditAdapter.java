package com.example.MyTreasury;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.MyViewHolder> {

    List<Data> DataList;
    //public Class myActivity;
    public DatabaseReference database_ref;
    int layout;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public Button delButton;
        public TextView txt_name;
        public TextView txt_del;

        public MyViewHolder(View ItemView) {
            super(ItemView);
            //delButton = itemView.findViewById(R.id.delButton);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_del = itemView.findViewById(R.id.txt_del);;
        }
    }

    public EditAdapter(List<Data> DataList, DatabaseReference database_ref,  int layout) {
        //this.myActivity = activity;
        this.DataList = DataList;
        this.database_ref = database_ref;
        this.layout = layout;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        EditAdapter.MyViewHolder viewH = new EditAdapter.MyViewHolder(v);
        return viewH;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final EditAdapter.MyViewHolder viewHolder = (EditAdapter.MyViewHolder)holder;

        Data data = DataList.get(position);

        final String id = data.getId();
        final String noun = data.getNoun();

        viewHolder.txt_name.setText(noun);
        viewHolder.txt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Query queryRef = database_ref.equalTo(noun);
                queryRef.once("value", function(snapshot) {
                    snapshot.forEach(function(child) {
                        child.ref.remove();
                    })
                queryRef.getRef().removeValue();
                System.out.println(queryRef.getPath().getParent().toString());


                /*
                queryRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot snapshot, String previousChild) {

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        snapshot.getRef().setValue(null);
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

                 */
                //ExpenseList.mAdapter.notifyDataSetChanged();
                //ExpenseList.mAdapter.notifyItemRemoved(Integer.parseInt(position));
                //ExpenseList.mAdapter.notifyItemRangeChanged(Integer.parseInt(position), ExpenseList.mAdapter.getItemCount());
                //database_ref.child(id).removeValue();
                database_ref.child(id).removeValue();
                //DataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());


            }

        });

    }

    @Override
    public int getItemCount() {
        return DataList.size();
    }



}