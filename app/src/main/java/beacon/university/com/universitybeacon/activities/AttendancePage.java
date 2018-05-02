package beacon.university.com.universitybeacon.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import beacon.university.com.universitybeacon.adapter.CafeScheduleAdapter;
import beacon.university.com.universitybeacon.adapter.ClassScheduleAdapter;
import beacon.university.com.universitybeacon.callback.ServerCallback;
import beacon.university.com.universitybeacon.model.CafeSchedule;
import beacon.university.com.universitybeacon.model.ClassDetails;
import beacon.university.com.universitybeacon.model.ClassSchedule;
import beacon.university.com.universitybeacon.model.StudentDetails;

import static beacon.university.com.universitybeacon.app.Constants.BASE_URL;
import static beacon.university.com.universitybeacon.app.Constants.CLASS_STARTED_URL;
import static beacon.university.com.universitybeacon.app.Constants.IS_PROF_URL;
import static beacon.university.com.universitybeacon.app.Constants.IS_STUD_URL;
import static beacon.university.com.universitybeacon.app.Constants.MARK_ATTENDANCE_URL;


public class AttendancePage extends AppCompatActivity {
    String className;
    String email = "randall@iu.edu";
//    String email="cgalani@iu.edu";

    String beaconName = "LH-1000";
    CafeScheduleAdapter mClassTimetableAdapter;
    ClassScheduleAdapter mClassScheduleAdapter;
    TextView textViewClassInfoName;
    TextView textViewClassInfoDept;
    ObjectMapper objectMapper;
    TypeFactory typeFactory;
    Button markAttendanceButton;
    private RecyclerView mClassTimetableRecyclerView;
    private RecyclerView mClassScheduleRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_page);
        textViewClassInfoName = findViewById(R.id.textview_class_info_name);
        textViewClassInfoDept = findViewById(R.id.textview_class_info_dept);
        markAttendanceButton = findViewById(R.id.buttonMarkAttendance);


        RecyclerView.LayoutManager mClassTimetableLayoutManager = new LinearLayoutManager(this);
        mClassTimetableRecyclerView = findViewById(R.id.recyclerViewClassTimeTable);
        mClassTimetableRecyclerView.setHasFixedSize(true);
        mClassTimetableRecyclerView.setLayoutManager(mClassTimetableLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mClassTimetableRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mClassTimetableRecyclerView.addItemDecoration(dividerItemDecoration);

        RecyclerView.LayoutManager mClassScheduleLayoutManager = new LinearLayoutManager(this);
        mClassScheduleRecyclerView = findViewById(R.id.recyclerViewClassSchedule);
        mClassScheduleRecyclerView.setHasFixedSize(true);
        mClassScheduleRecyclerView.setLayoutManager(mClassScheduleLayoutManager);
        dividerItemDecoration = new DividerItemDecoration(mClassScheduleRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);

        mClassScheduleRecyclerView.addItemDecoration(dividerItemDecoration);


        objectMapper = new ObjectMapper();
        typeFactory = objectMapper.getTypeFactory();

        initClass();
        checkRole(BASE_URL + IS_PROF_URL, new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Boolean value = Boolean.parseBoolean(result);
                if (value) {
                    markAttendanceButton.setText("Initiate Class");
                }
            }
        });

        fetchClassTimetable();
        fetchClassSchedule();


