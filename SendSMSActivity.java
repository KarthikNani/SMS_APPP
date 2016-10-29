package com.example.karthik.sms_app;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMSActivity extends AppCompatActivity {

    Button sendSmsButton;
    EditText toPhoneNumber;
    EditText smsMessageET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        sendSmsButton = (Button) findViewById(R.id.btnSendSms);
        toPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNo);
        smsMessageET = (EditText) findViewById(R.id.editTextSms);

        sendSmsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                sendSms();

            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PLAYGROUND", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);
            sendSmsButton.setEnabled(false);
        } else {
            Log.d("PLAYGROUND", "Permission is granted");
        }



    }

        private void sendSms() {

//            ActivityCompat.requestPermissions(this,new String[]{
//                    Manifest.permission.SEND_SMS},1);
//            Intent intent=new Intent(getApplicationContext(),SendSMSActivity.class);
//            PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
//
            String toPhone = toPhoneNumber.getText().toString();
            String smsMessage = smsMessageET.getText().toString();
            try {
                SmsManager smsManager = SmsManager.getDefault();
                Toast.makeText(this, toPhone,Toast.LENGTH_LONG).show();
                Toast.makeText(this, smsMessage,Toast.LENGTH_LONG).show();
                smsManager.sendTextMessage(toPhone, null, smsMessage, null, null);
                Toast.makeText(this, "SMS Sent",Toast.LENGTH_LONG).show();

            } catch(Exception e){
                Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }





//            Log.i("Send SMS", "");
//            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//
////            smsIntent.setData(Uri.parse("smsto:"));
////            smsIntent.setType("vnd.android-dir/mms-sms");
//            smsIntent.putExtra("address"  , new String (toPhone));
//            smsIntent.putExtra("sms_body"  , smsMessage);
//
//            try {
//                startActivity(smsIntent);
//                finish();
//                Log.i("Finished sending SMS...", "");
//            }
//            catch (android.content.ActivityNotFoundException ex) {
//                Toast.makeText(SendSMSActivity.this,
//                        "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
//            }
        }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("PLAYGROUND", "Permission has been granted");
//                textView.setText("You can send SMS!");
                sendSmsButton.setEnabled(true);
            } else {
                Log.d("PLAYGROUND", "Permission has been denied or request cancelled");
//                textView.setText("You can not send SMS!");
                sendSmsButton.setEnabled(false);
            }
        }
    }

        public void goToInbox(View v) {
            Intent intent = new Intent(SendSMSActivity.this,RecieveSMSActivity.class);
            startActivity(intent);
        }



}
