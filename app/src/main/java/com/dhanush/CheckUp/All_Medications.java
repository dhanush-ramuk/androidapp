package com.dhanush.CheckUp;

import java.io.Serializable;
import java.util.Map;

public class All_Medications implements Serializable {
    private Map<String, String> map;
    private Map<String, String> map1;
    public All_Medications(Map<String, String> a){
        this.map = a;
    }

    public Map<String, String> return_map(){
        return this.map;
    }
}
