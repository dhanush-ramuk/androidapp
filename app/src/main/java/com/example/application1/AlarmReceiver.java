package com.example.application1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("check", "alarm is working");
        String name = intent.getStringExtra("name");
        NotificationHelper helper = new NotificationHelper(context);
        NotificationCompat.Builder builder = helper.getChannelNotification(name);
        helper.getManager().notify(1, builder.build());
    }

}
