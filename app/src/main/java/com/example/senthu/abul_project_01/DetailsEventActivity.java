package com.example.senthu.abul_project_01;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class DetailsEventActivity extends AppCompatActivity {
    final String CHANNEL_ID = "Important_mail_channel";
    private ActionBar mActionBar;
    TextView dtype,daddress,dlocation,ddate,dtime,devent_id;
    Button accept,reset,back;
    NotificationManagerCompat mNotificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_event);
        getSupportActionBar().hide();
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        dtype=findViewById(R.id.de_type);
       daddress=findViewById(R.id.de_address);
       dtime=findViewById(R.id.de_time);
       dlocation=findViewById(R.id.de_location);
       ddate=findViewById(R.id.de_date);
       devent_id=findViewById(R.id.de_event_id);


       accept=findViewById(R.id.btn_accept);
       reset=findViewById(R.id.btn_reset);
       back=findViewById(R.id.btn_back);

        mNotificationManagerCompat = NotificationManagerCompat.from(DetailsEventActivity.this);


     Intent intent= getIntent();
     String address=intent.getStringExtra("address");
     String type=intent.getStringExtra("type");
     String time=intent.getStringExtra("time");
     String location=intent.getStringExtra("location");
        Double event_id=intent.getDoubleExtra("event_id",0);
        String date=date = getIntent().getStringExtra("date");



     if (intent!=null){
         mActionBar.setTitle(type);
         daddress.setText(address);
         dtype.setText(type);
         dtime.setText(time+"  (GMT+5:30)");
         dlocation.setText(location);
         ddate.setText(date);
         devent_id.setText(String.valueOf(event_id));
     }


     dlocation.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             gotourl(location);
         }
     });

     back.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent back= new Intent(DetailsEventActivity.this,EventActivity.class);
             startActivity(back);
         }
     });

     reset.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             AlertDialog.Builder builder= new AlertDialog.Builder(DetailsEventActivity.this);
             builder.setTitle("Aleart!")
                     .setMessage("do you want close")
                     .setCancelable(false)
                     .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {
                             Toast.makeText(DetailsEventActivity.this, "OK", Toast.LENGTH_SHORT).show();
                             dialogInterface.cancel();

                         }
                     })
                     .setNegativeButton("no", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {


                         }
                     });

             AlertDialog alertDialog= builder.create();
             alertDialog.show();
         }
     });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }




    private void gotourl(String location) {
        Uri uri= Uri.parse(location);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

}