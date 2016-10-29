package com.example.karthik.sms_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by KARTHIK on 10/27/2016.
 */

public class SMSBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    byte[] pdu;

    public void onReceive(Context context,Intent intent) {
        Bundle intentextras = intent.getExtras();
        if(intentextras != null) {
            Object[] sms = (Object[]) intentextras.get(SMS_BUNDLE);
            String smsMessageStr = "";

            for(int i = 0 ; i < sms.length;i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i],"3gpp");
                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();
                long timeMillis = smsMessage.getTimestampMillis();

                Date date = new Date(timeMillis);
                SimpleDateFormat format = new SimpleDateFormat(("dd/MM/yy"));
                String dateText = format.format(date);

                smsMessageStr += address + " at " + "\t" + dateText + "\n";
                smsMessageStr += smsBody + "\n";

                Toast.makeText(context, smsMessageStr,Toast.LENGTH_SHORT).show();

                RecieveSMSActivity inst = RecieveSMSActivity.instance().instance();
                if(inst!= null) {
                    inst.updateList(smsMessageStr);
                }


            }
        }

    }
}