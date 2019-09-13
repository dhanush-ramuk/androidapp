package com.example.application1;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class All_Results {
    private Map<String, String> map = new HashMap<String, String>();
    public All_Results(Map a){
        this.map = a;
    }

    public Map<String, String> get_map(){
        return this.map;
    }
}
