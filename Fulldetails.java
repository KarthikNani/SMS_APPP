package com.example.karthik.sms_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Fulldetails extends AppCompatActivity {
    String output;
    TextView namee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulldetails);
        namee = (TextView) findViewById(R.id.tvNamee);
        Intent i = getIntent();
        // getting attached intent data
        output = i.getStringExtra("team");
        namee.setText(output);

    }

}
