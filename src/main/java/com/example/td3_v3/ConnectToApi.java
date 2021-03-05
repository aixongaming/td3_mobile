package com.example.td3_v3;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Base64;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;



public class ConnectToApi extends AsyncTask<String,Void,String>{

    String total = "";
    private static final String BASE_URL = "aHR0cHM6Ly82MDEwMmYxNjZjMjFlMTAwMTcwNTAxMjgubW9ja2FwaS5pby9sYWJiYmFuay9jb25maWcv";

    @Override
    protected String doInBackground(String... params) {
        Log.d("in Thread", "");
        URL url = null;
        URLConnection urlConnection;
        InputStream in;
        int i;
        char c;

        try {
            byte[] decode_bytes = Base64.decode(BASE_URL, 0);
            String decoded_url = new String(decode_bytes, "UTF-8");
            url = new URL(decoded_url + params[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {

            urlConnection = url.openConnection();
            in = urlConnection.getInputStream();
            while((i = in.read())!=-1) {

                // converts integer to character
                c = (char)i;
                total += c;
                // prints character
            };
            Log.d("Coming from API", total);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return total;
    }
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }

}
