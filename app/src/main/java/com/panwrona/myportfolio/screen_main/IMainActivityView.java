package com.panwrona.myportfolio.screen_main;

public interface IMainActivityView {

    void initViews();

    class EmptyMainActivityView implements IMainActivityView {

        @Override
        public void initViews() {

        }
    }
}
