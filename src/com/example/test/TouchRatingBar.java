package com.example.test;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * 评分工具,五角星,可滑动
 * @author Administrator
 *
 */
public class TouchRatingBar extends View{
	
	private Drawable mDrawableSelect;
	private Drawable mDrawableNormal;
	
	private List<Rect> mRects;
	
	private int mCount 		= 5;
	private int mPosition 	= 0;

	public TouchRatingBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		
		mDrawableSelect = getResources().getDrawable(R.drawable.lion_icon_game_stars_yellow_big);
		mDrawableNormal = getResources().getDrawable(R.drawable.lion_icon_game_stars_gary_big);
		
		mRects = new ArrayList<Rect>();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < mCount; i++) {
			Rect rect = mRects.get(i);
			int itemWidth  = rect.right - rect.left;
			int itemHeight = rect.bottom - rect.top;
			
			if(mPosition >= i && mDrawableSelect != null){

				int itemDrawWidth = mDrawableSelect.getIntrinsicWidth();
				int drawLeft = (itemWidth - itemDrawWidth) / 2;

				int itemDrawHeight = mDrawableSelect.getIntrinsicHeight();
				int drawTop = (itemHeight - itemDrawHeight) / 2;
				
				mDrawableSelect.setBounds(rect.left + drawLeft, rect.top + drawTop, rect.left + drawLeft + itemDrawWidth, rect.top + drawTop + itemDrawHeight);
				mDrawableSelect.draw(canvas);
			}else if(mDrawableNormal != null){
				int itemDrawWidth = mDrawableNormal.getIntrinsicWidth();
				int drawLeft = (itemWidth - itemDrawWidth) / 2;

				int itemDrawHeight = mDrawableNormal.getIntrinsicHeight();
				int drawTop = (itemHeight - itemDrawHeight) / 2;
				
				mDrawableNormal.setBounds(rect.left + drawLeft, rect.top + drawTop, rect.left + drawLeft + itemDrawWidth, rect.top + drawTop + itemDrawHeight);
				mDrawableNormal.draw(canvas);

			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		checkTouchPosition(x, y);
		return true;
	}
	
	private void checkTouchPosition(int x, int y){
		int size = mRects.size();
		for (int i = 0; i < size; i++) {
			Rect rect = mRects.get(i);
			if(rect.contains(x, y)){
				mPosition = i;
				break;
			}
		}
		invalidate();
	}
	
	@SuppressLint("DrawAllocation") @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if(mCount == 0) mCount = 1;
		
		int itemWidth = getMeasuredWidth() / mCount;
		for (int i = 0; i < mCount; i++) {
			Rect rect;
			if(mRects.size() <= i){
				rect = new Rect();
				mRects.add(rect);
			}
			rect = mRects.get(i);
			rect.left 	= itemWidth * i;
			rect.top  	= 0;
			rect.right	= rect.left + itemWidth;
			rect.bottom = getMeasuredHeight();
		}
	}
	
}
