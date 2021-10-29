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

public class UserInfoModify extends AppCompatActivity {
    private TextView tv_major, tv_stuNum, tv_userName;
    private Button btn_selectImage, btn_save;
    private ImageView imageView;

    String userID, userPassword, userMajor, userName;
    int studentNumber;

    // connect to gallery variables
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    // image variables
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_modify);

        // find textview values
        tv_major = findViewById(R.id.tv_userInfoModify_major);
        tv_userName = findViewById(R.id.tv_userInfoModify_userName);
        tv_stuNum = findViewById(R.id.tv_userInfoModify_stuNum);

        btn_selectImage = findViewById(R.id.btn_userInfoModify_selectImage);
        btn_save = findViewById(R.id.btn_userInfoModify_save);

        imageView = findViewById(R.id.image);

        // get user information from before activity
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userPassword = intent.getStringExtra("userPassword");
        userName = intent.getStringExtra("userName");
        userMajor = intent.getStringExtra("userMajor");
        studentNumber = intent.getIntExtra("studentNumber", 0);

        // set textview on the tab
        tv_major.setText(userMajor);
        tv_stuNum.setText("" + studentNumber);
        tv_userName.setText(userName);

        btn_selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check runtime permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        // permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                        // show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        // permission already granted
                        pickImageFromGallery();
                    }
                } else {
                    // system os is less than marshmallow
                    pickImageFromGallery();
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();
                //String imageString = new String(imageBytes, Base64.DEFAULT);
                String imageString = java.util.Base64.getEncoder().encodeToString(imageBytes);
                int length = imageString.length();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");

                            if(success) {
                                Toast.makeText(getApplicationContext(), "success to register", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "failed to register", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };

                // request server
                SendImageRequest sendImageRequest = new SendImageRequest(userID, imageString, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserInfoModify.this);
                queue.add(sendImageRequest);
            }
        });
    }

    private void pickImageFromGallery(){
        // intent to pick image
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
        pickImageIntent.setType("image/*");
        startActivityForResult(pickImageIntent, IMAGE_PICK_CODE);;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    pickImageFromGallery();
                } else {
                    // permission was denied
                    Toast.makeText(getApplicationContext(), "사용권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//                byte[] imageBytes = byteArrayOutputStream.toByteArray();
//                //String imageString = new String(imageBytes, Base64.DEFAULT);
//                String imageString = java.util.Base64.getEncoder().encodeToString(imageBytes);
//
//                byte[] convertBytes = java.util.Base64.getDecoder().decode(imageString);
//                Bitmap testBitmap = BitmapFactory.decodeByteArray(convertBytes, 0, convertBytes.length);
//                imageView.setImageBitmap(testBitmap);

            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}