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
    ArrayList<String> a, b;
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
        RelativeLayout relout;
        relout = findViewById(R.id.main_relout);
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
        if(requestCode==999 && resultCode==RESULT_OK){
            a = new ArrayList<String>();
            a = data.getStringArrayListExtra("country_name");
            b = new ArrayList<String>();
            b = data.getStringArrayListExtra("values");

        }



        Log.i("count", "before map");

        map = new HashMap<String, String>();
        map.put("india", null);
        map.put("pakistan", null);
        map.put(a.get(0), b.get(0));
        map.put(a.get(1), b.get(1));
        Log.i("count", "after map");

        obj = new All_Results(map);



        the_brain(map);
        v = (ListView)findViewById(R.id.listview_main);
        v.setAdapter(adapter);

    }


    public void the_brain(Map mini_map){
        ArrayList<String> countries = new ArrayList<String>(mini_map.keySet());
        Log.i("count",a.get(0));

        ArrayList<String> values = new ArrayList<String>(mini_map.values());

        if(a.contains("india")){
                a_imp.add("india");
            Log.i("count", "True");

            b_imp.add(mini_map.get("india").toString());
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
            return view;
        }
    }
}
