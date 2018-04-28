package beacon.university.com.universitybeacon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beacon.university.com.universitybeacon.model.Example;

public class AttendancePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_page);
        requestWithSomeHttpHeaders();

    }
    public void requestWithSomeHttpHeaders() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://peaceful-garden-79339.herokuapp.com/getStudents";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        TypeFactory typeFactory = objectMapper.getTypeFactory();
                            List<Example> someClassList = objectMapper.readValue(response, typeFactory.constructCollectionType(List.class, Example.class));
                            for (Example a: someClassList){
                                Log.e("AA",a.getEmailId());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("beacon_name", "LH-1000");
                params.put("email_id", "cgalani@iu.edu");

                return params;
            }
        };
        queue.add(postRequest);

    }

}
