package com.dhanush.CheckUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
        simple_switch = findViewById(R.id.simpleSwitch);
        simple_switch_standard = findViewById(R.id.simpleSwitchStandard);
        String ii = getPrefs("flag", getApplicationContext());
        if(ii.equals("notfound")){
            //do nothing
            simple_switch_standard.setVisibility(View.GONE);
        } else{
            if(Integer.parseInt(ii) == 1){
                simple_switch.setChecked(true);
                simple_switch.setVisibility(View.VISIBLE);
                simple_switch_standard.setVisibility(View.GONE);
                simple_switch.setText("Traditional");
                info.setText("[(mg/dl),(g/dl), etc.]");
            }
            if(Integer.parseInt(ii) == 0){
                simple_switch.setVisibility(View.GONE);
                simple_switch_standard.setVisibility(View.VISIBLE);
                simple_switch_standard.setChecked(true);
                simple_switch.setText("Standard");
                info.setText("[(mmol/L),(d/l), etc.]");
            }
        }


        simple_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   simple_switch_standard.setChecked(false);
                   simple_switch.setText("Traditional");
                   info.setText("[(mg/dl),(g/dl), etc.]");
                   i = 1;
                   setPrefs("flag", String.valueOf(i), getApplicationContext());

               }
               else{
                   simple_switch_standard.setVisibility(View.VISIBLE);
                   simple_switch.setVisibility(View.GONE);
                   simple_switch_standard.setChecked(true);
                   simple_switch_standard.setText("Standard");
                   info.setText("[(mmol/L),(d/l), etc.]");
                   i =0;
                   setPrefs("flag", String.valueOf(i), getApplicationContext());
               }
            }
        });
        simple_switch_standard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    simple_switch.setChecked(false);
                    simple_switch_standard.setText("Standard");
                    info.setText("[(mmol/L),(d/l), etc.]");
                    i =0;
                    setPrefs("flag", String.valueOf(i), getApplicationContext());
                }
                else{

                    simple_switch_standard.setVisibility(View.GONE);
                    simple_switch.setVisibility(View.VISIBLE);
                    simple_switch.setChecked(true);
                    simple_switch.setText("Traditional");
                    info.setText("[(mg/dl),(g/dl), etc.]");
                    i = 1;
                    setPrefs("flag", String.valueOf(i), getApplicationContext());
                }
            }
        });
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
