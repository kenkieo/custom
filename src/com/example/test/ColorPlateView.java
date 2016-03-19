package com.example.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ColorPlateView extends View{
	
	private Paint mPaint;
	public ColorPlateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
	}

}
