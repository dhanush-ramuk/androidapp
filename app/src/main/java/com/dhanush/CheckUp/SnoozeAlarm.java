package com.dhanush.CheckUp;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SnoozeAlarm extends BroadcastReceiver {
    HelperClass helperClass = new HelperClass();
    @Override
    public void onReceive(Context context, Intent intent) {
        //if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED) && intent.getDataString().contains(context.getPackageName())) {

     String name = intent.getStringExtra("name");
            int kk = intent.getIntExtra("kk", 0);
             int alert = intent.getIntExtra("alert", 0);
             long time = Calendar.getInstance().getTimeInMillis() + 5 * 60000;
     schedule_alarm(context,kk,name, String.valueOf(time) ,alert, 0);
        //}
    }

    public void schedule_alarm(Context context, int kk, String name, String time, int alert, int notsnooze){

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent my = new Intent(context, AlarmReceiver.class);
        helperClass.schedule_alarm(context, alarmManager, my, kk, (Long.valueOf(time)), name, alert, notsnooze, 0);
    }

}
