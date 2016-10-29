package com.example.karthik.sms_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goToInbox(View v){
        Intent intent = new Intent(MainActivity.this,RecieveSMSActivity.class);
        startActivity(intent);
    }

    public void goToCompose(View v){
        Intent intent = new Intent(MainActivity.this,SendSMSActivity.class);
        startActivity(intent);

    }


}
