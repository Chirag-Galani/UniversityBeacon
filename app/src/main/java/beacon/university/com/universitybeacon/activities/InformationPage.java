package beacon.university.com.universitybeacon.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beacon.university.com.universitybeacon.R;
import beacon.university.com.universitybeacon.adapter.CafeScheduleAdapter;
import beacon.university.com.universitybeacon.adapter.ExpandableListAdapter;
import beacon.university.com.universitybeacon.model.CafeMenu;
import beacon.university.com.universitybeacon.model.CafeSchedule;

public class InformationPage extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    CafeScheduleAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_page);
        mRecyclerView = findViewById(R.id.cafeSchedulerecyclerViewSchedule);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        expListView = findViewById(R.id.lvExp);
        fetchMenu();
        fetchSchedule();

    }

    public void fetchSchedule() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://peaceful-garden-79339.herokuapp.com/foodCourtSchedule";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            TypeFactory typeFactory = objectMapper.getTypeFactory();
                            List<CafeSchedule> someClassList = objectMapper.readValue(response, typeFactory.constructCollectionType(List.class, CafeSchedule.class));
                            for (CafeSchedule a : someClassList) {
                                Log.e("AA", a.getDayString());
                                Log.e("AA", a.getTimings());
                            }
                            mAdapter = new CafeScheduleAdapter(someClassList);
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
                params.put("beacon_name", "gresham");
                return params;
            }
        };
        queue.add(postRequest);

    }

    public void fetchMenu() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://peaceful-garden-79339.herokuapp.com/foodCourtMenu";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            TypeFactory typeFactory = objectMapper.getTypeFactory();
                            List<CafeMenu> someClassList = objectMapper.readValue(response, typeFactory.constructCollectionType(List.class, CafeMenu.class));
//                            List<String> items = Arrays.asList(CommaSeparated.split("\\s*,\\s*"));


                            for (CafeMenu a : someClassList) {
                                Log.e("AA1", a.getStation());
                                listDataHeader.add(a.getStation());
                                List<String> items = Arrays.asList(a.getMenu().split("\\s*,\\s*"));
                                List<String> comingSoon = new ArrayList<String>();
                                for (String b : items) {
                                    Log.e("AA2", b);
                                    comingSoon.add(b);
                                }
                                listDataChild.put(a.getStation(), comingSoon); // Header, Child data
                            }
                            listAdapter = new ExpandableListAdapter(InformationPage.this, listDataHeader, listDataChild);
                            expListView.setAdapter(listAdapter);
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
                params.put("beacon_name", "gresham");
                return params;
            }
        };
        queue.add(postRequest);

    }
}
