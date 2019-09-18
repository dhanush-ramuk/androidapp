package com.example.application1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> basic_test_lhs, CBC_lhs, kidney_test_lhs, liver_test_lhs, electrolytes_lhs, proteins_lhs, general_test_lhs, lipid_panel_lhs;
    ArrayList<String> basic_test_rhs, CBC_rhs, kidney_test_rhs, liver_test_rhs, electrolytes_rhs, proteins_rhs, general_test_rhs, lipid_panel_rhs;
    String [] all_tests = {"weight", "cholesterol", "triglyceride", "HDL", "LDL", "glucose[fasting]", "glucose[random]", "calcium", "albumin", "total protein", "C02",
            "sodium", "potassium", "chloride", "alkaline phosphatase", "alanine aminotransferase", "aspartate aminotransferase", "bilirubin",
            "blood urea nitrogen", "creatinine", "WBC", "RBC", "hemoglobin", "platelets", "hematocrit", "BP"};
    ArrayList<String> main_value_text, second_value_text, third_value_text, day_text = new ArrayList<String>();
    ArrayList<String> main_value, second_value, third_value, date_text = new ArrayList<String>();
    ArrayList<String> prioritized_left = new ArrayList<String>();
    ArrayList<String> prioritized_left1 = new ArrayList<String>();
    ArrayList<String> prioritized_left2 = new ArrayList<String>();
    String string;
    Map<String, String> map;
    ArrayList<All_Results> obj = new ArrayList<All_Results>();
    ListView v;
    Customadapter1 adapter = new Customadapter1();
    Random rand = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.i("check", "create started");

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("mylist", "");
        ArrayList<All_Results> start_obj = gson.fromJson(json,
                new TypeToken<List<All_Results>>(){}.getType());
        if(start_obj!=null) {
            for (int i = 0; i < start_obj.size(); i++) {
                obj.add(start_obj.get(i));
            }
        }
        create_list(obj);
        FloatingActionButton fab = findViewById(R.id.fab_lab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), Activity2.class), 999);
            }
        });

    }

    protected void onStart(){
        super.onStart();
        Log.i("check", "onStart started");



    }

    protected void onStop(){
        super.onStop();
        Log.i("check", "Pause started");

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

        Log.i("check", "Return started");
        if(requestCode==999 && resultCode==RESULT_OK){
            Log.i("check", "Return started entered");

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
        go_figure_the_fuck_out(map);

        obj.add(new All_Results(map));


        clear_list();
        create_list(obj);

    }

    public void create_list(ArrayList<All_Results> obj){
        Log.i("check", "in_create_list");
        if(obj!=null && !obj.isEmpty()){
            Log.i("check", "in_create_list_list");

            for(int i=0;i<obj.size(); i++){
                 the_brain(obj.get(i).get_map());
            }
        }
    }

    public void clear_list(){

            main_value.clear();
            main_value_text.clear();

        adapter.notifyDataSetChanged();
    }

    public void go_figure_the_fuck_out(Map map){


        ArrayList<String> sorting, sorting1, sorting2;
        sorting = new ArrayList<String>();
        sorting1 = new ArrayList<String>();
        sorting2 = new ArrayList<String>();

        if(map.get("hemoglobin")!=null){
            sorting1.add("hemoglobin");
        }
        if(map.get("calcium")!=null){
            sorting1.add("calcium");
        }

        if(map.get("RBC")!=null){
            sorting1.add("RBC");
        }

        if(map.get("WBC")!=null){
            sorting1.add("WBC");
        }
        if(map.get("blood urea nitrogen")!=null){
            sorting1.add("blood urea nitrogen");
        }
        if(map.get("potassium")!=null){
            sorting1.add("potassium");
        }


        if(map.get("weight")!=null){
            sorting.add("weight");
        }
        if(map.get("creatinine")!=null){
            sorting.add("creatinine");
        }
        if(map.get("glucose[fasting]")!= null){
            sorting.add("glucose[random]");
        }
        if(map.get("cholesterol")!=null){
            sorting.add("cholesterol");
        }

        for(int i=0; i<all_tests.length; i++){
            if(sorting.contains(all_tests[i]) || sorting1.contains(all_tests[i])){
            }else{
                sorting2.add(all_tests[i]);
            }
        }

        int rand_int1 = rand.nextInt(sorting.size());
        int rand_int2 = rand.nextInt(sorting1.size());
        int rand_int3 = rand.nextInt(sorting2.size());

        if(!sorting.isEmpty()){
            prioritized_left.add(sorting.get(rand_int1));
        } else if(!sorting1.isEmpty()){
            prioritized_left.add(sorting1.get(rand_int2));
        } else{
            prioritized_left.add(sorting2.get(rand_int3));
        }

        int rand_int4, rand_int5, rand_int6;

        if(!sorting1.isEmpty()){
            do{
                rand_int4 = rand.nextInt(sorting1.size());
            }while(prioritized_left.contains(sorting1.get(rand_int4)));
            prioritized_left1.add(sorting1.get(rand_int4));
        } else if(!sorting.isEmpty()){
            do{
                rand_int5 = rand.nextInt(sorting.size());
            }while(prioritized_left.contains(sorting.get(rand_int5)));
            prioritized_left1.add(sorting.get(rand_int5));
        } else if(!sorting2.isEmpty()){
            do{
                rand_int6 = rand.nextInt(sorting.size());
            }while(prioritized_left.contains(sorting2.get(rand_int6)));
            prioritized_left1.add(sorting.get(rand_int6));
        } else{
            prioritized_left1.add("no value");
        }

        if(!sorting1.isEmpty()){
            do{
                rand_int4 = rand.nextInt(sorting1.size());
            }while(prioritized_left.contains(sorting1.get(rand_int4)) || prioritized_left1.contains(sorting1.get(rand_int4)));
            prioritized_left1.add(sorting1.get(rand_int4));
        } else if(!sorting.isEmpty()){
            do{
                rand_int5 = rand.nextInt(sorting.size());
            }while(prioritized_left.contains(sorting.get(rand_int5)) || prioritized_left1.contains(sorting.get(rand_int5)));
            prioritized_left1.add(sorting.get(rand_int5));
        } else if(!sorting2.isEmpty()){
            do{
                rand_int6 = rand.nextInt(sorting.size());
            }while(prioritized_left.contains(sorting2.get(rand_int6)) || prioritized_left1.contains(sorting2.get(rand_int6)));
            prioritized_left1.add(sorting.get(rand_int6));
        } else{
            prioritized_left1.add("no value");
        }
    }


    public void the_brain(Map mini_map){
//TODO add more lab tests in the main list and polish UI.









        v = (ListView)findViewById(R.id.listview_main);
        v.setAdapter(adapter);
    }
    public class Customadapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return main_value_text.size();
        }

        @Override
        public Object getItem(int i) {
            return main_value_text.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.main_list, null);
            TextView text1 = (TextView)view.findViewById(R.id.main_value);
            TextView text2 = (TextView)view.findViewById(R.id.main_value_name);
            text1.setText(main_value.get(i));
            text2.setText(main_value_text.get(i));
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
