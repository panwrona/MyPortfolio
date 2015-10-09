package com.panwrona.myportfolio.screen_main.screen_contact;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Bind;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.utils.GUIUtils;
import com.panwrona.myportfolio.utils.OnRevealAnimationListener;

public class ContactActivity extends MvpActivity<ContactActivityView, ContactActivityPresenter>
	implements ContactActivityView {

	@Bind(R.id.activity_contact_rl_container) RelativeLayout mRlContainer;
	@Bind(R.id.activity_contact_fab) FloatingActionButton mFab;
	@Bind(R.id.activity_contact_ll_container) LinearLayout mLlContainer;

	@Override
	protected ContactActivityPresenter createPresenter() {
		return new ContactActivityPresenterImpl();
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_contact;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupEnterAnimation();
		setupExitAnimation();
	}

	private void setupEnterAnimation() {
		Transition transition = TransitionInflater.from(this)
			.inflateTransition(R.transition.change_bound_with_arc);
		getWindow().setSharedElementEnterTransition(transition);
		transition.addListener(new Transition.TransitionListener() {
			@Override
			public void onTransitionStart(Transition transition) {

			}

			@Override
			public void onTransitionEnd(Transition transition) {
				transition.removeListener(this);
				animateRevealShow(mRlContainer);
			}

			@Override
			public void onTransitionCancel(Transition transition) {

			}

			@Override
			public void onTransitionPause(Transition transition) {

			}

			@Override
			public void onTransitionResume(Transition transition) {

			}
		});
	}

	private void animateRevealShow(final View viewRoot) {
		int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
		int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
		GUIUtils.animateRevealShow(this, mRlContainer, mFab.getWidth() / 2, R.color.accent_color,
			cx, cy, new OnRevealAnimationListener() {
				@Override
				public void onRevealHide() {

				}

				@Override
				public void onRevealShow() {
					initViews();
				}
			});
	}

	private void initViews() {
		new Handler(Looper.getMainLooper()).post(() -> {
			Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
			animation.setDuration(300);
			mLlContainer.startAnimation(animation);
			mLlContainer.setVisibility(View.VISIBLE);
		});
	}

	@Override
	public void onBackPressed() {
		GUIUtils.animateRevealHide(this, mRlContainer, R.color.accent_color, mFab.getWidth() / 2,
			new OnRevealAnimationListener() {
				@Override
				public void onRevealHide() {
					backPressed();
				}

				@Override
				public void onRevealShow() {

				}
			});
	}

	private void backPressed() {
		super.onBackPressed();
	}

	private void setupExitAnimation() {
		Fade fade = new Fade();
		getWindow().setReturnTransition(fade);
		fade.setDuration(getResources().getInteger(R.integer.animation_duration));
	}
}

