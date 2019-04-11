package com.toiler.enigmasystems;

import android.*;
import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Panel extends UiLang {

    RelativeLayout addWorker, searchWorker, workerLogin;
    TextView banglaBtn, englishBtn, about;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        loadLocale();

        checkAndRequestPermissions();
        addWorker = (RelativeLayout) findViewById(R.id.add_worker);
        searchWorker = (RelativeLayout) findViewById(R.id.search_worker);
        workerLogin = (RelativeLayout) findViewById(R.id.workerLogin);

        banglaBtn = (TextView) findViewById(R.id.banglaBtn);
        englishBtn = (TextView) findViewById(R.id.englishBtn);
        about = (TextView) findViewById(R.id.about);

        searchWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent searchWorkerIntent = new Intent(Panel.this, WorkerGrid.class);
                    startActivity(searchWorkerIntent);
            }
        });

        addWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
                    Intent addWorkerIntent = new Intent(Panel.this, AccountChooser.class);
                    startActivity(addWorkerIntent);
        }else{

            Context context = getApplicationContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(Panel.this);
            builder.setTitle(getResources().getString(R.string.confirm_txt));
            builder.setMessage(getResources().getString(R.string.internet_status));

            builder.setPositiveButton(getResources().getString(R.string.yes_btn_text), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                    intent.setComponent(cn);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            builder.setNegativeButton(getResources().getString(R.string.no_btn_text), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }


//                    Intent addWorkerIntent = new Intent(Panel.this, AccountChooser.class);
//                    startActivity(addWorkerIntent);
            }
        });

        workerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {

            Intent workerLoginIntent = new Intent(Panel.this, WorkerLogin.class);
            startActivity(workerLoginIntent);

        }else{

            Context context = getApplicationContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(Panel.this);
            builder.setTitle(getResources().getString(R.string.confirm_txt));
            builder.setMessage(getResources().getString(R.string.internet_status));

            builder.setPositiveButton(getResources().getString(R.string.yes_btn_text), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                    intent.setComponent(cn);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            builder.setNegativeButton(getResources().getString(R.string.no_btn_text), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
            }
        });

        banglaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLang("bn");
//                Intent i = getApplicationContext().getPackageManager()
//                        .getLaunchIntentForPackage(getApplicationContext().getPackageName());
//
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
                Intent i = getIntent();
                finish();
                startActivity(i);
            }
        });

        englishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLang("en");
//                Intent i = getApplicationContext().getPackageManager()
//                        .getLaunchIntentForPackage(getApplicationContext().getPackageName());
//
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
                Intent i = getIntent();
                finish();
                startActivity(i);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(Panel.this);
                View promptView = layoutInflater.inflate(R.layout.about_dialog, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Panel.this);
                alertDialogBuilder.setView(promptView);

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });


    }

    // Function for Multiple Device permission in Runtime
    private  boolean checkAndRequestPermissions() {
        int phoneCall = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE);
        int location = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int write_storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (phoneCall != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CALL_PHONE);
        }
        if (location != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(write_storage != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;

    }

    public void networkStateCheck(){

//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        android.net.NetworkInfo wifi = cm
//                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        android.net.NetworkInfo datac = cm
//                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        if ((wifi != null & datac != null)
//                && (wifi.isConnected() | datac.isConnected())) {
//
//        }else{
//
//            Context context = getApplicationContext();
//            AlertDialog.Builder builder = new AlertDialog.Builder(Panel.this);
//            builder.setTitle(getResources().getString(R.string.confirm_txt));
//            builder.setMessage(getResources().getString(R.string.internet_status));
//
//            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//
//                public void onClick(DialogInterface dialog, int which) {
//                    // Do nothing but close the dialog
//                    final Intent intent = new Intent(Intent.ACTION_MAIN, null);
//                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                    final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
//                    intent.setComponent(cn);
//                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }
//            });
//
//            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                    // Do nothing
//                    dialog.dismiss();
//                }
//            });
//
//            AlertDialog alert = builder.create();
//            alert.show();
//        }

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            Context context = getApplicationContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(Panel.this);
            builder.setTitle(getResources().getString(R.string.confirm_txt));
            builder.setMessage(getResources().getString(R.string.internet_status));

            builder.setPositiveButton(getResources().getString(R.string.yes_text_dialog), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                    intent.setComponent(cn);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            builder.setNegativeButton(getResources().getString(R.string.no_text_dialog), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

    }

}
