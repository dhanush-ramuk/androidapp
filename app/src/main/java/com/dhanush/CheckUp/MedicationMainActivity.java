package com.dhanush.CheckUp;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class MedicationMainActivity extends AppCompatActivity {
    ArrayList<All_Medications> obj;
    ListView Radapter;
    ArrayList<String> tablet_name_list;
    ArrayList<String> time1;
    ArrayList<String> time2;
    ArrayList<String> time3;
    AlarmManager alarmManager;
    Intent intentAlarm;
    Customadapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_main);
        tablet_name_list = new ArrayList<>();
        time1 = new ArrayList<>();
        time2 = new ArrayList<>();
        time3 = new ArrayList<>();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        intentAlarm =  new Intent(MedicationMainActivity.this, AlarmReceiver.class);

        Intent intent = getIntent();
        obj = (ArrayList<All_Medications>) intent.getSerializableExtra("list");
        create_medications_list(obj);
    }

    //Function to loop objects in ArrayList<Medication> to create main view
    public void create_medications_list(ArrayList<All_Medications> o){
        //check_function();
        for(int i=0; i<o.size(); i++){
            the_medication_brain(o.get(i).return_map());
        }

    }

    //function to delete alarm when delete button is clicked in main view medication list
    public void delete_Alarm(int i){
        for (int ii = 0; ii < Integer.parseInt(obj.get(i).return_map().get("size")); ii++) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Integer.parseInt(obj.get(i).return_map().get(String.valueOf(ii))), intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);
        }

    }
    //function to save Medication Remainder in sharedpreferences
    public void saveMedications(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString("mylist1", json);
        editor.apply();
    }

    //Function to set time and tablet name from object sent from above function
    public void the_medication_brain(Map<String, String> map) {
        tablet_name_list.add(map.get("name"));
        if (map.get("hour1") != null) {
            time1.add(map.get("hour1") + ":" + map.get("minute1") + " " + map.get("ampm1"));
        } else {
            time1.add("-");
        }
        if (map.get("hour2") != null) {
            time2.add(map.get("hour2") + ":" + map.get("minute2") + " " + map.get("ampm2"));
        } else {
            time2.add("-");
        }
        if (map.get("hour3") != null) {
            time3.add(map.get("hour3") + ":" + map.get("minute3") + " " + map.get("ampm3"));
        } else {
            time3.add("-");
        }

        //RecyclerView, place to display Medication remainder in main view
        adapter = new Customadapter2();
        ListView recyclerView = findViewById(R.id.listview_medication);
        recyclerView.setAdapter(adapter);
    }
    public void clear_medication_list(){
        tablet_name_list.clear();
        time1.clear();
        time2.clear();
        time3.clear();
    }
    public class Customadapter2 extends BaseAdapter {

        @Override
        public int getCount() {
            return tablet_name_list.size();
        }

        @Override
        public Object getItem(int i) {
            return tablet_name_list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.medication_list, null);
            TextView text1 = (TextView) view.findViewById(R.id.tablet_name_text);
            TextView text2 = (TextView) view.findViewById(R.id.time1_text);
            TextView text3 = (TextView) view.findViewById(R.id.time2_text);
            TextView text4 = (TextView) view.findViewById(R.id.time3_text);
            Button deleteButton = (Button) view.findViewById(R.id.delete_button);
            //TextView text5 = (TextView) view.findViewById(R.id.tablet_remaining_text);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    delete_Alarm(i);
                    obj.remove(i);
                    clear_medication_list();
                    if(obj.isEmpty()||obj.equals(null)){
                        Intent i = new Intent(MedicationMainActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                    create_medications_list(obj);
                    saveMedications();

                }
            });
            text1.setText(tablet_name_list.get(i));
            text2.setText(time1.get(i));
            text3.setText(time2.get(i));
            text4.setText(time3.get(i));
            //text5.setText("add code for this");
            return view;
        }

    }
}
