package com.dhanush.CheckUp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import javax.xml.transform.Result;

public class CustomBloodworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_bloodwork);
    }

    public void fab_goto_create_custombloodwork(View v){
        startActivityForResult(new Intent(getApplicationContext(), CreateCustomBloodworkActivity.class), 999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 999){
            //write code to get data and fill up the custom listview
        }
    }
}
