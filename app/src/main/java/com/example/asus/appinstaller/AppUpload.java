package com.example.asus.appinstaller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class AppUpload extends AppCompatActivity {

   Toolbar toolbar;
   AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_upload);

        toolbar=(Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("App Upload");



    }
}
