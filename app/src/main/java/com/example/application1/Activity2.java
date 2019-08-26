package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String [] basic_test = {"india", "pakistan"};
    String [] CBC = {"wbc", "rbc"};
    String [] kidney_test = {"urea", "creatinine", "uric acid"};
    Customadapter aaa;
    Customadapter1 aaa1;
    Customadapter2 aaa2;
    ArrayList<String> basic_test_lhs, CBC_lhs, kidney_test_lhs;
    ArrayList<String> basic_test_rhs, CBC_rhs, kidney_test_rhs;
    ListView listview_basic_test, listview_CBC, listview_kidney_test;
    Spinner spin_basic_test, spin_CBC, spin_kidney_test;
    ArrayAdapter basic_test_spin_elements, CBC_spin_elements, kidney_test_spin_elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        take_one();
        take_two();
        take_three();
    }

    public void take_one(){

        spin_basic_test = findViewById(R.id.spinner_basic_lab);
        spin_basic_test.setOnItemSelectedListener(this);
        basic_test_spin_elements = new ArrayAdapter(this,R.layout.spinner_design,basic_test);
        basic_test_spin_elements.setDropDownViewResource(R.layout.spinner_design);
        spin_basic_test.setAdapter(basic_test_spin_elements);
        listview_basic_test = (ListView) findViewById(R.id.listview_basic_lab);
        basic_test_rhs = new ArrayList<String>();
        basic_test_lhs =new ArrayList<String>();
        aaa = new Customadapter();
        listview_basic_test.setAdapter(aaa);
    }

    public void take_two(){
        spin_CBC = findViewById(R.id.spinner_CBC);
        listview_CBC = (ListView) findViewById(R.id.listview_CBC);
        spin_CBC.setOnItemSelectedListener(this);
        CBC_spin_elements = new ArrayAdapter(this, R.layout.spinner_design, CBC);
        CBC_spin_elements.setDropDownViewResource(R.layout.spinner_design);
        spin_CBC.setAdapter(CBC_spin_elements);
        CBC_lhs = new ArrayList<String>();
        CBC_rhs = new ArrayList<String>();
        aaa1 = new Customadapter1();
        listview_CBC.setAdapter(aaa1);

    }

    public void take_three(){
        spin_kidney_test = findViewById(R.id.spinner_kidneytest);
        listview_kidney_test = (ListView) findViewById(R.id.listview_kidneytest);
        spin_kidney_test.setOnItemSelectedListener(this);
        kidney_test_spin_elements = new ArrayAdapter(this, R.layout.spinner_design, kidney_test);
        kidney_test_spin_elements.setDropDownViewResource(R.layout.spinner_design);
        spin_kidney_test.setAdapter(kidney_test_spin_elements);
        kidney_test_lhs = new ArrayList<String>();
        kidney_test_rhs = new ArrayList<String>();
        aaa2 = new Customadapter2();
        listview_kidney_test.setAdapter(aaa2);

    }


    public void go_to_camera(View v){
        Intent i = new Intent(getApplicationContext(), Camera_Activity.class);
        startActivity(i);
    }
    public void onclick(View v){
        String Text = spin_basic_test.getSelectedItem().toString();
        basic_test_rhs.add(Text.toString());
        EditText e = (EditText)findViewById(R.id.enter_basic_lab);
        basic_test_lhs.add(e.getText().toString());
        aaa.notifyDataSetChanged();
    }

    public void onclick_CBC(View v){
        String text = spin_CBC.getSelectedItem().toString();
        CBC_lhs.add(text);
        EditText e = (EditText)findViewById(R.id.enter_CBC);
        CBC_rhs.add(e.getText().toString());
        aaa1.notifyDataSetChanged();

    }
    public void onclick_kidney_test(View v){
        String text = spin_kidney_test.getSelectedItem().toString();
        kidney_test_lhs.add(text);
        EditText e = (EditText)findViewById(R.id.enter_kidneytest);
        kidney_test_rhs.add(e.getText().toString());
        aaa2.notifyDataSetChanged();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public class Customadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return basic_test_lhs.size();
        }

        @Override
        public Object getItem(int i) {
            return basic_test_lhs.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.my_list, null);
            TextView text1 = (TextView)view.findViewById(R.id.lab_lhs);
            TextView text2 = (TextView)view.findViewById(R.id.lab_rhs);
            text1.setText(basic_test_rhs.get(i));
            text2.setText(basic_test_lhs.get(i));
            Button b = (Button)view.findViewById(R.id.delete_button_listview);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    basic_test_lhs.remove(i);
                    basic_test_rhs.remove(i);
                    aaa.notifyDataSetChanged();
                }
            });
            return view;
        }
    }

    public class Customadapter1 extends BaseAdapter{

        @Override
        public int getCount() {
            return CBC_lhs.size();
        }

        @Override
        public Object getItem(int i) {
            return CBC_lhs.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.my_list, null);
            TextView text1 = (TextView)view.findViewById(R.id.lab_lhs);
            TextView text2 = (TextView)view.findViewById(R.id.lab_rhs);
            text1.setText(CBC_rhs.get(i));
            text2.setText(CBC_lhs.get(i));
            Button b = (Button)view.findViewById(R.id.delete_button_listview);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    CBC_lhs.remove(i);
                    CBC_rhs.remove(i);
                    aaa1.notifyDataSetChanged();
                }
            });
            return view;
        }
    }

    public class Customadapter2 extends BaseAdapter{

        @Override
        public int getCount() {
            return kidney_test_lhs.size();
        }

        @Override
        public Object getItem(int i) {
            return kidney_test_lhs.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.my_list, null);
            TextView text1 = (TextView)view.findViewById(R.id.lab_lhs);
            TextView text2 = (TextView)view.findViewById(R.id.lab_rhs);
            text1.setText(kidney_test_rhs.get(i));
            text2.setText(kidney_test_lhs.get(i));
            Button b = (Button)view.findViewById(R.id.delete_button_listview);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    kidney_test_lhs.remove(i);
                    kidney_test_rhs.remove(i);
                    aaa2.notifyDataSetChanged();
                }
            });
            return view;
        }
    }

    public void back_to_main(View v){
        Intent i = new Intent();
        i.putStringArrayListExtra("country_name", basic_test_rhs);
        i.putStringArrayListExtra("values", basic_test_lhs);
        setResult(RESULT_OK, i);
        finish();
    }
}
