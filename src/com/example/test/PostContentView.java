package com.example.test;

import java.lang.reflect.Method;

import android.content.Context;
import android.graphics.Paint;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * 匹配maxLines显示...
 * 匹配中英文的自动换行
 * @author cgh
 *
 */
public class PostContentView extends TextView{
	
	private String 	mEllipsize = "...";
	private int 	mMaxLines;
	
	private PostContentBean mPostContentBean;
	
	public PostContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		setMovementMethod(CustomLinkMovementMethod.getInstance());
	}
	
	public void setContent(PostContentBean bean, boolean clickableSpan) {
		mPostContentBean = bean;
		CharSequence text = bean.mBuilder;
		if(!bean.mParsed){
//			text = parseContent(text, clickableSpan);
//			text = MatcherEmojiUtils.parseEmoji(getContext(), text);
		}
		setText(text);
	}
	
	public void setContent(PostContentBean bean){
		mPostContentBean = bean;
		CharSequence text = bean.mBuilder;
		if(!bean.mParsed){
//			text = MatcherEmojiUtils.parseEmoji(getContext(), text);
		}
		setText(text);
	}
	
//	private CharSequence parseContent(CharSequence text, final boolean clickableSpan) {
//		if (TextUtils.isEmpty(text))
//			return "";
//		List<ActionTranslator> translators = new ArrayList<ActionTranslator>();
//		text = GlobalTranslator.translate(text.toString(), translators);
//		SpannableString ss = new SpannableString(text);
//		if (clickableSpan) {
//			for (int i = 0; i < translators.size(); i++) {
//				final ActionTranslator actionTranslator = translators.get(i);
//				int textColor = Color.RED;
//				boolean underlineText = true;
//				if(actionTranslator instanceof ActionAtUserTranslator){
//					textColor = getResources().getColor(R.color.common_basic_red);
//					underlineText = false;
//				}
//				ss.setSpan(new ActionSpan(getContext(), actionTranslator, textColor, underlineText), actionTranslator.mStart, actionTranslator.mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//			}
//		}
//		return ss;
//	}
	
	@Override
	public void setMaxLines(int maxlines) {
		mMaxLines = maxlines;
		super.setMaxLines(maxlines);
	}
	
	private SpannableStringBuilder mBuilder = new SpannableStringBuilder();
	private Paint mPaint = new Paint();
	private SpannableStringBuilder mTmepBuilder = new SpannableStringBuilder();
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if(mPostContentBean != null && !mPostContentBean.mParsed){
			mBuilder.clear();
			if(getWidth() != 0){
				try {
					int contentLength = getWidth() - getPaddingLeft() - getPaddingRight();
					int length = getText().length();
					
					int lines = 0;
					int start = 0;
					
					for (int i = 0; i < length; i++) {
						int end = TextUtils.indexOf(getText(), '\n', i, length);
						if (end < 0) 
							i = length;
						else
						    i = end + 1;
						
						for (int j = start; j <= i; j++) {
							CharSequence sequence = getText().subSequence(start, j);
							mTmepBuilder.clear();
							mTmepBuilder.append(sequence);
							if(mMaxLines == lines + 1){
								mTmepBuilder.append(mEllipsize);
							}
							float w = measurePara(mTmepBuilder);
							if (w > contentLength || j == i){
								if(w > contentLength){
									j--;
								}
								
								end = sequence.length() - 1;
								if(j == i){
									end = sequence.length();
								}
								
								lines++;
				            	start = j;
				            	mBuilder.append(sequence.subSequence(0, end));
				            	if(mMaxLines == lines){
				            		i = length;
				            		j = i;
				            		mBuilder.append(mEllipsize);
								}
				            	if(j != length && mBuilder.charAt(mBuilder.length() - 1) != '\n'){
				            		mBuilder.append("\n");
				            	}
				            }
						}
					}
					mPostContentBean.mParsed = true;
					mPostContentBean.mBuilder.clear();
					mPostContentBean.mBuilder.append(mBuilder);
					setText(mBuilder);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private float measurePara(CharSequence sequence) throws Exception{
		if(android.os.Build.VERSION.SDK_INT < 16){
			Method measurePara = Layout.class.getDeclaredMethod("measurePara", TextPaint.class, CharSequence.class, int.class, int.class);
			measurePara.setAccessible(true);
			return (Float) measurePara.invoke(null, getPaint(), mPaint, sequence, 0, sequence.length());
		}else{
			Method measurePara = Layout.class.getDeclaredMethod("measurePara", TextPaint.class, CharSequence.class, int.class, int.class);
			measurePara.setAccessible(true);
			return (Float) measurePara.invoke(null, getPaint(), sequence, 0, sequence.length());
		}
	}
	
	public class PostContentBean {

		public String content;
		public SpannableStringBuilder mBuilder = new SpannableStringBuilder();
		public boolean mParsed;
	}

	
}
