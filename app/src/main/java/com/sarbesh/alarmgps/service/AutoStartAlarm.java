package com.sarbesh.alarmgps.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStartAlarm extends BroadcastReceiver {

//    Alarm alarm = new Alarm();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            // Set the alarm here.
        }

    }
}
