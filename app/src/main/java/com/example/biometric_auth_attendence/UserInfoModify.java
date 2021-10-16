package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserInfoModify extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName;

    String userID, userPassword, userMajor, userName;
    int studentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_modify);

        // find textview values
        tv_major = findViewById(R.id.tv_userInfoModify_major);
        tv_userName = findViewById(R.id.tv_userInfoModify_userName);
        tv_stuNum = findViewById(R.id.tv_userInfoModify_stuNum);

        // get user information from before activity
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userPassword = intent.getStringExtra("userPassword");
        userName = intent.getStringExtra("userName");
        userMajor = intent.getStringExtra("userMajor");
        studentNumber = intent.getIntExtra("studentNumber", 0);

        // set textview on the tab
        tv_major.setText(userMajor);
        tv_stuNum.setText("" + studentNumber);
        tv_userName.setText(userName);
    }
}