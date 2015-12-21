package com.panwrona.myportfolio.screen_coding.screen_tools;

import com.panwrona.myportfolio.data.database.entities.Tool;
import com.panwrona.myportfolio.mvp.MvpBasePresenter;
import java.util.ArrayList;
import java.util.List;

class ToolsPresenterImpl extends MvpBasePresenter<ToolsView> implements ToolsPresenter {

	private List<Tool> mTools = new ArrayList<Tool>();

	@Override
	public void loadToolsToRecyclerView() {
		getView().showProgressBar();
		for(int i = 0; i < 6; i++) {
			mTools.add(new Tool());
		}
		if (mTools.size() > 0) {
			getView().hideProgressBar();
			getView().loadTools(mTools);
		} else {
			getView().hideProgressBar();
			getView().showToolsDatabaseError();
		}
	}
}
