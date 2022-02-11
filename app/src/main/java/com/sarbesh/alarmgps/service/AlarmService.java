package com.sarbesh.alarmgps.service;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmService extends Service {
    private final Context mContext = this;

    private AlarmManager alarmManager;

    public void setAlarm(String time) throws ParseException {
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if(!checkGrantedPermissions()){
            getPermission();
        }
        SimpleDateFormat format = new SimpleDateFormat(" HH:mm:ss");
        Date parse = format.parse(time);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, ,);
    }

    private void getPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions((Activity) mContext,new String[]{
                    Manifest.permission.SCHEDULE_EXACT_ALARM,
                    Manifest.permission.SET_ALARM
            }, 102);
        } else {
            ActivityCompat.requestPermissions((Activity) mContext,new String[]{
                    Manifest.permission.SET_ALARM
            }, 102);
        }
    }

    private boolean checkGrantedPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return ActivityCompat.checkSelfPermission(mContext, Manifest.permission.SET_ALARM) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(mContext, Manifest.permission.SCHEDULE_EXACT_ALARM) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ActivityCompat.checkSelfPermission(mContext, Manifest.permission.SET_ALARM) == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
