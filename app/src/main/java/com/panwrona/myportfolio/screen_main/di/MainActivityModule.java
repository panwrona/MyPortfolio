package com.panwrona.myportfolio.screen_main.di;

import android.app.Activity;
import com.panwrona.myportfolio.di.PerActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
	private final Activity activity;

	public MainActivityModule(Activity activity) {
		this.activity = activity;
	}

	@Provides
	@PerActivity
	Activity provideActivity() { return activity;}
}
