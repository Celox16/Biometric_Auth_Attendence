package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AttendanceList extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        // find textview values
        tv_major = findViewById(R.id.tv_attendanceList_major);
        tv_stuNum = findViewById(R.id.tv_attendanceList_stuNum);
        tv_userName = findViewById(R.id.tv_attendanceList_userName);

        //get user info
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userName = intent.getStringExtra("userName");
        String userMajor = intent.getStringExtra("userMajor");
        int studentNumber = intent.getIntExtra("studentNumber", 0);

        // set textview on the tab
        tv_major.setText(userMajor);
        tv_stuNum.setText("" + studentNumber);
        tv_userName.setText(userName);
    }
}