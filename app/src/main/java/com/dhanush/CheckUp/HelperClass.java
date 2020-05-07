package com.dhanush.CheckUp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

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
            String smallDate = splitDate[2]+"/"+splitDate[1]+"/"+splitDate[0];
            return smallDate;
        }
    public ArrayList<All_Results> arrangeObjectsAscendingOrder(ArrayList<All_Results> obj) {
        ArrayList<All_Results> orderedObj = new ArrayList<>();
        ArrayList<Date> orderedDates = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
         for(int i=0; i<obj.size(); i++){
            dates.add(getIndividualDate(i, obj));
        }
        orderedDates.addAll(dates);
        orderedObj.addAll(obj);
        Collections.sort(orderedDates);
        for(Date date:orderedDates){
            Log.i("check", "ordered dates "+date);
        }
        for(Date date:dates){
            Log.i("check", "unordered dates "+date);
        }
        for(int i=0; i<orderedDates.size(); i++){
            Log.i("check", "objects "+obj.get(i));
            Log.i("check", "dates "+orderedDates.indexOf(dates.get(i)));
            orderedObj.remove(orderedDates.indexOf(dates.get(i)));
            orderedObj.add(orderedDates.indexOf(dates.get(i)), obj.get(i));
        }
        Log.i("check", "size "+orderedObj.size());
        return orderedObj;
    }

        public Date getIndividualDate(int j, ArrayList<All_Results> obj) {
            String date = obj.get(j).get_map2().get("date");
            String[] seperateDate = date.split("/", 0);
            int year = Integer.valueOf(seperateDate[0]);
            int month = Integer.valueOf(seperateDate[1]);
            int day = Integer.valueOf(seperateDate[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, (month-1));
            calendar.set(Calendar.DAY_OF_MONTH, day);
            Date individualDate = calendar.getTime();
            return individualDate;
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
