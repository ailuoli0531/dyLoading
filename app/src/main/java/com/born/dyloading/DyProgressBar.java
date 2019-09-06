package com.born.dyloading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * created by born on 2019-09-05.
 * 仿抖音加载中
 */
public class DyProgressBar extends View {
    private Paint mPaint;
    //view宽高
    private int mHeight;
    private int mWidth;
    //画线动态坐标
    private int curWidth;
    //中心坐标
    private int midWidth;
    //动画起始点距离中间的距离，默认0
    private int marginCenter=0;
    //动画速度，值越大越快，默认5
    private int speed = 5;

    //参数是否初始成功
    private boolean isParamInit = false;
    //是否停止
    private boolean isStop = true;

    public DyProgressBar(Context context) {
        super(context);
        init();
    }

    public DyProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DyProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(!isParamInit){
            initParam(canvas);
        }

        if(isStop){
            //清除轨迹
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        }else{
            //两边方向线
            canvas.drawLine(midWidth,mHeight,curWidth,mHeight,mPaint);
            canvas.drawLine(midWidth,mHeight,mWidth-curWidth,mHeight,mPaint);
            post(transRunable);
        }

    }

    //初始化参数
    private void initParam(Canvas canvas) {
        mHeight = canvas.getHeight();
        mWidth =  canvas.getWidth();
        curWidth = mWidth/2;
        midWidth = mWidth/2;
        isParamInit = true;
        mPaint.setStrokeWidth(mHeight);
    }

    private Runnable transRunable = new Runnable() {
        @Override
        public void run() {

            curWidth+=speed;
            if(marginCenter+curWidth>mWidth){
                curWidth = mWidth/2+marginCenter;
            }
            postInvalidate();
        }
    };

    /**
     * 停止动画
     */
    public void stopRun(){
        if(!isStop){
            isStop = true;
            curWidth = mWidth/2;
        }
    }

    /**
     * 开始动画
     */
    public void startRun(){
        if(isStop){
            isStop = false;
            postInvalidate();
        }
    }
}
