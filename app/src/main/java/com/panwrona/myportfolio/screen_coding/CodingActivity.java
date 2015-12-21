package com.panwrona.myportfolio.screen_coding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import butterknife.Bind;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.customviews.skills_level_view.SkillsLevelView;
import com.panwrona.myportfolio.customviews.skills_level_view.entities.Skill;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.screen_coding.views.CodingPagerAdapter;
import com.panwrona.myportfolio.utils.GUIUtils;
import com.panwrona.myportfolio.utils.Naming;
import java.util.ArrayList;
import java.util.List;

public class CodingActivity extends MvpActivity<CodingView, CodingPresenter>
	implements CodingView, ViewTreeObserver.OnGlobalLayoutListener,
	AppBarLayout.OnOffsetChangedListener {
	private static final float PERCENTAGE_TO_FADE_TITLE_DETAILS = 0.15f;
	private static final long COLLAPSING_ANIMATIONS_DURATION = 300;

	@Bind(R.id.skills_level_view) SkillsLevelView skillsLevelView;
	@Bind(R.id.activity_coding_toolbar) Toolbar mToolbar;
	@Bind(R.id.activity_coding_appbar) AppBarLayout mAppBar;
	@Bind(R.id.activity_coding_cl_main_layout) CoordinatorLayout mClMainLayout;
	@Bind(R.id.activity_coding_vp_container) ViewPager mVpContainer;
	@Bind(R.id.activity_coding_tl_container) TabLayout mTlContainer;
	@Bind(R.id.activity_coding_ctl) CollapsingToolbarLayout mCtlContainer;
	//@Bind(R.id.activity_coding_ll_container) LinearLayout mLlContainer;
	//@Bind(R.id.activity_coding_nsv_container) NestedScrollView mNsvContainer;
	//@Bind(R.id.activity_coding_cv_container) CardView mCvContainer;
	private boolean mIsTheSkillsViewVisible = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<Skill> skillList = new ArrayList<>();
		skillList.add(new Skill.Builder().level(4).name("Git").build());
		skillList.add(new Skill.Builder().level(2).name("Design").build());
		skillList.add(new Skill.Builder().level(3).name("Java").build());
		skillList.add(new Skill.Builder().level(2).name("Android").build());
		skillsLevelView.setSkillsList(skillList);
		initViews();
	}

	@Override
	protected CodingPresenter createPresenter() {
		return new CodingPresenterImp();
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_coding;
	}

	@Override
	public void onBackPressed() {
		setupReturnTransition();
	}

	@Override
	public void finish() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		super.finish();
	}

	private void superOnBackPressed() {
		super.onBackPressed();
	}

	@SuppressWarnings("ConstantConditions")
	private void initViews() {
		setSupportActionBar(mToolbar);
		getSupportActionBar().setTitle("");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(v -> onBackPressed());
		CodingPagerAdapter pagerAdapter = new CodingPagerAdapter(getSupportFragmentManager());
		mVpContainer.setAdapter(pagerAdapter);
		mTlContainer.setupWithViewPager(mVpContainer);
		mAppBar.addOnOffsetChangedListener(this);
		mClMainLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@Override
	public void onGlobalLayout() {
		new Handler(Looper.getMainLooper()).post(this::setupEnterTransition);
	}

	private void setupEnterTransition() {
		GUIUtils.startEnterTransitionSlideUp(this, mVpContainer);
		GUIUtils.startEnterTransitionSlideDown(this, mAppBar);
		mClMainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
	}

	private void setupReturnTransition() {
		GUIUtils.startReturnTransitionSlideUp(this, CodingActivity.this::superOnBackPressed,
			mAppBar);
		GUIUtils.startReturnTransitionSlideDown(this, CodingActivity.this::superOnBackPressed,
			mVpContainer);
	}

	@Override
	public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
		int maxScroll = mAppBar.getTotalScrollRange();
		float percentage = (float) Math.abs(i) / (float) maxScroll;
		handleSkillsViewFade(percentage);
	}

	private void handleSkillsViewFade(float percentage) {
		if (percentage >= PERCENTAGE_TO_FADE_TITLE_DETAILS) {
			if (mIsTheSkillsViewVisible) {
				startFadeOutAnimation(skillsLevelView);
				mIsTheSkillsViewVisible = false;
			}
		} else {
			if (!mIsTheSkillsViewVisible) {
				startFadeInAnimation(skillsLevelView);
				mIsTheSkillsViewVisible = true;
			}
		}
	}

	private void startFadeInAnimation(SkillsLevelView skillsLevelView) {
		Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
		animation.setDuration(COLLAPSING_ANIMATIONS_DURATION);
		skillsLevelView.startAnimation(animation);
		skillsLevelView.setVisibility(View.VISIBLE);
	}

	private void startFadeOutAnimation(SkillsLevelView skillsLevelView) {
		Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
		animation.setDuration(COLLAPSING_ANIMATIONS_DURATION);
		skillsLevelView.startAnimation(animation);
		skillsLevelView.setVisibility(View.INVISIBLE);
	}
}
