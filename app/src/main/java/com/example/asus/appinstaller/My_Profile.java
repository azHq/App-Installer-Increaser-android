package com.example.asus.appinstaller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class My_Profile extends AppCompatActivity {

    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__profile);

        MobileAds.initialize(this,"ca-app-pub-4543818442626948~6872462781");

        adView=(AdView) findViewById(R.id.adView);

        AdRequest adRequest=new AdRequest.Builder().build();

        adView.loadAd(adRequest);
    }
}
