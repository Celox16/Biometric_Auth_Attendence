package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ModifySugang extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName;
    private Button[] weeks = new Button[5];
    private SubjectListAdapter subjectListAdapter;
    private List<ListViewBtnItem> listViewBtnItems;

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

        // get user information
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userMajor = intent.getStringExtra("userMajor");
        String userName = intent.getStringExtra("userName");
        int studentNumber = intent.getIntExtra("studentNumber", 0);

        // set TextView on the tab
        tv_userName.setText(userName);
        tv_major.setText(userMajor);
        tv_stuNum.setText(""+studentNumber);
    }
}