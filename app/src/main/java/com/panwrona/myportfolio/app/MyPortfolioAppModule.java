package com.panwrona.myportfolio.app;

import android.app.Application;
import com.panwrona.myportfolio.buses.ActionBus;
import com.panwrona.myportfolio.buses.DataBus;
import com.panwrona.myportfolio.buses.RxBus;
import com.panwrona.myportfolio.data.api.ApiManager;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class MyPortfolioAppModule {

	private final MyPortfolioApp app;

	public MyPortfolioAppModule(MyPortfolioApp app) {
		this.app = app;
	}

	@Provides @Singleton Application application() {
		return app;
	}

	@Provides @Singleton ApiManager provideApiManager() {
		return new ApiManager();
	}

	@Provides
	@Singleton DataBus provideDataBus() {
		return new DataBus();
	}

	@Provides
	@Singleton ActionBus provideActionBus() {
		return new ActionBus();
	}
}