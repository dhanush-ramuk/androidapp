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
import android.widget.Toast;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String [] basic_test = {"weight", "BP"};
    String [] CBC = {"WBC", "RBC", "hemoglobin", "platelets", "hematocrit"};
    String [] kidney_panel = {"blood urea nitrogen", "creatinine"};
    String [] liver_panel = { "alkaline phosphatase", "alanine aminotransferase", "aspartate aminotransferase", "bilirubin"};
    String [] electrolyte_panel ={"C02", "sodium", "potassium", "chloride"};
    String [] proteins = {"albumin", "total protein"};
    String [] general_test = {"glucose[fasting]", "glucose[random]", "calcium"};
    String [] lipid_panel = {"cholestrol", "triglyceride", "HDL", "LDL"};
    Customadapter aaa;
    Customadapter1 aaa1;
    Customadapter2 aaa2;
    Customadapter2 aaa3;
    Customadapter2 aaa4;
    Customadapter2 aaa5;
    Customadapter2 aaa6;
    Customadapter2 aaa7;
    ArrayList<String> basic_test_lhs, CBC_lhs, kidney_test_lhs, liver_test_lhs, electrolytes_lhs, proteins_lhs, general_test_lhs, lipid_panel_lhs;
    ArrayList<String> basic_test_rhs, CBC_rhs, kidney_test_rhs, liver_test_rhs, electrolytes_rhs, proteins_rhs, general_test_rhs, lipid_panel_rhs;
    ListView listview_basic_test, listview_CBC, listview_kidney_test, listview_liver_test, listview_electrolyte, listview_proteins, listview_general_test, listview_lipid;
    Spinner spin_basic_test, spin_CBC, spin_kidney_test, spin_liver_test, spin_electrolyte, spin_proteins, spin_general_test, spin_lipid;
    ArrayAdapter basic_test_spin_elements, CBC_spin_elements, kidney_test_spin_elements, liver_test_spin_elements, electrolyte_spin_elements, proteins_spin_elements, general_test_spin_elements, lipid_spin_elements;

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
        spin_general_test = findViewById(R.id.spinner_generaltest);
        listview_general_test = (ListView) findViewById(R.id.listview_generaltest);
        spin_general_test.setOnItemSelectedListener(this);
        general_test_spin_elements = new ArrayAdapter(this, R.layout.spinner_design, general_test);
        general_test_spin_elements.setDropDownViewResource(R.layout.spinner_design);
        spin_general_test.setAdapter(kidney_test_spin_elements);
        general_test_lhs = new ArrayList<String>();
        general_test_rhs = new ArrayList<String>();
        aaa2 = new Customadapter2();
        listview_general_test.setAdapter(aaa2);

    }


    public void go_to_camera(View v){
        Intent i = new Intent(getApplicationContext(), Camera_Activity.class);
        startActivity(i);
    }
    public boolean check_text(String a, String b){
        if( b.isEmpty()){
            Toast.makeText(getApplicationContext(), "no value entered babe", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(basic_test_lhs.contains(a)){
            Toast.makeText(getApplicationContext(), "value already entered babe", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void onclick(View v){
        String Text = spin_basic_test.getSelectedItem().toString();
        EditText e = (EditText) findViewById(R.id.enter_basic_lab);
        String rhs = e.getText().toString();
            if(check_text(Text, rhs)){
                basic_test_lhs.add(Text.toString());
                basic_test_rhs.add(rhs);
                aaa.notifyDataSetChanged();
            }
    }

    public void onclick_CBC(View v){
        String text = spin_CBC.getSelectedItem().toString();
        CBC_lhs.add(text);
        EditText e = (EditText)findViewById(R.id.enter_CBC);
        CBC_rhs.add(e.getText().toString());
        aaa1.notifyDataSetChanged();

    }
    public void onclick_general_test(View v){
        String text = spin_general_test.getSelectedItem().toString();
        general_test_lhs.add(text);
        EditText e = (EditText)findViewById(R.id.enter_generaltest);
        general_test_rhs.add(e.getText().toString());
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
            text1.setText(basic_test_lhs.get(i));
            text2.setText(basic_test_rhs.get(i));
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
            text1.setText(CBC_lhs.get(i));
            text2.setText(CBC_rhs.get(i));
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
            return general_test_lhs.size();
        }

        @Override
        public Object getItem(int i) {
            return general_test_lhs.get(i);
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
            text1.setText(general_test_lhs.get(i));
            text2.setText(general_test_rhs.get(i));
            Button b = (Button)view.findViewById(R.id.delete_button_listview);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    general_test_lhs.remove(i);
                    general_test_rhs.remove(i);
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
