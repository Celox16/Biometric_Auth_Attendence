package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ModifySugang extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName;
    private Button[] weeks = new Button[5];
    private SubjectListAdapter subjectListAdapter;
    private List<SubjectList> listViewBtnItems;
    private ListView listView;

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

        // get user information from before activity
        Intent intentByBeforeActivity = getIntent();
        String userID = intentByBeforeActivity.getStringExtra("userID");
        String userMajor = intentByBeforeActivity.getStringExtra("userMajor");
        String userName = intentByBeforeActivity.getStringExtra("userName");
        int studentNumber = intentByBeforeActivity.getIntExtra("studentNumber", 0);

        // set ListView Adapter
        subjectListAdapter = new SubjectListAdapter(getApplicationContext(), listViewBtnItems);
        listView.setAdapter(subjectListAdapter);

        // get ListView subject list data from database
        Intent intentByDatabase = getIntent();
        try{
            JSONObject jsonObject = new JSONObject(intentByDatabase.getStringExtra("subjectList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            int subjectCode;
            String subjectName, dayOfTheWeek, professor, startTime, endTime, bluetoothName;
            while(count<jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                subjectCode = object.getInt("subjectCode");
                subjectName = object.getString("subjectName");
                dayOfTheWeek = object.getString("dayOfTheWeek");
                professor = object.getString("professor");
                startTime = object.getString("startTime");
                endTime = object.getString("endTime");
                bluetoothName = object.getString("bluetoothName");


            }
        }catch (Exception e){
            e.printStackTrace();
        }


        // set TextView on the tab
        tv_userName.setText(userName);
        tv_major.setText(userMajor);
        tv_stuNum.setText(""+studentNumber);
    }
}