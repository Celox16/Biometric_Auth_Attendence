package com.example.biometric_auth_attendence;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;

public class AttendanceCheck extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName, tv_subjectName, tv_annotation;
    private Button btn_attendanceCheck, btn_goBack;

    // biometric variables
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @RequiresApi(api = Build.VERSION_CODES.P)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_check);

        //find textview values
        tv_major = findViewById(R.id.tv_attendanceCheck_major);
        tv_stuNum = findViewById(R.id.tv_attendanceCheck_stuNum);
        tv_userName = findViewById(R.id.tv_attendanceCheck_userName);
        tv_annotation = findViewById(R.id.tv_attendanceCheck_annotation);

        tv_subjectName = findViewById(R.id.tv_attendanceCheck_subjectName);

        // find button values
        btn_attendanceCheck = findViewById(R.id.btn_attendanceCheck_attendanceCheck);
        btn_goBack = findViewById(R.id.btn_attendanceCheck_goBack);

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
        String endTime = intent.getStringExtra("endTime");
        String bluetoothName = intent.getStringExtra("bluetoothName");

        // set textview on the tab
        tv_userName.setText(userName);
        tv_major.setText(userMajor);
        tv_stuNum.setText("" + studentNumber);
        tv_subjectName.setText(subjectName);

        // initializing biometric variables
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(AttendanceCheck.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                tv_annotation.setText("Authentication error : " + errString);
                Toast.makeText(AttendanceCheck.this, "Authentication error : " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                tv_annotation.setText("Authentication succeeded");
                Toast.makeText(AttendanceCheck.this, "Authentication succeeded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                tv_annotation.setText("Authentication Failed");
                Toast.makeText(AttendanceCheck.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Attendance check using fingerprint or face")
                .setNegativeButtonText("Cancel")
                .build();

        // try to biometric authentication in AttendanceCheck.java
        biometricPrompt.authenticate(promptInfo);

        // button click listener
        btn_attendanceCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");

                            if(success){
                                Toast.makeText(getApplicationContext(), "success to attendance check", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "failed to attendance check", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };

                // TODO : make attendance check request class
            }
        });

        btn_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}