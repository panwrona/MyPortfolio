package com.panwrona.myportfolio.screen_coding.screen_tools;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.customviews.skills_level_view.SkillsLevelView;
import com.panwrona.myportfolio.customviews.skills_level_view.entities.Skill;
import com.panwrona.myportfolio.data.database.entities.Tool;
import java.util.ArrayList;
import java.util.List;

public class ToolsViewHolder extends RecyclerView.ViewHolder {

	@Bind(R.id.skills_level_view) SkillsLevelView mSkillLevelView;
	@Bind(R.id.tools_tv_name) TextView mTvTitle;
	@Bind(R.id.tools_tv_description) TextView mTvDescription;

	public ToolsViewHolder(View itemView, OnToolLinkClicked listener) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	public void updateViews(Tool tool) {
		Skill skill = new Skill.Builder().level(3).name("      ").build();
		List<Skill> skills = new ArrayList<>();
		skills.add(skill);
		mSkillLevelView.setSkillsList(skills);
		mTvDescription.setText("I am familiar with raw SQL queries and some android libraries. Most of all Ive been using ORMLite, but in this project I am testing ActiveAndroid library.");
		mTvTitle.setText("Databases");
	}
}
