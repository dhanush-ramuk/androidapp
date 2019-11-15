package com.example.application1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    String [] all_tests = {"weight", "cholesterol", "triglyceride", "HDL", "LDL", "glucose[fasting]", "glucose[random]", "calcium", "albumin", "total protein", "C02",
            "sodium", "potassium", "chloride", "alkaline phosphatase", "alanine amino transferase", "aspartate amino transferase", "bilirubin",
            "blood urea nitrogen", "creatinine", "WBC", "RBC", "hemoglobin", "platelets", "hematocrit", "BP"};
    String[] med_info = {"name", "hour1", "hour2", "hour3", "minute1", "minute2", "minute3", "ampm1", "ampm2", "ampm3"};
    ArrayList<String> main_value_text = new ArrayList<String>();
    ArrayList<String> main_value  = new ArrayList<String>();
    ArrayList<String> second_value_text = new ArrayList<String>();
    ArrayList<String> second_value  = new ArrayList<String>();
    ArrayList<String> third_value_text = new ArrayList<String>();
    ArrayList<String> third_value  = new ArrayList<String>();
    ArrayList<String> date_text = new ArrayList<String>();
    ArrayList<String> day_text  = new ArrayList<String>();
    ArrayList<String> prioritized_left;
    ArrayList<String> prioritized_left1;
    ArrayList<String> prioritized_left2;
    ArrayList<String> tablet_name_list = new ArrayList<>();
    ArrayList<String> time1 = new ArrayList<>();
    ArrayList<String> time2 = new ArrayList<>();
    ArrayList<String> time3 = new ArrayList<>();
    Map<String, String> map_med, map_med1;
    TextView textview_NO_LIST_ENTERED;
    ArrayList<All_Results> obj = new ArrayList<All_Results>();
    ArrayList<All_Medications> obj_med = new ArrayList<>();
    String day, date;
    ListView v;
    Customadapter1 adapter = new Customadapter1();
    RecyclerViewAdapter Radapter;
    Random rand = new Random();
    AlarmManager alarmManager;
    Intent intentAlarm;
    HelperClass helperClass = new HelperClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        v = (ListView)findViewById(R.id.listview_main);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        intentAlarm =  new Intent(MainActivity.this, AlarmReceiver.class);
        textview_NO_LIST_ENTERED = (TextView) findViewById(R.id.noListEnteredText);

        /*Using shared preference to get the Medication Remainder and All BloodWork Results object that contains
        all the previous results*/
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("mylist", "");
        String json1 = sharedPrefs.getString("mylist1", "");

        ArrayList<All_Results> start_obj = gson.fromJson(json,
                new TypeToken<List<All_Results>>(){}.getType());
        ArrayList<All_Medications> start_obj1 = gson.fromJson(json1,
                new TypeToken<List<All_Medications>>(){}.getType());

        if(start_obj!=null && !start_obj.isEmpty()) {
            obj.addAll(start_obj);
            create_list(obj);
        }

        if(start_obj1!=null && !start_obj1.isEmpty()){
            obj_med.addAll(start_obj1);
            create_medications_list(obj_med);
        }

        //Display NOTHING ENTERED when both the objects are NULL.
        if(obj.isEmpty()) {
            if (obj_med.isEmpty()) {
                textview_NO_LIST_ENTERED.setVisibility(View.VISIBLE);
            } else{
                textview_NO_LIST_ENTERED.setVisibility(View.INVISIBLE);
            }
        } else {
            textview_NO_LIST_ENTERED.setVisibility(View.INVISIBLE);
        }

        //FAB click to goto Activity2(BloodWork Entering)
        FloatingActionButton fab = findViewById(R.id.fab_lab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), Activity2.class), 999);
            }
        });

        //FAB click to goto Activity3(Medication Remainder)
        FloatingActionButton fab_tab = findViewById(R.id.fab_tab);
        fab_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), Activity3.class), 998);
            }
        });
    }
    //FAB click to goto App's information page
    public void gotoinfopage(View v){
        Intent i = new Intent(MainActivity.this, infoPage.class);
        startActivity(i);
    }

    /*The main function which activates when finish() function gets excecuted in other Activity. finish() finction
    started from startActivityForResult() function from MainActivity*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //TODO maybe remove cancel button in FullResult Activity as it is same as back button
        //finish() from FullResult Activity
        if(requestCode == 997 && resultCode == RESULT_OK) {

            //does nothing, clears and creates BloodWork main list when user clicks Okay(i.e goToMain) button
            int objectIndex = data.getIntExtra("value", -1);
            if(objectIndex != -1){
                obj.remove(objectIndex);
                clear_list();
                create_list(obj);
                clear_ArrayList();
                saveBloodResults();
            }
        }


        if (requestCode == 998 && resultCode == RESULT_OK) {
            ArrayList<String> hour, minute, ampm;
            ArrayList<Integer> hourin12;
            String tablet_name;
            int l = data.getIntExtra("boolean", 0);
            if (l == 0) {
                clear_medication_list();
                create_medications_list(obj_med);

            } else if (l == 1) {
                tablet_name = data.getStringExtra("tablet_name");
                hour = new ArrayList<String>();
                hour = data.getStringArrayListExtra("hour");
                minute = new ArrayList<String>();
                minute = data.getStringArrayListExtra("minute");
                ampm = new ArrayList<String>();
                ampm = data.getStringArrayListExtra("ampm");
                hourin12 = new ArrayList<Integer>();
                hourin12 = data.getIntegerArrayListExtra("hourin24");
                if (!hour.isEmpty() && !minute.isEmpty() && !ampm.isEmpty() && !hourin12.isEmpty()) {


                    Calendar startTime = Calendar.getInstance();
                    Calendar t = Calendar.getInstance();
                    startTime.set(Calendar.SECOND, 0);
                    Log.e("check", "working");


                    //TODO create a method to issue notification for medications remainder


                    map_med = new HashMap<>();
                    map_med1 = new HashMap<>();
                    for (String a : med_info)
                        map_med.put(a, null);
                    int size;
                    size = hour.size();
                    if (size == 2) {
                        map_med.put("hour1", hour.get(0));
                        map_med.put("hour2", hour.get(1));
                        map_med.put("minute1", check_minute(minute.get(0)));
                        map_med.put("minute2", check_minute(minute.get(1)));
                        map_med.put("ampm1", ampm.get(0));
                        map_med.put("ampm2", ampm.get(1));
                    } else if (size == 1) {

                        map_med.put("hour1", hour.get(0));
                        map_med.put("minute1", check_minute(minute.get(0)));
                        map_med.put("ampm1", ampm.get(0));
                    } else {
                        map_med.put("hour1", hour.get(0));
                        map_med.put("hour2", hour.get(1));
                        map_med.put("hour3", hour.get(2));
                        map_med.put("minute1", check_minute(minute.get(0)));
                        map_med.put("minute2", check_minute(minute.get(1)));
                        map_med.put("minute3", check_minute(minute.get(2)));
                        map_med.put("ampm1", ampm.get(0));
                        map_med.put("ampm2", ampm.get(1));
                        map_med.put("ampm3", ampm.get(2));

                    }
                    map_med.put("size", String.valueOf(size));
                    map_med.put("name", tablet_name);
                    int kk = 0;

                    for(int ii = 0; ii<obj_med.size(); ii++){
                        kk = kk + Integer.parseInt(obj_med.get(ii).return_map().get("size"));

                    }
                    Log.e("check", "kk "+kk);

                    for (int i = 0; i < hour.size(); i++) {
                        startTime.set(Calendar.HOUR_OF_DAY, hourin12.get(i));
                        startTime.set(Calendar.MINUTE, Integer.parseInt(minute.get(i)));
                        startTime.set(Calendar.SECOND, 00);
                        helperClass.schedule_alarm(getApplicationContext(), alarmManager, intentAlarm, kk, startTime.getTimeInMillis(), tablet_name );
                        map_med.put(String.valueOf(i) , String.valueOf(kk));
                        map_med.put(i+"time", String.valueOf(startTime.getTimeInMillis()));
                        kk = kk + 1;
                    }

                    obj_med.add(new All_Medications(map_med));
                    saveMedications();
                    clear_medication_list();
                    Log.e("check", "working4");
                    create_medications_list(obj_med);
                    if(obj_med.size()==2){

                        Toast toast= Toast.makeText(getApplicationContext(),
                                "Swipe ->", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 80);
                        toast.show();
                    }
                }
            }else {
                //do nothing

            }        }

        if (requestCode == 999 && resultCode == RESULT_OK) {

            int flag = 0;
            if (data.getIntExtra("boolean", 0) == 0) {
                clear_list();
                create_list(obj);
                clear_ArrayList();
            } else if (data.getIntExtra("boolean", 0) == 1) {
                ArrayList<String> basic_test_lhs, CBC_lhs, kidney_test_lhs, liver_test_lhs, electrolytes_lhs, proteins_lhs, general_test_lhs, lipid_panel_lhs;
                ArrayList<String> basic_test_rhs, CBC_rhs, kidney_test_rhs, liver_test_rhs, electrolytes_rhs, proteins_rhs, general_test_rhs, lipid_panel_rhs;
                prioritized_left = new ArrayList<>();
                prioritized_left1 = new ArrayList<>();
                prioritized_left2 = new ArrayList<>();
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
                Log.e("check", "day "+day);

                Map<String, String> map1 = new HashMap<>();
                Map<String, String> map2 = new HashMap<>();
                map2.put("date", date);
                map2.put("day", day);
                Map<String, String> map = new HashMap<String, String>();
                for (String a : all_tests) {
                    map.put(a, null);
                }


                if (!basic_test_rhs.isEmpty()) {
                    for (int i = 0; i < basic_test_lhs.size(); i++)
                        map.put(basic_test_lhs.get(i), basic_test_rhs.get(i));
                    flag = 1;
                }

                if (!kidney_test_rhs.isEmpty()) {
                    for (int i = 0; i < kidney_test_lhs.size(); i++)
                        map.put(kidney_test_lhs.get(i), kidney_test_rhs.get(i));
                    flag = 1;
                }
                if (!CBC_rhs.isEmpty()) {
                    for (int i = 0; i < CBC_lhs.size(); i++)
                        map.put(CBC_lhs.get(i), CBC_rhs.get(i));
                    flag = 1;
                }
                if (!liver_test_rhs.isEmpty()) {
                    for (int i = 0; i < liver_test_lhs.size(); i++) {
                        map.put(liver_test_lhs.get(i), liver_test_rhs.get(i));
                    }
                    flag = 1;
                }
                if (!electrolytes_lhs.isEmpty()) {
                    for (int i = 0; i < electrolytes_lhs.size(); i++)
                        map.put(electrolytes_lhs.get(i), electrolytes_rhs.get(i));
                    flag = 1;
                }
                if (!lipid_panel_rhs.isEmpty()) {
                    for (int i = 0; i < lipid_panel_lhs.size(); i++)
                        map.put(lipid_panel_lhs.get(i), lipid_panel_rhs.get(i));
                    flag = 1;
                }
                if (!proteins_rhs.isEmpty()) {
                    for (int i = 0; i < proteins_lhs.size(); i++)
                        map.put(proteins_lhs.get(i), proteins_rhs.get(i));
                    flag = 1;
                }
                if (!general_test_rhs.isEmpty()) {
                    for (int i = 0; i < general_test_lhs.size(); i++)
                        map.put(general_test_lhs.get(i), general_test_rhs.get(i));
                    flag = 1;
                }
                if(flag == 1) {
                    go_figure_the_fuck_out(map);
                    map1.put("p", prioritized_left.get(0));
                    map1.put("p1", prioritized_left1.get(0));
                    map1.put("p2", prioritized_left2.get(0));



                    obj.add(new All_Results(map, map1, map2));
                    saveBloodResults();

                    clear_list();
                    create_list(obj);
                    clear_ArrayList();

                }
                //do nothing
            }
        }
        if(obj.isEmpty()) {
            Log.e("check", "1");
            if (obj_med.isEmpty()) {
            textview_NO_LIST_ENTERED.setVisibility(View.VISIBLE);
                //setContentView(textView);
            } else{
                textview_NO_LIST_ENTERED.setVisibility(View.INVISIBLE);
                        }
        } else {
            textview_NO_LIST_ENTERED.setVisibility(View.INVISIBLE);
              }
    }
    public void go_figure_the_fuck_out(Map map){
        ArrayList<String> sorting, sorting1, sorting2;
        int rand_int1, rand_int2, rand_int3, rand_int4, rand_int5, rand_int6;
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
            sorting.add("glucose[fasting]");
        }
        if(map.get("cholesterol")!=null){
            sorting.add("cholesterol");
        }

        for(int i=0; i<all_tests.length; i++){
            if(sorting.contains(all_tests[i]) || sorting1.contains(all_tests[i])){
                //do nothing
            }else if(map.get(all_tests[i])!=null) {
                sorting2.add(all_tests[i]);

            }
        }

        if(!sorting.isEmpty()){
            rand_int1 = rand.nextInt(sorting.size());
            prioritized_left.add(sorting.get(rand_int1));
        } else if(!sorting1.isEmpty()){
            rand_int2 = rand.nextInt(sorting1.size());
            prioritized_left.add(sorting1.get(rand_int2));
        } else if(!sorting2.isEmpty()){

            rand_int3 = rand.nextInt(sorting2.size());
            Log.e("check", "pl"+ sorting2.size() );
            prioritized_left.add(sorting2.get(rand_int3));
        }

        if(!sorting1.isEmpty()){
            for(int i = 0; i<sorting1.size();i++){
                rand_int4 = rand.nextInt(sorting1.size());
                if(!prioritized_left.contains(sorting1.get(rand_int4))){
                    prioritized_left1.add(sorting1.get(rand_int4));
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
                if(!prioritized_left.contains(sorting2.get(rand_int6))){
                    prioritized_left1.add(sorting2.get(rand_int6));
                    break;
                }
            }
        }
        if(prioritized_left1.isEmpty()){
            prioritized_left1.add("-");
        }
        if(!sorting.isEmpty() ){
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
        if(!sorting2.isEmpty() && prioritized_left2.isEmpty()){
            for(int i = 0; i<sorting2.size();i++){
                rand_int4 = rand.nextInt(sorting2.size());
                if(!prioritized_left.contains(sorting2.get(rand_int4)))
                    if(!prioritized_left1.contains(sorting2.get(rand_int4))) {
                        prioritized_left2.add(sorting2.get(rand_int4));
                        break;
                    }
            }
        }
        if(prioritized_left2.isEmpty()){
            prioritized_left2.add(" ");
        }
    }

    public void create_list(ArrayList<All_Results> obj){
        if(obj!=null && !obj.isEmpty()){
            for(int i=0;i<obj.size(); i++){the_brain(obj.get(i).get_map(), obj.get(i).get_map1(), obj.get(i).get_map2());
            }
        }
    }
    public void the_brain(Map<String, String> mini_map, Map<String, String> mini_map2, Map<String, String> mini_map3 ){
        v = (ListView)findViewById(R.id.listview_main);
        String left = mini_map2.get("p").toString();
        String left1 = mini_map2.get("p1").toString();
        String left2 = mini_map2.get("p2").toString();
        date_text.add(mini_map3.get("date").toString());
        day_text.add(mini_map3.get("day").toString());
        main_value_text.add(shorten_test_name_main(left));
        main_value.add(mini_map.get(left));
        if(!left1.isEmpty()) {
            if (left1.equals("-")) {
                second_value_text.add(" - ");
                second_value.add("");
            } else {
                second_value_text.add(mini_map.get(left1));
                second_value.add(shorten_test_name_second(left1));
            }

        } else{

            second_value_text.add(" - ");
            second_value.add(" - ");
        }

        if(!left2.isEmpty()) {
            if (left2.equals(" ")) {
                third_value_text.add(" - ");
                third_value.add(" ");
            } else {
                third_value_text.add(mini_map.get(left2));
                third_value.add(shorten_test_name_second(left2));
            }
        } else{
            third_value_text.add(" ");
            third_value.add(" ");
        }

        v.setAdapter(adapter);

    }
    public void create_medications_list(ArrayList<All_Medications> o){
        if(obj.isEmpty())
            if (obj_med.isEmpty())
                textview_NO_LIST_ENTERED.setVisibility(View.VISIBLE);
            else
                textview_NO_LIST_ENTERED.setVisibility(View.INVISIBLE);
        else
            textview_NO_LIST_ENTERED.setVisibility(View.INVISIBLE);

        for(int i=0; i<o.size(); i++){
            the_medication_brain(o.get(i).return_map());
        }
    }

    public void the_medication_brain(Map<String, String> map){
        tablet_name_list.add(map.get("name"));
        if(map.get("hour1")!=null){
            time1.add(map.get("hour1") +":"+ map.get("minute1") + " " + map.get("ampm1"));
        }else{
            time1.add("-");
        }
        if(map.get("hour2")!=null){
            time2.add(map.get("hour2") +":"+ map.get("minute2") + " " + map.get("ampm2"));
        }else{
            time2.add("-");
        }
        if(map.get("hour3")!=null){
            time3.add(map.get("hour3") +":"+ map.get("minute3") +" " + map.get("ampm3"));
            Log.e("check", "time3 "+time3.get(0));
        }else{
            time3.add("-");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.listview_medications);
        recyclerView.setLayoutManager(layoutManager);
        Radapter = new RecyclerViewAdapter(this, tablet_name_list, time1, time2, time3);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(Radapter);
    }



    public String check_minute(String a){
        if(a.length()==1){
            a = "0"+a;
        }
        return a;
    }

public void delete_Alarm(int i){
    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    Intent intentAlarm= new Intent(MainActivity.this, AlarmReceiver.class);

    for(int ii = 0; ii < Integer.parseInt(obj_med.get(i).return_map().get("size")); ii++){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),   Integer.parseInt(obj_med.get(i).return_map().get(String.valueOf(ii))), intentAlarm, 0);
        alarmManager.cancel(pendingIntent);
    }
}
    public void saveBloodResults(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString("mylist", json);
        editor.apply();
    }

    public void saveMedications(){

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj_med);
        editor.putString("mylist1", json);
        editor.apply();    }


    public void clear_ArrayList(){
        if(!prioritized_left1.isEmpty())
            prioritized_left1.clear();
        if(!prioritized_left.isEmpty())
            prioritized_left.clear();
        if(!prioritized_left2.isEmpty())
            prioritized_left2.clear();
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

    public void clear_medication_list(){
        tablet_name_list.clear();
        time1.clear();
        time2.clear();
        time3.clear();
        if(Radapter!=null)
        Radapter.notifyDataSetChanged();
    }




public String shorten_test_name_main(String a){
        String str = null;
    if(a.equals("blood urea nitrogen"))
        str = "Urea";
    else if(a.equals("alanine amino transferase"))
        str = "ALT";
    else if(a.equals("aspartate amino transferase"))
        str = "AST";
    else if(a.equals("total protein"))
        str = "protein";
    else if(a.equals("glucose[fasting]"))
        str = "glucose[F]";
    else if(a.equals("glucose[random]"))
        str = "glucose[R]";
    else if(a.equals("alkaline phosphatase"))
        str = "ALP";
    else
        str = a;

    return str;
}
    public String shorten_test_name_second(String a){
       String str = null;
        if(a.equals("blood urea nitrogen"))
             str = "BUN";
        else if(a.equals("alanine amino transferase"))
            str = "ALT";
        else if(a.equals("aspartate amino transferase"))
            str = "AST";
        else if(a.equals("alkaline phosphatase"))
            str = "ALP";
        else if(a.equals("glucose[fasting]"))
            str = "BG[F]";
        else if(a.equals("glucose[random]"))
            str = "BG[R]";
        else if(a.equals("weight"))
            str = "Wt";
        else if(a.equals("cholesterol"))
            str = "chol";
        else if(a.equals("creatinine"))
            str = "Cr";
        else if(a.equals("calcium"))
            str = "Ca";
        else if(a.equals("albumin"))
            str = "ALB";
        else if(a.equals("total protein"))
            str = "TP";
        else if(a.equals("sodium"))
            str = "Na";
        else if(a.equals("potassium"))
            str = "K";
        else if(a.equals("chloride"))
            str = "Cl";
        else if(a.equals("bilirubin"))
            str = "Bn";
        else if(a.equals("hemoglobin"))
            str = "Hgb";
        else if(a.equals("hematocrit"))
            str = "Hct";
        else if(a.equals("platelets"))
            str = "PLT";
        else str = a;

        return str;
    }


    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private static final String TAG = "RecyclerViewAdapter";

        //vars
        private ArrayList<String> mtime1 = new ArrayList<>();
        private ArrayList<String> mtime2 = new ArrayList<>();
        private ArrayList<String> mtime3 = new ArrayList<>();
        private ArrayList<String> name = new ArrayList<>();

        private Context mContext;

        public RecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<String> time1, ArrayList<String> time2, ArrayList<String> time3) {
            name = names;
            mtime1 = time1;
            mtime2 = time2;
            mtime3 = time3;
            mContext = context;
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_list, parent, false);
            Button b = (Button) view.findViewById(R.id.delete_button);

            return new RecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, final int position) {

            holder.time1.setText(mtime1.get(position));
            holder.time2.setText(mtime2.get(position));
            holder.time3.setText(mtime3.get(position));
            holder.name.setText(name.get(position));
            holder.b.setOnClickListener((new View.OnClickListener() {
                public void onClick(View v) {
                    delete_Alarm(position);
                    obj_med.remove(position);
                    clear_medication_list();
                    create_medications_list(obj_med);
                    saveMedications();
                }
            }));
        }

        @Override
        public int getItemCount() {
            return name.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView name;
            TextView time1;
            TextView time2;
            TextView time3;
            Button b;


            public ViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.tablet_name_text);
                time1 = itemView.findViewById(R.id.time1_text);
                time2 = itemView.findViewById(R.id.time2_text);
                time3 = itemView.findViewById(R.id.time3_text);
                b = itemView.findViewById(R.id.delete_button);
            }
        }
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
            TextView t1 = (TextView)view.findViewById(R.id.main_value_updown);
            TextView text3 = (TextView)view.findViewById(R.id.second_value);
            TextView text4 = (TextView)view.findViewById(R.id.second_value_text);
            TextView t2 = (TextView)view.findViewById(R.id.second_value_updown);

            TextView text5 = (TextView)view.findViewById(R.id.third_value);
            TextView text6 = (TextView)view.findViewById(R.id.third_value_text);
            TextView t3 = (TextView)view.findViewById(R.id.third_value_updown);

            TextView text7 = (TextView)view.findViewById(R.id.date_text);
            TextView text8 = (TextView)view.findViewById(R.id.day_text);

            text1.setText(main_value.get(i));
            text2.setText(main_value_text.get(i));
            t1.setText(" ");
            t2.setText(" ");
            t3.setText(" ");

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
                    startActivityForResult(intent, 997);

                }
            });
            return view;
        }
    }
}

//TODO BuGs to fix 1.Lipid panel list view is not displaying(must add scrollview). 2. WBC(This bug fixing in progress) (prioritized left must be cleared before storing new value)
