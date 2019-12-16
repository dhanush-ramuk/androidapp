package com.example.sick;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class FullResult extends AppCompatActivity {
    ArrayList<All_Results> obj;
    String [] all_tests = {"weight", "cholesterol", "triglyceride", "HDL", "LDL", "glucose[fasting]", "glucose[random]", "calcium", "albumin", "total protein", "C02",
            "sodium", "potassium", "chloride", "alkaline phosphatase", "alanine amino transferase", "aspartate amino transferase", "bilirubin",
            "blood urea nitrogen", "creatinine", "WBC", "RBC", "hemoglobin", "platelets", "hematocrit", "BP"};
    int i;
    HelperClass helperClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_result);
        Intent intent = getIntent();
        obj = (ArrayList<All_Results>) intent.getSerializableExtra("list");
        i = intent.getIntExtra("object index", 0);
        set_value();

    }

    public void set_value(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.parent_layout);
        Map<String, String> map = obj.get(i).get_map();
        TextView date = (TextView) findViewById(R.id.dateText);
        TextView day = (TextView) findViewById(R.id.dayText);
        date.setText(obj.get(i).get_map2().get("date"));
        day.setText(obj.get(i).get_map2().get("day"));
        for (int j = 0; j < all_tests.length; j++){
            if(map.get(all_tests[j])!=null){
                View v = getLayoutInflater().inflate(R.layout.full_result_views_layout, null);
                TextView t1 = (TextView) v.findViewById(R.id.textView);
                TextView t2 = (TextView) v.findViewById(R.id.textView2);
                t1.setText(all_tests[j]);
                t2.setText(map.get(all_tests[j]) + " " +"[" + UnitIncluder(all_tests[j]).toLowerCase() + "]");
                layout.addView(v);
            }
        }
    }
    public String UnitIncluder(String testName){
        String testUnit = null;
        int i = Integer.parseInt(getPrefs("flag",getApplicationContext()));
        if (i == 1) {

            if (testName.equals("weight"))
                testUnit = "kg";
            else if (testName.equals("cholesterol") || testName.equals("triglyceride") || testName.equals("creatinine") || testName.equals("HDL") || testName.equals("LDL") || testName.equals("blood urea nitrogen") || testName.equals("glucose[fasting]") || testName.equals("glucose[random]") || testName.equals("bilirubin"))
                testUnit = "mg/dl";
            else if (testName.equals("alanine amino transferase") || testName.equals("alkaline phosphatase") || testName.equals("aspartate amino transferase"))
                testUnit = "u/l";
            else if (testName.equals("WBC") || testName.equals("RBC") || testName.equals("platelets"))
                testUnit = "μL−1";
            else if (testName.equals("hematocrit"))
                testUnit = "%";
            else if (testName.equals("hemoglobin") || testName.equals("albumin") || testName.equals("total protein"))
                testUnit = "g/dl";
            else if (testName.equals("BP"))
                testUnit = "mmHg";
            else if (testName.equals("C02") || testName.equals("sodium") || testName.equals("potassium") || testName.equals("chloride"))
                testUnit = "mEq/L";
            else
                testUnit = "";
            return testUnit;
        } else if(i==0){

            if (testName.equals("weight"))
                testUnit = "kg";
            else if (testName.equals("cholesterol") || testName.equals("triglyceride") || testName.equals("creatinine") || testName.equals("HDL") || testName.equals("LDL") || testName.equals("blood urea nitrogen") || testName.equals("glucose[fasting]") || testName.equals("glucose[random]") || testName.equals("bilirubin"))
                testUnit = "mmol/L";
            else if (testName.equals("alanine amino transferase") || testName.equals("alkaline phosphatase") || testName.equals("aspartate amino transferase"))
                testUnit = "u/l";
            else if (testName.equals("WBC") || testName.equals("RBC") || testName.equals("platelets"))
                testUnit = "L−1";
            else if (testName.equals("hematocrit"))
                testUnit = "/ of 1.0";
            else if (testName.equals("hemoglobin") || testName.equals("albumin") || testName.equals("total protein"))
                testUnit = "g/l";
            else if (testName.equals("BP"))
                testUnit = "mmHg";
            else if (testName.equals("C02") || testName.equals("sodium") || testName.equals("potassium") || testName.equals("chloride"))
                testUnit = "mmol/L";
            else
                testUnit = "";
            return testUnit;
        }
        return "b";
    }
    public static String getPrefs(String key, Context context){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(key, "1");
    }

    public void backToMain(View v){
        Intent intent = new Intent();
        intent.putExtra("value", -1);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void deleteResult(View v){
        Intent intent = new Intent();
        intent.putExtra("value", i);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void gotoTrackPage(View v){
        Intent i = new Intent(getApplicationContext(), trackpage.class);
        i.putExtra("list", obj);
        startActivityForResult(i, 995);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 995 && resultCode == RESULT_OK) {
            //do nothing
        }

        }
}
