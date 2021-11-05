package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewUserInfo extends AppCompatActivity {
    // view variables
    private TextView tv_major, tv_stuNum, tv_userName;
    private TextView major, stuNum, semester, address, status, birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_info);

        // find textview values
        tv_major = findViewById(R.id.tv_view_major);
        tv_stuNum = findViewById(R.id.tv_view_stuNum);
        tv_userName = findViewById(R.id.tv_view_userName);

        // find information values
        major = findViewById(R.id.tv_viewInfo_major);
        stuNum = findViewById(R.id.tv_viewInfo_studentNumber);
        semester = findViewById(R.id.tv_viewInfo_semester);
        address = findViewById(R.id.tv_viewInfo_address);
        status = findViewById(R.id.tv_viewInfo_status);
        birth = findViewById(R.id.tv_viewInfo_birth);

        // get user information
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userName = intent.getStringExtra("userName");
        String userMajor = intent.getStringExtra("userMajor");
        int studentNumber = intent.getIntExtra("studentNumber", 0);

        String userAddress = intent.getStringExtra("userAddress");
        String userStatus = intent.getStringExtra("userStatus");
        String userBirth = intent.getStringExtra("userBirth");
        String userSemester = intent.getStringExtra("userSemester");

        // set textview on the tab
        tv_userName.setText(userName);
        tv_major.setText(userMajor);
        tv_stuNum.setText(""+studentNumber);

        address.setText(userAddress);
        status.setText(userStatus);
        birth.setText(userBirth);
        semester.setText(userSemester);

    }
}