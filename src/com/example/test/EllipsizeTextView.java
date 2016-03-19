package com.example.test;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 最大行处显示...
 * @author cgh
 *
 */
public class EllipsizeTextView extends TextView{
	
	private String ellipsize = "...";
	private int mMaxLines;
	private boolean mEllipsize;
	
	public EllipsizeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if(mMaxLines > 0 && getLineCount() >= mMaxLines && !mEllipsize){
			mEllipsize = true;
			Layout layout = getLayout();
			float horiz = canvas.getWidth()- layout.getPaint().measureText(ellipsize);
			int end = layout.getOffsetForHorizontal(mMaxLines - 1, horiz);
			CharSequence sequence = layout.getText().subSequence(0, end);
			SpannableStringBuilder builder = new SpannableStringBuilder();
			builder.append(sequence);
			builder.append(ellipsize);
			setText(builder);
		}else{
			super.onDraw(canvas);
		}
	}
	
	@Override
	public void setMaxLines(int maxlines) {
		mMaxLines = maxlines;
		super.setMaxLines(maxlines);
	}
	
}
