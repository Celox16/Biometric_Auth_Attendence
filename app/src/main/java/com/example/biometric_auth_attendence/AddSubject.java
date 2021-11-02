package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
        String endTime = intent.getStringExtra("entTime");
        String bluetoothName = intent.getStringExtra("bluetoothName");

        // set textview on the tab
        tv_userName.setText(userName);
        tv_major.setText(userMajor);
        tv_stuNum.setText("" + studentNumber);
        tv_subjectName.setText(subjectName);

        btn_addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");

                            if(success){
                                Toast.makeText(getApplicationContext(), "success to add subject", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "failed to add subject", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                // request server (add subject)
                AddSubjectRequest addSubjectRequest = new AddSubjectRequest(userID, subjectCode, subjectName,
                        dayOfTheWeek, professor, startTime, endTime, bluetoothName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddSubject.this);
                queue.add(addSubjectRequest);
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