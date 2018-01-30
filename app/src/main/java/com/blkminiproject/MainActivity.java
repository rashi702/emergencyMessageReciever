package com.blkminiproject;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blkminiproject.service.AlwaysOnService;

public class MainActivity extends AppCompatActivity {

    private int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;
    private int MY_PERMISSIONS_REQUEST_CALL_PHONE = 11;
    private int MY_PERMISSIONS_REQUEST_CONTACTS = 12;

    private boolean decision=true;
    private Button button;
    private TextView textView;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_PERMISSIONS_REQUEST_SMS_RECEIVE){
            Log.i("TAG","MY_PERMISSIONS_REQUEST_SMS_RECIEVE");

        }
        if(requestCode==MY_PERMISSIONS_REQUEST_CALL_PHONE){
            Log.i("TAG","MY_PERMISSIONS_REQUEST_CALL_PHONE");
        }
        if(requestCode==MY_PERMISSIONS_REQUEST_CONTACTS){
            Log.i("TAG","MY_PERMISSIONS_REQUEST_CONTACTS");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        button =findViewById(R.id.emergencyButton);
        textView=findViewById(R.id.showNumText);

        setTitle("Emergency Application");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ContactList.class);
                startActivity(i);
            }
        });



//-------------------------------------------------------------------------------------------
        startService(new Intent(this, AlwaysOnService.class));
//-------------------------------------------------------------------------------------------



        //----------------------Permissions----------------------------------------------------------------

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_CONTACTS);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECEIVE_SMS)) {
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        MY_PERMISSIONS_REQUEST_SMS_RECEIVE);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        }

        //------------------------------------------------------------------------------------------------------

        SharedPreferences preferences = getSharedPreferences("emergencyNumber", Context.MODE_PRIVATE);
        String phoneNum = preferences.getString("Number","");
        String name = preferences.getString("Name","");
        if(phoneNum!="") {

            textView.setText("Specified Contact: "+name+" ("+phoneNum+")");
        }
        else textView.setText("No contact specified");

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String dial = bundle.getString("Num");
            if(dial!=null) {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }



    }



}
