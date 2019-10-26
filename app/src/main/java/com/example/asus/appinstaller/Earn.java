package com.example.asus.appinstaller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Earn extends AppCompatActivity {

    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn);

        adView=(AdView) findViewById(R.id.adView);

        AdRequest adRequest=new AdRequest.Builder().build();

        adView.loadAd(adRequest);


    }
}
