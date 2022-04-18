package com.example.senthu.abul_project_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity  {
    final String CHANNEL_ID = "Important_mail_channel";
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event>eventList;
     private ActionBar actionBar;
     Button logout;
    private SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Handler handler;
    NotificationManagerCompat mNotificationManagerCompat;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        // getSupportActionBar().hide();
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyerviewlist);
        rubEverysecound();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        preferences = getSharedPreferences("myprefrence", MODE_PRIVATE);
        mNotificationManagerCompat = NotificationManagerCompat.from(EventActivity.this);

        editor = preferences.edit();
    }

    private void contaxt() {
    }

    private void rubEverysecound() {
        handler= new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                eventList = new ArrayList<>();
                Loadevent();
                handler.postDelayed(this,10000);

            }
        };
        handler.post(run);

    }


    private void notifyDataSetChanged() {

    }

    private void Loadevent() {

        JsonArrayRequest request=new JsonArrayRequest(getBaseUrl(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                int i =0;

                while (i<array.length()){
                    try {

                        JSONObject object= array.getJSONObject(i);
                        String type= object.getString("type").trim();
                        String address= object.getString("address").trim();
                        String location= object.getString("location").trim();
                        String date= object.getString("date").trim();
                        String time=object.getString("time").trim();
                        int event_id=object.getInt("event_id");

                        Event event= new Event();
                        event.setDate(date);
                        event.setType(type);
                        event.setAddress(address);
                        event.setLocation(location);
                        event.setTime(time);
                        event.setEvent_id(event_id);
                        eventList.add(event);





                    } catch (JSONException e) {
                        e.printStackTrace();


                    }

                    i++;
                    if(i==6){
                        createSimpleNotification(getString(R.string.simple_notification_title), getString(R.string.simple_notification_text), 1);
                    }
                }

                setupData(eventList);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EventActivity.this, error.toString(), Toast.LENGTH_SHORT).show();


            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(EventActivity.this);
        requestQueue.add(request);

    }

    private void createSimpleNotification(String title, String text, int notificationId)  {

        mNotificationManagerCompat.cancelAll();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_baseline_notifications_24);


         Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                //Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        mNotificationManagerCompat.notify(notificationId, notification);
    }


    private void setupData(List<Event> eventList) {
        eventAdapter= new EventAdapter(EventActivity.this,eventList);
        recyclerView.setAdapter(eventAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.notifi){


            Toast.makeText(EventActivity.this,"YOU Have't NODIFICATION..!",Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.logout){


            editor.clear();
            editor.commit();
            Intent intent = new Intent(EventActivity.this,MainActivity.class);
            startActivity(intent);

            Toast.makeText(EventActivity.this,"Logout....",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    private String getBaseUrl (){

        //   return "https://hirushautomation.com/mb/event_details.php";


        return "http://"+getResources().getString(R.string.machine_ip_address)+"/abul_01/event_details.php";
    }



}