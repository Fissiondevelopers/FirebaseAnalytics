package com.jetslice.referncert;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shubham on 6/9/17.
 */

public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.ChapterViewHolder> {
    ArrayList<String> chapterlist;
    Context context;
    String bookname;
    int clsno;

    public ChapterListAdapter(ArrayList<String> chapterlist, Context context, String book, int clsno) {
        this.chapterlist = chapterlist;
        this.context = context;
        this.bookname=book;
        this.clsno=clsno;
    }

    @Override
    public ChapterListAdapter.ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_item, null);
        ChapterViewHolder rcv = new ChapterViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ChapterListAdapter.ChapterViewHolder holder, final int position) {
        holder.chapname.setText("" + chapterlist.get(position));

        Animation declerateX = AnimationUtils.loadAnimation(context, R.anim.chapters_anim);
        holder.chapname.startAnimation(declerateX);
        holder.chapname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, BookPDFView.class);
                i.putExtra("iBookname",bookname);
                i.putExtra("iClassno",clsno);
                i.putExtra("iChapter",position);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.chapterlist.size();
    }

    public class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView chapname;

        public ChapterViewHolder(View itemView) {
            super(itemView);
            chapname = itemView.findViewById(R.id.chapter_name);

        }
    }
}
