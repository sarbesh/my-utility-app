package com.sarbesh.alarmgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGpsAlarm(View view){
        startActivity(new Intent(getApplicationContext(),LocationActivity.class));
    }

    public void startLicUtil(View ciew){
        startActivity(new Intent(getApplicationContext(), LicActivity.class));
    }

    public void startBasic(View view){
        startActivity(new Intent(getApplicationContext(),BasicActivity.class));
    }

    public void startScrolling(View view){
        startActivity(new Intent(getApplicationContext(),ScrollingActivity.class));
    }

    public void startFD(View view){
        startActivity(new Intent(getApplicationContext(), FixedDeposit.class));
    }
}