package com.dhanush.CheckUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class infoPage extends AppCompatActivity {
Switch simple_switch, simple_switch_standard;

public static int i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        final TextView info = (TextView) findViewById(R.id.SIunitissettext);
        RadioButton but1 = (RadioButton) findViewById(R.id.radio_pirates1);
        RadioButton but2 = (RadioButton) findViewById(R.id.radio_ninjas1);
//        simple_switch = findViewById(R.id.simpleSwitch);
  //      simple_switch_standard = findViewById(R.id.simpleSwitchStandard);
        String ii = getPrefs("flag", getApplicationContext());
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioButtonRefill);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch(checkedId) {
                    case R.id.radio_pirates1:
                        i = 1;
                        info.setText("[(mg/dl),(g/dl), etc.]");
                        setPrefs("flag", String.valueOf(i), getApplicationContext());
                        break;
                    case R.id.radio_ninjas1:
                        i = 0;
                        info.setText("[(mmol/L),(d/l), etc.]");
                        setPrefs("flag", String.valueOf(i), getApplicationContext());
                        break;
                }
            }
        });
        if(ii.equals("notfound")){
            //do nothing
        } else{
            if(Integer.parseInt(ii) == 1){

                but1.setChecked(true);
                but2.setChecked(false);
                info.setText("[(mg/dl),(g/dl), etc.]");
            }
            if(Integer.parseInt(ii) == 0){
                but1.setChecked(false);
                but2.setChecked(true);
                info.setText("[(mmol/L),(d/l), etc.]");
            }
        }
    }
    public static String getPrefs(String key, Context context){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(key, "1");
    }
    public static void setPrefs(String key, String value, Context a){

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(a);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key, value);
        editor.apply();
    }
public void email(View v){
    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto","dhanush.10087@gmail.com", null));
    intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
    intent.putExtra(Intent.EXTRA_TEXT, "m");

    try {
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));

        finish();
    } catch (android.content.ActivityNotFoundException ex) {
        Toast.makeText(infoPage.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
    }
}

public void twitter(View v){
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/dhanush_ramuk"));
    startActivity(browserIntent);
}

    public void icons8(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://icons8.com"));
        startActivity(browserIntent);
    }




}
