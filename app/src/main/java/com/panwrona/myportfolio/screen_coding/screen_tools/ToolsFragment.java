package com.panwrona.myportfolio.screen_coding.screen_tools;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.Bind;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.data.database.entities.Tool;
import com.panwrona.myportfolio.mvp.MvpFragment;
import java.util.List;

public class ToolsFragment extends MvpFragment<ToolsView, ToolsPresenter> implements ToolsView {

	@Bind(R.id.fragment_tools_recyclerview) RecyclerView mRecyclerView;
	@Bind(R.id.fragment_tools_progressbar) ProgressBar mProgressBar;

	private ToolsRecyclewViewAdapter mRvAdapter;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		mRvAdapter = new ToolsRecyclewViewAdapter();
		mRecyclerView.setAdapter(mRvAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		presenter.loadToolsToRecyclerView();
	}

	@Override
	public void showProgressBar() {
		new Handler(Looper.getMainLooper()).post(() -> mProgressBar.setVisibility(View.VISIBLE));
	}

	@Override
	public void hideProgressBar() {
		new Handler(Looper.getMainLooper()).post(() -> mProgressBar.setVisibility(View.GONE));
	}

	@Override
	public void showToolsDatabaseError() {
		Toast.makeText(getActivity(), getString(R.string.tools_database_error), Toast.LENGTH_LONG).show();
	}

	@Override
	public void loadTools(List<Tool> tools) {
		mRvAdapter.setTools(tools);
		mRvAdapter.notifyDataSetChanged();
		mRecyclerView.setVisibility(View.VISIBLE);
	}
}
