package com.jso.technologies.tarot.scorer.api;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

public class OrientedTextView extends TextView{
    final boolean topDown;
 
    public OrientedTextView(Context context, AttributeSet attrs){
        super( context, attrs );
        final int gravity = getGravity();
        if (Gravity.isVertical( gravity ) && ( gravity & Gravity.VERTICAL_GRAVITY_MASK ) == Gravity.BOTTOM ) {
            setGravity((gravity & Gravity.HORIZONTAL_GRAVITY_MASK ) | Gravity.TOP );
            topDown = false;
        }
        else {
            topDown = true;
        }
    }
 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }
 
    @Override
    protected void onDraw( Canvas canvas ) {
    	int degres = 90;
    	
        TextPaint textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();
 
        canvas.save();
 
        if (topDown) {
            canvas.translate(getWidth(), 0);
            canvas.rotate(degres);
        }
        else {
            canvas.translate(0, getHeight());
            canvas.rotate(-degres);
        }
 
//        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
 
        getLayout().draw(canvas);
        canvas.restore();
    }
}