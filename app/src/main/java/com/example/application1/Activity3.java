package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class Activity3 extends AppCompatActivity  {
    String time = null;
    View view;
    String tablet_name;
    ArrayList<String> hour = new ArrayList<>();
    ArrayList<String> minute = new ArrayList<>();
    ArrayList<Integer> hourin12 = new ArrayList<>();

    ArrayList<String> ampm = new ArrayList<>();
    ArrayList<TimePicker> timepicker_list = new ArrayList<TimePicker>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
    }


    public void onclick_listview_for_time_enter(View v){
        EditText edittext = (EditText) findViewById(R.id.tablet_name);
        tablet_name = edittext.getText().toString();
            if(edittext.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "enter tablet name", Toast.LENGTH_SHORT).show();
            }
            else {
                Log.e("check", "ta da");
                LinearLayout parent_linearlayout = (LinearLayout) findViewById(R.id.main_linear_layout);
                edittext.setFocusable(false);
                Spinner spin = (Spinner) findViewById(R.id.spinner_tab_times);
                spin.setClickable(false);
                spin.setEnabled(false);
                Button but = (Button) findViewById(R.id.go_to_timepicker);
                but.setClickable(false);
                time = spin.getSelectedItem().toString();
                Log.e("check", "value of time in original string" + time);
                if (time.equals("once"))
                    time = "1";
                if (time.equals("twice"))
                    time = "2";
                if (time.equals("thrice"))
                    time = "3";
                Log.e("check", "value of time in string" + time);
                Log.e("check", "value of time" + Integer.parseInt(time));


                for (int i = 0; i < Integer.parseInt(time); i++) {
                    view = getLayoutInflater().inflate(R.layout.time_setfor_medication, null);
                    TextView t = (TextView) view.findViewById(R.id.number_of_times);
                    t.setText("Time " + (i + 1));
                    TimePicker picker = (TimePicker) view.findViewById(R.id.timepicker);
                    timepicker_list.add(picker);
                    parent_linearlayout.addView(view);
                }
            }
    }
    public String find_ampm(Integer hour, Integer flag){
        String format;
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        if(flag==0)
            return format;
        else
            return Integer.toString(hour);

    }
    public void back_to_main(View v) {
        Integer h;
        for(int i=0; i<timepicker_list.size(); i++){
            //TODO make the following code to support on API's  below 21.
            if(Build.VERSION.SDK_INT < 23) {
                h = timepicker_list.get(i).getCurrentHour();
                hourin12.add(h);
                ampm.add(find_ampm(h, 0));
                hour.add(find_ampm(h, 1));
                minute.add(Integer.toString(timepicker_list.get(i).getCurrentMinute()));
            }
            else {
                h = timepicker_list.get(i).getHour();
                hourin12.add(h);
                ampm.add(find_ampm(h, 0));
                hour.add(find_ampm(h, 1));
                minute.add(Integer.toString(timepicker_list.get(i).getMinute()));
            }
        }
        Intent i = new Intent();
        i.putExtra("tablet_name", tablet_name);
        i.putStringArrayListExtra("ampm", ampm);
        i.putStringArrayListExtra("hour", hour);
        i.putStringArrayListExtra("minute", minute);
        Log.e("check", "hourin12 "+hourin12.get(0));
        i.putIntegerArrayListExtra("hourin24", hourin12);
        setResult(RESULT_OK, i);
        finish();
    }
}
