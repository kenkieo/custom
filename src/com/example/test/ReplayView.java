package com.example.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;


public class ReplayView extends TextView{

	private Paint mPaint;
	
	public ReplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(0xFF323232);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		float radius = getWidth() / 2;
		canvas.drawCircle(radius, radius, radius, mPaint);
		super.onDraw(canvas);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width  = getMeasuredWidth();
		int height = getMeasuredHeight();
		
		if(width > height){
			height = width;
		}else{
			width = height;
		}
		setMeasuredDimension(width, width);
	}

}
