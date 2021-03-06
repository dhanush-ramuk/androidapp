package com.dhanush.CheckUp;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class PackageReplaced extends BroadcastReceiver {
    HelperClass helperClass = new HelperClass();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("check", "PackageReplaced");
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED) && intent.getDataString().contains(context.getPackageName())) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            Gson gson = new Gson();
            String json1 = sharedPrefs.getString("mylist1", "");
            ArrayList<All_Medications> start_obj1 = gson.fromJson(json1,
                    new TypeToken<List<All_Medications>>() {
                    }.getType());
            if (start_obj1 != null && !start_obj1.isEmpty())
                for (int i = 0; i < start_obj1.size(); i++) {
                    for (int j = 0; j < Integer.parseInt(start_obj1.get(i).return_map().get("size")); j++) {
                        int kk = Integer.parseInt(start_obj1.get(i).return_map().get(String.valueOf(j)));
                        String name = start_obj1.get(i).return_map().get("name");
                        String time = start_obj1.get(i).return_map().get(j + "time");
                        schedule_alarm(context, kk, name, time);
                    }
                }
        }
    }

    public void schedule_alarm(Context context, int kk, String name, String time){

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent my = new Intent(context, AlarmReceiver.class);
        helperClass.schedule_alarm(context, alarmManager, my, kk, (Long.valueOf(time)), name);
    }

}
