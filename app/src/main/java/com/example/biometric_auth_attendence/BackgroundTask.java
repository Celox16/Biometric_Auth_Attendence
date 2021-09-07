package com.example.biometric_auth_attendence;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*public class BackgroundTask extends AsyncTask<Void, Void, String> {
    String target;

    @Override
    protected void onPreExecute(){
        target = "http://125.141.27.3/biometric_auth/sugangList.php";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        Intent intent = new Intent(MainActivity.this, ModifySugang.class);
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            URL url = new URL(target);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp;
            StringBuilder stringBuilder = new StringBuilder();
            while((temp = bufferedReader.readLine())!=null){
                stringBuilder.append(temp="\n");
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString().trim();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;


    }
}*/
