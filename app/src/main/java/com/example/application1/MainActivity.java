package com.example.application1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> basic_test_lhs, CBC_lhs, kidney_test_lhs, liver_test_lhs, electrolytes_lhs, proteins_lhs, general_test_lhs, lipid_panel_lhs;
    ArrayList<String> basic_test_rhs, CBC_rhs, kidney_test_rhs, liver_test_rhs, electrolytes_rhs, proteins_rhs, general_test_rhs, lipid_panel_rhs;
    String [] all_tests = {"weight", "cholestrol", "triglyceride", "HDL", "LDL", "glucose[fasting]", "glucose[random]", "calcium", "albumin", "total protein", "C02",
            "sodium", "potassium", "chloride", "alkaline phosphatase", "alanine aminotransferase", "aspartate aminotransferase", "bilirubin",
            "blood urea nitrogen", "creatinine", "WBC", "RBC", "hemoglobin", "platelets", "hematocrit", "BP"};
    ArrayList<String> a_imp = new ArrayList<String>();
    ArrayList<String> b_imp = new ArrayList<String>();
    String string;
    Map<String, String> map;
    ListView v;
    Customadapter1 adapter = new Customadapter1();
    All_Results obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab_lab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), Activity2.class), 999);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i("check", "love4");

        if(requestCode==999 && resultCode==RESULT_OK){

            basic_test_lhs = new ArrayList<String>();
            basic_test_lhs = data.getStringArrayListExtra("basic_test_names");
            basic_test_rhs = new ArrayList<String>();
            basic_test_rhs = data.getStringArrayListExtra("basic_test_values");
            CBC_lhs = new ArrayList<String>();
            CBC_lhs = data.getStringArrayListExtra("CBC_test_names");
            CBC_rhs = new ArrayList<String>();
            CBC_rhs = data.getStringArrayListExtra("CBC_test_values");
            general_test_lhs = new ArrayList<String>();
            general_test_lhs = data.getStringArrayListExtra("general_test_names");
            general_test_rhs = new ArrayList<String>();
            general_test_rhs = data.getStringArrayListExtra("general_test_values");
            kidney_test_lhs = new ArrayList<String>();
            kidney_test_lhs = data.getStringArrayListExtra("kidney_test_names");
            kidney_test_rhs = new ArrayList<String>();
            kidney_test_rhs = data.getStringArrayListExtra("kidney_test_values");
            liver_test_lhs = new ArrayList<String>();
            liver_test_lhs = data.getStringArrayListExtra("liver_test_names");
            liver_test_rhs = new ArrayList<String>();
            liver_test_rhs = data.getStringArrayListExtra("liver_test_values");
            electrolytes_lhs = new ArrayList<String>();
            electrolytes_lhs = data.getStringArrayListExtra("electrolytes_test_names");
            electrolytes_rhs = new ArrayList<String>();
            electrolytes_rhs = data.getStringArrayListExtra("electrolytes_test_values");
            proteins_lhs = new ArrayList<String>();
            proteins_lhs = data.getStringArrayListExtra("proteins_test_names");
            proteins_rhs = new ArrayList<String>();
            proteins_rhs = data.getStringArrayListExtra("proteins_test_values");
            lipid_panel_lhs = new ArrayList<String>();
            lipid_panel_lhs = data.getStringArrayListExtra("lipid_test_names");
            lipid_panel_rhs = new ArrayList<String>();
            lipid_panel_rhs = data.getStringArrayListExtra("lipid_test_values");
        }
        Log.i("count", "before map");

        map = new HashMap<String, String>();
        for(String a: all_tests){
            map.put(a, null);
        }
        Log.i("count", "hello" + map.get("weight"));


        if(!basic_test_rhs.isEmpty()) {
            int n = basic_test_rhs.size();
            for (int i = 0; i < n; i++) {
                map.put(basic_test_lhs.get(0), basic_test_rhs.get(0));
            }
        }



        Log.i("count", "after map");

        obj = new All_Results(map);



        the_brain(map);
        v = (ListView)findViewById(R.id.listview_main);
        Log.i("count", "before map46");

        v.setAdapter(adapter);
        Log.i("count", "before map56");

    }


    public void the_brain(Map mini_map){
        ArrayList<String> countries = new ArrayList<String>(mini_map.keySet());

        Log.i("count", "hkk");

        ArrayList<String> values = new ArrayList<String>(mini_map.values());

        if((mini_map.get("weight")!=null)){
                a_imp.add("weight");
            Log.i("count", "True" + mini_map.get("weight"));

            b_imp.add(mini_map.get("weight").toString());
            }

    }
    public class Customadapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return a_imp.size();
        }

        @Override
        public Object getItem(int i) {
            return a_imp.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.main_list, null);
            TextView text1 = (TextView)view.findViewById(R.id.textView2);
            TextView text2 = (TextView)view.findViewById(R.id.textView3);
            text1.setText(b_imp.get(i));
            text2.setText(a_imp.get(i));
            RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.main_list_relout);

            rel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Toast.makeText(getApplicationContext(), "Ta da", Toast.LENGTH_SHORT).show();

                }
            });
            return view;
        }
    }
}
