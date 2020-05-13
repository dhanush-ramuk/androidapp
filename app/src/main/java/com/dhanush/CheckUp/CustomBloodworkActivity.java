package com.dhanush.CheckUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CustomBloodworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_bloodwork);
    }

    public void fab_goto_create_custombloodwork(View v){
        startActivityForResult(new Intent(getApplicationContext(), CreateCustomBloodworkActivity.class), 999);
    }
}
