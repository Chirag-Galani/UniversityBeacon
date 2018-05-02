package beacon.university.com.universitybeacon.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

import beacon.university.com.universitybeacon.R;
import beacon.university.com.universitybeacon.adapter.AnnouncementAdapter;
import beacon.university.com.universitybeacon.model.Announcement;

public class AnnouncementActivity extends AppCompatActivity {

    String userEmail;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        userEmail = getIntent().getStringExtra("USER_EMAIL");
        mRecyclerView = findViewById(R.id.recyclerViewNotice);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        fetchNotice();
    }

    public void fetchNotice() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://peaceful-garden-79339.herokuapp.com/announcement";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            TypeFactory typeFactory = objectMapper.getTypeFactory();
                            List<Announcement> someClassList = objectMapper.readValue(response, typeFactory.constructCollectionType(List.class, Announcement.class));
//                            for (AnnouncementActivity a: someClassList){
//                                Log.e("AA",a.getDayString());
//                                Log.e("AA",a.getTimings());
//                            }
                            AnnouncementAdapter mAdapter = new AnnouncementAdapter(someClassList);
                            mRecyclerView.setAdapter(mAdapter);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("beacon_name", "announcement");
                params.put("email_id", userEmail);
                return params;
            }
        };
        queue.add(postRequest);

    }
}
