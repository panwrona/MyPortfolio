package com.panwrona.myportfolio.screen_passion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.panwrona.myportfolio.screen_passion.screen_boards.BoardsFragment;
import com.panwrona.myportfolio.screen_passion.screen_music.MusicFragment;
import com.panwrona.myportfolio.screen_passion.screen_travel.TravelFragment;

public class PassionViewPagerAdapter extends FragmentPagerAdapter {
	private static final int BOARDS_POSITION = 0;
	private static final int TRAVEL_POSITION = 1;
	private static final int MUSIC_POSITION = 2;

	public PassionViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case BOARDS_POSITION:
				return BoardsFragment.newInstance();
			case TRAVEL_POSITION:
				return TravelFragment.newInstance();
			case MUSIC_POSITION:
				return MusicFragment.newInstance();
			default:
				return BoardsFragment.newInstance();
		}
	}

	@Override
	public int getCount() {
		return 3;
	}
}
