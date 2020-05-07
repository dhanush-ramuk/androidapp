package com.dhanush.CheckUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class trackpage extends AppCompatActivity {
    ArrayList<All_Results> obj;
    ArrayList<Date> dateArrayList, orderedDateArrayList;
    ArrayList<Float> dataPointArrayList, orderedDataPointArrayList;
    LinearLayout parent, parent1, parentGraphview;
    String dateanddayString = null;
    String[] all_tests = {"weight", "cholesterol", "triglyceride", "HDL", "LDL", "glucose[fasting]", "glucose[random]", "calcium", "albumin", "total protein", "C02",
            "sodium", "potassium", "chloride", "alkaline phosphatase", "alanine amino transferase", "aspartate amino transferase", "bilirubin",
            "blood urea nitrogen", "creatinine", "WBC", "RBC", "hemoglobin", "platelets", "hematocrit", "BP"};
    int flag = 0;
    HelperClass helperClass;
    Button graphViewButton;
    ScrollView defaultViewScrollbar, graphViewScrollbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackpage);
        helperClass = new HelperClass();
        Intent intent = getIntent();
        obj = (ArrayList<All_Results>) intent.getSerializableExtra("list");
        graphViewButton = findViewById(R.id.graph_view_button);
        figure_out(helperClass.arrangeObjectsAscendingOrder(obj));
        createGraphView(helperClass.arrangeObjectsAscendingOrder(obj));
        defaultViewScrollbar = findViewById(R.id.scrollview_deafultview);
        graphViewScrollbar = findViewById(R.id.scrollview_graphview);
        parent = (LinearLayout) findViewById(R.id.parentLinearLayout);
        parentGraphview = findViewById(R.id.parentLinearLayout1);

        graphViewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    if(graphViewButton.getText().toString().equals("GRAPH_VIEW")) {
                        defaultViewScrollbar.setVisibility(View.GONE);
                        graphViewScrollbar.setVisibility(View.VISIBLE);
                        graphViewButton.setText("DEFAULT_VIEW");
                    } else if(graphViewButton.getText().toString().equals("DEFAULT_VIEW")){
                        graphViewScrollbar.setVisibility(View.GONE);
                        defaultViewScrollbar.setVisibility(View.VISIBLE);
                        graphViewButton.setText("GRAPH_VIEW");
                    }
                  //dummyMethod();

            }
        }
        );
    }


    private void createGraphView(ArrayList<All_Results> obj) {
        for (int i = 0; i < all_tests.length; i++) {
            dateArrayList = new ArrayList<>();
            dataPointArrayList = new ArrayList<>();
            View view = getLayoutInflater().inflate(R.layout.graph_view_layout, null);
            parent = (LinearLayout) findViewById(R.id.parentLinearLayout1);
            TextView graphviewTextview = view.findViewById(R.id.graphview_textview);
            GraphView graphView = view.findViewById(R.id.graph_view);
            for (int j = 0; j < obj.size(); j++) {
                if (obj.get(j).get_map().get(all_tests[i]) != null) {

                    graphviewTextview.setText(all_tests[i]);
                    try{
                        if(checkForChar(obj.get(j).get_map().get(all_tests[i]))) {
                            dateArrayList.add(helperClass.getIndividualDate(j, obj));
                            dataPointArrayList.add(Float.parseFloat(obj.get(j).get_map().get(all_tests[i])));
                            flag = 1;
                        }
                } catch (NumberFormatException e){}
                }
            }
            if (flag == 1) {
                //TODO order the datearray list in ascending order with it corresponding values
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(generateDataPoints(dateArrayList, dataPointArrayList));
                series.setTitle(all_tests[i]);
                // styling series
                series.setColor(Color.rgb(255, 30, 88));
                series.setDrawDataPoints(true);
                series.setDataPointsRadius(18);
                series.setThickness(12);
                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                       // Toast.makeText(getApplicationContext(), "Series1: On Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
                    }
                });
                graphView.addSeries(series);


                GridLabelRenderer glr = graphView.getGridLabelRenderer();
                glr.setPadding(80);
                graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
                //TODO change the Labels to manual if less than 4 or to Human rounding
                //TODO make the UI of graph more good
                graphView.getGridLabelRenderer().setNumHorizontalLabels(4);
                graphView.getViewport().setMinX(dateArrayList.get(0).getTime());
                graphView.getViewport().setMaxX(dateArrayList.get(dateArrayList.size()-1).getTime());
                graphView.getViewport().setXAxisBoundsManual(true);
                graphView.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
                graphView.getGridLabelRenderer().setTextSize(44);
                graphView.getGridLabelRenderer().setHorizontalLabelsAngle(0);
                graphView.getGridLabelRenderer().setHumanRounding(false);
               // NumberFormat nf = NumberFormat.getInstance();
                //nf.setMaximumFractionDigits(2);
                //graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf,nf));
                flag = 0;
                parent.addView(view);
            }

        }
}



    private boolean checkForChar(String s) {
        int flag = 0;
        for(int i=0; i<s.length(); i++) {
            if ((((int)s.charAt(i)) >= 48 && ((int)s.charAt(i))<=57) || ((int)s.charAt(i)) == 46) {
                        flag = 1;
            }
        }
        if(flag==1){
            return true;
        }else{
            return false;
        }
    }

    private DataPoint[] generateDataPoints(ArrayList<Date> dateArrayList, ArrayList<Float> dataPointArrayList) {
        DataPoint[] values = new DataPoint[dateArrayList.size()];
        for(int i=0; i<dateArrayList.size(); i++){
            DataPoint value = new DataPoint(dateArrayList.get(i), dataPointArrayList.get(i));
            values[i] = value;
        }
        return values;
    }




    public String shorten_test_name_main(String a){
        if(a.equals("blood urea nitrogen"))
            a = "blood urea";
        else if(a.equals("alanine amino transferase"))
            a = "alanine aminotransferase";
        else if(a.equals("aspartate amino transferase"))
            a = "aspartate aminotransferase";
        return a;
    }

    public void figure_out(ArrayList<All_Results> o) {
        for (int i = 0; i < all_tests.length; i++) {
            parent = (LinearLayout) findViewById(R.id.parentLinearLayout);
            View v1 = getLayoutInflater().inflate(R.layout.bloodtracking1, null);
            TextView datenday = (TextView) v1.findViewById(R.id.dateandday);
            dateanddayString = all_tests[i];
            datenday.setText(shorten_test_name_main(dateanddayString) +" " + "[" + UnitIncluder(dateanddayString).toLowerCase() + "]");
            parent1 = (LinearLayout) v1.findViewById(R.id.parentLinearLayout2);
            for (int j = 0; j < o.size(); j++) {
                if (o.get(j).get_map().get(all_tests[i]) != null) {
                    View v2 = getLayoutInflater().inflate(R.layout.bloodtracking2, null);
                    TextView t1 = (TextView) v2.findViewById(R.id.textView);
                    TextView t2 = (TextView) v2.findViewById(R.id.textView2);
                    t1.setText(o.get(j).get_map2().get("date"));
                    t2.setText(o.get(j).get_map().get(all_tests[i]));
                    if(j!=0 && (o.get(j-1).get_map().get(all_tests[i]) != null)) {
                        ImageView upDownButton = (ImageView) v2.findViewById(R.id.upDownButton);
                        try {
                            if (Integer.valueOf(o.get(j).get_map().get(all_tests[i])) > Integer.valueOf(o.get(j - 1).get_map().get(all_tests[i]))) {
                                upDownButton.setImageResource(R.drawable.up);
                            }
                            if(Integer.valueOf(o.get(j).get_map().get(all_tests[i])) == Integer.valueOf(o.get(j - 1).get_map().get(all_tests[i]))){
                                upDownButton.setImageResource(R.drawable.nochange);
                            }
                            else if(Integer.valueOf(o.get(j).get_map().get(all_tests[i])) < Integer.valueOf(o.get(j - 1).get_map().get(all_tests[i]))) {
                                upDownButton.setImageResource(R.drawable.down);
                            }
                        } catch (NumberFormatException ne){
                            // catches error if the object contains string value which cannot be converted too Integer.
                        }
                    }
                    parent1.addView(v2);
                    flag = 1;
                }
            }
            if(flag == 1) {
                flag = 0;
                parent.addView(v1);
            }
        }
    }
    public String UnitIncluder(String testName){
        String testUnit = null;
        int i = Integer.parseInt(getPrefs("flag",getApplicationContext()));
        if (i == 1) {

            if (testName.equals("weight"))
                testUnit = "kg";
            else if (testName.equals("cholesterol") || testName.equals("triglyceride") || testName.equals("creatinine") || testName.equals("HDL") || testName.equals("LDL") || testName.equals("blood urea nitrogen") || testName.equals("glucose[fasting]") || testName.equals("glucose[random]") || testName.equals("bilirubin"))
                testUnit = "mg/dl";
            else if (testName.equals("alanine amino transferase") || testName.equals("alkaline phosphatase") || testName.equals("aspartate amino transferase"))
                testUnit = "u/l";
            else if (testName.equals("WBC") || testName.equals("RBC") || testName.equals("platelets"))
                testUnit = "μL−1";
            else if (testName.equals("hematocrit"))
                testUnit = "%";
            else if (testName.equals("hemoglobin") || testName.equals("albumin") || testName.equals("total protein"))
                testUnit = "g/dl";
            else if (testName.equals("BP"))
                testUnit = "mmHg";
            else if (testName.equals("C02") || testName.equals("sodium") || testName.equals("potassium") || testName.equals("chloride"))
                testUnit = "mEq/L";
            else
                testUnit = "";
            return testUnit;
        } else if(i==0){

            if (testName.equals("weight"))
                testUnit = "kg";
            else if (testName.equals("cholesterol") || testName.equals("triglyceride") || testName.equals("creatinine") || testName.equals("HDL") || testName.equals("LDL") || testName.equals("blood urea nitrogen") || testName.equals("glucose[fasting]") || testName.equals("glucose[random]") || testName.equals("bilirubin"))
                testUnit = "mmol/L";
            else if (testName.equals("alanine amino transferase") || testName.equals("alkaline phosphatase") || testName.equals("aspartate amino transferase"))
                testUnit = "u/l";
            else if (testName.equals("WBC") || testName.equals("RBC") || testName.equals("platelets"))
                testUnit = "L−1";
            else if (testName.equals("hematocrit"))
                testUnit = "/ of 1.0";
            else if (testName.equals("hemoglobin") || testName.equals("albumin") || testName.equals("total protein"))
                testUnit = "g/l";
            else if (testName.equals("BP"))
                testUnit = "mmHg";
            else if (testName.equals("C02") || testName.equals("sodium") || testName.equals("potassium") || testName.equals("chloride"))
                testUnit = "mmol/L";
            else
                testUnit = "";
            return testUnit;
        }
        return "b";
    }
    public static String getPrefs(String key, Context context){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(key, "1");
    }

    public void back_to(View v){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
