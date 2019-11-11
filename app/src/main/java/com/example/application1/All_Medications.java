package com.example.application1;

import java.io.Serializable;
import java.util.Map;

public class All_Medications implements Serializable {
    private Map<String, String> map;
    private Map<String, String> map1;
    public All_Medications(Map<String, String> a, Map<String, String> b){
        this.map = a;
        this.map1 = b;
    }



    public Map<String, String> return_map(){
        return this.map;
    }
    public Map<String, String> return_map1(){
        return this.map1;
    }
}
