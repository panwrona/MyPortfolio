package com.panwrona.myportfolio.screen_news;

import android.os.Bundle;
import android.util.Log;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CollectionTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TwitterListTimeline;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class TwitterNewsActivity extends MvpActivity<TwitterNewsView, TwitterNewsPresenter> implements TwitterNewsView {

    @Override
    protected TwitterNewsPresenter createPresenter() {
        return new TwitterNewsPresenterImpl();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_twitter_news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CollectionTimeline collectionTimeline = new CollectionTimeline.Builder().id(712228138583990272L).build();
        collectionTimeline.next(680209108419657718L, new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                for(Tweet tweet : result.data.items) {
                    Log.d("Tweet", tweet.text);
                }
            }

            @Override
            public void failure(TwitterException e) {

            }
        });
    }
}
