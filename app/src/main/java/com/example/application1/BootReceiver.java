package com.example.application1;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class BootReceiver extends BroadcastReceiver {
    private static int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json1 = sharedPrefs.getString("mylist1", "");
        ArrayList<All_Medications> start_obj1 = gson.fromJson(json1,
                new TypeToken<List<All_Medications>>(){}.getType());
  for(int i = 0; i < start_obj1.size(); i++) {
      for (int j = 0; j < Integer.parseInt(start_obj1.get(i).return_map().get("size")); j++) {
        int kk = Integer.parseInt(start_obj1.get(i).return_map().get(String.valueOf(j)));
        String name = start_obj1.get(i).return_map().get("name");
        String time = start_obj1.get(i).return_map().get(String.valueOf(j)+"mili");
            schedule_alarm(context, kk, name, time);


      }
  }
    }

    public void schedule_alarm(Context context, int kk, String name, String time){

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent my = new Intent(context, AlarmReceiver.class);
        PendingIntent p = PendingIntent.getBroadcast(context, kk, my, PendingIntent.FLAG_UPDATE_CURRENT);
        MainActivity m = new MainActivity();
        m.schedule_alarm(context, alarmManager, my, kk, (Long.valueOf(time)), name);
    }

}
