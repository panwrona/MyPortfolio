package com.panwrona.myportfolio.screen_main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.TextView;
import butterknife.OnClick;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.buses.DataBus;
import com.panwrona.myportfolio.customviews.pull_to_refresh.CircleRefreshLayout;
import com.panwrona.myportfolio.data.entities.GithubRepo;
import com.panwrona.myportfolio.data.event_entities.GithubRepoList;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.screen_coding.CodingActivity;
import com.panwrona.myportfolio.screen_main.di.MainActivityComponent;
import com.panwrona.myportfolio.screen_main.views.AboutMeViewHolder;
import com.panwrona.myportfolio.screen_main.views.MainRecyclerViewAdapter;
import com.panwrona.myportfolio.utils.RecyclerItemClickListener;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends MvpActivity<MainActivityView, MainActivityPresenter>
	implements MainActivityView, AppBarLayout.OnOffsetChangedListener {
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
	private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
	private static final int ALPHA_ANIMATIONS_DURATION = 200;

	@Inject DataBus dataBus;

	@Bind(R.id.toolbar) Toolbar mToolbar;
	@Bind(R.id.activity_main_ll_title_container) LinearLayout mLlTitleContainer;
	@Bind(R.id.activity_main_ll_action_items_container) LinearLayout mLlActionItemsContainer;
	@Bind(R.id.activity_main_ll_toolbar_items_container) LinearLayout mLlToolbarItemsContainer;
	@Bind(R.id.activity_main_tv_toolbar_title) TextView mTvToolbarTitle;
	@Bind(R.id.activity_main_app_bar_layout) AppBarLayout mAppBarLayout;
	@Bind(R.id.activity_main_rl_placeholder) RelativeLayout mRlPlaceholder;
	@Bind(R.id.activity_main_fl_title) FrameLayout mFlTitleContainer;
	@Bind(R.id.toolbar_rl) RelativeLayout mRlToolbarMainLayout;
	@Bind(R.id.activity_main_cl) CoordinatorLayout mClCoordinatorLayout;
	@Bind(R.id.activity_main_rv_recycler_view) RecyclerView mRecyclerView;

	private MainActivityComponent component;
	private boolean mIsTheTitleVisible = false;
	private boolean mIsTheTitleContainerVisible = true;

	@Override
	protected MainActivityPresenter createPresenter() {
		return new MainActivityPresenterImpl(this);
	}

	@Override
	protected Transition getEnterTransition() {
		return null;
	}

	@Override
	protected Transition getExitTransition() {
		return null;
	}

	@Override
	protected Transition getReturnTransition() {
		return null;
	}

	@Override
	protected Transition getReenterTransition() {
		return null;
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_main;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		component = MainActivityComponent.Initializer.init(this);
		component.inject(this);
		super.onCreate(savedInstanceState);
		initToolbar();
		dataBus.register(this);
		presenter.registerDataBus();
		presenter.subscribe();
		mToolbar.post(this::revealTransition);
		startAlphaAnimation(mTvToolbarTitle, 0, View.INVISIBLE);
		init();
	}

	private void init() {
		mRecyclerView.setAdapter(new MainRecyclerViewAdapter(this));
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mAppBarLayout.addOnOffsetChangedListener(this);
		mClCoordinatorLayout.setOnTouchListener((v, event) -> true);
		initParallaxValues();
	}

	@Override
	protected void onDestroy() {
		presenter.unsubscribe();
		dataBus.unregister(this);
		presenter.unregisterDataBus();
		super.onDestroy();
	}

	private void revealTransition() {
		int cx = mRlPlaceholder.getMeasuredWidth() / 2;
		int cy = mRlPlaceholder.getMeasuredHeight() / 2;

		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		int bigRadius = Math.max(displayMetrics.widthPixels, mRlPlaceholder.getHeight());
		Animator anim =
			ViewAnimationUtils.createCircularReveal(mRlPlaceholder, cx, cy, 0, bigRadius);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.setDuration(500);
		mRlPlaceholder.setVisibility(View.VISIBLE);
		anim.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				ObjectAnimator alphaAnimator =
					ObjectAnimator.ofFloat(mRlToolbarMainLayout, View.ALPHA, 0, 1);
				alphaAnimator.setDuration(500);
				alphaAnimator.setInterpolator(new DecelerateInterpolator());
				alphaAnimator.addListener(new Animator.AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animation) {
						mRlToolbarMainLayout.setVisibility(View.VISIBLE);
					}

					@Override
					public void onAnimationEnd(Animator animation) {
						populateData();
					}

					@Override
					public void onAnimationCancel(Animator animation) {

					}

					@Override
					public void onAnimationRepeat(Animator animation) {

					}
				});
				alphaAnimator.start();
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
		anim.start();
	}

	private void populateData() {
		presenter.getGithubRepos();
	}

	private void initToolbar() {
		setSupportActionBar(mToolbar);
		if (getSupportActionBar() != null) {
			mToolbar.setTitle("");
			getSupportActionBar().setTitle("");
		}
	}

	private void initParallaxValues() {

		CollapsingToolbarLayout.LayoutParams petDetailsLp =
			(CollapsingToolbarLayout.LayoutParams) mRlPlaceholder.getLayoutParams();

		CollapsingToolbarLayout.LayoutParams petBackgroundLp =
			(CollapsingToolbarLayout.LayoutParams) mFlTitleContainer.getLayoutParams();

		petDetailsLp.setParallaxMultiplier(0.9f);
		petBackgroundLp.setParallaxMultiplier(0.3f);

		mRlPlaceholder.setLayoutParams(petDetailsLp);
		mFlTitleContainer.setLayoutParams(petBackgroundLp);
	}

	@Override
	public void updateReposList(List<GithubRepo> reposList) {

	}

	@OnClick(R.id.portfolio_logo)
	public void onPortfolioLogoClicked() {
		CodingActivity.startActivity(this);
	}

	private void updateViews(GithubRepoList githubRepoList) {
		updateReposList(githubRepoList.getGithubRepos());
	}

	public MainActivityComponent getComponent() {
		return component;
	}

	@Override
	public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
		int maxScroll = appBarLayout.getTotalScrollRange();
		float percentage = (float) Math.abs(offset) / (float) maxScroll;
		handleAlphaOnTitle(percentage);
		handleToolbarTitleVisibility(percentage);
	}

	private void handleToolbarTitleVisibility(float percentage) {
		if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

			if (!mIsTheTitleVisible) {
				startAlphaAnimation(mTvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
				startAlphaAnimation(mLlToolbarItemsContainer, ALPHA_ANIMATIONS_DURATION,
					View.VISIBLE);
				mIsTheTitleVisible = true;
			}
		} else {

			if (mIsTheTitleVisible) {
				startAlphaAnimation(mTvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
				startAlphaAnimation(mLlToolbarItemsContainer, ALPHA_ANIMATIONS_DURATION,
					View.INVISIBLE);

				mIsTheTitleVisible = false;
			}
		}
	}

	private void handleAlphaOnTitle(float percentage) {
		if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
			if (mIsTheTitleContainerVisible) {
				startAlphaAnimation(mLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
				startAlphaAnimation(mLlActionItemsContainer, ALPHA_ANIMATIONS_DURATION,
					View.INVISIBLE);
				mIsTheTitleContainerVisible = false;
			}
		} else {
			if (!mIsTheTitleContainerVisible) {
				startAlphaAnimation(mLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
				startAlphaAnimation(mLlActionItemsContainer, ALPHA_ANIMATIONS_DURATION,
					View.VISIBLE);
				mIsTheTitleContainerVisible = true;
			}
		}
	}

	public static void startAlphaAnimation(View v, long duration, int visibility) {
		AlphaAnimation alphaAnimation =
			(visibility == View.VISIBLE) ? new AlphaAnimation(0f, 1f) : new AlphaAnimation(1f, 0f);
		alphaAnimation.setDuration(duration);
		alphaAnimation.setFillAfter(true);
		v.startAnimation(alphaAnimation);
	}
}
