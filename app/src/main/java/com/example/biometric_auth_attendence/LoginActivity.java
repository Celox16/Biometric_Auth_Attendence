package com.example.biometric_auth_attendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_password;
    private Button btn_login, btn_register;

    String imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // find correct values
        et_id = findViewById(R.id.et_login_ID);
        et_password = findViewById(R.id.et_login_password);
        btn_login = findViewById(R.id.btn_login_login);
        btn_register = findViewById(R.id.btn_login_register);

        // if register button clicked
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // if login button clicked
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get entered data in EditText
                String userID = et_id.getText().toString();
                String userPassword = et_password.getText().toString();

                /*
                //////////////image///////////////
                // TODO : delete code
                Response.Listener<String> responseImage = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");

                            if(success){
                                imageString = jsonObject.getString("image");
                            } else {
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                LoginImageRequest loginImageRequest = new LoginImageRequest(userID, responseImage);
                RequestQueue imageQueue = Volley.newRequestQueue(LoginActivity.this);
                imageQueue.add(loginImageRequest);

                /////////////////////////////////
                */

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");

                            if(success){ // success to login
                                // get user info from raspberryPI database
                                String userID = jsonObject.getString("userID");
                                String userPassword = jsonObject.getString("userPassword");
                                String userMajor = jsonObject.getString("userMajor");
                                String userName = jsonObject.getString("userName");
                                int studentNumber = jsonObject.getInt("studentNumber");

                                Toast.makeText(getApplicationContext(), "success to login", Toast.LENGTH_SHORT).show();

                                // make a new intent and parse user info data
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPassword);
                                intent.putExtra("userMajor", userMajor);
                                intent.putExtra("userName", userName);
                                intent.putExtra("studentNumber", studentNumber);

                                // send main image
                                intent.putExtra("imageString", imageString);

                                startActivity(intent);
                                finish();
                            }
                            else{ // failed to login
                                Toast.makeText(getApplicationContext(), "failed to login", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}