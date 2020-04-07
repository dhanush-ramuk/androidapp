package com.dhanush.CheckUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Datepicker extends AppCompatActivity {
    android.widget.DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        datePicker = (android.widget.DatePicker) findViewById(R.id.simpleDatePicker);
    }

    public void back_to_bloodwork(View v){
        Intent i = new Intent();
        i.putExtra("day", datePicker.getDayOfMonth());
        i.putExtra("month", (datePicker.getMonth()+1));
        i.putExtra("year", datePicker.getYear());
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date date = new Date(datePicker.getYear(), datePicker.getMonth(), (datePicker.getDayOfMonth()-1));
        String dayOfWeek = simpledateformat.format(date);
        i.putExtra("dayofweek", dayOfWeek);
        setResult(RESULT_OK, i);
        finish();
    }

}
