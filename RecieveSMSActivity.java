package com.example.karthik.sms_app;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecieveSMSActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static RecieveSMSActivity inst;
    ArrayList<String> smsMessageList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayadpater;

    public static RecieveSMSActivity instance(){
        return inst;
    }

    protected void onStart() {
        super.onStart();
        inst = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_sms);

        smsListView = (ListView) findViewById(R.id.SMSList);
        arrayadpater = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,smsMessageList);
        smsListView.setAdapter(arrayadpater);
        smsListView.setOnItemClickListener(this);

        refreshSmsInbox();

        smsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item
                String team = ((TextView) view).getText().toString();

                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), Fulldetails.class);
                // sending data to new activity
                i.putExtra("team", team);
                startActivity(i);

            }
        });

    }

    public void refreshSmsInbox() {


        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            ContentResolver contentResolver = getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");
            int timeMillis = smsInboxCursor.getColumnIndex("date");
            Date date = new Date(timeMillis);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
            String dateText = format.format(date);

            if(indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
            arrayadpater.clear();
            do {
                String str = smsInboxCursor.getString(indexAddress) + " at " +  "\n" + smsInboxCursor.getString(indexBody) + dateText+ "\n";
                arrayadpater.add(str);

            }while(smsInboxCursor.moveToNext());
        } else {
            Log.i("Hello", "Out of permission");
        }




    }

    public void updateList(final String smsMessage) {
        arrayadpater.insert(smsMessage,0);
        arrayadpater.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            String[] smsMessages = smsMessageList.get(position).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";

            for(int i = 1; i < smsMessages.length;i++) {
                smsMessage += smsMessages[i];
            }
            String smsMessgageStr = address + "\n";
            smsMessgageStr += smsMessage;
            Toast.makeText(this, smsMessgageStr, Toast.LENGTH_SHORT).show();



        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void goToCompose(View v) {
        Intent intent = new Intent(RecieveSMSActivity.this,SendSMSActivity.class);
        startActivity(intent);
    }
}
