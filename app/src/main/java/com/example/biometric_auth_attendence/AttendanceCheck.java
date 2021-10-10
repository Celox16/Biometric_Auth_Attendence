package com.example.biometric_auth_attendence;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executor;

public class AttendanceCheck extends AppCompatActivity {
    // layout variables
    private TextView tv_major, tv_stuNum, tv_userName, tv_subjectName, tv_annotation;
    private Button btn_attendanceCheck, btn_goBack, btn_bluetooth, btn_fingerprint;

    // biometric variables
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private boolean biometricJudgement = false;

    // bluetooth variables
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    private BluetoothAdapter bluetoothAdapter;
    // TODO : change to boolean value (false is default value)
    private boolean bluetoothJudgement = true;

    @RequiresApi(api = Build.VERSION_CODES.P)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_check);

        // bluetooth adapter setting
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //find textview values
        tv_major = findViewById(R.id.tv_attendanceCheck_major);
        tv_stuNum = findViewById(R.id.tv_attendanceCheck_stuNum);
        tv_userName = findViewById(R.id.tv_attendanceCheck_userName);
        tv_annotation = findViewById(R.id.tv_attendanceCheck_annotation);

        tv_subjectName = findViewById(R.id.tv_attendanceCheck_subjectName);

        // find button values
        btn_attendanceCheck = findViewById(R.id.btn_attendanceCheck_attendanceCheck);
        btn_goBack = findViewById(R.id.btn_attendanceCheck_goBack);
        btn_bluetooth = findViewById(R.id.btn_attendanceCheck_bluetooth);
        btn_fingerprint = findViewById(R.id.btn_attendanceCheck_fingerprint);

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
                biometricJudgement = true;
                if(bluetoothJudgement && biometricJudgement) {
                    btn_attendanceCheck.setVisibility(View.VISIBLE);
                    btn_goBack.setVisibility(View.VISIBLE);
                }
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

        // button click listener
        btn_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bluetoothAdapter.isEnabled()){
                    Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                    for(BluetoothDevice device : devices){
                        try{
                            Method method = device.getClass().getMethod("isConnected", (Class[]) null);
                            boolean connected = (boolean) method.invoke(device, (Object[]) null);
                            if(connected && device.getAddress().equals(bluetoothName)){
                                    bluetoothJudgement = true;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if(bluetoothJudgement && biometricJudgement){
                        btn_attendanceCheck.setVisibility(View.VISIBLE);
                        btn_goBack.setVisibility(View.VISIBLE);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Connect to right bluetooth device", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Turn on bluetooth to get paired devices", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // try to biometric authentication in AttendanceCheck.java
                biometricPrompt.authenticate(promptInfo);
            }
        });

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

                // make current time
                SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
                String currentTime = format1.format(System.currentTimeMillis());
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sendCurrentTime = format2.format(System.currentTimeMillis());

                // make current day of week
                LocalDate localDate = LocalDate.now();
                DayOfWeek dayOfWeek = localDate.getDayOfWeek();

                // calculate time (for judgement attendance status), "f" is mean formatted
                /////////////////////error line/////////////////
                int fStartTime = Integer.parseInt(startTime.replace(":", ""));
                int fEndTime = Integer.parseInt(endTime.replace(":", ""));
                int fCurrentTime = Integer.parseInt(currentTime.replace(":", ""));
                ////////////////////////////////////////////////
                /*

                if(!dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US).toUpperCase(Locale.ROOT).equals(dayOfTheWeek)){
                    Toast.makeText(getApplicationContext(),subjectName + "(은)는" + dayOfTheWeek + "요일입니다.", Toast.LENGTH_SHORT).show();
                }
                else if(fCurrentTime < fStartTime){
                    Toast.makeText(getApplicationContext(), "수업시작 전입니다.", Toast.LENGTH_SHORT).show();
                }
                else if(fCurrentTime >= fStartTime && fCurrentTime <= (fStartTime + 5)){
                    // TODO : make attendance request default
                    String status = "출석";
                    AttendanceCheckRequest attendanceCheckRequest =
                            new AttendanceCheckRequest(subjectName, userID, Integer.toString(studentNumber), sendCurrentTime,
                                    status, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(AttendanceCheck.this);
                    queue.add(attendanceCheckRequest);

                    Toast.makeText(getApplicationContext(), "출석입니다.", Toast.LENGTH_SHORT).show();
                }
                else if(fCurrentTime < (fStartTime + 5) && fCurrentTime <= (fStartTime + 30)){
                    // TODO : make attendance request late
                    String status = "지각";
                    AttendanceCheckRequest attendanceCheckRequest =
                            new AttendanceCheckRequest(subjectName, userID, Integer.toString(studentNumber), sendCurrentTime,
                                    status, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(attendanceCheckRequest);

                    Toast.makeText(getApplicationContext(), "지각입니다.", Toast.LENGTH_SHORT).show();
                }
                else if(fCurrentTime > (fStartTime + 30) && fCurrentTime <= fEndTime){
                    // TODO : make attendance request toooooo late
                    Toast.makeText(getApplicationContext(), "수업에 너무 늦으셨습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"수업이 끝났습니다.", Toast.LENGTH_SHORT).show();
                }
                finish();
                */
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
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