package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class medicationResultsActivity extends AppCompatActivity {

    ArrayList<All_Medications> obj;
    int i;
    Map<String, String> mapMed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_results);
        Intent intent = getIntent();
        Log.e("check", "3");
        obj = (ArrayList<All_Medications>) intent.getSerializableExtra("list");
        i = intent.getIntExtra("object index", 0);
        Log.e("check", "4");
        set_value();
    }

    private void set_value(){
        TextView tabletName = (TextView) findViewById(R.id.tabletName);
        mapMed = new HashMap<>();
        mapMed = obj.get(i).return_map();
        tabletName.setText(mapMed.get("name").toString());
    }

    public void backToMain(View v){
        Intent intent = new Intent();
        intent.putExtra("value", -1);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void deleteResult(View v){
        Intent intent = new Intent();
        intent.putExtra("value", i);
        setResult(RESULT_OK, intent);
        finish();
    }
}
