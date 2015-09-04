package com.panwrona.myportfolio.customviews.circle_category_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;
import com.panwrona.myportfolio.R;

public class CodingCircleCategoryView extends View {

	private float radius;
	private float xCenter, yCenter;

	private Paint backgroundPaint, textPaint;
	private float strokeWidth = 0;
	float fontSize = 0;
	private float textLength;
	private float textHeight;

	public CodingCircleCategoryView(Context context) {
		this(context, null);
	}

	public CodingCircleCategoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	@SuppressWarnings("deprecation")
	private void init(Context context, AttributeSet attrs) {
		int backgroundColor = 0;
		int textColor = 0;
		if(attrs != null) {
			TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CodingCircleCategoryView);
			try {
				radius = array.getDimension(R.styleable.CodingCircleCategoryView_codingRadius, 0);
				backgroundColor = array.getColor(
					R.styleable.CodingCircleCategoryView_codingBackgroundColor, 0);
				textColor =
					array.getColor(R.styleable.CodingCircleCategoryView_codingTextColor, 0);
				strokeWidth = array.getDimension(R.styleable.CodingCircleCategoryView_codingStroke, 0);
				fontSize = array.getDimension(R.styleable.CodingCircleCategoryView_codingFontSize, 0);
			} finally {
				array.recycle();
			}
		}

		Typeface typeface = TypefaceUtils.load(context.getAssets(), "fonts/Roboto-Bold.ttf");

		backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backgroundPaint.setColor(backgroundColor);
		backgroundPaint.setStrokeWidth(strokeWidth);
		backgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);

		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextSize(fontSize);
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setTypeface(typeface);
		textPaint.setColor(textColor);

		textLength = textPaint.measureText("< />");
		Rect textBounds = new Rect();
		textPaint.getTextBounds("< />", 0, 1, textBounds);
		textHeight = textBounds.bottom - textBounds.top;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		xCenter = w / 2;
		yCenter = h / 2;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(xCenter, yCenter, radius, backgroundPaint);
		canvas.drawText("< />", xCenter - textLength / 4, yCenter + textHeight / 2, textPaint);
	}
}
