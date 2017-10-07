package com.jetslice.referncert;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button open_lib, sett, recent_book;
    SharedPreferences prefs;
    int clsno, chapterno;
    String Bookname;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANTx = 10;
    private static final int REQUEST_PERMISSION_SETTINGx = 11;

    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        prefs = getSharedPreferences("LatestRead", MODE_PRIVATE);
        open_lib = (Button) findViewById(R.id.open_lib);
        sett = (Button) findViewById(R.id.sett);
        recent_book = (Button) findViewById(R.id.recent_book);

        TextView tv = (TextView) findViewById(R.id.prefdet);
        tv.setText("clsno : " + prefs.getInt("spClassno", clsno) + "\t" + prefs.getString("spBookname", Bookname) + "\t" + prefs.getInt("spChapno", chapterno));
        Log.e("F", "cls--- "+prefs.getInt("spClassno", clsno));
        Log.e("F", "cbk--- "+prefs.getString("spBookname", Bookname));
        Log.e("F", "chap--- "+prefs.getInt("spChapno", chapterno));
        open_lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermission();

            }
        });
        sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSettings = new Intent(MainActivity.this, AppSettings.class);
                startActivity(toSettings);
            }
        });
        recent_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermission1();
            }
        });
    }

    private void getPermission1() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANTx);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
//                        System.exit(1);
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTINGx);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        System.exit(1);
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANTx);
            }
            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
//            dostuff();
            doOpenLibStuff();
        }
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
//                        System.exit(1);
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        System.exit(1);
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
            }
            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
//            dostuff();
            doRecentbookSuff();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
//                dostuff();
                doRecentbookSuff();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
//                            System.exit(1);

                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
                }
            }
        } else if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANTx) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
//                dostuff();
                doOpenLibStuff();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
//                            System.exit(1);

                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                // dostuff();
                doRecentbookSuff();
            }
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                //dostuff();
            }
        }
    }

    public void doRecentbookSuff() {
        Intent toClassScreen = new Intent(MainActivity.this, ClassScreen.class);
        startActivity(toClassScreen);
    }

    public void doOpenLibStuff() {
        ArrayList<String> chapterset = getChapterArray();
        int vchapno = prefs.getInt("spChapno", chapterno) - 1;
        String booknamer=prefs.getString("spBookname", Bookname);
        if(vchapno<0 || booknamer==null){
            vchapno=0;
            booknamer="nobook";
        }
        else{
            booknamer.trim();
        }
        String chaptername = chapterset.get(vchapno);
        File loadfile = new File("/sdcard/ReferNcert/Class " + prefs.getInt("spClassno", clsno) + "/" + booknamer + "/" + chaptername + ".pdf");
        SharedPreferences peditor = getSharedPreferences("FileBitSize", MODE_PRIVATE);
        boolean filesizesequal= (peditor.getLong("size_"+prefs.getInt("spClassno", clsno)+booknamer.trim()+vchapno,404)==loadfile.length());
        Log.e("tt", "doOpenLibStuff: ooooooooooooooooo "+filesizesequal);
        if ( (!loadfile.exists()) || (prefs.getString("spBookname", Bookname)==null  && prefs.getInt("spClassno", clsno)==0 && prefs.getInt("spChapno", chapterno)==0))  {
            Toast.makeText(getBaseContext(),"No last read file saved or file may be deleted from storage.Try Redownloading it.",Toast.LENGTH_SHORT).show();
        }
        else if(filesizesequal){
            Intent toPDFScreen = new Intent(MainActivity.this, LAstOpenedbook.class);
            toPDFScreen.putExtra("jBookname", prefs.getString("spBookname", Bookname));
            toPDFScreen.putExtra("jClassno", prefs.getInt("spClassno", clsno));
            toPDFScreen.putExtra("jChapter", prefs.getInt("spChapno", chapterno));
            startActivity(toPDFScreen);
        }
        else{
            Toast.makeText(getBaseContext(),"No last read file saved or file may be deleted from storage.Try Redownloading it.",Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<String> getChapterArray() {
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
}