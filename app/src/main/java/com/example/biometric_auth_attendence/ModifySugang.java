package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    String url = "http://125.141.27.3/biometric_auth/modifySugang.php";
    SubjectList subjectList;

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
        subjectListAdapterTest = new SubjectListAdapterTest(this, subjectListArrayList);
        listView.setAdapter(subjectListAdapterTest);

        retrieveData();

        // get user information from before activity
        Intent intentByBeforeActivity = getIntent();
        String userID = intentByBeforeActivity.getStringExtra("userID");
        String userMajor = intentByBeforeActivity.getStringExtra("userMajor");
        String userName = intentByBeforeActivity.getStringExtra("userName");
        int studentNumber = intentByBeforeActivity.getIntExtra("studentNumber", 0);

        // set textview on the tab
        tv_major.setText(userMajor);
        tv_stuNum.setText("" + studentNumber);
        tv_userName.setText(userName);
    }

    public void retrieveData(){
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        subjectListArrayList.clear();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if(success.equals("1")){
                                for(int i = 0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int subjectCode = object.getInt("subjectCode");
                                    String subjectName = object.getString("subjectName");
                                    String dayOfTheWeek = object.getString("dayOfTheWeek");
                                    String professor = object.getString("professor");
                                    String startTime = object.getString("startTime");
                                    String endTime = object.getString("endTime");
                                    String bluetoothName = object.getString("bluetoothName");

                                    subjectList = new SubjectList(subjectCode, subjectName, dayOfTheWeek, professor,startTime, endTime, bluetoothName);
                                    subjectListArrayList.add(subjectList);
                                    subjectListAdapterTest.notifyDataSetChanged();
                                }
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModifySugang.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}