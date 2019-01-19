package com.jh.chartview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jh.chartview.R;


/**
 * ------------------------------------------------
 * Copyright © 2014年 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @Author sunny
 * @Date 2017/3/1  11:27
 * @Version v1.0.0
 * @Annotation 创建一个图表抽象基类
 * <p>
 * 注意事项：
 * 1，图表控件宽高的确定
 * 可以通过获取屏幕的宽高，再减去边距
 * <p>
 * 2，坐标轴上文字的标记
 * 3，标题的位置
 * 由屏幕宽度的一半再减去标题字符串宽度的一半，就得到标题显示在屏幕正中间
 */
public abstract class BaseGraphView extends View {

    public static final String TAG = "BaseGraphView";
    //视图宽度
    public int width;
    //视图高度
    public int height;
    //坐标原点位置
    public final int originX = 100;
    public final int originY = 800;

    //X坐标轴最大值
    public float maxAxisValueX;
    //X坐标轴刻度线数量
    public int axisDivideSizeX;
    //Y坐标轴最大值
    public float maxAxisValueY;
    //Y坐标轴刻度线数量
    public int axisDivideSizeY;

    //坐标轴画笔
    private Paint mXYAxisPaint;
    private Paint mGraphTitlePaint;
    private Paint mAxisNamePaint;

    //图表私有属性
    private String mGrapthTitle;
    private String mXAxisName;
    private String mYAxisName;
    private int mAxisTextColor;
    private float mAxisTextSize;
    //柱状图数据,  第一个维度为值，第二个纬度为颜色
    public int columnInfo[][];
    //画笔
    public Paint mPaint;

    public BaseGraphView(Context context) {
        this(context, null);
    }

