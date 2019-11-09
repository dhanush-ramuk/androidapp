package com.example.application1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class FullResult extends AppCompatActivity {
ArrayList<All_Results> obj;
    String [] all_tests = {"weight", "cholesterol", "triglyceride", "HDL", "LDL", "glucose[fasting]", "glucose[random]", "calcium", "albumin", "total protein", "C02",
            "sodium", "potassium", "chloride", "alkaline phosphatase", "alanine amino transferase", "aspartate amino transferase", "bilirubin",
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
                t2.setText(map.get(all_tests[j]));
                layout.addView(v);
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
