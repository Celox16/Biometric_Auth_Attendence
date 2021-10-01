package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AddSubject extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName, tv_subjectName;
    private Button btn_addSubject, btn_goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        // find textview values
        tv_major = findViewById(R.id.tv_addSubject_major);
        tv_stuNum = findViewById(R.id.tv_addSubject_stuNum);
        tv_userName = findViewById(R.id.tv_addSubject_userName);
        tv_subjectName = findViewById(R.id.tv_addSubject_subjectName);

        // fin button values
        btn_addSubject = findViewById(R.id.btn_addSubject_addSubject);
        btn_goBack = findViewById(R.id.btn_addSubject_goBack);

        // get user information
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userMajor = intent.getStringExtra("userMajor");
        String userName = intent.getStringExtra("userName");
        int studentNumber = intent.getIntExtra("studentNumber", 0);

        // get subject information
        String subjectName = intent.getStringExtra("subjectName");
        int subjectCode = intent.getIntExtra("subjectCode", 0);
        String dayOfTheWeek = intent.getStringExtra("dayOfTheWeek");
        String professor = intent.getStringExtra("professor");
        String startTime = intent.getStringExtra("startTime");
        String entTime = intent.getStringExtra("entTime");
        String bluetoothName = intent.getStringExtra("bluetoothName");

        // set textview on the tab
        tv_userName.setText(userName);
        tv_major.setText(userMajor);
        tv_stuNum.setText("" + studentNumber);
        tv_subjectName.setText(subjectName);

    }
}