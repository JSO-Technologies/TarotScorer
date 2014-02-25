package com.jso.technologies.tarot.scorer.api;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class TextProgressBar extends ProgressBar {
	private String textLeft;
	private String textRight;
	private String textCenter;
    private Paint textPaintLeft;
    private Paint textPaintRight;
    private Paint textPaintCenter;
 
    public TextProgressBar(Context context) {
        super(context);
        init();
    }
 
    public TextProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
 
    public TextProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    private void init() {
    	textLeft = "";
        textRight = "";
        textCenter = "";
        textPaintLeft = new Paint();
        textPaintLeft.setColor(Color.BLACK);
        textPaintLeft.setTextSize(16);
        textPaintRight = new Paint();
        textPaintRight.setColor(Color.BLACK);
        textPaintRight.setTextSize(16);
        textPaintCenter = new Paint();
        textPaintCenter.setColor(Color.BLACK);
        textPaintCenter.setTypeface(Typeface.DEFAULT_BOLD);
        textPaintCenter.setTextSize(16);
    }
 
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        Rect bounds = new Rect();
        textPaintLeft.getTextBounds(textLeft, 0, textLeft.length(), bounds);
        int x = 5;
        int y = getHeight() / 2 - bounds.centerY();
        canvas.drawText(textLeft, x, y, textPaintLeft);
        
        bounds = new Rect();
        textPaintRight.getTextBounds(textRight, 0, textRight.length(), bounds);
        x = getWidth() - bounds.width() - 5;
        y = getHeight() / 2 - bounds.centerY();
        canvas.drawText(textRight, x, y, textPaintRight);
        
        bounds = new Rect();
        textPaintCenter.getTextBounds(textCenter, 0, textCenter.length(), bounds);
        x = getWidth() / 2 - bounds.centerX();
        y = getHeight() / 2 - bounds.centerY();
        canvas.drawText(textCenter, x, y, textPaintCenter);
    }
 
    public synchronized void setTextLeft(String text) {
        this.textLeft = text;
        drawableStateChanged();
    }
    
    public synchronized void setTextRight(String text) {
        this.textRight = text;
        drawableStateChanged();
    }
    
    public synchronized void setTextCenter(String text) {
        this.textCenter = text;
        drawableStateChanged();
    }
    
    public synchronized void setTexts(String textLeft, String textCenter, String textRight) {
    	this.textLeft = textLeft;
    	this.textCenter = textCenter;
        this.textRight = textRight;
        drawableStateChanged();
    }
}
