package com.panwrona.myportfolio.screen_coding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import butterknife.Bind;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.customviews.skills_level_view.SkillsLevelView;
import com.panwrona.myportfolio.customviews.skills_level_view.entities.Skill;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.utils.GUIUtils;
import com.panwrona.myportfolio.utils.Naming;
import java.util.ArrayList;
import java.util.List;

public class CodingActivity extends MvpActivity<CodingView, CodingPresenter>
	implements CodingView, ViewTreeObserver.OnGlobalLayoutListener {
	public static final String EXTRA_EPICENTER = Naming.EXTRA + "EPICENTER";
	@Bind(R.id.skills_level_view) SkillsLevelView skillsLevelView;
	@Bind(R.id.activity_coding_toolbar) Toolbar mToolbar;
	@Bind(R.id.activity_coding_appbar) AppBarLayout mAppBar;
	@Bind(R.id.activity_coding_cl_main_layout) CoordinatorLayout mClMainLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<Skill> skillList = new ArrayList<>();
		skillList.add(new Skill.Builder().level(4).name("Git").build());
		skillList.add(new Skill.Builder().level(2).name("Design").build());
		skillList.add(new Skill.Builder().level(3).name("Java").build());
		skillList.add(new Skill.Builder().level(3).name("Android").build());
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

	private void setupReturnTransition() {
		GUIUtils.startReturnTransitionSlideUp(this, CodingActivity.this::superOnBackPressed,
			mToolbar, mAppBar);
		//GUIUtils.startReturnTransitionSlideDown(this, CodingActivity.this::superOnBackPressed,
		//	mCvContainer);
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
		mClMainLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@Override
	public void onGlobalLayout() {
		new Handler(Looper.getMainLooper()).post(() -> setupEnterTransition());
	}

	private void setupEnterTransition() {
		//GUIUtils.startEnterTransitionSlideUp(this, mCvContainer);
		GUIUtils.startEnterTransitionSlideDown(this, mAppBar, mToolbar);
		mClMainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
	}
}
