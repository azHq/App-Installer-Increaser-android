package com.example.asus.appinstaller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser UID;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth=FirebaseAuth.getInstance();

        UID=mAuth.getCurrentUser();
        if(UID!=null){

            Intent tnt=new Intent(this,Authentication.class);
            startActivity(tnt);
        }
        else{

            Intent tnt=new Intent(this,Navdrawer.class);
            startActivity(tnt);
        }









    }
}
