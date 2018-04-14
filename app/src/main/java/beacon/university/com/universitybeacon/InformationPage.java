package beacon.university.com.universitybeacon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class InformationPage extends AppCompatActivity {

    private static TextView tvHeading;
    private static TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_page);
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        tvContent = (TextView) findViewById(R.id.tvContent);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        Intent intent = getIntent();
        String beacon_url = intent.getStringExtra("URL");
        String temp_url = "https://www.google.com/url?q=https://github.iu.edu/raw/cgalani/test_json/master/ppp.json?token%3DAAAhsQ0vM840wTdCYtcfEOWDh6CLyp5jks5a2mvGwA%253D%253D&sa=D&source=hangouts&ust=1523745486908000&usg=AFQjCNEGGJg3y684vsksVFKN-V_PjEyUQw";
        /*
        StringRequest request = new StringRequest(Request.Method.GET, temp_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //tvContent.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvContent.setText(error.toString());
            }
        });

        requestQueue.add(request);
        */
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, temp_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                tvContent.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvContent.setText(error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
