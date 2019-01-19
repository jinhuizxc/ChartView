package com.jh.chartview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jh.chartview.mpchart.MPPieChartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * 自定义View 画直方图、饼状图、雷达图、波浪图、进度条
 * https://github.com/Omooo/ChartsDemo
 */
public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btn_chart)
    Button btnChart;
    @BindView(R.id.btn_mp_chart)
    Button btnMpChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }



    @OnClick({R.id.btn_chart, R.id.btn_mp_chart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_chart:
                startActivity(new Intent(this, LineChartActivity.class));
                break;
            case R.id.btn_mp_chart:
                startActivity(new Intent(this, MPPieChartActivity.class));
                break;
        }
    }
}
