package com.dhanush.CheckUp;
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
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    ArrayList<String> prioritized_left;
    ArrayList<String> prioritized_left1;
    ArrayList<String> prioritized_left2;
    AlarmManager alarmManager;
    TextView textview_NO_LIST_ENTERED;
    ArrayList<All_Results> obj;
    Intent intentAlarm;
    HelperClass helperClass;
    RelativeLayout BloodWork;
    int flag1, alertfornextbloodwork, havedoctorscomment;
    String doctorscomment = " ";
    int daya = 0, montha = 0, yeara = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        BloodWork = (RelativeLayout) findViewById(R.id.BloodWorkMainActivity);
        if(intent.getIntExtra("empty", -1) == 1){
            BloodWork.setVisibility(View.GONE);
        }

        intentAlarm =  new Intent(MainActivity.this, AlarmReceiver.class);
        helperClass = new HelperClass();
        textview_NO_LIST_ENTERED = (TextView) findViewById(R.id.noListEnteredText);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        //Three list to add prioritized values of user entered lab values
        prioritized_left = new ArrayList<>();
        prioritized_left1 = new ArrayList<>();
        prioritized_left2 = new ArrayList<>();

        obj = new ArrayList<>();
        /*Using shared preference to get the Medication Remainder and All BloodWork Results object that contains
        all the previous results*/
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("mylist", "");

        ArrayList<All_Results> start_obj = gson.fromJson(json,
                new TypeToken<List<All_Results>>(){}.getType());



        if(start_obj!=null && !start_obj.isEmpty()) {
            obj.addAll(start_obj);
            //create_list(obj);
            BloodWork.setVisibility(View.VISIBLE);
        }



        //Display NOTHING ENTERED when both the objects are NULL.
        if(obj.isEmpty()) {

                textview_NO_LIST_ENTERED.setVisibility(View.VISIBLE);
                BloodWork.setVisibility(View.GONE);

        } else {
            textview_NO_LIST_ENTERED.setVisibility(View.INVISIBLE);
            BloodWork.setVisibility(View.VISIBLE);
        }


        //FAB click to goto Activity2(BloodWork Entering)
        FloatingActionButton fab = findViewById(R.id.fab_lab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), Activity2.class), 999);
            }
        });

    }
    //FAB click to goto App's information page
    public void gotoinfopage(View v){
        Intent i = new Intent(MainActivity.this, infoPage.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*Using shared preference to get the Medication Remainder and All BloodWork Results object that contains
        all the previous results*/
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("mylist", "");
        if(obj!=null && !obj.isEmpty()) {
                obj = gson.fromJson(json, new TypeToken<List<All_Results>>() {}.getType());
        }



    }

    /*The main function which activates when finish() function gets excecuted in other Activity. finish() finction
                started from startActivityForResult() function from MainActivity*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //From Activity2
        if (requestCode == 999 && resultCode == RESULT_OK) {
            int flag = 0;
            //when user clicks close button
            if (data.getIntExtra("boolean", 0) == 0) {
                clear_list();
                //create_list(obj);
                clear_ArrayList();
            }

            //user clicks okay button with values entered
            else if (data.getIntExtra("boolean", 0) == 1) {
                ArrayList<String> basic_test_lhs, CBC_lhs, kidney_test_lhs, liver_test_lhs, electrolytes_lhs, proteins_lhs, general_test_lhs, lipid_panel_lhs;
                ArrayList<String> basic_test_rhs, CBC_rhs, kidney_test_rhs, liver_test_rhs, electrolytes_rhs, proteins_rhs, general_test_rhs, lipid_panel_rhs;
                String day, date;
                Calendar startDate = Calendar.getInstance();
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
                havedoctorscomment = data.getIntExtra("havedoctorscomment", 0);
                if( havedoctorscomment == 1){
                    doctorscomment = data.getStringExtra("doctorsComment");
                } else{
                    doctorscomment = " ";
                }
                alertfornextbloodwork = data.getIntExtra("alertfornextbloodwork", 0);
                Map<String, String> map1 = new HashMap<>();
                Map<String, String> map2 = new HashMap<>();
                Map<String, String> map = new HashMap<String, String>();
                int kk;
                Random rand = new Random();
                kk = rand.nextInt(Integer.MAX_VALUE);
                if(alertfornextbloodwork == 1){
                    daya = data.getIntExtra("alertDay", 0);
                    montha = data.getIntExtra("alertMonth", 0);
                    yeara = data.getIntExtra("alertYear", 0);
                    startDate.set(Calendar.YEAR, yeara);
                    startDate.set(Calendar.MONTH, montha);
                    startDate.set(Calendar.DAY_OF_MONTH, daya);
                    startDate.set(Calendar.HOUR_OF_DAY, 8);
                    startDate.set(Calendar.MINUTE, 30);
                    startDate.set(Calendar.SECOND, 0);
                    helperClass.schedule_alarm(getApplicationContext(), alarmManager, intentAlarm, kk, startDate.getTimeInMillis(), "Today", 0, 1, 1);
                    map2.put("kkvaluerefill", String.valueOf(kk));
                   /* if(startDate.getTimeInMillis() > (System.currentTimeMillis() + (172800000))) {
                        helperClass.schedule_alarm(getApplicationContext(), alarmManager, intentAlarm, kk, startDate.getTimeInMillis(), "Tomorrow", 0, 1, 1);

                    } */

                    map2.put("Refilltime", String.valueOf(startDate.getTimeInMillis()));

                }


                //Entering date and day value
                map2.put("date", date);
                map2.put("day", day);
                map2.put("doctorscomment", doctorscomment);
                map2.put("haveDoctorsComment", String.valueOf(havedoctorscomment));
                map2.put("daya", String.valueOf(daya));
                map2.put("montha", String.valueOf(montha));
                map2.put("yeara", String.valueOf(yeara));
                map2.put("havealertfornextbloodwork", String.valueOf(alertfornextbloodwork));

              /*  if (alertfornextbloodwork == 1){
                    startDate.set(Calendar.YEAR, yeara);
                    startDate.set(Calendar.MONTH, montha);
                    startDate.set(Calendar.DAY_OF_MONTH, daya);
                    startDate.set(Calendar.HOUR_OF_DAY, 9);
                    startDate.set(Calendar.MINUTE, 0);
                    startDate.set(Calendar.SECOND, 0);
                    helperClass.schedule_alarm(getApplicationContext(), alarmManager, intentAlarm, kk, startDate.getTimeInMillis(), "refill", 0, 1, 1);
                    map.put("kkvaluerefill", String.valueOf(kk));
                    map.put("Refilltime", String.valueOf(startTime.getTimeInMillis()));
                    kk = kk + 1;
                }
*/
                //entering BloodWorks values into Map function
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

                //If user entered value and pressed okay, then,
                if (flag == 1) {

                    //function to prioritize lab values to display in main screen
                    go_figure_the_fuck_out(map);

                    //adding prioritized value into map function
                    map1.put("p", prioritized_left.get(0));
                    map1.put("p1", prioritized_left1.get(0));
                    map1.put("p2", prioritized_left2.get(0));

                    //saving map of lab values and prioritized value into Arraylist of object
                    obj.add(new All_Results(map, map1, map2));

                    //function to save Arraylist of object into Shared Preferences
                    saveBloodResults(obj);
                    // clear_list();
                    //create_list(obj);
                    clear_ArrayList();
                }

            }
        }
        //Display NOTHING ENTERED when both the objects are NULL.
        if(obj.isEmpty()) {

                textview_NO_LIST_ENTERED.setVisibility(View.INVISIBLE);
                BloodWork.setVisibility(View.GONE);
            } else {
            textview_NO_LIST_ENTERED.setVisibility(View.INVISIBLE);
            BloodWork.setVisibility(View.VISIBLE);
        }
    }

    public void goToBloodWorkMainActivity(View v){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("mylist", "");
        ArrayList<All_Results> obj = gson.fromJson(json,
                new TypeToken<List<All_Results>>(){}.getType());
        Intent intent = new Intent(getApplicationContext(), BloodWorkMainActivity.class);
        intent.putExtra("list", obj);
        startActivity(intent);
    }


    public void go_figure_the_fuck_out(Map map){
        Random rand = new Random();
        ArrayList<String> sorting, sorting1, sorting2;
        int rand_int1, rand_int2, rand_int3, rand_int4, rand_int5, rand_int6;
        sorting = new ArrayList<String>();
        sorting1 = new ArrayList<String>();
        sorting2 = new ArrayList<String>();

        //User entered tests are arranged into different list based on priority
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

        /* 3 value from user entered test result is retrieved from all user entered test result to be displayed in the main view */
        //randomly selected and entered into Prioritized list
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

    //function to add zero before minute in time if it is single digit(if time is 12:9 => 12:09)
    public String check_minute(String a){
        if(a.length()==1)
            a = "0"+a;
        return a;
    }



    //function to save BloodWork value in sharedpreferences
    public void saveBloodResults(ArrayList<All_Results> obj){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString("mylist", json);
        editor.apply();
    }



    public void clear_ArrayList(){
        if(!prioritized_left1.isEmpty())
            prioritized_left1.clear();
        if(!prioritized_left.isEmpty())
            prioritized_left.clear();
        if(!prioritized_left2.isEmpty())
            prioritized_left2.clear();
    }

    public void clear_list(){
      /*  main_value.clear();
        main_value_text.clear();
        second_value.clear();
        second_value_text.clear();
        third_value.clear();
        third_value_text.clear();
        date_text.clear();
        day_text.clear();
        adapter.notifyDataSetChanged();
    */
    }






}
