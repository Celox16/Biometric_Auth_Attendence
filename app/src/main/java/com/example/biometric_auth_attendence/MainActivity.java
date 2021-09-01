package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName;
    private Button btn_attendance, btn_check, btn_userInfoModify, btn_sugangInfoModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_major = findViewById(R.id.tv_main_major);
        tv_stuNum = findViewById(R.id.tv_main_stuNum);
        tv_userName = findViewById(R.id.tv_main_userName);

        btn_attendance = findViewById(R.id.btn_main_attendance);
        btn_check = findViewById(R.id.btn_main_check);
        btn_userInfoModify = findViewById(R.id.btn_main_userInfoModify);
        btn_sugangInfoModify = findViewById(R.id.btn_main_sugangInfoModify);

        Intent intent = getIntent();
        String userMajor = intent.getStringExtra("userMajor");
        String userName = intent.getStringExtra("userName");
        int studentNumber = intent.getIntExtra("studentNumber", 0);

        tv_userName.setText(userName);
        tv_major.setText(userMajor);
        tv_stuNum.setText("" + studentNumber);
    }
}