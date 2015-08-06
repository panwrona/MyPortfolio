package com.panwrona.myportfolio.customviews.skills_level_view.entities;

import java.util.List;

public class Skills {
	private List<Skill> skillList;
	private int scale;

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public List<Skill> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<Skill> skillList) {
		this.skillList = skillList;
	}

}
