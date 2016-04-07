package com.panwrona.myportfolio.screen_passion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import butterknife.Bind;
import butterknife.OnClick;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.customviews.ZoomOutPageTransformer;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.viewpagerindicator.CirclePageIndicator;

public class PassionActivity extends MvpActivity<PassionActivityView, PassionActivityPresenter> implements PassionActivityView {

	@Bind(R.id.activity_passion_vp_container) ViewPager mVpContainer;
	@Bind(R.id.activity_passion_circle_pager_indicator) CirclePageIndicator mCirclePagerIndicator;

	@Override
	protected PassionActivityPresenter createPresenter() {
		return new PassionActivityPresenterImpl();
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_passion;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mVpContainer.setAdapter(new PassionViewPagerAdapter(getSupportFragmentManager()));
		mVpContainer.setPageTransformer(true, new ZoomOutPageTransformer());
		mCirclePagerIndicator.setViewPager(mVpContainer);
	}

	@Override
	public void finish() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		super.finish();
	}

	@OnClick(R.id.activity_passion_vw_close)
	public void onCloseClicked() {
		super.onBackPressed();
	}
}
