package com.example.application1;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class All_Results {
    private Map<String, String> map;
    private Map<String, String> map1;

    public All_Results(Map<String, String> a, Map<String, String> b){
        this.map = a;
        this.map1 = b;
    }

    public Map<String, String> get_map(){
        return this.map;
    }

    public Map<String, String> get_map1(){
        return this.map1;
    }


}
