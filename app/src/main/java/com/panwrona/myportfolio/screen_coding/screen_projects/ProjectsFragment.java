package com.panwrona.myportfolio.screen_coding.screen_projects;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpFragment;

public class ProjectsFragment extends MvpFragment<ProjectsView, ProjectsPresenter> implements ProjectsView{
    @Override
    protected ProjectsPresenter createPresenter() {
        return new ProjectsPresenterImpl();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_projects;
    }

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }
}
