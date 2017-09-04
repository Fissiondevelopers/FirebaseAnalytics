package com.jetslice.referncert;

/**
 * Created by shubham on 4/9/17.
 */

import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class AllClassesAdapter extends RecyclerView.Adapter<AllClassesAdapter.RecyclerViewHolders> {

    private List<String> itemList;
    private Context context;

    public AllClassesAdapter(Context context, List<String> itemList) {
        this.itemList = itemList;
        this.context = context;
    }


    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.class_number_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders recyclerViewHolders, int i) {
        int num = i + 1;
        recyclerViewHolders.countryName.setText("Class " + num);
        recyclerViewHolders.imgv.setImageResource(R.drawable.tenth);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView countryName;
        ImageView imgv;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.classname);
            imgv = itemView.findViewById(R.id.classpic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent i = new Intent(context, BooksList.class);
//                    int classno = getAdapterPosition() + 1;
//                    Toast.makeText(context, "Class " + classno, Toast.LENGTH_SHORT).show();
//                    i.putExtra("Classno", classno);
//                    context.startActivity(i);
                }
            });
        }

    }


}