package com.example.asus.appinstaller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class LogInForm extends AppCompatActivity {

    Toolbar appbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_form);

        //appbar=(Toolbar)findViewById(R.id.app_bar);
       // appbar.setTitle("Sign in");
    }
}
