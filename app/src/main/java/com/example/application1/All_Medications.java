package com.example.application1;

import java.util.Map;

public class All_Medications {
    private Map<String, String> map;
    public All_Medications(Map<String, String> a){
        this.map = a;
    }

    public Map<String, String> return_map(){
        return this.map;
    }
}
