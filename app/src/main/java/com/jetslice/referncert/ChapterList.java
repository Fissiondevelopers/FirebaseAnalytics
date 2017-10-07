package com.jetslice.referncert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ChapterList extends AppCompatActivity {
    private GridLayoutManager BookLayout;
    ArrayList<String> chapterlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        String book=getIntent().getStringExtra("Bookname");
        int clsno=getIntent().getIntExtra("Classno",6);
        Log.e("Ds", "onCreate:@@@@22 "+book);
        try {
            chapterlist=getBookChapters(book,clsno);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<chapterlist.size();i++){
            Log.e("FDs", "## "+chapterlist.get(i) );
        }
        BookLayout = new GridLayoutManager(this, 2);
        RecyclerView rView = (RecyclerView) findViewById(R.id.rv_chapter_list);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));
        ChapterListAdapter rcAdapter = new ChapterListAdapter(chapterlist,this,book,clsno);
        rView.setAdapter(rcAdapter);
        getSupportActionBar().setTitle(""+book);
    }

//    private ArrayList<String> getBookChapters(String Book) throws IOException {
//        ArrayList<String> BookChapters=new ArrayList<>();
//        InputStream is;
//        boolean flag ;
//        is = getResources().openRawResource(R.raw.risa);
//        flag = false;
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        String line;
//        while ((line = reader.readLine()) != null){
//            String arr[] = line.split(",");
//            if (arr[1].equalsIgnoreCase(String.valueOf(Book))){
//                BookChapters.add(arr[2]);
//                flag=true;
//            }
//        }
//        return BookChapters;
//    }

    private ArrayList<String> getBookChapters(String Book, int clsno) throws IOException {
        ArrayList<String> BookChapters=new ArrayList<>();
        InputStream is;
        boolean flag ;
        is = getResources().openRawResource(R.raw.expmp);
        flag = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null){
            String arr[] = line.split(",");
            if ((arr[1].equals(Book)) && (Integer.parseInt(arr[0])==clsno)){
                BookChapters.add(arr[2]);
                flag=true;
            }
        }
        return BookChapters;
    }
}