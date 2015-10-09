package com.panwrona.myportfolio.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.screen_main.MainActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class GUIUtils {

	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = null;

		if(drawable != null) {
			if (drawable instanceof BitmapDrawable) {
				BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
				if (bitmapDrawable.getBitmap() != null) {
					return bitmapDrawable.getBitmap();
				}
			}

			if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
				bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
			} else {
				bitmap =
					Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
						Bitmap.Config.ARGB_8888);
			}

			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
		}
		return bitmap;
	}

	public static void animateRevealHide(final Context ctx, final View view, @ColorRes int color,
		final int finalRadius, OnRevealAnimationListener listener) {
		int cx = (view.getLeft() + view.getRight()) / 2;
		int cy = (view.getTop() + view.getBottom()) / 2;
		int initialRadius = view.getWidth();

		Animator anim =
			ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, finalRadius);
		anim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
				view.setBackgroundColor(ctx.getResources().getColor(color));
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				listener.onRevealHide();
				view.setVisibility(View.INVISIBLE);
			}
		});
		anim.setDuration(ctx.getResources().getInteger(R.integer.animation_duration));
		anim.start();
	}

	public static void animateRevealShow(final Context ctx, final View view, final int startRadius,
		@ColorRes int color, int x, int y, OnRevealAnimationListener listener) {
		float finalRadius = (float) Math.hypot(view.getWidth(), view.getHeight());

		Animator anim =
			ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, finalRadius);
		anim.setDuration(ctx.getResources().getInteger(R.integer.animation_duration));
		anim.setStartDelay(50);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				view.setBackgroundColor(ctx.getResources().getColor(color));
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.VISIBLE);
				listener.onRevealShow();
			}
		});
		anim.start();
	}

	public static void startEnterTransitionSlideUp(Context ctx, View... views) {
		Animation slideAnimationUp = AnimationUtils.loadAnimation(ctx, R.anim.abc_slide_in_bottom);
		slideAnimationUp.setDuration(300);
		slideAnimationUp.setInterpolator(new LinearOutSlowInInterpolator());
		slideAnimationUp.setAnimationListener(getShowAnimationListener(null, views));
		startAnimations(slideAnimationUp, views);
	}

	public static void startEnterTransitionSlideDown(Context ctx, View... views) {
		Animation slideAnimationDown = AnimationUtils.loadAnimation(ctx, R.anim.abc_slide_in_top);
		slideAnimationDown.setDuration(300);
		slideAnimationDown.setInterpolator(new LinearOutSlowInInterpolator());
		slideAnimationDown.setAnimationListener(getShowAnimationListener(null, views));
		startAnimations(slideAnimationDown, views);
	}

	public static void startReturnTransitionSlideDown(Context ctx, OnReturnAnimationFinished listener, View... views) {
		Animation slideAnimation = AnimationUtils.loadAnimation(ctx, R.anim.slide_out_bottom);
		slideAnimation.setDuration(300);
		slideAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		slideAnimation.setAnimationListener(getGoneAnimationListener(listener, views));
		startAnimations(slideAnimation, views);
	}

	public static void startReturnTransitionSlideUp(Context ctx, OnReturnAnimationFinished listener, View... views) {
		Animation slideAnimationUp = AnimationUtils.loadAnimation(ctx, R.anim.slide_out_top);
		slideAnimationUp.setDuration(300);
		slideAnimationUp.setInterpolator(new AccelerateDecelerateInterpolator());
		slideAnimationUp.setAnimationListener(getGoneAnimationListener(listener, views));
		startAnimations(slideAnimationUp, views);
	}

	public static void startScaleUpAnimation(Context ctx, View... views) {
		Animation scaleAnimation = AnimationUtils.loadAnimation(ctx, R.anim.scale_up);
		scaleAnimation.setDuration(300);
		scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		scaleAnimation.setAnimationListener(getShowAnimationListener(null, views));
		startAnimations(scaleAnimation, views);
	}

	public static void startScaleDownAnimation(Context ctx, View... views) {
		Animation scaleAnimation = AnimationUtils.loadAnimation(ctx, R.anim.scale_down);
		scaleAnimation.setDuration(300);
		scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		scaleAnimation.setAnimationListener(getGoneAnimationListener(null, views));
		startAnimations(scaleAnimation, views);
	}

	private static Animation.AnimationListener getGoneAnimationListener(OnReturnAnimationFinished listener, View... views) {
		return new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				for(View v : views) {
					v.setVisibility(View.INVISIBLE);
				}
				if(listener != null) {
					listener.onAnimationFinished();
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		};
	}

	private static Animation.AnimationListener getShowAnimationListener(OnReturnAnimationFinished listener, View... views) {
		return new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				for(View v : views) {
					v.setVisibility(View.VISIBLE);
				}
				if(listener != null) {
					listener.onAnimationFinished();
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		};
	}

	private static void startAnimations(Animation animation, View... views) {
		for(View v : views) {
			v.startAnimation(animation);
		}
	}

	public interface OnReturnAnimationFinished {
		void onAnimationFinished();
	}
}
