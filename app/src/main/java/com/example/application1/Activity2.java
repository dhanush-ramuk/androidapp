package com.example.application1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Activity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String [] basic_test = {"weight", "BP"};
    String [] CBC = {"WBC", "RBC", "hemoglobin", "platelets", "hematocrit"};
    String [] kidney_panel = {"blood urea nitrogen", "creatinine"};
    String [] liver_panel = { "alkaline phosphatase", "alanine amino transferase", "aspartate amino transferase", "bilirubin"};
    String [] electrolyte_panel ={"C02", "sodium", "potassium", "chloride"};
    String [] proteins = {"albumin", "total protein"};
    String [] general_test = {"glucose[fasting]", "glucose[random]", "calcium"};
    String [] lipid_panel = {"cholesterol", "triglyceride", "HDL", "LDL"};
    String strDate, strDay;
    Customadapter aaa;
    Customadapter1 aaa1;
    Customadapter2 aaa2;
    Customadapter3 aaa3;
    Customadapter4 aaa4;
    Customadapter5 aaa5;
    Customadapter6 aaa6;
    Customadapter7 aaa7;
    ArrayList<String> basic_test_lhs, CBC_lhs, kidney_test_lhs, liver_test_lhs, electrolytes_lhs, proteins_lhs, general_test_lhs, lipid_panel_lhs;
    ArrayList<String> basic_test_rhs, CBC_rhs, kidney_test_rhs, liver_test_rhs, electrolytes_rhs, proteins_rhs, general_test_rhs, lipid_panel_rhs;
    ListView listview_basic_test, listview_CBC, listview_kidney_test, listview_liver_test, listview_electrolyte, listview_proteins, listview_general_test, listview_lipid;
    Spinner spin_basic_test, spin_CBC, spin_kidney_test, spin_liver_test, spin_electrolyte, spin_proteins, spin_general_test, spin_lipid;
    ArrayAdapter<String> basic_test_spin_elements, CBC_spin_elements, kidney_test_spin_elements, liver_test_spin_elements, electrolyte_spin_elements, proteins_spin_elements, general_test_spin_elements, lipid_spin_elements;
    HelperClass helperClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        final FloatingActionButton b = (FloatingActionButton) findViewById(R.id.fab_camera);
        final FloatingActionButton b1 = (FloatingActionButton) findViewById(R.id.fab_back_lab);
        final View rootView = findViewById(R.id.rel);
