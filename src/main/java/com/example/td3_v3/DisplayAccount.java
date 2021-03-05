package com.example.td3_v3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class DisplayAccount extends AppCompatActivity {

    TextView welcometext;
    TextView accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_account);
        welcometext = findViewById(R.id.display_welcome);
        accounts = findViewById(R.id.display_accounts);
        String account = getIntent().getStringExtra("id");
        welcometext.setText("Welcome back "+ account);

    }

    protected void onStart() {
        super.onStart();
        File f = getFileStreamPath("accounts.txt");
        Log.d("File exists: ", Boolean.toString(f.exists()));
        String result = "";
        if(f.exists())
        {
            try {
                FileInputStream fin = openFileInput("accounts.txt");
                int c;
                String temp="";
                while( (c = fin.read()) != -1){
                    temp = temp + Character.toString((char)c);
                }
                result = temp;

                fin.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                FileOutputStream fout = openFileOutput("accounts.txt", Context.MODE_PRIVATE);
                GetAccount acc = new GetAccount();
                try {
                    result = acc.execute().get();

                    fout.write(result.getBytes());
                    fout.close();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        accounts.setText(result);

    }

    public void refresh(View v) throws ExecutionException, InterruptedException, IOException {
        FileOutputStream fout = openFileOutput("accounts", Context.MODE_PRIVATE);
        String result = "";
        GetAccount acc = new GetAccount();
        result = acc.execute().get();
        fout.write(result.getBytes());
        fout.close();
    }
}
