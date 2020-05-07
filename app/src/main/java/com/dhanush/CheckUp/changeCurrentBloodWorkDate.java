package com.dhanush.CheckUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class changeCurrentBloodWorkDate extends AppCompatActivity {
    android.widget.DatePicker BloodWorkdatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_current_blood_work_date);
        Intent i = getIntent();
        String day = i.getStringExtra("day");
        String month = i.getStringExtra("month");
        String year = i.getStringExtra("year");
        Log.i("check", "date "+day+month+year );
        BloodWorkdatePicker = (android.widget.DatePicker) findViewById(R.id.BloodWorkDatePicker);
        setDate(day, month, year);
    }

    private void setDate(String day, String month, String year) {
        if(day != null){
            BloodWorkdatePicker.updateDate(Integer.parseInt(year), (Integer.parseInt(month)-1), Integer.parseInt(day));
        }
    }

    public void backToActivity2(View V) {
        Intent i = new Intent();
        i.putExtra("boolean", "0");
        Log.i("check", "day "+BloodWorkdatePicker.getDayOfMonth());
        if(BloodWorkdatePicker.getDayOfMonth() < 10) {
            String day = '0'+String.valueOf(BloodWorkdatePicker.getDayOfMonth());
            Log.i("check", "day "+day);
            i.putExtra("day", day);
            Log.i("check", "day value "+Integer.parseInt(day));
        } else {
            i.putExtra("day", String.valueOf(BloodWorkdatePicker.getDayOfMonth()));
        }
        if(BloodWorkdatePicker.getMonth() < 10) {
            String day = '0'+String.valueOf(BloodWorkdatePicker.getMonth()+1);
            i.putExtra("month", day);
        } else {
            i.putExtra("month", String.valueOf(BloodWorkdatePicker.getMonth()+1));
        }
        i.putExtra("year", String.valueOf(BloodWorkdatePicker.getYear()));
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date date = new Date(BloodWorkdatePicker.getYear(), BloodWorkdatePicker.getMonth(), (BloodWorkdatePicker.getDayOfMonth() - 1));
        String dayOfWeek = simpledateformat.format(date);
        i.putExtra("dayofweek", dayOfWeek);
        setResult(RESULT_OK, i);
        finish();

    }

}
