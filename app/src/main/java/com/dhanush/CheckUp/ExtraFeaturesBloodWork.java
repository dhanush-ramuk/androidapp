package com.dhanush.CheckUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class ExtraFeaturesBloodWork extends AppCompatActivity {
    int alertForNextBloodWork = 0;
    android.widget.DatePicker BloodWorkdatePicker, AlertNextBloodWork;
    EditText doctorsComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_features_blood_work);
        AlertNextBloodWork = (android.widget.DatePicker) findViewById(R.id.simpleDatePicker);
        BloodWorkdatePicker = (android.widget.DatePicker) findViewById(R.id.BloodWorkDatePicker);

        doctorsComment = (EditText) findViewById(R.id.DoctorsCommentEditText);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioButtonRefill);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch(checkedId) {
                    case R.id.radio_pirates1:
                        alertForNextBloodWork = 0;
                        AlertNextBloodWork.setVisibility(View.GONE);
                        Log.e("check", "pirates");
                        break;
                    case R.id.radio_ninjas1:
                        alertForNextBloodWork = 1;
                        AlertNextBloodWork.setVisibility(View.VISIBLE);
                        Log.e("check", "pirates");
                        break;
                }
            }
        });

    }
    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {

        }
    }

    public void backToMedicationReminderSettings(View V){
        Intent i = new Intent();
        if(alertForNextBloodWork!=0) {
            i.putExtra("dayalert", AlertNextBloodWork.getDayOfMonth());
            i.putExtra("monthalert", (AlertNextBloodWork.getMonth()));
            i.putExtra("yearalert", AlertNextBloodWork.getYear());
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
            Date date = new Date(AlertNextBloodWork.getYear(), AlertNextBloodWork.getMonth(), (AlertNextBloodWork.getDayOfMonth() - 1));
            String dayOfWeek = simpledateformat.format(date);
            i.putExtra("dayofweekNextReminder", dayOfWeek);
        }
        if(!doctorsComment.getText().toString().equals("")){
            i.putExtra("doctorsComment", doctorsComment.getText().toString());
            Log.e("check", "written something");
        } else {
            Log.e("check", "empty");

        }
        i.putExtra("alertForNextBloodWork", alertForNextBloodWork);
        i.putExtra("boolean", 1);
        i.putExtra("day", BloodWorkdatePicker.getDayOfMonth());
        i.putExtra("month", (BloodWorkdatePicker.getMonth()+1));
        i.putExtra("year", BloodWorkdatePicker.getYear());
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date date = new Date(BloodWorkdatePicker.getYear(), BloodWorkdatePicker.getMonth(), (BloodWorkdatePicker.getDayOfMonth()-1));
        String dayOfWeek = simpledateformat.format(date);
        i.putExtra("dayofweek", dayOfWeek);
        setResult(RESULT_OK, i);
        finish();
    }

    public void closeActivity(View v){
        Intent i = new Intent();
        i.putExtra("boolean", "0");
        setResult(RESULT_OK, i);
        finish();
    }
}
