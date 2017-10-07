package com.jetslice.referncert;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

import is.arontibo.library.ElasticDownloadView;

public class BookPDFView extends AppCompatActivity {
    private StorageReference mStorageRef;
    ArrayList<String> chapterset;
    File localFile;
    PDFView pdfView;
    String bookname;
    int clsno, chapterno;
    static int adfreq = 0;
    private ElasticDownloadView bnp;
    private AdView mAdView;
    SharedPreferences sp;
    StorageReference riversRef;
    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;
    Intent resultIntent,resultIntent1;
    PendingIntent resultpendingIntent,resultpendingIntent1;
    private int Notifyid = 1;
    private long Maxfile;
    static long a;
    static long filesize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        mAdView= (AdView) findViewById(R.id.adView);
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(BookPDFView.this)
                .setSmallIcon(R.mipmap.ic_launcher_round);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        bnp= (ElasticDownloadView) findViewById(R.id.elastic_download_view);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        bookname = getIntent().getStringExtra("iBookname");
        clsno = getIntent().getIntExtra("iClassno", 4);
        chapterno = getIntent().getIntExtra("iChapter", 3);
        chapterset = getchapterset();
        sp=getSharedPreferences("LatestRead",MODE_PRIVATE);
        a=0;
        Toast.makeText(getBaseContext(),"$#$ "+a,Toast.LENGTH_SHORT).show();

//        Toast.makeText(getBaseContext(), "Class " + clsno + "/" + bookname.trim() + "/" + chapterset.get(chapterno) + ".pdf", Toast.LENGTH_SHORT).show();
        String url = "Class " + clsno + "/" + bookname + "/" + chapterset.get(chapterno) + ".pdf";
        File loadfile=new File("/sdcard/ReferNcert/Class "+clsno+"/"+bookname.trim()+"/"+chapterset.get(chapterno)+".pdf");
        SharedPreferences peditor = getSharedPreferences("FileBitSize", MODE_PRIVATE);
        boolean filesizesequal= (peditor.getLong("size_"+clsno+bookname.trim()+chapterno,404)==loadfile.length());
        if(loadfile.exists() && filesizesequal){
            loadinpdf(loadfile);
            bnp.setVisibility(View.GONE);
            pdfView.setVisibility(View.VISIBLE);
        }else
        {
            Log.e("SA", "FSFSF");
            dotask(clsno,bookname.trim(),chapterset.get(chapterno));
            Toast.makeText(getBaseContext(),"$$$   "+a,Toast.LENGTH_SHORT).show();
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

    private void dotask(final int clsno, final String booknamex, final String chpno) {
        riversRef = mStorageRef.child("Class "+clsno+"/"+booknamex+"/"+chpno+".pdf");
        File rootPath = new File("/sdcard/ReferNcert/Class "+clsno+"/"+booknamex+"/");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }

        String savingName=chapterset.get(chapterno)+".pdf";

        localFile = new File(rootPath,savingName);
        bnp.startIntro();



        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        loadinpdf(localFile);
                        Toast.makeText(getBaseContext(), "Loaded", Toast.LENGTH_SHORT).show();
                        bnp.onEnterAnimationFinished();
                        bnp.success();
                        bnp.setVisibility(View.GONE);
                        resultIntent = new Intent(BookPDFView.this,LAstOpenedbook.class);
                        resultpendingIntent = PendingIntent.getActivity(BookPDFView.this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                        if(Maxfile==localFile.length()) {
                            mBuilder.setContentText("Download Complete")
                                    .setContentTitle("Class Book Downloaded")
                                    .setProgress(0, 0, false)
                                    .setContentIntent(resultpendingIntent);

                            mNotifyManager.notify(Notifyid, mBuilder.build());
                        }
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest);
                        mAdView.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                Toast.makeText(getBaseContext(),"Ad Count = "+adfreq,Toast.LENGTH_SHORT).show();
                                ++adfreq;
                            }

                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                resultIntent1 = new Intent(BookPDFView.this,MainActivity.class);
                resultpendingIntent1 = PendingIntent.getActivity(BookPDFView.this,0,resultIntent1,PendingIntent.FLAG_UPDATE_CURRENT);
                Toast.makeText(getBaseContext(), "Failed" + exception, Toast.LENGTH_SHORT).show();
                bnp.fail();
                mBuilder.setProgress(0,0,false)
                        .setContentText("Try again")
                        .setContentTitle("Download failed")
                        .setContentIntent(resultpendingIntent1)
                        .setColor(getResources().getColor(R.color.red_wine));
                mNotifyManager.notify(Notifyid,mBuilder.build());
                }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            @SuppressWarnings("VisibleForTests")
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Maxfile = taskSnapshot.getTotalByteCount();
                Double progress = (100.0 * taskSnapshot.getBytesTransferred())/ taskSnapshot.getTotalByteCount();
                Log.e("BookPDFView","onProgress: The value of the max is: "+taskSnapshot.getTotalByteCount());
                SharedPreferences.Editor editor = getSharedPreferences("FileBitSize", MODE_PRIVATE).edit();
                editor.putLong("size_"+clsno+bookname.trim()+chapterno,taskSnapshot.getTotalByteCount());
                editor.commit();
                Log.e("ef0","-- "+taskSnapshot.getTotalByteCount());
                filesize=taskSnapshot.getTotalByteCount();

                if(taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount()<1) {
                    bnp.setProgress((float) Math.floor(progress));
                    mBuilder.setContentText("Downloading...")
                            .setContentTitle("Class "+clsno+"book "+bookname)
                            .setProgress(100, (int) Math.floor(progress), false);
                    mNotifyManager.notify(Notifyid, mBuilder.build());
                }
                if((taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount()==0.99)){
                    bnp.success();
                }
                if(taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount()==1){
//                    bnp.setVisibility(View.GONE);
                }
                if(!isOnline() && (taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount()<1)){
                    bnp.fail();
                    localFile.delete();
                    mNotifyManager.cancel(Notifyid);
                    mBuilder.setContentText("Network not available")
                            .setContentTitle("Download failed")
                            .setProgress(0,0,false);
                    mNotifyManager.notify(Notifyid,mBuilder.build());
                    Toast.makeText(BookPDFView.this, "Network not available", Toast.LENGTH_SHORT).show();
                }
                if(localFile.length()==Maxfile&&localFile.exists()){
                    mNotifyManager.cancel(Notifyid);
                }
                a=  (taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                Log.e("ef0","-- "+a);
//                SharedPreferences filesizesaver=getSharedPreferences(""+clsno+"/"+bookname+"/"+chapterset.get(chapterno),MODE_PRIVATE);
//                SharedPreferences.Editor sizesaver=filesizesaver.edit();
//                sizesaver.putInt("TotalFileSize",(int)a);
//                Log.e("ddd", "from SP: "+filesizesaver.getInt("TotalFileSize",9));
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        File loadfile=new File("/sdcard/ReferNcert/Class "+clsno+"/"+bookname.trim()+"/"+chapterset.get(chapterno)+".pdf");
        Log.e("DDD", "onDestroy--------------: current length "+loadfile.length());
        Log.e("DDD", "onDestroy--------------: totalSize"+filesize);
        Toast.makeText(getBaseContext(),"goga ++ "+a,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Stop download on back pressed
    private void loadinpdf(File localFile) {
        pdfView.fromFile(localFile).load();
        int chapx=chapterno+1;
        SharedPreferences.Editor saver=sp.edit();
        saver.putInt("spClassno",clsno);
        saver.putString("spBookname",bookname.trim());
        saver.putInt("spChapno",chapx);
        saver.commit();
    }
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

//TODO Use file hiding system using .<name>
