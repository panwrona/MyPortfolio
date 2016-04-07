package com.panwrona.myportfolio.data.twitter;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;

public class TwitterManager extends TwitterApiClient {

    public TwitterManager(Session session) {
        super(session);
    }
}
