package com.panwrona.myportfolio.screen_about_me.screen_education;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpFragment;
import com.panwrona.myportfolio.utils.Constants;
import com.panwrona.myportfolio.utils.IntentUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class EducationFragment extends MvpFragment<EducationFragmentView, EducationFragmentPresenter> implements EducationFragmentView, View.OnClickListener {

	@Bind(R.id.fragment_education_btn_bachelor_homepage)
	Button mBtnBachelorHomepage;
	@Bind(R.id.fragment_education_btn_master_homepage)
	Button mBtnMasterHomepage;
	
	@Override
	protected EducationFragmentPresenter createPresenter() {
		return new EducationFragmentPresenterImpl();
	}

	@Override
	public int getLayout() {
		return R.layout.fragment_education;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mBtnBachelorHomepage.setOnClickListener(this);
		mBtnMasterHomepage.setOnClickListener(this);
	}

	public static EducationFragment newInstance() {
		return new EducationFragment();
	}

	@Override
	public void onClick(View v) {
		IntentUtils.startBrowser(getActivity(), Constants.PWR_URL);
	}
}
