package com.panwrona.myportfolio.screen_main.di;

import com.panwrona.myportfolio.app.MyPortfolioApp;
import com.panwrona.myportfolio.app.MyPortfolioAppComponent;
import com.panwrona.myportfolio.di.PerActivity;
import com.panwrona.myportfolio.screen_main.MainActivity;
import com.panwrona.myportfolio.screen_main.MainActivityPresenterImpl;
import dagger.Component;

@PerActivity
@Component(dependencies = MyPortfolioAppComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {

	public final static class Initializer {
		public static MainActivityComponent init(MainActivity activity) {
			return DaggerMainActivityComponent.builder().myPortfolioAppComponent(((MyPortfolioApp)activity.getApplication()).component())
				.mainActivityModule(new MainActivityModule(activity))
				.build();
		}
	}

	void inject(MainActivity mainActivity);
	void inject(MainActivityPresenterImpl mainActivityPresenter);
}
