package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class AttendanceSearch extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName;
    private ListView listView;
    SubjectListAdapterTest subjectListAdapterTest;
    public static ArrayList<SubjectList> subjectListArrayList = new ArrayList<>();
    String url = "http://125.141.27.3/biometric_auth/getSubjectListFromStudentTable.php";
    SubjectList subjectList;

    String userID, userMajor, userName;
    int studentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_search);

        // find textview values
        tv_major = findViewById(R.id.tv_attendanceSearch_major);
        tv_stuNum = findViewById(R.id.tv_attendanceSearch_stuNum);
        tv_userName = findViewById(R.id.tv_attendanceSearch_userName);

        listView = findViewById(R.id.ls_attendanceSearch_subjectList);
        subjectListAdapterTest = new SubjectListAdapterTest(AttendanceSearch.this, subjectListArrayList);
        listView.setAdapter(subjectListAdapterTest);

        retrieveData();

        // get user information from before activity
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userName = intent.getStringExtra("userName");
        userMajor = intent.getStringExtra("userMajor");
        studentNumber = intent.getIntExtra("studentNumber", 0);

        // set textview on the tab
        tv_major.setText(userMajor);
        tv_stuNum.setText(""+studentNumber);
        tv_userName.setText(userName);
    }

    public void retrieveData(){
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        subjectListArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int subjectCode = object.getInt("subjectCode");
                                    String subjectName = object.getString("subjectName");
                                    String dayOfTheWeek = object.getString("dayOfTheWeek");
                                    String professor = object.getString("professor");
                                    String startTime = object.getString("startTime");
                                    String endTime = object.getString("endTime");
                                    String bluetoothName = object.getString("bluetoothName");

                                    subjectList = new SubjectList(subjectCode, subjectName, dayOfTheWeek, professor, startTime, endTime, bluetoothName);
                                    subjectListArrayList.add(subjectList);
                                    subjectListAdapterTest.notifyDataSetChanged();
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();

                params.put("userID", userID);

                return params;
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intentAttendanceResult = new Intent(AttendanceSearch.this, AttendanceResult.class);

                // send user information
                intentAttendanceResult.putExtra("userID", userID);
                intentAttendanceResult.putExtra("userName", userName);
                intentAttendanceResult.putExtra("userMajor", userMajor);
                intentAttendanceResult.putExtra("studentNumber", studentNumber);

                // send selected subject information
                intentAttendanceResult.putExtra("subjectName", subjectListArrayList.get(position).getSubjectName());
                intentAttendanceResult.putExtra("subjectCode", subjectListArrayList.get(position).getSubjectCode());
                intentAttendanceResult.putExtra("dayOfTheWeek", subjectListArrayList.get(position).getDayOfTheWeek());
                intentAttendanceResult.putExtra("professor", subjectListArrayList.get(position).getProfessor());
                intentAttendanceResult.putExtra("startTime", subjectListArrayList.get(position).getStartTime());
                intentAttendanceResult.putExtra("endTime", subjectListArrayList.get(position).getEndTime());
                intentAttendanceResult.putExtra("bluetoothName", subjectListArrayList.get(position).getBluetoothName());

                startActivity(intentAttendanceResult);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}