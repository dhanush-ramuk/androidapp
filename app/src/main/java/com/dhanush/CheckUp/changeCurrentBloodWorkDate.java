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
        BloodWorkdatePicker = (android.widget.DatePicker) findViewById(R.id.BloodWorkDatePicker);
    }

    public void backToActivity2(View V) {
        Intent i = new Intent();
        i.putExtra("boolean", "0");
        i.putExtra("day", BloodWorkdatePicker.getDayOfMonth());
        i.putExtra("month", (BloodWorkdatePicker.getMonth() + 1));
        i.putExtra("year", BloodWorkdatePicker.getYear());
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date date = new Date(BloodWorkdatePicker.getYear(), BloodWorkdatePicker.getMonth(), (BloodWorkdatePicker.getDayOfMonth() - 1));
        String dayOfWeek = simpledateformat.format(date);
        i.putExtra("dayofweek", dayOfWeek);
        setResult(RESULT_OK, i);
        finish();

    }

}
