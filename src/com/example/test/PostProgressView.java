package com.example.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
/**
 * 圆形进度条,中间显示进度
 * @author Administrator
 *
 */
public class PostProgressView extends View {

	private Paint mPaint;
	private int mMax = 100;
	private int mProgress;

	private float mRadius;
	private RectF mOval;
	private int mStrokeWith = 5;
	private String text;

	public PostProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(mStrokeWith);
		mPaint.setTextSize(DisplayUtils.sp2px(getContext(), 14));

		mOval = new RectF();
		test();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(mPaint != null && text != null){
			mPaint.setStyle(Style.STROKE);
			mPaint.setColor(getResources().getColor(R.color.common_gray));
			canvas.drawCircle(mRadius, mRadius, mRadius - mStrokeWith, mPaint);
			if (mMax == 0) {
				mMax = 100;
			}
			mPaint.setColor(getResources().getColor(R.color.common_basic_red));
			canvas.save();
			canvas.rotate(270, mRadius, mRadius);
			canvas.drawArc(mOval, 0, mProgress * 360 / mMax, false, mPaint);
			canvas.restore();
			
			mPaint.setStyle(Style.FILL);
			float x = (getWidth() - mPaint.measureText(text)) / 2;
			float y = getHeight() / 2 - mPaint.ascent() / 2 - mPaint.descent() / 2;
			canvas.drawText(text, x, y, mPaint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mRadius = getMeasuredWidth() / 2;

		mOval.left = mStrokeWith;
		mOval.top = mStrokeWith;
		mOval.right = getMeasuredWidth() - mStrokeWith;
		mOval.bottom = getMeasuredHeight() - mStrokeWith;
	}

	public void setProgress(int progress) {
		mProgress = progress;
		text = mProgress + "%";
		invalidate();
	}
	
	public void test(){
		postDelayed(new Runnable() {
			
			@Override
			public void run() {
				setProgress(mProgress+1);
				if(mProgress < mMax){
					test();
				}
			}
		}, 20);
	}
	
}
