package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
                intentAttendanceList.putExtra("studentNumber", studentNumber);

                startActivity(intentAttendanceList);
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make a new intent and parse user info data
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

    /*
    public class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://125.141.27.3/biometric_auth/sugangList.php";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(MainActivity.this, ModifySugang.class);
            intent.putExtra("subjectList", result);

            MainActivity.this.startActivity(intent);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp = "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/
}