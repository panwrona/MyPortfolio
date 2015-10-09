package com.panwrona.myportfolio.screen_email;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpActivity;

public class EmailActivity extends MvpActivity<EmailActivityView, EmailActivityPresenter> implements EmailActivityView{
	@Override
	protected EmailActivityPresenter createPresenter() {
		return new EmailActivityPresenterImpl();
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_email;
	}
}
