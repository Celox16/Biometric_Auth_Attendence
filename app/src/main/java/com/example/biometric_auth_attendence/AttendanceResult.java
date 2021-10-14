package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttendanceResult extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName, tv_subjectName, tv_dayOfWeek, tv_time;
    private ListView listView;
    AttendanceResultAdapter attendanceResultAdapter;
    public static ArrayList<AttendanceResultList> attendanceResultListArrayList = new ArrayList<>();
    String url = "http://125.141.27.3/biometric_auth/attendanceResult.php";
    AttendanceResultList attendanceResultList;

    String userID, userMajor, userName, subjectName, startTime, endTime, dayOfTheWeek;
    int studentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_result);

        //find textview values
        tv_major = findViewById(R.id.tv_attendanceResult_major);
        tv_stuNum = findViewById(R.id.tv_attendanceResult_stuNum);
        tv_userName = findViewById(R.id.tv_attendanceResult_userName);

        tv_subjectName = findViewById(R.id.tv_attendanceResult_subjectName);
        tv_dayOfWeek = findViewById(R.id.tv_attendanceResult_dayOfWeek);
        tv_time = findViewById(R.id.tv_attendanceResult_time);

        listView = findViewById(R.id.ls_attendanceResult_attList);
        attendanceResultAdapter = new AttendanceResultAdapter(getApplicationContext(), attendanceResultListArrayList);
        listView.setAdapter(attendanceResultAdapter);

        retrieveData();

        // get user information from before activity
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userName = intent.getStringExtra("userName");
        userMajor = intent.getStringExtra("userMajor");
        studentNumber = intent.getIntExtra("studentNumber", 0);

        // get subject information from before activity
        subjectName = intent.getStringExtra("subjectName");
        dayOfTheWeek = intent.getStringExtra("dayOfTheWeek");
        startTime = intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");


        // set textview on the tab
        tv_major.setText(userMajor);
        tv_stuNum.setText("" + studentNumber);
        tv_userName.setText(userName);

        tv_subjectName.setText(subjectName);
        tv_dayOfWeek.setText("(" + dayOfTheWeek + ")");
        tv_time.setText(startTime + " ~ " + endTime);
    }

    public void retrieveData() {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        attendanceResultListArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    // set values by object
                                    String userID = object.getString("userID");
                                    int studentNumber = object.getInt("studentNumber");
                                    String userName = object.getString("userName");
                                    String arrivalTime = object.getString("arrivalTime");
                                    String status = object.getString("status");

                                    // formatting correct day, time format
                                    //  2021-10-11 15:00:06
                                    //  1234567890123456789
                                    String day = arrivalTime.substring(5, 10);
                                    String time = arrivalTime.substring(11);

                                    attendanceResultList = new AttendanceResultList(day, time, status);
                                    attendanceResultListArrayList.add(attendanceResultList);
                                    attendanceResultAdapter.notifyDataSetChanged();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("userID", userID);
                params.put("subjectName", subjectName);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}