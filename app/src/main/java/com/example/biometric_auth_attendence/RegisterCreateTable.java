package com.example.biometric_auth_attendence;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterCreateTable extends StringRequest {
    // set server URL (connect ot php file)
    final static private String URL = "http://125.141.27.3/biometric_auth/createTable.php";
    private Map<String, String> map;

    public RegisterCreateTable(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
