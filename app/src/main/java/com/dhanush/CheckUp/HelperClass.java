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

        public String splitDate(String date){
            String[] splitDate = date.split("/", 0);
            String smallDate = splitDate[1]+"/"+splitDate[2];
            return smallDate;
        }
    public String shortenTestName(String a){
        String str = null;
        if(a.equals("blood urea nitrogen"))
            str = "Blood Urea";
        else if(a.equals("alanine amino transferase"))
            str = "ALT";
        else if(a.equals("aspartate amino transferase"))
            str = "AST";
        else if(a.equals("total protein"))
            str = "Total Protein";
        else if(a.equals("glucose[fasting]"))
            str = "Glucose[F]";
        else if(a.equals("glucose[random]"))
            str = "Glucose[R]";
        else if(a.equals("alkaline phosphatase"))
            str = "ALP";
        else if(a.equals("weight"))
            str = "Weight";
        else if(a.equals("cholesterol"))
            str = "Cholesterol";
        else if(a.equals("creatinine"))
            str = "Creatinine";
        else if(a.equals("calcium"))
            str = "Calcium";
        else if(a.equals("albumin"))
            str = "Albumin";
        else if(a.equals("sodium"))
            str = "Sodium";
        else if(a.equals("potassium"))
            str = "Potassium";
        else if(a.equals("chloride"))
            str = "Chloride";
        else if(a.equals("bilirubin"))
            str = "Bilirubin";
        else if(a.equals("hemoglobin"))
            str = "Hemoglobin";
        else if(a.equals("hematocrit"))
            str = "Hematocrit";
        else if(a.equals("platelets"))
            str = "Platelets";
        else
            str = a;
        return str;
    }

}
