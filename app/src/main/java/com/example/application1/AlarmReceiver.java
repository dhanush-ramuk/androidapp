package com.example.application1;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    Long t;
    private static int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("check", "alarm is working");
        String name = intent.getStringExtra("name");
        int kk = intent.getIntExtra("kk", 0);
        String time = intent.getStringExtra("time");

        NotificationHelper helper = new NotificationHelper(context);
        NotificationCompat.Builder builder = helper.getChannelNotification(name);
            helper.getManager().notify(NOTIFICATION_ID++, builder.build());

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
     Intent my = new Intent(context, AlarmReceiver.class);
        PendingIntent p = PendingIntent.getBroadcast(context, kk, my,PendingIntent.FLAG_UPDATE_CURRENT );
        MainActivity m = new MainActivity();
        m.schedule_alarm(context, alarmManager, my, kk, (Long.valueOf(time)+  86400000), name);

Log.e("check", "alarm");
    }

}
