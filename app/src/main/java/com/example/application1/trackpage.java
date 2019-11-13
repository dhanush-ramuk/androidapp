package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class trackpage extends AppCompatActivity {
    ArrayList<All_Results> obj;
    LinearLayout parent, parent1;
    String dateanddayString = null;
    String[] all_tests = {"weight", "cholesterol", "triglyceride", "HDL", "LDL", "glucose[fasting]", "glucose[random]", "calcium", "albumin", "total protein", "C02",
            "sodium", "potassium", "chloride", "alkaline phosphatase", "alanine amino transferase", "aspartate amino transferase", "bilirubin",
            "blood urea nitrogen", "creatinine", "WBC", "RBC", "hemoglobin", "platelets", "hematocrit", "BP"};
int flag = 0;
HelperClass helperClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackpage);
        helperClass = new HelperClass();
        Intent intent = getIntent();
        obj = (ArrayList<All_Results>) intent.getSerializableExtra("list");

        figure_out(obj);


    }

    public void figure_out1(ArrayList<All_Results> o) {
        for (int i = 0; i < o.size(); i++) {
            parent = (LinearLayout) findViewById(R.id.parentLinearLayout);
            View v1 = getLayoutInflater().inflate(R.layout.bloodtracking1, null);

            TextView datenday = (TextView) v1.findViewById(R.id.dateandday);
            dateanddayString = o.get(i).get_map2().get("date") + ", " + o.get(i).get_map2().get("day");
            Log.e("check", "dateandtime " + dateanddayString);
            datenday.setText("On " + dateanddayString);
            parent1 = (LinearLayout) v1.findViewById(R.id.parentLinearLayout2);
            for (int j = 0; j < all_tests.length; j++) {
                Log.e("check", "equal " + o.get(i).get_map().get(all_tests[j]));
                if (o.get(i).get_map().get(all_tests[j]) != null) {
                    View v2 = getLayoutInflater().inflate(R.layout.bloodtracking2, null);
                    TextView t1 = (TextView) v2.findViewById(R.id.textView);
                    TextView t2 = (TextView) v2.findViewById(R.id.textView2);
                    Log.e("check", "textvalues " + o.get(i).get_map().get(all_tests[j]));
                    t1.setText(all_tests[j]);
                    t2.setText(o.get(i).get_map().get(all_tests[j]));
                    parent1.addView(v2);
                }
            }
            parent.addView(v1);

        }
    }
        public void figure_out(ArrayList<All_Results> o) {
            for (int i = 0; i < all_tests.length; i++) {
                parent = (LinearLayout) findViewById(R.id.parentLinearLayout);
                View v1 = getLayoutInflater().inflate(R.layout.bloodtracking1, null);
                TextView datenday = (TextView) v1.findViewById(R.id.dateandday);
                dateanddayString = all_tests[i];
                Log.e("check", "dateandtime " + dateanddayString);
                datenday.setText(dateanddayString +" " + "[" + helperClass.UnitIncluder(dateanddayString) + "]");
                parent1 = (LinearLayout) v1.findViewById(R.id.parentLinearLayout2);
                for (int j = 0; j < o.size(); j++) {
                    Log.e("check", "check "+o.get(j).get_map().get(all_tests[i]));
                    if (o.get(j).get_map().get(all_tests[i]) != null) {
                        View v2 = getLayoutInflater().inflate(R.layout.bloodtracking2, null);
                        TextView t1 = (TextView) v2.findViewById(R.id.textView);
                        TextView t2 = (TextView) v2.findViewById(R.id.textView2);
                        t1.setText(o.get(j).get_map2().get("date"));
                        t2.setText(o.get(j).get_map().get(all_tests[i]));
                        parent1.addView(v2);
                        flag = 1;
                    }
                }
                if(flag == 1) {
                    flag = 0;
                    parent.addView(v1);
                }
            }
    }

    public void back_to(View v){
        Intent intent = new Intent();

        setResult(RESULT_OK, intent);
        finish();
    }
}