helperClass = new HelperClass();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                if (heightDiff > dpToPx(Activity2.this, 200)) { // if more than 200 dp, it's probably a keyboard...
                    // ... do something here
                    b.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);

                }else {
                    b.setVisibility(View.VISIBLE);
                    b1.setVisibility(View.VISIBLE);

                }
            }
        });


        set_date();
        take_one();
        take_two();
        take_three();
        take_four();
        take_five();
        take_six();
        take_seven();
        take_eight();
    }
    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public void set_date(){
        Date now = new Date();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        strDate = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
        strDay = new SimpleDateFormat("EEEE").format(now);
       // TextView dateview = (TextView) findViewById(R.id.dateview);
       // dateview.setText(strDate + "    " + strDay);

    }

    public void take_one(){

        spin_basic_test = findViewById(R.id.spinner_basic_lab);
        spin_basic_test.setOnItemSelectedListener(this);
        basic_test_spin_elements = new ArrayAdapter<String>(this,R.layout.spinner_design,basic_test);
        basic_test_spin_elements.setDropDownViewResource(R.layout.spinner_dropdown_design);
        spin_basic_test.setAdapter(basic_test_spin_elements);
        listview_basic_test = (ListView) findViewById(R.id.listview_basic_lab);
        basic_test_rhs = new ArrayList<String>();
        basic_test_lhs =new ArrayList<String>();
        aaa = new Customadapter();
        listview_basic_test.setAdapter(aaa);
    }
    public static void updateListViewHeight(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            return;
        }
        // get listview height
        int totalHeight = 0;
        int adapterCount = myListAdapter.getCount();
        for (int size = 0; size < adapterCount; size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // Change Height of ListView
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (adapterCount));
        myListView.setLayoutParams(params);
    }

    public void take_two(){
        spin_CBC = findViewById(R.id.spinner_CBC);
        listview_CBC = (ListView) findViewById(R.id.listview_CBC);
        spin_CBC.setOnItemSelectedListener(this);
        CBC_spin_elements = new ArrayAdapter<String>(this, R.layout.spinner_design, CBC);
        CBC_spin_elements.setDropDownViewResource(R.layout.spinner_dropdown_design);
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
        general_test_spin_elements = new ArrayAdapter<String>(this, R.layout.spinner_design, general_test);
        general_test_spin_elements.setDropDownViewResource(R.layout.spinner_dropdown_design);
        spin_general_test.setAdapter(general_test_spin_elements);
        general_test_lhs = new ArrayList<String>();
        general_test_rhs = new ArrayList<String>();
        aaa2 = new Customadapter2();
        listview_general_test.setAdapter(aaa2);

    }

    public void take_four(){
        spin_kidney_test = findViewById(R.id.spinner_kidneytest);
        listview_kidney_test = (ListView) findViewById(R.id.listview_kidneytest);
        spin_kidney_test.setOnItemSelectedListener(this);
        kidney_test_spin_elements = new ArrayAdapter<String>(this, R.layout.spinner_design, kidney_panel);
        kidney_test_spin_elements.setDropDownViewResource(R.layout.spinner_dropdown_design);
        spin_kidney_test.setAdapter(kidney_test_spin_elements);
        kidney_test_lhs = new ArrayList<String>();
        kidney_test_rhs = new ArrayList<String>();
        aaa3 = new Customadapter3();
        listview_kidney_test.setAdapter(aaa3);

    }


    public void take_five(){
        spin_liver_test = findViewById(R.id.spinner_livertest);
        listview_liver_test = (ListView) findViewById(R.id.listview_livertest);
        spin_liver_test.setOnItemSelectedListener(this);
        liver_test_spin_elements = new ArrayAdapter<String>(this, R.layout.spinner_design, liver_panel);
        liver_test_spin_elements.setDropDownViewResource(R.layout.spinner_dropdown_design);
        spin_liver_test.setAdapter(liver_test_spin_elements);
        liver_test_lhs = new ArrayList<String>();
        liver_test_rhs = new ArrayList<String>();
        aaa4 = new Customadapter4();
        listview_liver_test.setAdapter(aaa4);

    }

    public void take_six(){
        spin_electrolyte = findViewById(R.id.spinner_electrolytestest);
        listview_electrolyte = (ListView) findViewById(R.id.listview_electrolytestest);
        spin_electrolyte.setOnItemSelectedListener(this);
        electrolyte_spin_elements = new ArrayAdapter<String>(this, R.layout.spinner_design, electrolyte_panel);
        electrolyte_spin_elements.setDropDownViewResource(R.layout.spinner_dropdown_design);
        spin_electrolyte.setAdapter(electrolyte_spin_elements);
        electrolytes_lhs = new ArrayList<String>();
        electrolytes_rhs = new ArrayList<String>();
        aaa5 = new Customadapter5();
        listview_electrolyte.setAdapter(aaa5);
    }


    public void take_seven(){
        spin_proteins = findViewById(R.id.spinner_proteintest);
        listview_proteins = (ListView) findViewById(R.id.listview_proteintest);
        spin_proteins.setOnItemSelectedListener(this);
        proteins_spin_elements = new ArrayAdapter<String>(this, R.layout.spinner_design, proteins);
        proteins_spin_elements.setDropDownViewResource(R.layout.spinner_dropdown_design);
        spin_proteins.setAdapter(proteins_spin_elements);
        proteins_lhs = new ArrayList<String>();
        proteins_rhs = new ArrayList<String>();
        aaa6 = new Customadapter6();
        listview_proteins.setAdapter(aaa6);
    }

    public void take_eight(){
        spin_lipid = findViewById(R.id.spinner_lipidstest);
        listview_lipid = (ListView) findViewById(R.id.listview_lipidstest);
        spin_lipid.setOnItemSelectedListener(this);
        lipid_spin_elements = new ArrayAdapter<String>(this, R.layout.spinner_design, lipid_panel);
        lipid_spin_elements.setDropDownViewResource(R.layout.spinner_dropdown_design);
        spin_lipid.setAdapter(lipid_spin_elements);
        lipid_panel_lhs = new ArrayList<String>();
        lipid_panel_rhs = new ArrayList<String>();
        aaa7 = new Customadapter7();
        listview_lipid.setAdapter(aaa7);
    }


    public void go_to_camera(View v){
        Intent i = new Intent(getApplicationContext(), Camera_Activity.class);
        startActivity(i);
    }
    public boolean check_text(String a, String b, ArrayList<String> c){
        if( b.isEmpty()){
            Toast.makeText(getApplicationContext(), "no value entered babe", Toast.LENGTH_SHORT).show();
            return false;
        } else if(c.contains(a)) {
            Toast.makeText(getApplicationContext(), "value already entered babe", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }
    public void onclick(View v){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        String Text = spin_basic_test.getSelectedItem().toString();
        EditText e = (EditText) findViewById(R.id.enter_basic_lab);
        String rhs = e.getText().toString();
        e.getText().clear();
        e.setCursorVisible(false);
        if(check_text(Text, rhs, basic_test_lhs)){
                    basic_test_lhs.add(Text.toString());
                    basic_test_rhs.add(rhs);
                    aaa.notifyDataSetChanged();
            }
            updateListViewHeight(listview_basic_test);
    }

    public void onclick_CBC(View v){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        String Text = spin_CBC.getSelectedItem().toString();
        EditText e = (EditText) findViewById(R.id.enter_CBC);
        String rhs = e.getText().toString();
        e.getText().clear();
        e.setCursorVisible(false);
        if(check_text(Text, rhs, CBC_lhs)) {
            CBC_lhs.add(Text.toString());
                CBC_rhs.add(rhs);
                aaa1.notifyDataSetChanged();

        }
        updateListViewHeight(listview_CBC);
    }

    public void onclick_general_test(View v){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        String Text = spin_general_test.getSelectedItem().toString();
        EditText e = (EditText) findViewById(R.id.enter_generaltest);
        String rhs = e.getText().toString();
        e.getText().clear();
        e.setCursorVisible(false);
        if(check_text(Text, rhs, general_test_lhs)) {
                general_test_lhs.add(Text.toString());
                general_test_rhs.add(rhs);
                aaa2.notifyDataSetChanged();
        }
        updateListViewHeight(listview_general_test);
    }

    public void onclick_kidney_test(View v){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        String Text = spin_kidney_test.getSelectedItem().toString();
        EditText e = (EditText) findViewById(R.id.enter_kidneytest);
        String rhs = e.getText().toString();
        e.getText().clear();
        e.setCursorVisible(false);
        if(check_text(Text, rhs, kidney_test_lhs)){
            kidney_test_lhs.add(Text.toString());
            kidney_test_rhs.add(rhs);
            aaa3.notifyDataSetChanged();
        }
        updateListViewHeight(listview_kidney_test);
    }

    public void onclick_liver_test(View v){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        String Text = spin_liver_test.getSelectedItem().toString();
        EditText e = (EditText) findViewById(R.id.enter_livertest);
        String rhs = e.getText().toString();
        e.getText().clear();
        e.setCursorVisible(false);
        if(check_text(Text, rhs, liver_test_lhs)){
            liver_test_lhs.add(Text.toString());
            liver_test_rhs.add(rhs);
            aaa4.notifyDataSetChanged();
        }
        updateListViewHeight(listview_liver_test);
    }

    public void onclick_electrolytes_test(View v){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        String Text = spin_electrolyte.getSelectedItem().toString();
        EditText e = (EditText) findViewById(R.id.enter_electrolytestest);
        String rhs = e.getText().toString();
        e.getText().clear();
        e.setCursorVisible(false);
        if(check_text(Text, rhs, electrolytes_lhs)){
            electrolytes_lhs.add(Text.toString());
            electrolytes_rhs.add(rhs);
            aaa5.notifyDataSetChanged();
        }
        updateListViewHeight(listview_electrolyte);
    }

    public void onclick_protein_test(View v){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        String Text = spin_proteins.getSelectedItem().toString();
        EditText e = (EditText) findViewById(R.id.enter_proteintest);
        String rhs = e.getText().toString();
        e.getText().clear();
        e.setCursorVisible(false);
        if(check_text(Text, rhs, proteins_lhs)){
            proteins_lhs.add(Text.toString());
            proteins_rhs.add(rhs);
            aaa6.notifyDataSetChanged();
        }
        updateListViewHeight(listview_proteins);
    }

    public void onclick_lipids_test(View v){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        String Text = spin_lipid.getSelectedItem().toString();
        EditText e = (EditText) findViewById(R.id.enter_lipidstest);
        String rhs = e.getText().toString();
        e.getText().clear();
        e.setCursorVisible(false);
        if(check_text(Text, rhs, lipid_panel_lhs)){
            lipid_panel_lhs.add(Text.toString());
            lipid_panel_rhs.add(rhs);
            aaa7.notifyDataSetChanged();
        }
        updateListViewHeight(listview_lipid);
    }

    public void show_cursor(View v){
        EditText e;
        switch (v.getId()){
            case R.id.enter_basic_lab:
                 e = (EditText) findViewById(R.id.enter_basic_lab);
                 e.setCursorVisible(true);
                 break;
            case R.id.enter_generaltest:
                 e = (EditText) findViewById(R.id.enter_generaltest);
                 e.setCursorVisible(true);
                 break;
            case R.id.enter_CBC:
                 e = (EditText) findViewById(R.id.enter_CBC);
                 e.setCursorVisible(true);
                 break;
            case R.id.enter_kidneytest:
                 e = (EditText) findViewById(R.id.enter_kidneytest);
                 e.setCursorVisible(true);
                 break;
            case R.id.enter_livertest:
                 e = (EditText) findViewById(R.id.enter_livertest);
                 e.setCursorVisible(true);
                 break;
            case R.id.enter_lipidstest:
                 e = (EditText) findViewById(R.id.enter_lipidstest);
                 e.setCursorVisible(true);
                 break;
            case R.id.enter_electrolytestest:
                 e = (EditText) findViewById(R.id.enter_electrolytestest);
                 e.setCursorVisible(true);
                 break;
            case R.id.enter_proteintest:
                 e = (EditText) findViewById(R.id.enter_proteintest);
                 e.setCursorVisible(true);
                 break;

        }
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
            text2.setText(basic_test_rhs.get(i) + " " + "(" + helperClass.UnitIncluder(basic_test_lhs.get(i)) + ")");
            Button b = (Button)view.findViewById(R.id.delete_button_listview);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    basic_test_lhs.remove(i);
                    basic_test_rhs.remove(i);
                    aaa.notifyDataSetChanged();
                    updateListViewHeight(listview_basic_test);
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
            text2.setText(CBC_rhs.get(i) + " " + "(" + helperClass.UnitIncluder(CBC_lhs.get(i)) + ")");
            Button b = (Button)view.findViewById(R.id.delete_button_listview);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    CBC_lhs.remove(i);
                    CBC_rhs.remove(i);
                    aaa1.notifyDataSetChanged();
                    updateListViewHeight(listview_CBC);

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
            text2.setText(general_test_rhs.get(i) + " " + "(" + helperClass.UnitIncluder(general_test_lhs.get(i)) + ")");
            Button b = (Button)view.findViewById(R.id.delete_button_listview);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    general_test_lhs.remove(i);
                    general_test_rhs.remove(i);
                    aaa2.notifyDataSetChanged();
                    updateListViewHeight(listview_general_test);

                }
            });
            return view;
        }
    }
    public class Customadapter3 extends BaseAdapter{

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
            text1.setText(kidney_test_lhs.get(i));
            text2.setText(kidney_test_rhs.get(i) + " " + "(" + helperClass.UnitIncluder(kidney_test_lhs.get(i)) + ")");
            Button b = (Button)view.findViewById(R.id.delete_button_listview);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    kidney_test_lhs.remove(i);
                    kidney_test_rhs.remove(i);
                    aaa3.notifyDataSetChanged();
                    updateListViewHeight(listview_kidney_test);
                }
            });
            return view;
        }
    }
    public class Customadapter4 extends BaseAdapter{

        @Override
        public int getCount() {
            return liver_test_lhs.size();
        }

        @Override
        public Object getItem(int i) {
            return liver_test_lhs.get(i);
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
            text1.setText(liver_test_lhs.get(i));
            text2.setText(liver_test_rhs.get(i) + " " + "(" + helperClass.UnitIncluder(liver_test_lhs.get(i)) + ")");
            Button b = (Button)view.findViewById(R.id.delete_button_listview);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    liver_test_lhs.remove(i);
                    liver_test_rhs.remove(i);
                    aaa4.notifyDataSetChanged();
                    updateListViewHeight(listview_liver_test);
                }
            });
            return view;
        }
    }

    public class Customadapter5 extends BaseAdapter{

        @Override
        public int getCount() {
            return electrolytes_lhs.size();
        }

        @Override
        public Object getItem(int i) {
            return electrolytes_lhs.get(i);
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
            text1.setText(electrolytes_lhs.get(i));
            text2.setText(electrolytes_rhs.get(i) + " " + "(" + helperClass.UnitIncluder(electrolytes_lhs.get(i)) + ")");
            Button b = (Button)view.findViewById(R.id.delete_button_listview);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    electrolytes_lhs.remove(i);
                    electrolytes_rhs.remove(i);
                    aaa5.notifyDataSetChanged();
                    updateListViewHeight(listview_electrolyte);
                }
            });
            return view;
        }
    }
    public class Customadapter6 extends BaseAdapter{

        @Override
        public int getCount() {
            return proteins_lhs.size();
        }

        @Override
        public Object getItem(int i) {
            return proteins_lhs.get(i);
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
            text1.setText(proteins_lhs.get(i));
            text2.setText(proteins_rhs.get(i) + " " + "(" + helperClass.UnitIncluder(proteins_lhs.get(i)) +")");
            Button b = (Button)view.findViewById(R.id.delete_button_listview);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    proteins_lhs.remove(i);
                    proteins_rhs.remove(i);
                    aaa6.notifyDataSetChanged();
                    updateListViewHeight(listview_proteins);
                }
            });
            return view;
        }
    }

    public class Customadapter7 extends BaseAdapter{

        @Override
        public int getCount() {
            return lipid_panel_lhs.size();
        }

        @Override
        public Object getItem(int i) {
            return lipid_panel_lhs.get(i);
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
            text1.setText(lipid_panel_lhs.get(i));
            text2.setText(lipid_panel_rhs.get(i) + " " + "(" +helperClass.UnitIncluder(lipid_panel_lhs.get(i)) + ")");
            Button b = (Button)view.findViewById(R.id.delete_button_listview);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    lipid_panel_lhs.remove(i);
                    lipid_panel_rhs.remove(i);
                    aaa7.notifyDataSetChanged();
                    updateListViewHeight(listview_lipid);
                }
            });
            return view;
        }
    }


    public void back_to_main(View v){
        Intent i = new Intent();
        i.putStringArrayListExtra("basic_test_names", basic_test_lhs);
        i.putStringArrayListExtra("basic_test_values", basic_test_rhs);
        i.putStringArrayListExtra("CBC_test_names", CBC_lhs);
        i.putStringArrayListExtra("CBC_test_values", CBC_rhs);
        i.putStringArrayListExtra("kidney_test_names", kidney_test_lhs);
        i.putStringArrayListExtra("kidney_test_values", kidney_test_rhs);
        i.putStringArrayListExtra("liver_test_names", liver_test_lhs);
        i.putStringArrayListExtra("liver_test_values", liver_test_rhs);
        i.putStringArrayListExtra("electrolytes_test_names", electrolytes_lhs);
        i.putStringArrayListExtra("electrolytes_test_values", electrolytes_rhs);
        i.putStringArrayListExtra("proteins_test_names", proteins_lhs);
        i.putStringArrayListExtra("proteins_test_values", proteins_rhs);
        i.putStringArrayListExtra("general_test_names", general_test_lhs);
        i.putStringArrayListExtra("general_test_values", general_test_rhs);
        i.putStringArrayListExtra("lipid_test_names", lipid_panel_lhs);
        i.putStringArrayListExtra("lipid_test_values", lipid_panel_rhs);
        i.putExtra("date", strDate);
        Log.e("check", "date "+strDate);
        i.putExtra("day", strDay);
        i.putExtra("boolean", 1);

        setResult(RESULT_OK, i);
        finish();

    }

    public void closeActivity(View v){
        Intent i = new Intent();
        i.putExtra("boolean", 0);

        setResult(RESULT_OK, i);
        finish();
    }
}
//TODO 1.Change name to shorter form 2. Entering same value bug fix