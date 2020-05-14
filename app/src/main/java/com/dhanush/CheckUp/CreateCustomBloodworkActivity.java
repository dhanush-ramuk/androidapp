package com.dhanush.CheckUp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CreateCustomBloodworkActivity extends AppCompatActivity {
    ArrayList<View> addTestViewArrayList;
    ArrayList<String> testNameArrayList, testUnitArrayList;
    EditText firstTestNameEditText, firstTestUnitEditText, restTestNameEditText, restTextUnitEditText;
    String firstTestName, restTestName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_custom_bloodwork);
        FloatingActionButton addTestFAB = findViewById(R.id.add_test_fab);
        final LinearLayout addTestLinearLayout = findViewById(R.id.add_test_main_linear_layout);
        firstTestNameEditText = findViewById(R.id.first_testname_edittext);
        firstTestUnitEditText = findViewById(R.id.first_testunit_edittext);
        testNameArrayList = new ArrayList<>();
        testUnitArrayList = new ArrayList<>();
        addTestViewArrayList = new ArrayList<>();
        addTestFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addTestViewArrayList.isEmpty()){
                    View lastView = addTestViewArrayList.get((addTestViewArrayList.size()-1));
                    restTestNameEditText = lastView.findViewById(R.id.rest_testname_edittext);
                    restTestName = restTestNameEditText.getText().toString();
                    View addTestView = getLayoutInflater().inflate(R.layout.add_test, null);
                    if(!restTestName.trim().isEmpty()) {
                        addTestLinearLayout.addView(addTestView);
                        addTestViewArrayList.add(addTestView);
                    } else{
                        Log.e("check", "Please fill up the test name");
                    }
                }else {
                    firstTestName = firstTestNameEditText.getText().toString();
                    View addTestView = getLayoutInflater().inflate(R.layout.add_test, null);
                    if(!firstTestName.trim().isEmpty()) {
                        addTestLinearLayout.addView(addTestView);
                        addTestViewArrayList.add(addTestView);
                    } else{
                        Log.e("check", "Please fill up the test name");
                    }

                }

            }
        });
    }

    public void fab_goto_custombloodwork(View v){
        
    }
}