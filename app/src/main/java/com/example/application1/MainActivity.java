package com.example.application1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.*;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    ArrayList<All_Results> obj = new ArrayList<All_Results>();
    ListView v;
    Customadapter1 adapter = new Customadapter1();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create_list();
        FloatingActionButton fab = findViewById(R.id.fab_lab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), Activity2.class), 999);
            }
        });

    }
    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("mylist", "");
        ArrayList<All_Results> lstArrayList = gson.fromJson(json,
                new TypeToken<List<All_Results>>(){}.getType());
        if(!lstArrayList.isEmpty()) {
            obj = (ArrayList<All_Results>) lstArrayList.clone();
        }
    }

    protected void onPause(){
        super.onPause();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString("mylist", json);
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i("check", "love4");
        clear_list();
        create_list();
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

        if(!kidney_test_rhs.isEmpty()) {
            int n = kidney_test_rhs.size();
            for (int i = 0; i < n; i++) {
                map.put(kidney_test_lhs.get(0), kidney_test_rhs.get(0));
            }
        }
        if(!CBC_rhs.isEmpty()) {
            int n = CBC_rhs.size();
            for (int i = 0; i < n; i++) {
                map.put(CBC_lhs.get(0), CBC_rhs.get(0));
            }
        }
        if(!liver_test_rhs.isEmpty()) {
            int n = liver_test_rhs.size();
            for (int i = 0; i < n; i++) {
                map.put(liver_test_lhs.get(0), liver_test_rhs.get(0));
            }
        }
        if(!electrolytes_lhs.isEmpty()) {
            int n = electrolytes_rhs.size();
            for (int i = 0; i < n; i++) {
                map.put(electrolytes_lhs.get(0), electrolytes_rhs.get(0));
            }
        }
        if(!lipid_panel_rhs.isEmpty()) {
            int n = lipid_panel_rhs.size();
            for (int i = 0; i < n; i++) {
                map.put(lipid_panel_lhs.get(0), lipid_panel_rhs.get(0));
            }
        }
        if(!proteins_rhs.isEmpty()) {
            int n = proteins_rhs.size();
            for (int i = 0; i < n; i++) {
                map.put(proteins_lhs.get(0), proteins_rhs.get(0));
            }
        }
        if(!general_test_rhs.isEmpty()) {
            int n = general_test_rhs.size();
            for (int i = 0; i < n; i++) {
                map.put(general_test_lhs.get(0), general_test_rhs.get(0));
            }
        }


        Log.i("count", "after map");


        obj.add(new All_Results(map));



        the_brain(map);
        v = (ListView)findViewById(R.id.listview_main);
        Log.i("count", "before map46");

        v.setAdapter(adapter);
        Log.i("count", "before map56");

    }

    public void create_list(){
        if(!obj.isEmpty()){
            for(int i=0;i<obj.size(); i++){
                 the_brain(obj.get(i).get_map());
            }
        }
    }

    public void clear_list(){
        int i = 0;
        while(!a_imp.isEmpty()){
            a_imp.remove(i);
            b_imp.remove(i);
            i++;
        }
        adapter.notifyDataSetChanged();
    }

    public void the_brain(Map mini_map){


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
