package com.dhanush.CheckUp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private PowerManager.WakeLock screenWakeLock;
    HelperClass helperClass = new HelperClass();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (screenWakeLock == null){
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            screenWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "app:tag");
            screenWakeLock.acquire();
        }
        Log.e("check", "refill1");
        String name = intent.getStringExtra("name");
        int kk = intent.getIntExtra("kk", 0);
        String time = intent.getStringExtra("time");
        int alert = intent.getIntExtra("alert", 0);
        int notsnooze = intent.getIntExtra("notsnooze", 0);
        int isRefillReminder = intent.getIntExtra("isRefillReminder", 0);
        NotificationHelper helper = new NotificationHelper(context);
        NotificationCompat.Builder builder = helper.getChannelNotification(name, kk, alert, isRefillReminder);
        if(name.equals("Today") || name.equals("Tomorrow")){
            helper.getManager().notify(kk, builder.build());
        }
        //helper.getManager().cancel(kk);
        if (screenWakeLock != null)
            screenWakeLock.release();

        //cancelling the alarm once it gets fired
        cancel_alarm(context, kk, time, name, alert, notsnooze, isRefillReminder);
    }

    public void cancel_alarm(Context context, int kk, String time, String name, int alert, int notsnooze, int isRefillReminder){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent in = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, kk, in, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        //creating the canceled alarm the next day
        if(notsnooze == 1 && isRefillReminder == 0)
            setAlarm(time, context, alarmManager, in, kk, name, alert);
    }

    public void setAlarm(String time, Context context, AlarmManager alarmManager, Intent intentAlarm, int kk, String tablet_name, int alert){
        Long nextTime = Long.valueOf(time) + (24 * 60 * 60 * 1000);
        helperClass.schedule_alarm(context, alarmManager, intentAlarm, kk, nextTime, tablet_name, alert, 1, 0);
    }
}
