package com.panwrona.myportfolio.customviews.support_layouts;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class MyNestedScrollView extends NestedScrollView {

	public MyNestedScrollView(Context context) {
		super(context);
	}


	public MyNestedScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private float xDistance, yDistance, lastX, lastY;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				xDistance = yDistance = 0f;
				lastX = ev.getX();
				lastY = ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				final float curX = ev.getX();
				final float curY = ev.getY();
				xDistance += Math.abs(curX - lastX);
				yDistance += Math.abs(curY - lastY);
				lastX = curX;
				lastY = curY;
				if (xDistance > yDistance)
					return false;
		}

		return super.onInterceptTouchEvent(ev) || ev.getPointerCount() == 2;
	}
}