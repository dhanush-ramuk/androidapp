package com.dhanush.CheckUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExtraFeaturesBloodWork extends AppCompatActivity {
    int alertForNextBloodWork = 0;
    android.widget.DatePicker BloodWorkdatePicker, AlertNextBloodWork;
    EditText doctorsComment;
    Button commentOkayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_features_blood_work);
        AlertNextBloodWork = (android.widget.DatePicker) findViewById(R.id.simpleDatePicker);
        BloodWorkdatePicker = (android.widget.DatePicker) findViewById(R.id.BloodWorkDatePicker);
        commentOkayButton = (Button) findViewById(R.id.okButtonComment);
        doctorsComment = (EditText) findViewById(R.id.DoctorsCommentEditText);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioButtonRefill);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
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

    public void setDoctorsComment(View v) {
        doctorsComment.setCursorVisible(true);
        if(!doctorsComment.getText().toString().equals(" ")) {
            if (commentOkayButton.getText().toString().equals("Ok")) {
                commentOkayButton.setText("Edit");
                commentOkayButton.setBackgroundColor(Color.parseColor("#d50000"));

            } else {
                commentOkayButton.setText("Ok");
                commentOkayButton.setBackgroundColor(Color.parseColor("#00c853"));
            }
            if (commentOkayButton.getText().toString().equals("Edit")) {
                doctorsComment.setFocusable(false);
                doctorsComment.setEnabled(false);
                doctorsComment.setCursorVisible(false);
            } else {
                doctorsComment.setFocusable(true);
                doctorsComment.setTextIsSelectable(true);
                doctorsComment.setEnabled(true);
            }
        }
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
            Calendar startDate = Calendar.getInstance();
            startDate.set(Calendar.YEAR, AlertNextBloodWork.getYear());
            startDate.set(Calendar.MONTH, AlertNextBloodWork.getMonth());
            startDate.set(Calendar.DAY_OF_MONTH, AlertNextBloodWork.getDayOfMonth());
            startDate.set(Calendar.HOUR_OF_DAY, Calendar.HOUR_OF_DAY);
            startDate.set(Calendar.MINUTE, 0);
            startDate.set(Calendar.SECOND, 0);
            Log.e("check", "current time " +System.currentTimeMillis());
            Log.e("check", "set time "+startDate.getTimeInMillis());
            if((System.currentTimeMillis() >= startDate.getTimeInMillis()) && alertForNextBloodWork == 1){
                Toast.makeText(getApplicationContext(), "The BloodWork remainder is in the past", Toast.LENGTH_LONG).show();
            }
            else {
                String text = doctorsComment.getText().toString().trim();
                Log.e("check", "doctor "+text);
                if(commentOkayButton.getText().toString().equals("Edit") && !text.isEmpty()){
                    i.putExtra("havedoctorscomment", 1);
                    Log.d("check", "doctors comment "+doctorsComment.getText().toString());
                    Log.d("check", "doctors commentttt "+doctorsComment.getText().toString());
                    i.putExtra("doctorsComment", doctorsComment.getText().toString());

                } else {
                    i.putExtra("havedoctorscomment", 0);
                }
                i.putExtra("alertForNextBloodWork", alertForNextBloodWork);
                i.putExtra("boolean", 1);
                setResult(RESULT_OK, i);
                finish();
            }
        } else {
            String text = doctorsComment.getText().toString().trim();
            Log.e("check", "doctor "+text);
            if(commentOkayButton.getText().toString().equals("Edit") && !text.isEmpty()){
                i.putExtra("havedoctorscomment", 1);
                Log.d("check", "doctors comment "+doctorsComment.getText().toString());
                i.putExtra("doctorsComment", doctorsComment.getText().toString());

            } else {
                i.putExtra("havedoctorscomment", 0);
            }
            i.putExtra("alertForNextBloodWork", alertForNextBloodWork);
            i.putExtra("boolean", 1);
            setResult(RESULT_OK, i);
            finish();
        }
    }

    public void closeActivity(View v){
        Intent i = new Intent();
        i.putExtra("boolean", "0");
        setResult(RESULT_OK, i);
        finish();
    }
}
