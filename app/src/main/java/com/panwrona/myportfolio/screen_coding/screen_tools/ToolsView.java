package com.panwrona.myportfolio.screen_coding.screen_tools;

import com.panwrona.myportfolio.data.database.entities.Tool;
import com.panwrona.myportfolio.mvp.MvpView;
import java.util.List;

interface ToolsView extends MvpView {
	void showProgressBar();
	void hideProgressBar();
	void showToolsDatabaseError();
	void loadTools(List<Tool> mTools);
}
