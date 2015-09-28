package com.panwrona.myportfolio.customviews.circle_category_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.panwrona.myportfolio.utils.GUIUtils;
import com.panwrona.myportfolio.R;

public class CircleCategoryView extends ImageView {

	private float radius;
	private float xCenter, yCenter;

	private Paint backgroundPaint;
	private float strokeWidth = 0;

	private Bitmap mBitmap;
	private Drawable drawable;

	public CircleCategoryView(Context context) {
		this(context, null);
	}

	public CircleCategoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	@SuppressWarnings("deprecation")
	private void init(Context context, AttributeSet attrs) {
		int backgroundColor = 0;
		if(attrs != null) {
			TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleCategoryView);
			try {
				radius = array.getDimension(R.styleable.CircleCategoryView_catRadius, 0);
				backgroundColor = array.getColor(
					R.styleable.CircleCategoryView_catBackgroundColor, 0);
				strokeWidth = array.getDimension(R.styleable.CircleCategoryView_catStroke, 0);
				drawable = array.getDrawable(R.styleable.CircleCategoryView_catDrawable);
			} finally {
				array.recycle();
			}
		}

		backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backgroundPaint.setColor(backgroundColor);
		backgroundPaint.setStrokeWidth(strokeWidth);
		backgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mBitmap = GUIUtils.drawableToBitmap(drawable);
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
		if(mBitmap != null) {
			canvas.drawBitmap(mBitmap, xCenter - mBitmap.getWidth() / 2, yCenter - mBitmap.getHeight() / 2, null);
		}
	}
}
