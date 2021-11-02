package com.example.biometric_auth_attendence;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    // view variables
    private TextView tv_major, tv_stuNum, tv_userName;
    private Button btn_attendance, btn_check, btn_userInfoModify, btn_sugangInfoModify;
    private ImageView imageView;

    // on back pressed variables
    private long backKeyPressedTime = 0;

    @Override
    public void onBackPressed() {
        // delete primitive onBackPressed
        // super.onBackPressed();
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로가기를 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        //
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        String userPassword = intent.getStringExtra("userPassword");
        String userMajor = intent.getStringExtra("userMajor");
        String userName = intent.getStringExtra("userName");
        int studentNumber = intent.getIntExtra("studentNumber", 0);
        // get user image
        String imageString = intent.getStringExtra("imageString");

        // set textview on the tab
        tv_userName.setText(userName);
        tv_major.setText(userMajor);
        tv_stuNum.setText("" + studentNumber);

        // button click
        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // a new intent and parse user info data
                Intent intentAttendanceList = new Intent(MainActivity.this, AttendanceList.class);
                intentAttendanceList.putExtra("userID", userID);
                intentAttendanceList.putExtra("userName", userName);
                intentAttendanceList.putExtra("userMajor", userMajor);
                intentAttendanceList.putExtra("studentNumber", studentNumber);

                startActivity(intentAttendanceList);
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //a new intent and parse user info data
                Intent intentAttendanceSearch = new Intent(getApplicationContext(), AttendanceSearch.class);
                intentAttendanceSearch.putExtra("userID", userID);
                intentAttendanceSearch.putExtra("userName", userName);
                intentAttendanceSearch.putExtra("userMajor", userMajor);
                intentAttendanceSearch.putExtra("studentNumber", studentNumber);

                startActivity(intentAttendanceSearch);
            }
        });

        btn_userInfoModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //a new intent and parse user info data
                Intent intentUserInfoModify = new Intent(getApplicationContext(), UserInfoModify.class);
                intentUserInfoModify.putExtra("userID", userID);
                intentUserInfoModify.putExtra("userPassword", userPassword);
                intentUserInfoModify.putExtra("userName", userName);
                intentUserInfoModify.putExtra("userMajor", userMajor);
                intentUserInfoModify.putExtra("studentNumber", studentNumber);

                startActivity(intentUserInfoModify);
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