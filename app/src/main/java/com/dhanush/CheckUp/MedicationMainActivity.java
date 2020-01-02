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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class MedicationMainActivity extends AppCompatActivity {
    ArrayList<All_Medications> obj;
    RecyclerViewAdapter Radapter;
    ArrayList<String> tablet_name_list;
    ArrayList<String> time1;
    ArrayList<String> time2;
    ArrayList<String> time3;
    AlarmManager alarmManager;
    Intent intentAlarm;

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
        Log.e("check", "size "+o.size());
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
        }else{
            time3.add("-");
        }

        //RecyclerView, place to display Medication remainder in main view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.listview_medications);
        recyclerView.setLayoutManager(layoutManager);

        //Radapter, RecyclerView Adapter to set all Medication info in the main view
        Radapter = new MedicationMainActivity.RecyclerViewAdapter(this, tablet_name_list, time1, time2, time3);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(Radapter);
        recyclerView.scrollToPosition(tablet_name_list.size());
    }

    public void clear_medication_list(){
        tablet_name_list.clear();
        time1.clear();
        time2.clear();
        time3.clear();
        if(Radapter!=null)
            Radapter.notifyDataSetChanged();
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
                    obj.remove(position);
                    clear_medication_list();
                    create_medications_list(obj);
                    saveMedications();
                    if(obj.isEmpty()){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("empty", 2);
                        startActivity(intent);
                    }
                }
            }));
        }


       /* public void check_function(){
            for(int i = 0; i < obj.size(); i++) {
                for (int j = 0; j < Integer.parseInt(obj.get(i).return_map().get("size")); j++) {
                    int kk = Integer.parseInt(obj.get(i).return_map().get(String.valueOf(j)));
                    String name = obj.get(i).return_map().get("name");
                    String time = obj.get(i).return_map().get(j+"time");
                    helperClass.schedule_alarm(getApplicationContext(), alarmManager, intentAlarm, kk, (Long.valueOf(time)), name);            }
            }
        }*/

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


}
