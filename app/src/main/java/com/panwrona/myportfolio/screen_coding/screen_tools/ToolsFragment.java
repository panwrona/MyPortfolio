package com.panwrona.myportfolio.screen_coding.screen_tools;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpFragment;

public class ToolsFragment extends MvpFragment<ToolsView, ToolsPresenter> implements ToolsView {
    @Override
    protected ToolsPresenter createPresenter() {
        return new ToolsPresenterImpl();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_tools;
    }

    public static ToolsFragment newInstance() {
        return new ToolsFragment();
    }
}
