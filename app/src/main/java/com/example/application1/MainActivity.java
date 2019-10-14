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

import java.io.Serializable;
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
    int rand_int1, rand_int2, rand_int3, rand_int4, rand_int5, rand_int6;
    ArrayList<String> main_value_text = new ArrayList<String>();
    ArrayList<String> main_value  = new ArrayList<String>();
    ArrayList<String> second_value_text = new ArrayList<String>();
    ArrayList<String> second_value  = new ArrayList<String>();
    ArrayList<String> third_value_text = new ArrayList<String>();
    ArrayList<String> third_value  = new ArrayList<String>();
    ArrayList<String> date_text = new ArrayList<String>();
    ArrayList<String> day_text  = new ArrayList<String>();
    ArrayList<String> prioritized_left = new ArrayList<String>();
    ArrayList<String> prioritized_left1 = new ArrayList<String>();
    ArrayList<String> prioritized_left2 = new ArrayList<String>();
    ArrayList<String> hour, minute, ampm;
    Map<String, String> map;
    Map<String, String> map1;

    ArrayList<All_Results> obj = new ArrayList<All_Results>();
    String day, date;
    ListView v;
    Customadapter1 adapter = new Customadapter1();
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("check", "create started");

        // Using shared preference to get the object that contains all the previous results.
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("mylist", "");
        ArrayList<All_Results> start_obj = gson.fromJson(json,
                new TypeToken<List<All_Results>>(){}.getType());
        if(start_obj!=null) {
                obj.addAll(start_obj);
        }
        create_list(obj);

        //button click -> activity to get all the results from user.
        FloatingActionButton fab = findViewById(R.id.fab_lab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), Activity2.class), 999);
            }
        });

        FloatingActionButton fab_tab = findViewById(R.id.fab_tab);
        fab_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), Activity3.class), 998);
            }
        });
    }



    protected void onResume(){
        super.onResume();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("check", "check");
        Log.e("check", "code "+requestCode);
        Log.e("check", "code "+resultCode);
        if (requestCode == 998 && resultCode == RESULT_OK) {
            //TODO add code for notification of tablet remainder and creating main list for tablet
            Log.e("check", "check");
            hour = new ArrayList<String>();
            hour = data.getStringArrayListExtra("hour");
            minute = new ArrayList<String>();
            minute = data.getStringArrayListExtra("minute");
            ampm = new ArrayList<String>();
            ampm = data.getStringArrayListExtra("ampm");
            Log.e("check", "hour "+hour.get(0));
            Log.e("check", "minute "+minute.get(1));
            Log.e("check", "ampm "+ampm.get(2));
        }

        if (requestCode == 999 && resultCode == RESULT_OK) {
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
            date = data.getStringExtra("date");
            day = data.getStringExtra("day");


            map = new HashMap<String, String>();
            for (String a : all_tests) {
                map.put(a, null);
            }


            if (!basic_test_rhs.isEmpty()) {
                for (int i = 0; i < basic_test_lhs.size(); i++)
                    map.put(basic_test_lhs.get(i), basic_test_rhs.get(i));
            }

            if (!kidney_test_rhs.isEmpty()) {
                for (int i = 0; i < kidney_test_lhs.size(); i++)
                    map.put(kidney_test_lhs.get(i), kidney_test_rhs.get(i));
            }
            if (!CBC_rhs.isEmpty()) {
                for (int i = 0; i < CBC_lhs.size(); i++)
                    map.put(CBC_lhs.get(i), CBC_rhs.get(i));
            }
            if (!liver_test_rhs.isEmpty()) {
                for (int i = 0; i < liver_test_lhs.size(); i++)
                    map.put(liver_test_lhs.get(i), liver_test_rhs.get(i));
            }
            if (!electrolytes_lhs.isEmpty()) {
                for (int i = 0; i < electrolytes_lhs.size(); i++)
                    map.put(electrolytes_lhs.get(i), electrolytes_rhs.get(i));
            }
            if (!lipid_panel_rhs.isEmpty()) {
                for (int i = 0; i < lipid_panel_lhs.size(); i++)
                    map.put(lipid_panel_lhs.get(i), lipid_panel_rhs.get(i));
            }
            if (!proteins_rhs.isEmpty()) {
                for (int i = 0; i < proteins_lhs.size(); i++)
                    map.put(proteins_lhs.get(i), proteins_rhs.get(i));
            }
            if (!general_test_rhs.isEmpty()) {
                for (int i = 0; i < general_test_lhs.size(); i++)
                    map.put(general_test_lhs.get(i), general_test_rhs.get(i));
            }

            go_figure_the_fuck_out(map);
            Log.i("check", "value of left1 before adding to object " + prioritized_left1);
            map1 = new HashMap<String, String>();
            map1.put("p", prioritized_left.get(0));
            map1.put("p1", prioritized_left1.get(0));
            map1.put("p2", prioritized_left2.get(0));


            obj.add(new All_Results(map, map1));

            //Saving the object that contains the current values entered by the user in shared preference
            //Saving it in the onStop method
            //SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            //SharedPreferences.Editor editor = sharedPrefs.edit();
            //Gson gson = new Gson();
            //String json = gson.toJson(obj);
            //editor.putString("mylist", json);
            //editor.apply();

            clear_list();
            create_list(obj);
            clear_ArrayList();

        }
    }

    public void clear_ArrayList(){
        if(!prioritized_left1.isEmpty())
            prioritized_left1.clear();
        if(!prioritized_left.isEmpty())
            prioritized_left.clear();
        if(!prioritized_left2.isEmpty())
            prioritized_left2.clear();
    }

    public void create_list(ArrayList<All_Results> obj){
        if(obj!=null && !obj.isEmpty()){
            for(int i=0;i<obj.size(); i++){the_brain(obj.get(i).get_map(), obj.get(i).get_map1());
            }

        }
    }

    public void clear_list(){
        main_value.clear();
        main_value_text.clear();
        second_value.clear();
        second_value_text.clear();
        third_value.clear();
        third_value_text.clear();
        date_text.clear();
        day_text.clear();
        adapter.notifyDataSetChanged();
    }

    public void go_figure_the_fuck_out(Map map){
        ArrayList<String> sorting, sorting1, sorting2;
        sorting = new ArrayList<String>();
        sorting1 = new ArrayList<String>();
        sorting2 = new ArrayList<String>();

        // Assigning the user entered test values to different ArrayList based on the test priority.
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
            Log.i("check", "WBC added in sorting 1");
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
                //do nothing
            }else if(map.get(all_tests[i])!=null) {
                sorting2.add(all_tests[i]);
                Log.i("check", "checking value of sorting" + all_tests[i]);

            }
        }

            Log.i("check", "size of sorting2"+ sorting2.size());
        //TODO change this algorithm to be more personalized.
        if(!sorting.isEmpty()){
            rand_int1 = rand.nextInt(sorting.size());
            prioritized_left.add(sorting.get(rand_int1));
        } else if(!sorting1.isEmpty()){
            rand_int2 = rand.nextInt(sorting1.size());
            prioritized_left.add(sorting1.get(rand_int2));
        } else if(!sorting2.isEmpty()){
            rand_int3 = rand.nextInt(sorting2.size());
            prioritized_left.add(sorting2.get(rand_int3));
        }

        if(!sorting1.isEmpty()){
            for(int i = 0; i<sorting1.size();i++){
                rand_int4 = rand.nextInt(sorting1.size());
                if(!prioritized_left.contains(sorting1.get(rand_int4))){
                    prioritized_left1.add(sorting1.get(rand_int4));
                    Log.i("check", "added WBC to left1"+ sorting1.get(rand_int4));
                    break;
                }
            }

        }
        if(!sorting.isEmpty() && prioritized_left1.isEmpty()){
            for(int i = 0; i<sorting.size();i++){
                rand_int5 = rand.nextInt(sorting.size());
                if(!prioritized_left.contains(sorting.get(rand_int5))){
                    prioritized_left1.add(sorting.get(rand_int5));
                    break;
                }
            }

        }
        if(!sorting2.isEmpty() && prioritized_left1.isEmpty()){
            for(int i = 0; i<sorting2.size();i++){
                rand_int6 = rand.nextInt(sorting2.size());
                Log.i("check", "value"+ sorting2.get(rand_int6));
                if(!prioritized_left.contains(sorting2.get(rand_int6))){
                    Log.i("check", "tri");
                    prioritized_left1.add(sorting2.get(rand_int6));
                    break;
                }
            }
        }
        if(prioritized_left1.isEmpty()){
            Log.i("check", "left1 is empty");
            prioritized_left1.add("-");
        }

        if(!sorting2.isEmpty()){
            for(int i = 0; i<sorting2.size();i++){
                rand_int4 = rand.nextInt(sorting2.size());
                if(!prioritized_left.contains(sorting2.get(rand_int4)))
                    if(!prioritized_left1.contains(sorting2.get(rand_int4))) {
                        prioritized_left2.add(sorting2.get(rand_int4));
                        break;
                    }
            }


        }
        if(!sorting.isEmpty() && prioritized_left2.isEmpty()){
            for(int i = 0; i<sorting.size();i++){
                rand_int5 = rand.nextInt(sorting.size());
                if(!prioritized_left.contains(sorting.get(rand_int5)))
                    if(!prioritized_left1.contains(sorting.get(rand_int5))) {
                        prioritized_left2.add(sorting.get(rand_int5));
                        break;
                    }
            }

        }
        if(!sorting1.isEmpty() && prioritized_left2.isEmpty()){
            for(int i = 0; i<sorting1.size();i++){
                rand_int6 = rand.nextInt(sorting1.size());
                if(!prioritized_left.contains(sorting1.get(rand_int6)))
                    if(!prioritized_left1.contains(sorting1.get(rand_int6))) {
                        prioritized_left2.add(sorting1.get(rand_int6));
                        break;
                    }
            }

        }
        if(prioritized_left2.isEmpty()){

            prioritized_left2.add(" ");
        }
    }


    public void the_brain(Map<String, String> mini_map, Map<String, String> mini_map2 ){
//TODO add more lab tests in the main list and polish UI.
        String left = mini_map2.get("p").toString();
        String left1 = mini_map2.get("p1").toString();
        String left2 = mini_map2.get("p2").toString();
        main_value_text.add(left);
        main_value.add(mini_map.get(left));
        Log.i("check", "left_value "+ mini_map.get(left));
        if(!left1.isEmpty()) {

            Log.i("check", "value of left1"+left1);
           // Log.i("check", "left1_value "+ mini_map.get(left1.get(0)));
            if (left1.equals("-")) {
                second_value_text.add(" ");
                second_value.add("-");
            } else {
                Log.i("check", "second value"+ mini_map.get(left1));
                Log.i("check", "check mini_map "+ mini_map.get("WBC"));
                second_value_text.add(mini_map.get(left1));
                second_value.add(left1);
            }

        } else{

            second_value_text.add(" ");
            second_value.add("-");
        }

        if(!left2.isEmpty()) {
            if (left2.equals(" ")) {
                third_value_text.add(" ");
                third_value.add(" ");
            } else {
             //   Log.i("check", "all_values "+ mini_map.get(left2.get(0)));
                third_value_text.add(mini_map.get(left2));
                third_value.add(left2);
            }
        } else{
            third_value_text.add(" ");
            third_value.add("-");
        }
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
            TextView text3 = (TextView)view.findViewById(R.id.second_value);
            TextView text4 = (TextView)view.findViewById(R.id.second_value_text);
            TextView text5 = (TextView)view.findViewById(R.id.third_value);
            TextView text6 = (TextView)view.findViewById(R.id.third_value_text);
            TextView text7 = (TextView)view.findViewById(R.id.date_text);
            TextView text8 = (TextView)view.findViewById(R.id.day_text);

            text1.setText(main_value.get(i));
            text2.setText(main_value_text.get(i));
            if(!second_value.isEmpty()) {
                text4.setText(second_value.get(i));
                text3.setText(second_value_text.get(i));
            }
            if(!third_value.isEmpty()) {
                text6.setText((third_value.get(i)));
                text5.setText(third_value_text.get(i));
            }
            if(!date_text.isEmpty()) {
                text7.setText(date_text.get(i));
                text8.setText(day_text.get(i));
            }
            RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.main_list_relout);

            rel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Toast.makeText(getApplicationContext(), "Ta da", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), FullResult.class);
                    intent.putExtra("object index", i);
                   intent.putExtra("list", obj);
                    startActivity(intent);

                }
            });
            return view;
        }
    }
}
//TODO BuGs to fix 1.Lipid panel list view is not displaying(must add scrollview). 2. WBC(This bug fixing in progress) (prioritized left must be cleared before storing new value)
