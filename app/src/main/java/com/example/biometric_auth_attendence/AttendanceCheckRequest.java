package com.example.biometric_auth_attendence;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AttendanceCheckRequest extends StringRequest {
    // set server URL (connect to php file)
    final static private String URL = "http://125.141.27.3/biometric_auth/attendanceCheck.php";
    private Map<String, String> map;

    public AttendanceCheckRequest(String subjectName, String userID, String studentNumber,
                                  String arrivalTime, String status, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("subjectName", subjectName);
        map.put("userID", userID);
        map.put("studentNumber", studentNumber);
        map.put("arrivalTime", arrivalTime);
        map.put("status", status);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }
}
