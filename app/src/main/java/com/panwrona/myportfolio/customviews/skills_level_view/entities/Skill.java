package com.panwrona.myportfolio.customviews.skills_level_view.entities;

public class Skill {

	private final int level;
	private final String name;

	public Skill(Builder builder) {
		this.level = builder.level;
		this.name = builder.name;
	}

	public int getLevel() {
		return this.level;
	}

	public String getName() {
		return name;
	}

	public static class Builder {
		  private int level;
		  private String name;

		public Builder level(int level) {
			  this.level = level;
			  return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Skill build() {
			return new Skill(this);
		}
	}

}
