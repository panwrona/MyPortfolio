package com.panwrona.myportfolio.screen_coding;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.View;
import butterknife.Bind;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.customviews.skills_level_view.SkillsLevelView;
import com.panwrona.myportfolio.customviews.skills_level_view.entities.Skill;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.ui.lollipop_animations.transitions.RevealTransition;
import com.panwrona.myportfolio.utils.Naming;
import java.util.ArrayList;
import java.util.List;

public class CodingActivity extends MvpActivity<CodingView, CodingPresenter> implements CodingView {
	public static final String EXTRA_EPICENTER = Naming.EXTRA + "EPICENTER";
	@Bind(R.id.skills_level_view)
	SkillsLevelView skillsLevelView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<Skill> skillList = new ArrayList<>();
		skillList.add(new Skill.Builder().level(4).name("Git").build());
		skillList.add(new Skill.Builder().level(2).name("Design").build());
		skillList.add(new Skill.Builder().level(3).name("Java").build());
		skillList.add(new Skill.Builder().level(3).name("Android").build());
		skillsLevelView.setSkillsList(skillList);
	}

	@Override protected CodingPresenter createPresenter() {
		return new CodingPresenterImp();
	}

	@Override protected Transition getEnterTransition() {
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		int bigRadius = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
		Point epicenter = getIntent().getParcelableExtra(EXTRA_EPICENTER);
		RevealTransition reveal = new RevealTransition(epicenter, 0, bigRadius, 500);
		reveal.addTarget(R.id.activity_coding_ll_main_layout);
		reveal.addTarget(android.R.id.statusBarBackground);
		return reveal;
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
		return R.layout.activity_coding;
	}

	public static void startActivity(Activity activity, View view, String transitionName, Context ctx) {
		Intent intent = new Intent(ctx, CodingActivity.class);
		ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity,view, transitionName);
		ctx.startActivity(intent, options.toBundle());
	}

	public static void startActivity(Context context) {
		Intent intent = new Intent(context.getApplicationContext(), CodingActivity.class);
		context.startActivity(intent);
	}
}
