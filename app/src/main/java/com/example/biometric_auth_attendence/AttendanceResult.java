package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AttendanceResult extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName;
    private ListView listView;
    AttendanceResultAdapter attendanceResultAdapter;
    public static ArrayList<AttendanceResultList> attendanceResultListArrayList = new ArrayList<>();
    String url = "http://125.141.27.3/biometric_auth/temp?.php";
    AttendanceResultList attendanceResultList;

    String userID, userMajor, userName;
    int studentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_result);

        //find textview values
        tv_major = findViewById(R.id.tv_attendanceResult_major);
        tv_stuNum = findViewById(R.id.tv_attendanceResult_stuNum);
        tv_userName = findViewById(R.id.tv_attendanceResult_userName);

        listView = findViewById(R.id.ls_attendanceResult_attList);
        listView.setAdapter(attendanceResultAdapter);

        // TODO : retrieveData()

        // get user information from before activity
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userName = intent.getStringExtra("userName");
        userMajor = intent.getStringExtra("userMajor");
        studentNumber = intent.getIntExtra("studentNumber", 0);

        // set textview on the tab
        tv_major.setText(userMajor);
        tv_stuNum.setText(studentNumber);
        tv_userName.setText(userName);
    }

    public void retrieveData(){

    }
}