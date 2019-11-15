package com.example.application1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

public class HelperClass {
    String [] all_tests = {"weight", "cholesterol", "triglyceride", "HDL", "LDL", "glucose[fasting]",
            "glucose[random]", "calcium", "albumin", "total protein", "C02", "sodium", "potassium",
            "chloride", "alkaline phosphatase", "alanine amino transferase",
            "aspartate amino transferase", "bilirubin", "blood urea nitrogen", "creatinine", "WBC",
            "RBC", "hemoglobin", "platelets", "hematocrit", "BP"};
    public String UnitIncluder(String testName){
        String testUnit = null;
        infoPage obj = new infoPage();
        int i = infoPage.i;
        Log.e("CHECK", "I VALUE "+i);
        if (i == 1) {

            if (testName.equals("weight"))
                testUnit = "kg";
            else if (testName.equals("cholesterol") || testName.equals("triglyceride") || testName.equals("creatinine") || testName.equals("HDL") || testName.equals("LDL") || testName.equals("blood urea nitrogen") || testName.equals("glucose[fasting]") || testName.equals("glucose[random]") || testName.equals("bilirubin"))
                testUnit = "mg/dl";
            else if (testName.equals("alanine amino transferase") || testName.equals("alkaline phosphatase") || testName.equals("aspartate amino transferase"))
                testUnit = "u/l";
            else if (testName.equals("WBC") || testName.equals("RBC") || testName.equals("platelets"))
                testUnit = "μL−1";
            else if (testName.equals("hematocrit"))
                testUnit = "%";
            else if (testName.equals("hemoglobin") || testName.equals("albumin") || testName.equals("total protein"))
                testUnit = "g/dl";
            else if (testName.equals("BP"))
                testUnit = "mmHg";
            else if (testName.equals("C02") || testName.equals("sodium") || testName.equals("potassium") || testName.equals("chloride"))
                testUnit = "mEq/L";
            else
                testUnit = "";
            return testUnit;
        } else if(i==0){

            if (testName.equals("weight"))
                testUnit = "kg";
            else if (testName.equals("cholesterol") || testName.equals("triglyceride") || testName.equals("creatinine") || testName.equals("HDL") || testName.equals("LDL") || testName.equals("blood urea nitrogen") || testName.equals("glucose[fasting]") || testName.equals("glucose[random]") || testName.equals("bilirubin"))
                testUnit = "mmol/L";
            else if (testName.equals("alanine amino transferase") || testName.equals("alkaline phosphatase") || testName.equals("aspartate amino transferase"))
                testUnit = "u/l";
            else if (testName.equals("WBC") || testName.equals("RBC") || testName.equals("platelets"))
                testUnit = "L−1";
            else if (testName.equals("hematocrit"))
                testUnit = "/ of 1.0";
            else if (testName.equals("hemoglobin") || testName.equals("albumin") || testName.equals("total protein"))
                testUnit = "g/l";
            else if (testName.equals("BP"))
                testUnit = "mmHg";
            else if (testName.equals("C02") || testName.equals("sodium") || testName.equals("potassium") || testName.equals("chloride"))
                testUnit = "mmol/L";
            else
                testUnit = "";
            return testUnit;
        }
        return "b";
    }

    public void schedule_alarm(Context context, AlarmManager alarmManager, Intent intentAlarm, int kk, Long startTime, String tablet_name) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(startTime);
        if(cal.before(Calendar.getInstance())) {
            Log.e("check", "alarmtime "+cal);
            Log.e("check", "currenttime "+Calendar.getInstance());
            cal.add(Calendar.DATE, 1);
        }
        Log.e("check", "after add "+cal);
        intentAlarm.putExtra("name", tablet_name);
        intentAlarm.putExtra("kk", kk);
        intentAlarm.putExtra("time", String.valueOf(startTime));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, kk, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
            if (Build.VERSION.SDK_INT < 23) {
                if (Build.VERSION.SDK_INT >= 19) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            }
        }

}
