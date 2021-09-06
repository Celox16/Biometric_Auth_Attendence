package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName;
    private Button btn_attendance, btn_check, btn_userInfoModify, btn_sugangInfoModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find textview values
        tv_major = findViewById(R.id.tv_main_major);
        tv_stuNum = findViewById(R.id.tv_main_stuNum);
        tv_userName = findViewById(R.id.tv_main_userName);

        btn_attendance = findViewById(R.id.btn_main_attendance);
        btn_check = findViewById(R.id.btn_main_check);
        btn_userInfoModify = findViewById(R.id.btn_main_userInfoModify);
        btn_sugangInfoModify = findViewById(R.id.btn_main_sugangInfoModify);

        // get user info
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userMajor = intent.getStringExtra("userMajor");
        String userName = intent.getStringExtra("userName");
        int studentNumber = intent.getIntExtra("studentNumber", 0);

        // set textview on the tab
        tv_userName.setText(userName);
        tv_major.setText(userMajor);
        tv_stuNum.setText("" + studentNumber);

        // button click
        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make a new intent and parse user info data
                Intent intentAttendanceList = new Intent(MainActivity.this, AttendanceList.class);
                intentAttendanceList.putExtra("userID", userID);
                intentAttendanceList.putExtra("userName", userName);
                intentAttendanceList.putExtra("userMajor", userMajor);
                intentAttendanceList.putExtra("stduentNumber", studentNumber);
                startActivity(intentAttendanceList);
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make a new intent and parse user info data
            }
        });

        btn_userInfoModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make a new intent and parse user info data

            }
        });

        btn_sugangInfoModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make a new intent and parse user info data
                Intent intentModifySugang = new Intent(MainActivity.this, ModifySugang.class);
                intentModifySugang.putExtra("userID", userID);
                intentModifySugang.putExtra("userName", userName);
                intentModifySugang.putExtra("userMajor", userMajor);
                intentModifySugang.putExtra("studentNumber", studentNumber);
                startActivity(intentModifySugang);
            }
        });
    }
}