package com.panwrona.myportfolio.actions.github_actions;

import android.util.Log;

import com.panwrona.myportfolio.actions.DataBundle;
import com.panwrona.myportfolio.buses.ActionBus;
import com.panwrona.myportfolio.data.api.ApiManager;
import com.panwrona.myportfolio.data.entities.GithubRepo;
import com.panwrona.myportfolio.data.entities.GithubRepoResponse;
import com.panwrona.myportfolio.data.entities.OwnerResponse;
import com.panwrona.myportfolio.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

public class GithubActionsCreator {
	private static final String TAG = GithubActionsCreator.class.getSimpleName();
	private ActionBus actionBus;
	@Inject ApiManager apiManager;
	CompositeSubscription compositeSubscription = new CompositeSubscription();

	@Inject
	public GithubActionsCreator(ActionBus actionBus) {
		this.actionBus = actionBus;
	}

	public final void downloadRepos() {
		compositeSubscription.add(
			apiManager.getGithubRepos().subscribe(new Subscriber<List<GithubRepo>>() {
				@Override public void onCompleted() {

				}

				@Override public void onError(Throwable e) {
					Log.d(TAG, "onError");
				}

				@Override public void onNext(List<GithubRepo> githubRepos) {
					DataBundle<GithubAction.GithubDataKey> bundle =
						new DataBundle<GithubAction.GithubDataKey>();
					bundle.put(GithubAction.GithubDataKey.DESCRIPTION,
						githubRepos);
					actionBus.post(
						new GithubAction(GithubAction.GithubActionType.DOWNLOAD_REPOS, bundle));
				}
			}));
	}

	public final void getOwner() {
		compositeSubscription.add(apiManager.getOwner().subscribe(new Subscriber<OwnerResponse>() {
			@Override public void onCompleted() {

			}

			@Override public void onError(Throwable e) {
				Log.d(TAG, "onError");
			}

			@Override public void onNext(OwnerResponse ownerResponse) {
				DataBundle<GithubAction.GithubDataKey> bundle =
					new DataBundle<GithubAction.GithubDataKey>();
				bundle.put(GithubAction.GithubDataKey.DESCRIPTION, ownerResponse.getOwner());
				actionBus.post(new GithubAction(GithubAction.GithubActionType.GET_OWNER, bundle));
			}
		}));
	}

	public void subscribe() {
		RxUtils.getNewCompositeSubIfUnsubscribed(compositeSubscription);
	}

	public void unSubscribe() {
		RxUtils.unsubscribeIfNotNull(compositeSubscription);
	}
}
