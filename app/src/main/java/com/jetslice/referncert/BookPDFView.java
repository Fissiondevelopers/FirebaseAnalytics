package com.jetslice.referncert;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    File imagePath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);


        mAdView = (AdView) findViewById(R.id.adView);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        bnp = (ElasticDownloadView) findViewById(R.id.elastic_download_view);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        bookname = getIntent().getStringExtra("iBookname");
        clsno = getIntent().getIntExtra("iClassno", 4);
        chapterno = getIntent().getIntExtra("iChapter", 3);
        chapterset = getchapterset();
        sp = getSharedPreferences("LatestRead", MODE_PRIVATE);

//        Toast.makeText(getBaseContext(), "Class " + clsno + "/" + bookname.trim() + "/" + chapterset.get(chapterno) + ".pdf", Toast.LENGTH_SHORT).show();
        String url = "Class " + clsno + "/" + bookname + "/" + chapterset.get(chapterno) + ".pdf";
        File loadfile = new File("/sdcard/ReferNcert/Class " + clsno + "/" + bookname.trim() + "/" + chapterset.get(chapterno) + ".pdf");
        if (loadfile.exists()) {
            loadinpdf(loadfile);
            bnp.setVisibility(View.GONE);
            pdfView.setVisibility(View.VISIBLE);
        } else {
            Log.e("SA", "FSFSF");
            dotask(clsno, bookname.trim(), chapterset.get(chapterno));

        }
        getWindow().setBackgroundDrawable(null);


    }
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    public void saveBitmap(Bitmap bitmap) {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
    private void shareIt() {
        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "Checkout this app Referncert on playstore //url//";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Tweecher score");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
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

    private void dotask(int clsno, final String booknamex, final String chpno) {
        riversRef = mStorageRef.child("Class " + clsno + "/" + booknamex + "/" + chpno + ".pdf");
        File rootPath = new File("/sdcard/ReferNcert/Class " + clsno + "/" + booknamex + "/");
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        String savingName = chapterset.get(chapterno) + ".pdf";

        localFile = new File(rootPath, savingName);
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
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest);
                        mAdView.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                Toast.makeText(getBaseContext(), "Ad Count = " + adfreq, Toast.LENGTH_SHORT).show();
                                ++adfreq;

                            }

                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getBaseContext(), "Failed" + exception, Toast.LENGTH_SHORT).show();
                bnp.fail();
            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            @SuppressWarnings("VisibleForTests")
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.d("BookPDFView", "onProgress: The value of the max is: " + taskSnapshot.getTotalByteCount());
                Log.d("BookPDFView", "onProgress: The progress is: " + progress);
                if (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount() < 1) {
                    bnp.setProgress((float) Math.floor(progress));
                }
                if ((taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount() == 0.99)) {
                    bnp.success();
                }
                if (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount() == 1) {
//                    bnp.setVisibility(View.GONE);
                }
                if (!isOnline() && (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount() != 0.99)) {
                    bnp.fail();
                    localFile.delete();
                    Toast.makeText(BookPDFView.this, "Network not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // Todo add notification which shows progress of download in it

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Stop download on back pressed
    private void loadinpdf(File localFile) {
        pdfView.fromFile(localFile).load();
        int chapx = chapterno + 1;
        SharedPreferences.Editor saver = sp.edit();
        saver.putInt("spClassno", clsno);
        saver.putString("spBookname", bookname.trim());
        saver.putInt("spChapno", chapx);
        saver.commit();
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(BookPDFView.this,"Pooo",Toast.LENGTH_SHORT).show();
                Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);
                shareIt();

                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);


        }

//TODO delete file if not downloded completely
    }

}