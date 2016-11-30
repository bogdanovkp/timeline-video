package com.example.bogdanovkp.testscale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by bogdanovkp on 01.09.2016.
 */
public class MyScaleView extends View {
    private GestureDetector gestureDetector;
    private static float pxmm = 30;
    private int lineSmall;
    private int lineMedium;
    private int lineLarge;
    int width = 480;
    int height;
    float startingPoint = 0;
    private int endPoint = 0;
    private Paint rulerPaint;
    private Paint textPaint;
    private Paint paintRectGreen;
    private int ratio = 90;
    private int maxRatio = 90;
    private int scale = 1;
    private static final int SCALE_MAX = 10;
    private static final int SCALE_MIN = 1;


    public MyScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

        lineSmall = (int) context.getResources().getDimension(R.dimen.scale_line_small);
        lineMedium = (int) context.getResources().getDimension(R.dimen.scale_line_medium);
        lineLarge = (int) context.getResources().getDimension(R.dimen.scale_line_large);

        rulerPaint = new Paint();
        rulerPaint.setStyle(Paint.Style.STROKE);
        rulerPaint.setStrokeWidth(0);
        rulerPaint.setAntiAlias(false);
        rulerPaint.setColor(Color.BLACK);

        textPaint = new TextPaint();
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(0);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(context.getResources().getDimension(R.dimen.txt_size));

        paintRectGreen = new Paint();
        paintRectGreen.setColor(ContextCompat.getColor(context, R.color.green));
        paintRectGreen.setStyle(Paint.Style.FILL);

    }

    private void init(){
        if (!isInEditMode()){
           // gestureDetector = new GestureDetector(getContext(), gestureListener);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        ratio = (int) Math.floor((1440 / (width * scale / pxmm)));
        maxRatio = ratio;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        startingPoint = 0;

        for (int i = 0; i <= width; i++){
            int size = (i % 6 == 0) ? lineLarge : (i % 3 == 0) ? lineMedium : lineSmall;
            canvas.drawLine(startingPoint, endPoint + size, startingPoint, endPoint, rulerPaint);

            if (size == lineLarge){
                Calendar calendar = Calendar.getInstance();
                int hour = i * ratio / 60;
                int minute = (i * ratio) - (hour * 60);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);

                DateFormat df = new SimpleDateFormat("H:mm");
                String date = df.format(calendar.getTime());
                if (hour * 60 + minute >= 600){
                    canvas.drawText(date, startingPoint - pxmm * 2, endPoint + size + textPaint.getTextSize(), textPaint);
                }else {
                    canvas.drawText(date, startingPoint - pxmm, endPoint + size + textPaint.getTextSize(), textPaint);
                }
            }
            canvas.drawRect(startingPoint, height/2, (startingPoint + pxmm), height, paintRectGreen);

            startingPoint = startingPoint + pxmm;
        }
    }

    public void addScale(){
        if (scale >= SCALE_MIN && scale < SCALE_MAX){
            scale += 1;
            ratio = ratio / 2;
            if (ratio < SCALE_MIN){
                ratio = 1;
            }
            invalidate();
        }
    }

    public void deductScale(){
        if (scale > SCALE_MIN && scale <= SCALE_MAX){
            scale -= 1;
            ratio = ratio * 2;
            if (ratio > maxRatio){
                ratio = maxRatio;
            }
            invalidate();
        }
    }


}
