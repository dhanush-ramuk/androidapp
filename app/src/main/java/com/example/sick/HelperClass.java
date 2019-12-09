package com.example.sick;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

public class HelperClass {


    public void schedule_alarm(Context context, AlarmManager alarmManager, Intent intentAlarm, int kk, Long startTime, String tablet_name) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(startTime);
        if(cal.before(Calendar.getInstance())) {
            cal.add(Calendar.DATE, 1);
        }
        intentAlarm.putExtra("name", tablet_name);
        intentAlarm.putExtra("kk", kk);
        intentAlarm.putExtra("time", String.valueOf(startTime));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, kk, intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
            if (Build.VERSION.SDK_INT < 23) {
                if (Build.VERSION.SDK_INT >= 19) {
                    if(System.currentTimeMillis()<cal.getTimeInMillis())
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                } else {
                    if(System.currentTimeMillis()<cal.getTimeInMillis())
                        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                }
            } else {
                if(System.currentTimeMillis()<cal.getTimeInMillis())
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            }
        }

}
