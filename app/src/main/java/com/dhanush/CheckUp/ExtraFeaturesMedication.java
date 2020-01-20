package com.dhanush.CheckUp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ExtraFeaturesMedication extends AppCompatActivity {
int alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_features_medication);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioButtonAlert);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch(checkedId) {
                    case R.id.radio_pirates:
                            alert = 0;
                            Log.e("check", "pirates");
                        break;
                    case R.id.radio_ninjas:
                            alert = 1;
                        Log.e("check", "ninjas");
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
            case R.id.radio_pirates:
                if (checked)
                    alert = 0;
                break;
            case R.id.radio_ninjas:
                if (checked)
                    alert = 1;
                break;
        }
    }
}
