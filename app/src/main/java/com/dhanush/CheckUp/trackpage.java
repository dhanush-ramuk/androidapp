package com.dhanush.CheckUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class trackpage extends AppCompatActivity {
    ArrayList<All_Results> obj;
    ArrayList<Date> dateArrayList;
    ArrayList<Integer> dataPointArrayList;
    LinearLayout parent, parent1;
    String dateanddayString = null;
    String[] all_tests = {"weight", "cholesterol", "triglyceride", "HDL", "LDL", "glucose[fasting]", "glucose[random]", "calcium", "albumin", "total protein", "C02",
            "sodium", "potassium", "chloride", "alkaline phosphatase", "alanine amino transferase", "aspartate amino transferase", "bilirubin",
            "blood urea nitrogen", "creatinine", "WBC", "RBC", "hemoglobin", "platelets", "hematocrit", "BP"};
    int flag = 0;
    HelperClass helperClass;
    Button graphViewButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackpage);
        helperClass = new HelperClass();
        graphViewButton = findViewById(R.id.graph_view_button);
        Intent intent = getIntent();
        dateArrayList = new ArrayList<>();
        dataPointArrayList = new ArrayList<>();
        obj = (ArrayList<All_Results>) intent.getSerializableExtra("list");
        //figure_out(obj);
        parent = (LinearLayout) findViewById(R.id.parentLinearLayout);
        graphViewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                    //parent.setVisibility(View.GONE);
                   createGraphView();
                  //dummyMethod();

            }
        }
        );
    }

    private void dummyMethod() {

            View view = getLayoutInflater().inflate(R.layout.graph_view_layout, null);
            parent = (LinearLayout) findViewById(R.id.parentLinearLayout1);
            // View v1 = getLayoutInflater().inflate(R.layout.bloodtracking1, null);
            //TextView datenday = (TextView) v1.findViewById(R.id.dateandday);
            //dateanddayString = all_tests[i];
            //datenday.setText(shorten_test_name_main(dateanddayString) + " " + "[" + UnitIncluder(dateanddayString).toLowerCase() + "]");
            //parent1 = (LinearLayout) v1.findViewById(R.id.parentLinearLayout2);
            GraphView graphView = view.findViewById(R.id.graph_view);

                //LineGraphSeries<DataPoint> series = new LineGraphSeries<>(generateDataPoints(dateArrayList, dataPointArrayList));
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                        new DataPoint(0, 1),
                        new DataPoint(1, 5),
                        new DataPoint(2, 3)
                });
                graphView.addSeries(series);
                parent.addView(view);


    }

    private void createGraphView() {

        for (int i = 0; i < all_tests.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.graph_view_layout, null);
            parent = (LinearLayout) findViewById(R.id.parentLinearLayout1);
            GraphView graphView = view.findViewById(R.id.graph_view);
            for (int j = 0; j < obj.size(); j++) {
                if (obj.get(j).get_map().get(all_tests[i]) != null) {
                    flag = 1;
                    try{
                    dateArrayList.add(getIndividualDate(j));
                    dataPointArrayList.add(Integer.valueOf(obj.get(j).get_map().get(all_tests[i])));
                } catch (NumberFormatException e){}
                }
            }
            if (flag == 1) {
               LineGraphSeries<DataPoint> series = new LineGraphSeries<>(generateDataPoints(dateArrayList, dataPointArrayList));

                graphView.addSeries(series);
                graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
                graphView.getGridLabelRenderer().setNumHorizontalLabels(3);
              //  graphView.getViewport().setMinX(dateArrayList.get(0).getTime());
               // graphView.getViewport().setMaxX(dateArrayList.get(dateArrayList.size()-1).getTime());
                graphView.getViewport().setXAxisBoundsManual(true);
                graphView.getGridLabelRenderer().setHumanRounding(false);
                flag = 0;
                parent.addView(view);
            }

        }
}

    private DataPoint[] generateDataPoints(ArrayList<Date> dateArrayList, ArrayList<Integer> dataPointArrayList) {
        DataPoint[] values = new DataPoint[dateArrayList.size()];
        for(int i=0; i<dateArrayList.size(); i++){
            DataPoint value = new DataPoint(dateArrayList.get(i), dataPointArrayList.get(i));
            Log.i("check", "values "+value );
            Log.i("check", "size "+dateArrayList.size());
            values[i] = value;
        }
        return values;
    }


    private Date getIndividualDate(int j) {
        String date = obj.get(j).get_map2().get("date");
        String[] seperateDate = date.split("/", 0);
        int year = Integer.valueOf(seperateDate[0]);
        int month = Integer.valueOf(seperateDate[1]);
        int day = Integer.valueOf(seperateDate[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, (month-1));
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date individualDate = calendar.getTime();
        Log.i("check", "date" +calendar.getTime());
        return individualDate;

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
                            else {
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
