package com.example.biometric_auth_attendence;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ModifyUserInfoRequest extends StringRequest {
    // set server URL (connect to php file)
    final static private String URL = Variables.address + "modifyTest.php";
    private Map<String, String> map;

    public ModifyUserInfoRequest (String userID, String major, String stuNum,
            String semester, String address, String status,
            String birth, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userMajor", major);
        map.put("studentNumber", stuNum);
        map.put("userSemester", semester);
        map.put("userAddress", address);
        map.put("userStatus", status);
        map.put("userBirth", birth);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
