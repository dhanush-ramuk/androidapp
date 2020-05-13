package com.dhanush.CheckUp;

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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class BloodWorkMainActivity extends AppCompatActivity {
    ArrayList<All_Results> obj, arranged_obj;
    ArrayList<String> main_value_text;
    ArrayList<String> main_value;
    ArrayList<String> second_value_text;
    ArrayList<String> second_value;
    ArrayList<String> third_value_text;
    ArrayList<String> third_value;
    ArrayList<String> date_text;
    ArrayList<String> day_text;
    Customadapter1 adapter;
    HelperClass helperClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_value = new ArrayList<>();
        main_value_text = new ArrayList<>();
        second_value = new ArrayList<>();
        second_value_text = new ArrayList<>();
        third_value = new ArrayList<>();
        third_value_text = new ArrayList<>();
        day_text = new ArrayList<>();
        date_text = new ArrayList<>();
        adapter = new Customadapter1();
        helperClass = new HelperClass();

        setContentView(R.layout.activity_blood_work_main);
        Intent intent = getIntent();
        //TODO order the listed bloodwork results according to the ascending order of their date
        obj = (ArrayList<All_Results>) intent.getSerializableExtra("list");
        arranged_obj = new ArrayList<>();
        arranged_obj = helperClass.arrangeObjectsAscendingOrder(obj);
        create_list(arranged_obj);
        saveBloodResults(arranged_obj);

    }

    private void saveBloodResults(ArrayList<All_Results> obj){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString("mylist", json);
        editor.apply();
    }

    //Function to loop all objects in ArrayList<Results>. Map function is retrieved from object and main view is created
    public void create_list(ArrayList<All_Results> obj){
        if(obj!=null && !obj.isEmpty()){
            for(int i=0;i<obj.size(); i++){
                the_brain(obj.get(i).get_map(), obj.get(i).get_map1(), obj.get(i).get_map2());
            }
        }
    }

    //Objects from create_list function is sent to the_brain function, three BloodWork values, its name, day and date is set here
    public void the_brain(Map<String, String> mini_map, Map<String, String> mini_map2, Map<String, String> mini_map3 ){

        //Listview v, UI where BloodWork result to be displayed in main view
        ListView v = (ListView)findViewById(R.id.listview_main);
        String left = mini_map2.get("p").toString();
        String left1 = mini_map2.get("p1").toString();
        String left2 = mini_map2.get("p2").toString();
        date_text.add(mini_map3.get("date"));
        day_text.add(mini_map3.get("day").toString());
        main_value_text.add(shorten_test_name_main(left));
        main_value.add(mini_map.get(left));
        if(!left1.isEmpty()) {
            if (left1.equals("-")) {
                second_value_text.add("  ");
                second_value.add("");
            } else {
                second_value_text.add(mini_map.get(left1));
                second_value.add(shorten_test_name_second(left1));
            }

        } else{

            second_value_text.add("  ");
            second_value.add(" ");
        }

        if(!left2.isEmpty()) {
            if (left2.equals(" ")) {
                third_value_text.add("  ");
                third_value.add(" ");
            } else {
                third_value_text.add(mini_map.get(left2));
                third_value.add(shorten_test_name_second(left2));
            }
        } else{
            third_value_text.add(" ");
            third_value.add(" ");
        }

        //Listview Adapter to set all BloodWork values
        v.setAdapter(adapter);
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //TODO maybe remove cancel button in FullResult Activity as it is same as back button
        //finish() from FullResult Activity
        if (requestCode == 997 && resultCode == RESULT_OK) {

            //user deletes the BloodWork result from FullResult Activity

            //index value of deleted object
            int objectIndex = data.getIntExtra("value", -1);
            if (objectIndex != -1) {
                obj.remove(objectIndex);
                clear_list();
                create_list(obj);
                saveBloodResults(obj);
                if(obj.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("empty", 1);
                    startActivity(intent);

                }
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

    //function to save BloodWork value in sharedpreferences
    public void saveBloodResults(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString("mylist", json);
        editor.apply();
    }

    //function to shorten main test name to be displayed in main view
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

    //function to shorten second and third test name to be displayed in main view
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

    //TODO replace this button to the activity_blood_work_main layout
    public void gotoTrackPage(View v){
        Intent i = new Intent(getApplicationContext(), trackpage.class);
        i.putExtra("list", obj);
        startActivityForResult(i, 995);
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
            HelperClass helperClass = new HelperClass();
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
//            TextView text8 = (TextView)view.findViewById(R.id.day_text);

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
                text7.setText(helperClass.splitDate(date_text.get(i)));
              //  text8.setText(day_text.get(i));
            }

            //Mainview BloodWork relative layout
            RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.main_list_relout);

            //When clicked, goto FullResult activity
            rel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
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
