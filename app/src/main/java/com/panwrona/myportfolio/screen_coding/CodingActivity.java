package com.panwrona.myportfolio.screen_coding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.transition.Transition;
import butterknife.Bind;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.customviews.skills_level_view.SkillsLevelView;
import com.panwrona.myportfolio.customviews.skills_level_view.entities.Skill;
import com.panwrona.myportfolio.mvp.MvpActivity;
import java.util.ArrayList;
import java.util.List;

public class CodingActivity extends MvpActivity<CodingView, CodingPresenter> implements CodingView {

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
		return R.layout.activity_coding;
	}

	public static void startActivity(Context ctx) {
		Intent intent = new Intent(ctx, CodingActivity.class);
		ctx.startActivity(intent);
	}
}
