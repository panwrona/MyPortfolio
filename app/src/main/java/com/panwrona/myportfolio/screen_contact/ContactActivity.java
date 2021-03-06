package com.panwrona.myportfolio.screen_contact;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.OnClick;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.utils.Constants;
import com.panwrona.myportfolio.utils.GUIUtils;
import com.panwrona.myportfolio.utils.IntentUtils;
import com.panwrona.myportfolio.utils.OnRevealAnimationListener;

public class ContactActivity extends MvpActivity<ContactActivityView, ContactActivityPresenter>
	implements ContactActivityView {

	@Bind(R.id.activity_contact_rl_container) RelativeLayout mRlContainer;
	@Bind(R.id.activity_contact_fab) FloatingActionButton mFab;
	@Bind(R.id.activity_contact_ll_container) LinearLayout mLlContainer;
	@Bind(R.id.activity_contact_iv_close) ImageView mIvClose;

	@Override
	protected ContactActivityPresenter createPresenter() {
		return new ContactActivityPresenterImpl();
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_contact;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			setupEnterAnimation();
			setupExitAnimation();
		} else {
			initViews();
		}

	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void setupEnterAnimation() {
		Transition transition = TransitionInflater.from(this)
			.inflateTransition(R.transition.change_bound_with_arc);
		getWindow().setSharedElementEnterTransition(transition);
		transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow(mRlContainer);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
	}

	private void animateRevealShow(final View viewRoot) {
		int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
		int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
		GUIUtils.animateRevealShow(this, mRlContainer, mFab.getWidth() / 2, R.color.accent_color,
                cx, cy, new OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {

                    }

                    @Override
                    public void onRevealShow() {
                        initViews();
                    }
                });
	}

	private void initViews() {
		new Handler(Looper.getMainLooper()).post(() -> {
            Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
            animation.setDuration(300);
            mLlContainer.startAnimation(animation);
            mIvClose.startAnimation(animation);
            mLlContainer.setVisibility(View.VISIBLE);
            mIvClose.setVisibility(View.VISIBLE);
        });
	}

	@Override
	public void onBackPressed() {
		GUIUtils.animateRevealHide(this, mRlContainer, R.color.accent_color, mFab.getWidth() / 2,
				new OnRevealAnimationListener() {
					@Override
					public void onRevealHide() {
						backPressed();
					}

					@Override
					public void onRevealShow() {

					}
				});
	}

	@OnClick(R.id.activity_contact_iv_close)
	public void onIvCloseClicked() {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			onBackPressed();
		} else {
			backPressed();
		}
	}

	@OnClick(R.id.activity_contact_iv_twitter)
	public void onTwitterClicked() {
		IntentUtils.startBrowser(this, Constants.TWITTER_URL);
	}

	@OnClick(R.id.activity_contact_iv_linkedin)
	public void onLinkedInClicked() {
		IntentUtils.startBrowser(this, Constants.LINKEDIN_URL);
	}

    @OnClick(R.id.activity_contact_iv_github)
    public void onGithubClicked() {
        IntentUtils.startBrowser(this, Constants.GITHUB_URL);
    }

	@OnClick(R.id.activity_contact_iv_email)
	public void onEmailClicked() {
		startEmail(Constants.EMAIL);
	}

	private void startEmail(String email) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
            Snackbar.make(mRlContainer, R.string.error_no_email_client, Snackbar.LENGTH_LONG).show();
		}
	}

	private void backPressed() {
		super.onBackPressed();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void setupExitAnimation() {
		Fade fade = new Fade();
		getWindow().setReturnTransition(fade);
		fade.setDuration(getResources().getInteger(R.integer.animation_duration));
	}
}

