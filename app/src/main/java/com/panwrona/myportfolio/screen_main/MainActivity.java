package com.panwrona.myportfolio.screen_main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import butterknife.Bind;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.data.entities.GithubRepo;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.screen_main.di.MainActivityComponent;
import java.util.List;

public class MainActivity extends MvpActivity<MainActivityView, MainActivityPresenter>
	implements MainActivityView {
	private static final String TAG = MainActivity.class.getSimpleName();

	@Bind(R.id.toolbar) Toolbar mToolbar;
	@Bind(R.id.toolbar_rl) RelativeLayout mRlToolbarMainLayout;
	private MainActivityComponent component;

	@Override protected MainActivityPresenter createPresenter() {
		return new MainActivityPresenterImpl(this);
	}

	private void revealTransition() {
		int cx = mToolbar.getMeasuredWidth() / 2;
		int cy = mToolbar.getMeasuredHeight() / 2;

		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		int bigRadius = Math.max(displayMetrics.widthPixels, mToolbar.getHeight());
		Animator anim = ViewAnimationUtils.createCircularReveal(mToolbar, cx, cy, 0, bigRadius);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.setDuration(500);
		mToolbar.setVisibility(View.VISIBLE);
		anim.addListener(new Animator.AnimatorListener() {
			@Override public void onAnimationStart(Animator animation) {

			}

			@Override public void onAnimationEnd(Animator animation) {
				ObjectAnimator alphaAnimator =
					ObjectAnimator.ofFloat(mRlToolbarMainLayout, View.ALPHA, 0, 1);
				alphaAnimator.setDuration(500);
				alphaAnimator.setInterpolator(new DecelerateInterpolator());
				alphaAnimator.addListener(new Animator.AnimatorListener() {
					@Override public void onAnimationStart(Animator animation) {
						mRlToolbarMainLayout.setVisibility(View.VISIBLE);
					}

					@Override public void onAnimationEnd(Animator animation) {
						populateData();
					}

					@Override public void onAnimationCancel(Animator animation) {

					}

					@Override public void onAnimationRepeat(Animator animation) {

					}
				});
				alphaAnimator.start();
			}

			@Override public void onAnimationCancel(Animator animation) {

			}

			@Override public void onAnimationRepeat(Animator animation) {

			}
		});
		anim.start();
	}

	private void populateData() {
		presenter.getGithubRepos();
	}

	@Override protected Transition getEnterTransition() {
		return null;
	}

	@Override protected Transition getExitTransition() {
		return null;
	}

	@Override protected Transition getReturnTransition() {
		return null;
	}

	@Override protected Transition getReenterTransition() {
		return null;
	}

	@Override protected int getLayout() {
		return R.layout.activity_main;
	}

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		component = MainActivityComponent.Initializer.init(this);
		component.inject(this);
		initToolbar();
		mToolbar.post(new Runnable() {
			@Override public void run() {
				revealTransition();
			}
		});
	}

	@Override protected void onStart() {
		super.onStart();
		presenter.subscribe();
		presenter.registerDataBus();
	}

	@Override protected void onPause() {
		super.onPause();
		presenter.unsubscribe();
		presenter.unregisterDataBus();
	}

	private void initToolbar() {
		setSupportActionBar(mToolbar);
		if (getSupportActionBar() != null) {
			mToolbar.setTitle("");
			getSupportActionBar().setTitle("");
		}
	}

	@Override public void updateReposList(List<GithubRepo> reposList) {
		Log.d(TAG, "githubRepos: " + reposList.get(0).getName());
	}

	public MainActivityComponent getComponent() {
		return component;
	}
}
