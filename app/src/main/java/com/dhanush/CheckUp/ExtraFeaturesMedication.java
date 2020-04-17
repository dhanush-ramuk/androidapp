package com.dhanush.CheckUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class ExtraFeaturesMedication extends AppCompatActivity {
    int alertForNotificationType = 0;
    int alertForRefill = 0;
    DatePicker simpleDatePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_features_medication);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioButtonAlert);
        simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch(checkedId) {
                    case R.id.radio_pirates:
                            alertForNotificationType = 0;
                        break;
                    case R.id.radio_ninjas:
                            alertForNotificationType = 1;
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
            case R.id.radio_pirates1:
                if (checked)
                    alertForRefill = 0;
                    simpleDatePicker.setVisibility(View.GONE);
                break;
            case R.id.radio_ninjas1:
                if (checked)
                    alertForRefill = 1;
                    simpleDatePicker.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void backToMedicationReminderSettings(View V){
            Intent i = new Intent();
            if(alertForRefill!=0) {
                i.putExtra("day", simpleDatePicker.getDayOfMonth());
                i.putExtra("month", (simpleDatePicker.getMonth()));
                i.putExtra("year", simpleDatePicker.getYear());
                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                Date date = new Date(simpleDatePicker.getYear(), simpleDatePicker.getMonth(), (simpleDatePicker.getDayOfMonth() - 1));
                String dayOfWeek = simpledateformat.format(date);
                i.putExtra("dayofweek", dayOfWeek);
            }
            i.putExtra("notificationType", alertForNotificationType);
            i.putExtra("refillValue", alertForRefill);
            i.putExtra("boolean", 1);
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
