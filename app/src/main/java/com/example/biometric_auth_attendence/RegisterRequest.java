package com.example.biometric_auth_attendence;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    // set server URL (connect to php file)
    final static private String URL = Variables.address + "register.php";
    private Map<String, String> map;

    public RegisterRequest(String userID, String userPassword, String userName, int studentNumber, String userMajor,
                           String userAddress, String userStatus, String userBirth, String userSemester, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPassword);
        map.put("userName", userName);
        map.put("studentNumber", studentNumber + "");
        map.put("userMajor", userMajor);

        // addition
        map.put("userAddress", userAddress);
        map.put("userStatus", userStatus);
        map.put("userBirth", userBirth);
        map.put("userSemester", userSemester);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}