package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class infoPage extends AppCompatActivity {
Switch simple_switch;

public static int i = 1;
Map<String, String> Sswitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        final TextView info = (TextView) findViewById(R.id.SIunitissettext);
        simple_switch = findViewById(R.id.simpleSwitch);
        String ii = getPrefs("flag", getApplicationContext());
        if(ii.equals("notfound")){
            //do nothing
        } else{
            if(Integer.parseInt(ii) == 1){
                simple_switch.setChecked(true);
                simple_switch.setText("Traditional Unit");
                info.setText("[(mg/dl),(g/dl), etc.]");
            } else if(Integer.parseInt(ii) == 0){
                simple_switch.setChecked(false);
                simple_switch.setText("Standard Unit");
                info.setText("[(mmol/L),(d/l), etc.]");
            }
        }


        simple_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   simple_switch.setText("Traditional Unit");
                   info.setText("[(mg/dl),(g/dl), etc.]");
                   i = 1;
               } else {
                   simple_switch.setText("Standard Unit");
                   info.setText("[(mmol/L),(g/l), etc.]");
                   i = 0;
               }
               setPrefs("flag", String.valueOf(i), getApplicationContext());

            }
        });
    }
    public static String getPrefs(String key, Context context){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(key, "notfound");
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
        Log.i("Finished sending email...", "");
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

    public void show(View v){
        LinearLayout l = (LinearLayout) findViewById(R.id.featuresList);
        TextView b = (TextView) findViewById(R.id.updownButton);
        if (l.getVisibility() == View.GONE) {
            l.setVisibility(View.VISIBLE);
            b.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.up, 0, 0);
            //videoView_linearlayout.setVisibility(View.GONE);
        } else {
            l.setVisibility(View.GONE);
            b.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.down, 0, 0);
        }
    }


}
