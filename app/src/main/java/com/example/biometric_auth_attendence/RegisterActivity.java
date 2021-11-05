package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_id, et_password, et_name, et_studentNumber, et_major,
            et_passwordCheck, et_status, et_address, et_birth, et_semester;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // find view by id values
        et_id = findViewById(R.id.et_register_ID);
        et_password = findViewById(R.id.et_register_password);
        et_name = findViewById(R.id.et_register_name);
        et_studentNumber = findViewById(R.id.et_register_studentNumber);
        et_major = findViewById(R.id.et_register_major);
        et_passwordCheck = findViewById(R.id.et_register_password2);
        et_address = findViewById(R.id.et_register_address);
        et_status = findViewById(R.id.et_register_status);
        et_birth = findViewById(R.id.et_register_birth);
        et_semester = findViewById(R.id.et_register_semester);

        btn_register = findViewById(R.id.btn_register_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get entered data in EditText
                String userID = et_id.getText().toString();
                String userPassword = et_password.getText().toString();
                String userName = et_name.getText().toString();
                String userMajor = et_major.getText().toString();
                int studentNumber = Integer.parseInt(et_studentNumber.getText().toString());
                String userPasswordCheck = et_passwordCheck.getText().toString();

                String userAddress = et_address.getText().toString();
                String userStatus = et_status.getText().toString();
                String userBirth = et_birth.getText().toString();
                String userSemester = et_semester.getText().toString();

                if(!userPassword.equals(userPasswordCheck)){
                    Toast.makeText(getApplicationContext(), "비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if(userID.length()==0 || userPassword.length()==0 || userName.length()==0 ||
                        et_studentNumber.getText().length()==0 || userAddress.length()==0 ||
                        userStatus.length()==0 || userBirth.length()==0 || userSemester.length()==0){
                    Toast.makeText(getApplicationContext(), "빈칸을 모두 채워주세요", Toast.LENGTH_SHORT).show();
                    return;
                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");

                            if (success) { // success to register
                                Toast.makeText(getApplicationContext(), "success to register", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else { // failed to register
                                Toast.makeText(getApplicationContext(), "failed to register", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // request server (register table)
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, studentNumber, userMajor,
                        userAddress, userStatus, userBirth, userSemester, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

                // request server (create table)
                RegisterCreateTable registerCreateTable = new RegisterCreateTable(userID, responseListener);
                RequestQueue queue1 = Volley.newRequestQueue(RegisterActivity.this);
                queue1.add(registerCreateTable);
            }
        });
    }
}