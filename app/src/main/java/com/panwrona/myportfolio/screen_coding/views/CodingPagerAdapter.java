package com.panwrona.myportfolio.screen_coding.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.panwrona.myportfolio.screen_coding.screen_github.GithubFragment;
import com.panwrona.myportfolio.screen_coding.screen_projects.ProjectsFragment;
import com.panwrona.myportfolio.screen_coding.screen_tools.ToolsFragment;

public final class CodingPagerAdapter extends FragmentPagerAdapter {
    private static final int GITHUB_POSITION = 0;
    private static final int PROJECTS_POSITION = 2;
    private static final int TOOLS_POSITION = 1;
    private static final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Github", "Projects", "Tools"};


    public CodingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case GITHUB_POSITION:
                return GithubFragment.newInstance();
            case PROJECTS_POSITION:
                return ProjectsFragment.newInstance();
            case TOOLS_POSITION:
                return ToolsFragment.newInstance();
            default:
                return GithubFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
