package com.example.myapp;
import org.json.JSONException; 
import org.json.JSONObject; 
import android.util.Log; 
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast; 
import com.android.volley.Request; 
import com.android.volley.RequestQueue; 
import com.android.volley.Response; 
import com.android.volley.toolbox.JsonObjectRequest; 
import com.android.volley.toolbox.Volley; 
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {
    String Data_Text = ""; 
    
    // function for making a HTTP request using Volley and 
    private void getRequest(TextView textVie) { 
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.boredapi.com/api/activity/"; 
        
        
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {
        // Display the first 500 characters of the response string.
        try {
        JSONObject jsonObj = new JSONObject(response);
        textVie.setText("You need today doing:  " + jsonObj.getString("activity"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
        textVie.setText("That didn't work!");
        }
    });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    } 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.header);


        getRequest(textView);

    }
}
