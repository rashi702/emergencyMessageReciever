package com.blkminiproject.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;



/**
 * Created by Parth on 1/27/2018.
 */

public class MessageReciever extends BroadcastReceiver {

    public static final String search = "Emergency";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");

            for (int i = 0; i < pdus.length; i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

                String sender = smsMessage.getOriginatingAddress();
                String message = smsMessage.getDisplayMessageBody();

                //Toast.makeText(context, "Sent: " + sender + " Message: " + message, Toast.LENGTH_SHORT).show();

                if (message.toLowerCase().contains(search.toLowerCase())) {

                    Toast.makeText(context, "Emergency Detected", Toast.LENGTH_LONG).show();


                    SharedPreferences preferences = context.getSharedPreferences("emergencyNumber", Context.MODE_PRIVATE);
                    String phoneNum = preferences.getString("Number", "");

                    if (phoneNum != "") {
                        Toast.makeText(context, "Calling.." + phoneNum, Toast.LENGTH_SHORT).show();
                        String dial = "tel:" + phoneNum;
                        goToMain(context,dial);

                    }
                    else Toast.makeText(context, "No number specified", Toast.LENGTH_SHORT).show();
                }


            }

        }

    }

    public void goToMain(Context context,String dial){

        Intent intent = new Intent();
        intent.setClassName("com.blkminiproject","com.blkminiproject.MainActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Num",dial);
        context.startActivity(intent);

    }

}
