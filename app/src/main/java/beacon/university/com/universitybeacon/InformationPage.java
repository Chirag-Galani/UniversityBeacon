package beacon.university.com.universitybeacon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beacon.university.com.universitybeacon.model.Food_Court;

public class InformationPage extends AppCompatActivity {

    private static TextView tvHeading;
    private static TextView tvContent;
    private List<Food_Court> menu;
    private Type listType = new TypeToken<List<Food_Court>>(){}.getType();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_page);
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        tvContent = (TextView) findViewById(R.id.tvContent);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        Intent intent = getIntent();
        String beacon_url = intent.getStringExtra("URL");
        String temp_url = "https://peaceful-garden-79339.herokuapp.com/foodCourtSchedule";

        //Setting up JSON Request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, temp_url, null,
           new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Successful Response
                //tvContent.setText(response.toString());
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                try {
                    List<Food_Court> pojoList = new ArrayList<>();
                    for(int i = 0; i < response.length(); i++)
                    {
                        //JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String jsonObject = response.getString(i);

                        Food_Court obj = objectMapper.readValue(jsonObject,Food_Court.class);
                        pojoList.add(obj);
                    }
                    Log.e("PJOJO",pojoList.get(0).toString());
                } catch (Exception e)
                {
                    Log.w("Exception = ","" + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvContent.setText(error.toString());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap();
                headers.put("beacon_name", "gresham");
                return headers;
            }
        };

        requestQueue.add(jsonArrayRequest);
    }
}
