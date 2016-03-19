package com.example.test;

import java.lang.reflect.Method;

import android.content.Context;
import android.graphics.Canvas;
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
	
	public PostContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		setMovementMethod(CustomLinkMovementMethod.getInstance());//内链点击
	}
	
	public void setContent(CharSequence text, boolean clickableSpan, int maxLines) {
//		text = parseContent(text, clickableSpan);//处理内链接跳转
//		text = MatcherEmojiUtils.parseEmoji(getContext(), text);//处理表情
		setText(text);
	}
	
	public void setContent(CharSequence text){
//		text = MatcherEmojiUtils.parseEmoji(getContext(), text);//处理表情
		setText(text);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		setText("comment_contjsdkfj jwj23jr 2顶替日3求爆照暧昧在今年13在岁的程欣是一名早产儿，出生6个月后被查出患有先天性脑瘫，" +
				"父母带她四处求医，债台高筑，但病情一直不见好转。13年来，僵硬地躺着，是程欣生活中主要的姿势。家人一度只能靠喂白酒来缓解她的疼痛ent=" +
				"[*$emoji_am*$]1[*$emoji_yiw*$]2[*$emoji_ll*$]3[*$emoji_ll*$]" +
				"[*$emoji_ll*$]4[*$emoji_yiw*$]5[*$emoji_qq*$]6[*$emoji_huaix*$]" +
				"[*$emoji_huaix*$]7[*$emoji_qq*$]8[*$emoji_qq*$]9[*$emoji_huaix*$]" +
				"[*$emoji_huaix*$]10[*$emoji_ll*$]11[*$emoji_ll*$]12[*$emoji_ll*$]" +
				"[*$emoji_ll*$]13[*$emoji_pz*$]14[*$emoji_ll*$]15[*$emoji_ll*$]16");
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
	
	private boolean mCheckLayout;
	private SpannableStringBuilder mBuilder = new SpannableStringBuilder();
	private Paint mPaint = new Paint();
	private SpannableStringBuilder mTmepBuilder = new SpannableStringBuilder();
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if(!mCheckLayout){
			mCheckLayout = true;
			
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
						    	i = end;
						 
						 
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
				            	
				            	if(j != length){
				            		mBuilder.append("\n");
				            	}
				            }
						}
					}
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
	
}
