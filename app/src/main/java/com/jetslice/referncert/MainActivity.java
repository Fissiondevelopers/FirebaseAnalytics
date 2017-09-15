package com.jetslice.referncert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button open_lib,sett,recent_book;
    SharedPreferences prefs;
    int clsno,chapterno;
    String Bookname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("LatestRead", MODE_PRIVATE);
        open_lib= (Button) findViewById(R.id.open_lib);
        sett= (Button) findViewById(R.id.sett);
        recent_book= (Button) findViewById(R.id.recent_book);

        TextView tv= (TextView) findViewById(R.id.prefdet);
        tv.setText("clsno : "+prefs.getInt("spClassno",clsno)+"\t"+prefs.getString("spBookname",Bookname)+"\t"+prefs.getInt("spChapno",chapterno));
        open_lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toClassScreen=new Intent(MainActivity.this,ClassScreen.class);
                startActivity(toClassScreen);
            }
        });
        sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSettings=new Intent(MainActivity.this,AppSettings.class);
                startActivity(toSettings);
            }
        });
        recent_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPDFScreen=new Intent(MainActivity.this,LAstOpenedbook.class);
                toPDFScreen.putExtra("jBookname",prefs.getString("spBookname",Bookname));
                toPDFScreen.putExtra("jClassno",prefs.getInt("spClassno",clsno));
                toPDFScreen.putExtra("jChapter",prefs.getInt("spChapno",chapterno));
                startActivity(toPDFScreen);
            }
        });
        getWindow().setBackgroundDrawable(null);
    }
}