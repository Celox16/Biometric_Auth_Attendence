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
    private EditText et_id, et_password, et_name, et_studentNumber, et_major;
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
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, studentNumber, userMajor, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

                // request server (create table)
                RegisterCreateTable registerCreateTable = new RegisterCreateTable(userID, responseListener);
                RequestQueue queue1 = Volley.newRequestQueue(RegisterActivity.this);
                queue1.add(registerCreateTable);

                // request server (create image table)
                RegisterImageTable registerImageTable = new RegisterImageTable(userID, responseListener);
                RequestQueue queue2 = Volley.newRequestQueue(RegisterActivity.this);
                queue2.add(registerImageTable);
            }
        });
    }
}