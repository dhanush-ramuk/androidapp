package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class Activity3 extends AppCompatActivity  {
    List<Integer> number;
    int i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
    }

    public void onclick_open_medications(View v){
    //TODO need to get rid of custom adapter and programmatically add views.
        number = new ArrayList<>();
        LinearLayout parent_linearlayout = (LinearLayout) findViewById(R.id.main_linear_layout);
        View view = getLayoutInflater().inflate(R.layout.tab_res, null);
        TextView textview = (TextView) view.findViewById(R.id.textView);
        EditText edittext = (EditText) view.findViewById(R.id.edittext_tab_name);
        number.add(i);
        textview.setText("tablet " +  Integer.toString(number.get(0)));
        edittext.setText("tablet "+ Integer.toString(number.get(0)) +" name");
        i = i + 1;
        number.add(i);
        parent_linearlayout.addView(view);

    }
    public void back_to_main(View v) {
        finish();
    }
}
