package com.panwrona.myportfolio.app;

import android.app.Application;
import com.panwrona.myportfolio.buses.DataBus;
import com.panwrona.myportfolio.data.api.ApiManager;

import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {MyPortfolioAppModule.class})
public interface MyPortfolioAppComponent {

	public final static class Initializer {
		public static MyPortfolioAppComponent init(MyPortfolioApp app) {
			return DaggerMyPortfolioAppComponent.builder()
				.myPortfolioAppModule(new MyPortfolioAppModule(app))
				.build();
		}
	}

	void inject(MyPortfolioApp app);

	Application application();
	DataBus dataBus();
	ApiManager apiManager();
}
