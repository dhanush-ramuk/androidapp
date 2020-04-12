package com.dhanush.CheckUp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }


    public NotificationCompat.Builder getChannelNotification(String name, int kk, int alert, int isRefillReminder) {
        /*Intent snoozeIntent = new Intent(this, SnoozeAlarm.class);
        //snoozeIntent.setAction(ACTION_SNOOZE);
        snoozeIntent.putExtra("name", name);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        snoozeIntent.putExtra("kk", kk);
        snoozeIntent.putExtra("alert", alert);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);
        PendingIntent notifyPIntent =
                PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0);
         */
        if(isRefillReminder == 1) {
            return new NotificationCompat.Builder(getApplicationContext(), channelID)
                    .setContentTitle("BloodWork Reminder")
                    .setContentText("You have BloodWork scheduled " + name.toUpperCase())
                    .setSmallIcon(R.drawable.ic_notification)
                    .setColor(getResources().getColor(R.color.appIconColor))
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.large_round_notification));
        }
        else {
            return new NotificationCompat.Builder(getApplicationContext(), channelID)
                    .setContentTitle("Medication Reminder")
                    .setContentText("Refill your" + name.toUpperCase())
                    .setSmallIcon(R.drawable.ic_notification)
                    .setColor(getResources().getColor(R.color.appIconColor))
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.large_round_notification));
        }

    }
}
