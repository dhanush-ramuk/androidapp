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
import java.util.List;

public class BootReceiverBloodWork extends BroadcastReceiver {
        HelperClass helperClass = new HelperClass();
        @Override
        public void onReceive(Context context, Intent intent) {
            int kkr = 0, alert = 0, isRefillReminder = 0;
            String name = "";
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            Gson gson = new Gson();
            String json1 = sharedPrefs.getString("mylist1", "");
            ArrayList<All_Results> start_obj1 = gson.fromJson(json1,
                    new TypeToken<List<All_Results>>(){}.getType());
            if(start_obj1 != null && !start_obj1.isEmpty())
                for(int i = 0; i < start_obj1.size(); i++) {
                    int kk = Integer.parseInt(start_obj1.get(i).get_map2().get("kkvaluerefill"));
                    String time = start_obj1.get(i).get_map2().get("refilltime");
                    schedule_alarm(context, kk, "Today", time, alert, 0);
                }

        }
        public void schedule_alarm(Context context, int kk, String name, String time, int alert, int isRefillReminder){
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent my = new Intent(context, AlarmReceiver.class);
            helperClass.schedule_alarm(context, alarmManager, my, kk, (Long.valueOf(time)), name, alert, 1, isRefillReminder);
        }
}
