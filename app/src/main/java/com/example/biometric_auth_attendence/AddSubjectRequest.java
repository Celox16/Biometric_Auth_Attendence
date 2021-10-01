package com.example.biometric_auth_attendence;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddSubjectRequest extends StringRequest {
    // set server URL (connect to php file)
    final static private String URL = "http://125.141.27.3/biometric_auth/addSubjectTest.php";
    private Map<String, String> map;

    public AddSubjectRequest(String userID, int subjectCode, String subjectName, String dayOfTheWeek, String professor,
                             String startTime, String endTime, String bluetoothName, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("subjectCode", ""+subjectCode);
        map.put("subjectName", subjectName);
        map.put("dayOfTheWeek", dayOfTheWeek);
        map.put("professor", professor);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("bluetoothName", bluetoothName);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }
}