//        requestWithSomeHttpHeaders();
    }

    public void markAttendanceButtonClick(final View v) {
        final ServerCallback isClassStartedServerCallback = new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e("AA", "isClassStartedServerCallback=" + result);
                Boolean value = Boolean.parseBoolean(result);
                if (value)
                    Toast.makeText(AttendancePage.this, "Class has been initiated for attendance", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AttendancePage.this, "Oops! Class has already been initiated for attendance", Toast.LENGTH_LONG).show();
            }
        };
        final ServerCallback attendanceMarkCallback = new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e("AA", "attendanceMarkCallback=" + result);
                Boolean value = Boolean.parseBoolean(result);
                if (value)
                    Toast.makeText(AttendancePage.this, "Your attendance has been marked", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AttendancePage.this, "Oops! Your attendance has already been marked.", Toast.LENGTH_LONG).show();
            }
        };
        final ServerCallback isStudentCallback = new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e("AA", "isStudentCallback=" + result);
                Boolean value = Boolean.parseBoolean(result);
                if (value) {
                    fetchBooleanData(BASE_URL + MARK_ATTENDANCE_URL, attendanceMarkCallback);
                } else {
                    Toast.makeText(AttendancePage.this, "You are not registered for this course. Please contact administrator", Toast.LENGTH_LONG).show();
                }
            }
        };

        ServerCallback isProfServerCallback = new ServerCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e("AA", "isProfServerCallback=" + result);
                Boolean value = Boolean.parseBoolean(result);
                if (value) {
                    fetchBooleanData(BASE_URL + CLASS_STARTED_URL, isClassStartedServerCallback);
                } else {
                    fetchBooleanData(BASE_URL + IS_STUD_URL, isStudentCallback);
                }
            }
        };
        checkRole(BASE_URL + IS_PROF_URL, isProfServerCallback);
    }

    public void initClass() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://peaceful-garden-79339.herokuapp.com/className";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            ClassDetails classDetails = objectMapper.readValue(response, ClassDetails.class);
                            className = classDetails.getClassName();
                            textViewClassInfoName.setText(classDetails.getClassName());
                            textViewClassInfoDept.setText(classDetails.getDepartmentId());
//                            Log.e("AA",className);
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

                return fetchHeader();
            }
        };
        queue.add(postRequest);

    }

    public void checkRole(String url, final ServerCallback callback) {
        Log.e("SS", url);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("ERROR", "error => " + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return fetchHeader();
            }
        };
        queue.add(postRequest);
    }

    public void fetchBooleanData(String url, final ServerCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                        callback.onSuccess(error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return fetchHeader();
            }
        };
        queue.add(postRequest);
    }

    public void requestWithSomeHttpHeaders() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://peaceful-garden-79339.herokuapp.com/getStudents";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            List<StudentDetails> someClassList = objectMapper.readValue(response, typeFactory.constructCollectionType(List.class, StudentDetails.class));
                            for (StudentDetails a : someClassList) {
                                Log.e("AA", a.getEmailId());
                            }
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
                return fetchHeader();
            }
        };
        queue.add(postRequest);

    }

    public void fetchClassTimetable() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://peaceful-garden-79339.herokuapp.com/classTimings";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            List<CafeSchedule> someClassList = objectMapper.readValue(response, typeFactory.constructCollectionType(List.class, CafeSchedule.class));
                            for (CafeSchedule a : someClassList) {
                                Log.e("AA", a.getDayString());
                                Log.e("AA", a.getTimings());
                            }
                            mClassTimetableAdapter = new CafeScheduleAdapter(someClassList);
                            mClassTimetableRecyclerView.setAdapter(mClassTimetableAdapter);

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
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                return fetchHeader();
            }
        };
        queue.add(postRequest);
    }

    public void fetchClassSchedule() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://peaceful-garden-79339.herokuapp.com/classSchedule";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            List<ClassSchedule> someClassList = objectMapper.readValue(response, typeFactory.constructCollectionType(List.class, ClassSchedule.class));
                            mClassScheduleAdapter = new ClassScheduleAdapter(someClassList);
                            mClassScheduleRecyclerView.setAdapter(mClassScheduleAdapter);
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
                        Log.e("ERROR", "error => ");
                        error.printStackTrace();
//                        Log.e
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
//                return fetchHeader();
                Map<String, String> params = new HashMap<String, String>();
                params.put("beacon_name", beaconName);
                params.put("email_id", email);
                return params;
            }
        };
        queue.add(postRequest);
    }

    private Map<String, String> fetchHeader() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("beacon_name", beaconName);
        params.put("email_id", email);
        return params;
    }
}
