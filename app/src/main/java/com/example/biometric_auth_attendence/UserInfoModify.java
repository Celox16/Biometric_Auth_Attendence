package com.example.biometric_auth_attendence;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserInfoModify extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName;
    private EditText et_major, et_studentNumber, et_semester, et_address, et_status, et_birth;
    private Button btn_save;

    String userID, userPassword, userMajor, userName, userAddress, userStatus, userBirth, userSemester;
    String modifiedMajor, modifiedName, modifiedAddress, modifiedStatus, modifiedBirth, modifiedSemester, modifiedStuNum;
    String sendMajor, sendStuNum, sendSemester, sendAddress, sendStatus, sendBirth;
    int studentNumber;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_modify);

        //find button values;
        btn_save = findViewById(R.id.btn_userInfoModify_save);

        // find textview values
        tv_major = findViewById(R.id.tv_userInfoModify_major);
        tv_userName = findViewById(R.id.tv_userInfoModify_userName);
        tv_stuNum = findViewById(R.id.tv_userInfoModify_stuNum);

        // find edittext values
        et_major = findViewById(R.id.et_userInfoModify_major);
        et_studentNumber = findViewById(R.id.et_userInfoModify_studentNumber);
        et_semester = findViewById(R.id.et_userInfoModify_semester);
        et_address = findViewById(R.id.et_userInfoModify_address);
        et_status = findViewById(R.id.et_userInfoModify_status);
        et_birth = findViewById(R.id.et_userInfoModify_birth);

        // get user information from before activity
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userPassword = intent.getStringExtra("userPassword");
        userName = intent.getStringExtra("userName");
        userMajor = intent.getStringExtra("userMajor");
        studentNumber = intent.getIntExtra("studentNumber", 0);

        userAddress = intent.getStringExtra("userAddress");
        userStatus = intent.getStringExtra("userStatus");
        userBirth = intent.getStringExtra("userBirth");
        userSemester = intent.getStringExtra("userSemester");

        // set textview on the tab
        tv_major.setText(userMajor);
        tv_stuNum.setText("" + studentNumber);
        tv_userName.setText(userName);

        // set edittext hint
        et_major.setHint(userMajor);
        et_studentNumber.setHint("" + studentNumber);
        et_semester.setHint(userSemester);
        et_address.setHint(userAddress);
        et_status.setHint(userStatus);
        et_birth.setHint(userBirth);

        // save button click listener
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get text from EditText
                modifiedMajor = et_major.getText().toString();
                modifiedStuNum = et_studentNumber.getText().toString();
                modifiedSemester = et_semester.getText().toString();
                modifiedAddress = et_address.getText().toString();
                modifiedStatus = et_status.getText().toString();
                modifiedBirth = et_birth.getText().toString();

                // if modified EditText length is more than 0
                // 전부다 받고 전부 업데이트 해버리는 방식으로
                if (modifiedMajor.length() > 0) {
                    sendMajor = modifiedMajor;
                } else {
                    sendMajor = userMajor;
                }

                if (modifiedStuNum.length() > 0) {
                    sendStuNum = modifiedStuNum;
                } else {
                    sendStuNum = String.valueOf(studentNumber);
                }

                if(modifiedSemester.length() > 0) {
                    sendSemester = modifiedSemester;
                } else {
                    sendSemester = userSemester;
                }

                if(modifiedAddress.length() > 0) {
                    sendAddress = modifiedAddress;
                } else {
                    sendAddress = userAddress;
                }

                if(modifiedStatus.length() > 0) {
                    sendStatus = modifiedStatus;
                } else {
                    sendStatus = userStatus;
                }

                if(modifiedBirth.length() > 0) {
                    sendBirth = modifiedBirth;
                } else {
                    sendBirth = userBirth;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");

                            if(success){
                                Toast.makeText(getApplicationContext(), "수정 완료", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "수정 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // send server request
                ModifyUserInfoRequest modifyUserInfoRequest = new ModifyUserInfoRequest(userID, sendMajor, sendStuNum,
                        sendSemester, sendAddress, sendStatus, sendBirth, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserInfoModify.this);
                queue.add(modifyUserInfoRequest);
            }
        });
    }
}