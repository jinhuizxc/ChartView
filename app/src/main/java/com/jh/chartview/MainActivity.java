package com.jh.chartview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jh.chartview.view.MyChartView;

public class MainActivity extends AppCompatActivity {

    private MyChartView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chartView = (MyChartView) findViewById(R.id.columnView);
        chartView.setXAxisValue(10, 9);
        chartView.setYAxisValue(10,7);
        chartView.setGrapthTitle("Android柱形图");

        int columnInfo[][] = new int[][]{
                {6, Color.BLUE},
                {5, Color.GREEN},
                {4, Color.RED},
                {3, Color.BLUE},
                {5, Color.YELLOW},
                {3, Color.LTGRAY},
                {2, Color.BLUE}};

        chartView.setColumnInfo(columnInfo);


    }
}
