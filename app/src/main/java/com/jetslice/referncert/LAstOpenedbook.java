package com.jetslice.referncert;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.ArrayList;

public class LAstOpenedbook extends AppCompatActivity {
    ArrayList<String> chapterset;
    PDFView pdfView;
    String bookname;
    int clsno, chapterno;
    static int adfreq = 0;
    private AdView mAdView;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_openedbook);

        prefs = getSharedPreferences("LatestRead", MODE_PRIVATE);
        mAdView = (AdView) findViewById(R.id.lastadView);
        pdfView = (PDFView) findViewById(R.id.lastpdfView);
        chapterset = getchapterset();
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(getBaseContext(), "Ad Count = " + adfreq, Toast.LENGTH_SHORT).show();
                ++adfreq;
            }
        });
        String clsnow = String.valueOf(prefs.getInt("spClassno", clsno));
        String booknamew = prefs.getString("spBookname", bookname).trim();
        int vchapno = prefs.getInt("spChapno", chapterno) - 1;
        String chapterw = chapterset.get(vchapno);
        File loadfile = new File("/sdcard/ReferNcert/Class " + clsnow + "/" + booknamew + "/" + chapterw + ".pdf");
//        SharedPreferences peditor = getSharedPreferences("FileBitSize", MODE_PRIVATE);
//        boolean filesizesequal= (peditor.getLong("size_"+clsnow+booknamew.trim()+vchapno,404)==loadfile.length());
//        Log.e("Ddd", " -----=================== StorageFileSize "+loadfile.length());
//        Log.e("Ddd", " -----=================== TotalFileSize "+peditor.getLong("size_"+clsnow+booknamew.trim()+vchapno,404));
//        Log.e("Ddd", " -----=================== TotalFileSize "+filesizesequal);


        if (loadfile.exists()) {
                pdfView.setVisibility(View.VISIBLE);
                loadinpdf(loadfile);
        } else {
            Toast.makeText(getBaseContext(), "File not found.Or may not be Downloaded Completely", Toast.LENGTH_SHORT).show();
        }
        getWindow().setBackgroundDrawable(null);
    }

    private ArrayList<String> getchapterset() {
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void loadinpdf(File localFile) {
        pdfView.fromFile(localFile).load();
    }
}