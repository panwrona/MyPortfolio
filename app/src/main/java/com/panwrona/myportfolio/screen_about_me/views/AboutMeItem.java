package com.panwrona.myportfolio.screen_about_me.views;

import android.support.annotation.DrawableRes;

public class AboutMeItem {

	@DrawableRes private int drawable;
	private String title;

	public AboutMeItem(@DrawableRes int drawable, String title) {
		this.drawable = drawable;
		this.title = title;
	}

	public int getDrawable() {
		return drawable;
	}

	public void setDrawable(@DrawableRes int drawable) {
		this.drawable = drawable;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
