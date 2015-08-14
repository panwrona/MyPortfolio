package com.panwrona.myportfolio.screen_coding;

import android.os.Bundle;
import android.transition.Transition;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.customviews.skills_level_view.SkillsLevelView;
import com.panwrona.myportfolio.customviews.skills_level_view.entities.Skill;
import com.panwrona.myportfolio.mvp.MvpActivity;
import java.util.ArrayList;
import java.util.List;

public class CodingActivity extends MvpActivity<CodingView, CodingPresenter> implements CodingView {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SkillsLevelView skillsLevelView = (SkillsLevelView)findViewById(R.id.skills_level_view);
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
}
