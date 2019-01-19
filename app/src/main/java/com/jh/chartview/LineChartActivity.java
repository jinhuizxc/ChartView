package com.jh.chartview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.jh.chartview.mpchart.MPPieChartActivity;
import com.jh.chartview.view.MyChartView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LineChartActivity extends AppCompatActivity {

    @BindView(R.id.chartView)
    MyChartView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        ButterKnife.bind(this);

        chartView.setXAxisValue(10, 9);
        chartView.setYAxisValue(10, 7);
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
