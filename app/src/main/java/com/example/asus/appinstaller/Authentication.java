package com.example.asus.appinstaller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Authentication extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);



    }

    public void doSignIn(View v){

       Intent tnt=new Intent(this,LogInForm.class);
        startActivity(tnt);


    }

    public void doSignUP(View v){

        Intent tnt=new Intent(this,SignUp.class);
        startActivity(tnt);
    }

    public void doFaceBookLogIn(View v){

    }

    public void Help(View v){

    }
}
