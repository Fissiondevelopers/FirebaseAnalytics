package com.jetslice.referncert;

/**
 * Created by shubham on 4/9/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
        Animation slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up_animation);
        layoutView.startAnimation(slideUpAnimation);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders recyclerViewHolders, int i) {
        int num = i + 1;
        recyclerViewHolders.countryName.setText("Class " + num);
        recyclerViewHolders.imgv.setImageResource(R.drawable.zxc);
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
                    Intent i = new Intent(context, BooksLists.class);
                    i.putExtra("Classno",getAdapterPosition()+1);
                    context.startActivity(i);
                }
            });
        }

    }


}