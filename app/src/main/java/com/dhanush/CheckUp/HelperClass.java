package com.dhanush.CheckUp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class HelperClass {


    public void schedule_alarm(Context context, AlarmManager alarmManager, Intent intentAlarm, int kk, Long startTime, String tablet_name, int alert, int notsnooze, int isrefillReminder) {
        if (System.currentTimeMillis() > startTime) {
            startTime = startTime + (24 * 60 * 60 * 1000);
        }
        intentAlarm.putExtra("name", tablet_name);
        intentAlarm.putExtra("kk", kk);
        intentAlarm.putExtra("time", String.valueOf(startTime));
        intentAlarm.putExtra("alert", alert);
        intentAlarm.putExtra("notsnooze", notsnooze);
        intentAlarm.putExtra("isRefillReminder", isrefillReminder);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, kk, intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
            if (Build.VERSION.SDK_INT < 23) {
                if (Build.VERSION.SDK_INT >= 19) {
                    if(System.currentTimeMillis()<startTime)
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);
                } else {
                    if(System.currentTimeMillis()<startTime)
                        alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);
                }
            } else {
                if(System.currentTimeMillis()<startTime)
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);
            }
        }

}
