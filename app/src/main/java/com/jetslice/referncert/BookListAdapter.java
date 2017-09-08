package com.jetslice.referncert;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shubham on 6/9/17.
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.RecyclerViewHoldersx> {
    private ArrayList<String> itemList;
    Context context;
    int clsno;

    public BookListAdapter(ArrayList<String> itemList, Context context,int clsno) {
        this.itemList = itemList;
        this.context = context;
        this.clsno=clsno;
    }

    @Override
    public BookListAdapter.RecyclerViewHoldersx onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, null);
        Animation slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up_animation);
        layoutView.startAnimation(slideUpAnimation);
        RecyclerViewHoldersx rcv = new RecyclerViewHoldersx(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final BookListAdapter.RecyclerViewHoldersx holder, int position) {
        holder.bookname.setText("" + itemList.get(position));
        holder.bookopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chaptername = holder.bookname.getText().toString();
                Intent i = new Intent(context, ChapterList.class);
                i.putExtra("Bookname", chaptername);
                i.putExtra("Classno", clsno);
                context.startActivity(i);
                Toast.makeText(context, "" + chaptername, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


    public class RecyclerViewHoldersx extends RecyclerView.ViewHolder {


        TextView bookname;
        Button bookopen;
        ImageView bookimage;

        public RecyclerViewHoldersx(View itemView) {
            super(itemView);
            bookname = itemView.findViewById(R.id.book_name);
            bookopen = itemView.findViewById(R.id.open_book);
            bookimage = itemView.findViewById(R.id.book_pic);

        }
    }
}
