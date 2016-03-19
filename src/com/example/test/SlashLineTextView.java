package com.example.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * 划线类似Deprecated功能
 * @author Administrator
 *
 */
public class SlashLineTextView extends TextView{
	
	private int mPadding;
	private Paint mPaint;

	@Deprecated
	public SlashLineTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(getResources().getColor(R.color.common_textcolor_gray));
		mPadding = 10;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int top 	= (getHeight() - mPadding) / 2;
		int bottom 	= top + mPadding;
		canvas.drawLine(0, top, getWidth(), bottom, mPaint);
	}
}
