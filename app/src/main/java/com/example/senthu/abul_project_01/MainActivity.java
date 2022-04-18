package com.example.senthu.abul_project_01;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView email, password;
    Button login,sigin;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ProgressBar mProgress;



    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        preferences = getSharedPreferences("myprefrence", MODE_PRIVATE);
        editor = preferences.edit();
        if (preferences.contains("saved_name")) {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            startActivity(intent);
        } else {


            email = findViewById(R.id.et_email);
            password = findViewById(R.id.et_pass);
            login = findViewById(R.id.btn_login);
            mProgress = findViewById(R.id.progress);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    signIn(email.getText().toString(), password.getText().toString());
                }
            });

        }
    }



    private void signIn( final String email, final String password) {

        mProgress.setVisibility(View.VISIBLE);

        mRequestQueue = Volley.newRequestQueue(MainActivity.this);

        mStringRequest = new StringRequest(Request.Method.POST,
                getBaseUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");

                    if (success.equals("1")) {
                        String my_name = email;
                        String my_pass = password;
                        editor.putString("saved_name", my_name);
                        editor.putString("saved_pass", my_pass);
                        editor.commit();

                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();



                        startActivity(new Intent(MainActivity.this,EventActivity.class));
                    }
                    if (success.equals("0")) {

                        mProgress.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    mProgress.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                mProgress.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("password",password);

                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        mRequestQueue.add(mStringRequest);
    }

    private String getBaseUrl (){


       return "https://hirushautomation.com/mb/user_profile.php";

      //  return "http://"+getResources().getString(R.string.machine_ip_address)+"/abul_01/user_profile.php";
    }
}