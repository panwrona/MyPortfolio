package com.panwrona.myportfolio.screen_coding.screen_tools;

import com.panwrona.myportfolio.mvp.MvpPresenter;

public interface ToolsPresenter extends MvpPresenter<ToolsView> {
	void loadToolsToRecyclerView();
}
