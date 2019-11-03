package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class FullResult extends AppCompatActivity {
ArrayList<All_Results> obj;
    String [] all_tests = {"weight", "cholesterol", "triglyceride", "HDL", "LDL", "glucose[fasting]", "glucose[random]", "calcium", "albumin", "total protein", "C02",
            "sodium", "potassium", "chloride", "alkaline phosphatase", "alanine aminotransferase", "aspartate aminotransferase", "bilirubin",
            "blood urea nitrogen", "creatinine", "WBC", "RBC", "hemoglobin", "platelets", "hematocrit", "BP"};
    int i;
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
        TextView t1 = (TextView) findViewById(R.id.textView);
        TextView t2 = (TextView) findViewById(R.id.textView2);
        Log.i("check", "7"+ i);
        Log.i("check", "map value"+ obj.get(i).get_map());
        Map<String, String> map = obj.get(i).get_map();
        Log.i("check", "8");
        for (int j = 0; j < all_tests.length; j++){
            if(map.get(all_tests[j])!=null){
                t1.setText(all_tests[j]);
                t2.setText(map.get(all_tests[j]));
            }
        }
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
}
