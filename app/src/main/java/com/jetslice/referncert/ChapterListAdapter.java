package com.jetslice.referncert;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.github.lzyzsd.randomcolor.RandomColor;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

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
        ArrayList<String> chapterset=getChapterList();
        File loadfile=new File("/sdcard/ReferNcert/Class "+clsno+"/"+bookname.trim()+"/"+chapterset.get(position)+".pdf");
        SharedPreferences peditor = context.getSharedPreferences("FileBitSize", MODE_PRIVATE);
        boolean filesizesequal= (peditor.getLong("size_"+clsno+bookname.trim()+position,404)==loadfile.length());
        if(loadfile.exists() && filesizesequal){
            holder.checkProg.setImageResource(R.mipmap.download_completed);
        }
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

        RandomColor randomColoar = new RandomColor();
//        int color = randomColor.randomColor();

        holder.chapname.setBackgroundColor(generateRandomColor());
        holder.cv_item.setCardBackgroundColor(generateRandomColorCard());

    }

    public int generateRandomColor() {
        // This is the base color which will be mixed with the generated osne
        final Random mRandom = new Random(System.currentTimeMillis());
        final int baseColor = Color.TRANSPARENT;

        final int baseRed = Color.red(baseColor);
        final int baseGreen = Color.green(baseColor);
        final int baseBlue = Color.blue(baseColor);

        final int red = (int) ((baseRed + mRandom.nextInt(256)) /1);
        final int green = (int) ((baseGreen + mRandom.nextInt(256)) / 1);
        final int blue = (int) ((baseBlue + mRandom.nextInt(256)) / 1);

        return Color.rgb(red, green, blue);
    }
    public int generateRandomColorCard() {
        // This is the base color which will be mixed with the generated osne
        final Random mRandom = new Random(System.currentTimeMillis());
        final int baseColor = Color.WHITE;

        final int baseRed = Color.red(baseColor);
        final int baseGreen = Color.green(baseColor);
        final int baseBlue = Color.blue(baseColor);

        final int red = (int) ((baseRed + mRandom.nextInt(256)) /2);
        final int green = (int) ((baseGreen + mRandom.nextInt(256)) / 2);
        final int blue = (int) ((baseBlue + mRandom.nextInt(256)) / 2);

        return Color.rgb(red, green, blue);
    }

    private ArrayList<String> getChapterList() {
        ArrayList<String> chapters = new ArrayList<>();
        chapters.add("Chapter01");
        chapters.add("Chapter02");
        chapters.add("Chapter03");
        chapters.add("Chapter04");
        chapters.add("Chapter05");
        chapters.add("Chapter06");
        chapters.add("Chapter07");
        chapters.add("Chapter08");
        chapters.add("Chapter09");
        chapters.add("Chapter10");
        chapters.add("Chapter11");
        chapters.add("Chapter12");
        chapters.add("Chapter13");
        chapters.add("Chapter14");
        chapters.add("Chapter15");
        chapters.add("Chapter16");
        chapters.add("Chapter17");
        chapters.add("Chapter18");
        chapters.add("Chapter19");
        chapters.add("Chapter20");
        chapters.add("Chapter21");
        chapters.add("Chapter22");
        chapters.add("Chapter23");
        chapters.add("Chapter24");
        chapters.add("Chapter25");
        chapters.add("Chapter26");
        chapters.add("Chapter27");
        chapters.add("Chapter28");
        chapters.add("Chapter29");
        chapters.add("Chapter30");
        chapters.add("Chapter31");
        chapters.add("Chapter32");
        chapters.add("Chapter33");
        chapters.add("Chapter34");
        chapters.add("Chapter35");
        return chapters;
    }

    @Override
    public int getItemCount() {
        return this.chapterlist.size();
    }

    public class ChapterViewHolder extends RecyclerView.ViewHolder {
        Button chapname;
        ImageView checkProg;
        CardView cv_item;

        public ChapterViewHolder(View itemView) {
            super(itemView);
            chapname = itemView.findViewById(R.id.chapter_name);
            checkProg=itemView.findViewById(R.id.ivcheck);
            cv_item=itemView.findViewById(R.id.chapter_item);
        }
    }
}