    public BaseGraphView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SunnyGraphStyle);

        mGrapthTitle = typedArray.getString(R.styleable.SunnyGraphStyle_graphTitle);
        mXAxisName = typedArray.getString(R.styleable.SunnyGraphStyle_X_AxisName);
        mYAxisName = typedArray.getString(R.styleable.SunnyGraphStyle_Y_AxisName);
        mAxisTextColor = typedArray.getColor(R.styleable.SunnyGraphStyle_axisTextColor, context.getResources().getColor(android.R.color.black));
        mAxisTextSize = typedArray.getDimension(R.styleable.SunnyGraphStyle_axisTextSize, 12);

        if (typedArray != null) {
            typedArray.recycle();
        }

        initPaint();

    }

    private void initPaint() {

        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setTextSize(20);
        }
    }


    public void setGrapthTitle(String grapthTitle) {
        mGrapthTitle = grapthTitle;
    }

    public void setXAxisName(String XAxisName) {
        mXAxisName = XAxisName;
    }

    public void setYAxisName(String YAxisName) {
        mYAxisName = YAxisName;
    }

    public void setAxisTextColor(int axisTextColor) {
        mAxisTextColor = axisTextColor;
    }

    public void setAxisTextSize(float axisTextSize) {
        mAxisTextSize = axisTextSize;
    }

    /**
     * 手动设置X轴最大值及等份数
     *
     * @param maxAxisValueX
     * @param dividedSize
     */
    public void setXAxisValue(float maxAxisValueX, int dividedSize) {
        this.maxAxisValueX = maxAxisValueX;
        this.axisDivideSizeX = dividedSize;
    }

    /**
     * 手动设置Y轴最大值及等份数
     *
     * @param maxAxisValueY
     * @param dividedSize
     */
    public void setYAxisValue(float maxAxisValueY, int dividedSize) {
        this.maxAxisValueY = maxAxisValueY;
        this.axisDivideSizeY = dividedSize;
    }

    /**
     * 传入柱状图数据
     *
     * @param columnInfo
     */
    public void setColumnInfo(int[][] columnInfo) {
        this.columnInfo = columnInfo;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        width = MeasureSpec.getSize(widthMeasureSpec)  ;
//        height = MeasureSpec.getSize(heightMeasureSpec);
        Log.e(",,,,,,,,,", "width:" + width + ",height:" + height);
    }

    /**
     * 锤子os:
     * E/test: getWidth: 1080
     * E/test: getHeight: 1870
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        width = getWidth() - originX - 100;
        Log.e("test", "getWidth: " + getWidth());
        Log.e("test", "getHeight: " + getHeight());
//        height = getHeight() - 400;
        height = (originY > getHeight() ? getHeight() : originY) - 300;
        drawAxisX(canvas, mPaint); //X轴
        drawAxisY(canvas, mPaint); //Y轴
        drawXAxisScale(canvas, mPaint); //x轴刻度
        drawXAxisScaleValue(canvas, mPaint); //x轴刻度值
        drawYAxisScale(canvas, mPaint); //y轴刻度
        drawYAxisScaleValue(canvas, mPaint); //y轴刻度值

        drawXArrow(canvas, mPaint); //x轴箭头
        drawYArrow(canvas, mPaint); //y轴箭头
        drawColumn(canvas, mPaint); //方块区域

        drawTitle(canvas, mPaint); //标题,最后改变画笔，绘制标题

    }


    /**
     * 画标题
     *
     * @param canvas
     * @param paint
     */
    private void drawTitle(Canvas canvas, Paint paint) {
//        if (TextUtils.isEmpty(mGrapthTitle)) {
//            mPaint.setTextSize(mAxisTextSize);
//            mPaint.setColor(mAxisTextColor);
//            mPaint.setFakeBoldText(true);//粗体
//            canvas.drawText(mGrapthTitle, (getWidth() / 2) - mPaint.measureText(mGrapthTitle) / 2, originY + 40, mPaint);//字体的位置自行脑补--
//        }
        //画标题
        Log.e(TAG, "drawTitle: " + mGrapthTitle);
        if (mGrapthTitle != null) {
            //设置画笔绘制文字的属性
            mPaint.setColor(mAxisTextColor);
            mPaint.setTextSize(mAxisTextSize);
            mPaint.setFakeBoldText(true); //粗体
//            canvas.drawText(mGrapthTitle, (getWidth() / 2) - mPaint.measureText(mGrapthTitle) / 2, originY + 100, mPaint);
            canvas.drawText(mGrapthTitle, (getWidth() / 2) - (paint.measureText(mGrapthTitle) / 2), originY + 100, paint);
        }
    }


    /**
     * Y轴箭头
     *
     * @param canvas
     * @param mPaint
     */
    private void drawYArrow(Canvas canvas, Paint mPaint) {
        //画三角形（Y轴箭头）
        Path mPathX = new Path();
        mPathX.moveTo(originX, originY - height - 30);//起始点
        mPathX.lineTo(originX - 10, originY - height);//下一点
        mPathX.lineTo(originX + 10, originY - height);//下一点
        mPathX.close();
        canvas.drawPath(mPathX, mPaint);
        canvas.drawText(mYAxisName, originX - 50, originY - height - 30, mPaint);
    }

    /**
     * X轴箭头
     *
     * @param canvas
     * @param paint
     */
    private void drawXArrow(Canvas canvas, Paint paint) {
        //画三角形（X轴箭头）
        Path mPathX = new Path();
        mPathX.moveTo(originX + width + 30, originY);//起始点
        mPathX.lineTo(originX + width, originY - 10);//下一点
        mPathX.lineTo(originX + width, originY + 10);//下一点
        mPathX.close();
        canvas.drawPath(mPathX, paint);
        canvas.drawText(mXAxisName, originX + width, originY + 30, paint);
    }

    /**
     * Y轴刻度值
     *
     * @param canvas
     * @param paint
     */
    protected abstract void drawYAxisScaleValue(Canvas canvas, Paint paint);

    /**
     * Y轴刻度
     *
     * @param canvas
     * @param paint
     */
    protected abstract void drawYAxisScale(Canvas canvas, Paint paint);

    /**
     * X轴刻度值
     *
     * @param canvas
     * @param paint
     */
    protected abstract void drawXAxisScaleValue(Canvas canvas, Paint paint);

    /**
     * X轴刻度
     *
     * @param canvas
     * @param paint
     */
    protected abstract void drawXAxisScale(Canvas canvas, Paint paint);

    /**
     * 画Y轴
     *
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawAxisY(Canvas canvas, Paint mPaint);

    /**
     * 画X轴
     *
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawAxisX(Canvas canvas, Paint mPaint);

    /**
     * 柱状区域
     *
     * @param canvas
     * @param paint
     */
    protected abstract void drawColumn(Canvas canvas, Paint paint);


}
