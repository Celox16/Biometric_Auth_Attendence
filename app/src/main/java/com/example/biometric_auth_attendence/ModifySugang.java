package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;

public class ModifySugang extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName;
    private Button[] weeks = new Button[5];
    private ListView listView;
    SubjectListAdapterTest subjectListAdapterTest;
    public static ArrayList<SubjectList> subjectListArrayList = new ArrayList<>();
    String url = Variables.address + "getDBTest.php";
    SubjectList subjectList;

    String userID, userMajor, userName;
    int studentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_sugang);

        // find view by values
        tv_major = findViewById(R.id.tv_modifySugang_major);
        tv_stuNum = findViewById(R.id.tv_modifySugang_stuNum);
        tv_userName = findViewById(R.id.tv_modifySugang_userName);

        weeks[0] = findViewById(R.id.btn_modifySugang_MON);
        weeks[1] = findViewById(R.id.btn_modifySugang_TUE);
        weeks[2] = findViewById(R.id.btn_modifySugang_WED);
        weeks[3] = findViewById(R.id.btn_modifySugang_THU);
        weeks[4] = findViewById(R.id.btn_modifySugang_FRI);

        listView = findViewById(R.id.ls_modifySugang_subjectList);
        subjectListAdapterTest = new SubjectListAdapterTest(ModifySugang.this, subjectListArrayList);
        listView.setAdapter(subjectListAdapterTest);

        retrieveData();

        // get user information from before activity
        Intent intentByBeforeActivity = getIntent();
        userID = intentByBeforeActivity.getStringExtra("userID");
        userMajor = intentByBeforeActivity.getStringExtra("userMajor");
        userName = intentByBeforeActivity.getStringExtra("userName");
        studentNumber = intentByBeforeActivity.getIntExtra("studentNumber", 0);

        // set textview on the tab
        tv_major.setText(userMajor);
        tv_stuNum.setText("" + studentNumber);
        tv_userName.setText(userName);
    }

    public void retrieveData() {
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
                Toast.makeText(ModifySugang.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intentAddSubject = new Intent(ModifySugang.this, AddSubject.class);

                // send user information
                intentAddSubject.putExtra("userID", userID);
                intentAddSubject.putExtra("userName", userName);
                intentAddSubject.putExtra("userMajor", userMajor);
                intentAddSubject.putExtra("studentNumber", studentNumber);

                // send selected subject information
                intentAddSubject.putExtra("subjectName", subjectListArrayList.get(position).getSubjectName());
                intentAddSubject.putExtra("subjectCode", subjectListArrayList.get(position).getSubjectCode());
                intentAddSubject.putExtra("dayOfTheWeek", subjectListArrayList.get(position).getDayOfTheWeek());
                intentAddSubject.putExtra("professor", subjectListArrayList.get(position).getProfessor());
                intentAddSubject.putExtra("startTime", subjectListArrayList.get(position).getStartTime());
                intentAddSubject.putExtra("entTime", subjectListArrayList.get(position).getEndTime());
                intentAddSubject.putExtra("bluetoothName", subjectListArrayList.get(position).getBluetoothName());
                startActivity(intentAddSubject);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}