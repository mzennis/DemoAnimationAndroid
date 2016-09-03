package com.mzennis.demo;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.mzennis.demo.utilities.ApplicationHelper;
import com.mzennis.demo.utilities.LoggerHelper;
import com.mzennis.demo.utilities.TransitionHelper;
import com.mzennis.demo.utilities.VersionHelper;

/**
 * Created by mzennis on 9/4/16.
 */
public class BaseActivity extends AppCompatActivity {

	protected Toolbar toolbar;

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		setupToolbar();
	}

	@TargetApi(VersionHelper.API_21)
	private void init21() {
		statusBarColor(getWindow(), ContextCompat.getColor(this, R.color.colorPrimaryDark));

		final String name = ApplicationHelper.getName();
		final Bitmap icon = ApplicationHelper.getIcon();
		final int color = ContextCompat.getColor(this, R.color.colorPrimary);
		final ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(name, icon, color);
		setTaskDescription(taskDescription);
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
	}

	@TargetApi(VersionHelper.API_21)
	public boolean statusBarColor(@NonNull final Window window, @ColorInt final int color) {
		if (VersionHelper.sdk() >= VersionHelper.API_21) {
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(color);
			return true;
		}
		return false;
	}

	@Override
	public void setContentView(final View view) {
		super.setContentView(view);
		setupToolbar();
	}

	@Override
	public void setContentView(final View view, final ViewGroup.LayoutParams layoutParams) {
		super.setContentView(view, layoutParams);
		setupToolbar();
	}

	protected void setupToolbar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			LoggerHelper.verbose("Found a Toolbar");
			setSupportActionBar(toolbar);
			setTitle(ApplicationHelper.getName());
		}

		if (VersionHelper.sdk() >= VersionHelper.API_21) {
			init21();
		}
	}

	@Override
	public void setSupportActionBar(final Toolbar toolbar) {
		super.setSupportActionBar(toolbar);
		this.toolbar = toolbar;
	}

	// Lifecycle
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void setDisplayHome(final boolean showBackButton) {
		final ActionBar actionBar = getSupportActionBar();
		if (actionBar == null) {
			LoggerHelper.warning("ActionBar was NULL");
			return ;
		}
		actionBar.setDisplayHomeAsUpEnabled(showBackButton);
	}


	@SuppressWarnings("unchecked") void transitionTo(Intent i) {
		final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
		ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
		startActivity(i, transitionActivityOptions.toBundle());
	}
}
