package com.mobile.congkhanh197.mobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etPositionX = findViewById(R.id.et_posX);
        final EditText etPositionY = findViewById(R.id.et_posY);
        final EditText etPositionX1 = findViewById(R.id.et_posX1);
        final EditText etPositionX2 = findViewById(R.id.et_posX2);
        final EditText etPositionY1 = findViewById(R.id.et_posY1);
        final EditText etPositionY2 = findViewById(R.id.et_posY2);
        final TextView tvSee = findViewById(R.id.tv_info);
        TextView tvDistant = findViewById(R.id.tv_distant);
        Button btSee = findViewById(R.id.bt_see);
        Button btDistant = findViewById(R.id.bt_see);
        btSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(etPositionX.getText().toString().matches("")) && !(etPositionY.getText().toString().matches(""))){
                    String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
                            +Double.parseDouble(etPositionX.getText().toString())
                            +","
                            +Double.parseDouble(etPositionY.getText().toString())
                            +"&key=AIzaSyAuwpQoYgpe6eRNw1c9tAsLO8UjH_qP_-E";
                    //String testURL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961453&key=AIzaSyAuwpQoYgpe6eRNw1c9tAsLO8UjH_qP_-E";
                    etPositionX.setEnabled(false);
                    etPositionY.setEnabled(false);
                    //Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
                    final ProgressBar progressBar = findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    etPositionX.setEnabled(true);
                                    etPositionY.setEnabled(true);
                                    tvSee.setText("ok");
                                    try {
                                        Toast.makeText(getApplicationContext(),"covert",Toast.LENGTH_LONG).show();
                                        JSONObject obj = new JSONObject(response);
                                        JSONArray list = obj.getJSONArray("results");
                                        if(list.length() == 0){
                                            tvSee.setText("This position is not exist");
                                        }
                                        JSONObject name = list.getJSONObject(0);
                                        tvSee.setText(name.getString("formatted_address"));


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //tvSee.setText("+1");
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                }else{
                    Toast.makeText(getApplicationContext(),"Please fill right information!",Toast.LENGTH_LONG).show();
                }
            }
        });

        btDistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(etPositionX1.getText().toString().matches("")) && !(etPositionY1.getText().toString().matches(""))
                        &&!(etPositionX2.getText().toString().matches("")) && !(etPositionY2.getText().toString().matches(""))){
                    String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
                            +Double.parseDouble(etPositionX.getText().toString())
                            +","
                            +Double.parseDouble(etPositionY.getText().toString())
                            +"&key=AIzaSyAuwpQoYgpe6eRNw1c9tAsLO8UjH_qP_-E";
                    //String testURL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961453&key=AIzaSyAuwpQoYgpe6eRNw1c9tAsLO8UjH_qP_-E";
                    etPositionX.setEnabled(false);
                    etPositionY.setEnabled(false);
                    //Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
                    final ProgressBar progressBar = findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    etPositionX.setEnabled(true);
                                    etPositionY.setEnabled(true);
                                    tvSee.setText("ok");
                                    try {
                                        Toast.makeText(getApplicationContext(),"covert",Toast.LENGTH_LONG).show();
                                        JSONObject obj = new JSONObject(response);
                                        JSONArray list = obj.getJSONArray("results");
                                        if(list.length() == 0){
                                            tvSee.setText("This position is not exist");
                                        }
                                        JSONObject name = list.getJSONObject(0);
                                        tvSee.setText(name.getString("formatted_address"));


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                }else{
                    Toast.makeText(getApplicationContext(),"Please fill right information!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
