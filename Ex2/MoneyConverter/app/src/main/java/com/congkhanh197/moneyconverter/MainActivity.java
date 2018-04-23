package com.congkhanh197.moneyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class MainActivity extends AppCompatActivity {

    Double usd, vnd,jpy, cny, sgd;
    Double eur = 1.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editTextInput = findViewById(R.id.et_input);
        final EditText editTextOutput = findViewById(R.id.et_output);
        final Spinner spinnerInput = findViewById(R.id.sp_input);
        final Spinner spinnerOutput = findViewById(R.id.sp_output);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInput.setAdapter(adapter);
        spinnerOutput.setAdapter(adapter);

        String url_rates = "http://data.fixer.io/api/latest?access_key=a37bb588614b312f2aeb99bb936ecbe5&symbols=USD,VND,JPY,CNY,SGD";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_rates,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        usd = obj.getJSONObject("rates").getDouble("USD");
                        vnd = obj.getJSONObject("rates").getDouble("VND");
                        jpy = obj.getJSONObject("rates").getDouble("JPY");
                        cny = obj.getJSONObject("rates").getDouble("CNY");
                        sgd = obj.getJSONObject("rates").getDouble("SGD");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Get rates",error.toString());
                }
            }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        spinnerInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String input = editTextInput.getText().toString();
                if(!input.matches("")){
                    Double out = Double.parseDouble(input)*getRates(spinnerInput.getSelectedItem().toString(),spinnerOutput.getSelectedItem().toString());
                    editTextOutput.setText(out.toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerOutput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String output = editTextOutput.getText().toString();
                if(!output.matches("")){
                    Double out = Double.parseDouble(output)*getRates(spinnerInput.getSelectedItem().toString(),spinnerOutput.getSelectedItem().toString());
                    editTextOutput.setText(out.toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().matches("")) {
                    Double out = Double.parseDouble(s.toString())*getRates(spinnerInput.getSelectedItem().toString(),spinnerOutput.getSelectedItem().toString());
                    editTextOutput.setText(out.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    Double getRates(String inputType, String outType){
        Double result = 1.0;
        switch (inputType){
            case "USD":
                result = usd;
                break;
            case "VND":
                result = vnd;
                break;
            case "JPY":
                result = jpy;
                break;
            case "CNY":
                result = cny;
                break;
            case "EUR":
                result = eur;
                break;
            case "SGD":
                result = sgd;
                break;
        }
        switch (outType){
            case "USD":
                result = usd/result;
                break;
            case "VND":
                result = vnd/result;
                break;
            case "JPY":
                result = jpy/result;
                break;
            case "CNY":
                result = cny/result;
                break;
            case "EUR":
                result = eur/result;
                break;
            case "SGD":
                result = sgd/result;
                break;
        }
        return result;
    }

}
