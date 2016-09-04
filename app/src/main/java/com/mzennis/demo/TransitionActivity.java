package com.mzennis.demo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.View;

import com.mzennis.demo.databinding.ActivityTransitionBinding;
import com.mzennis.demo.object.MainObject;

/**
 * Created by mzennis on 9/4/16.
 */
public class TransitionActivity extends BaseActivity {

	private MainObject sample;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bindData();
		setDisplayHome(true);
		setupWindowAnimations();
		setupLayout();
	}

	private void bindData() {
		ActivityTransitionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_transition);
		sample = (MainObject) getIntent().getExtras().getSerializable(MainObject.SAMPLE_TAG);
		binding.setTransitionSample(sample);
		setTitle("Activity Transition");
	}

	private void setupLayout() {

		findViewById(R.id.show_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(TransitionActivity.this, TransactionSlideActivity.class);
				i.putExtra(MainObject.SAMPLE_TAG, sample);
				transitionTo(i);
			}
		});

		findViewById(R.id.exit_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Visibility returnTransition = buildReturnTransition();
				getWindow().setReturnTransition(returnTransition);

				finishAfterTransition();
			}
		});
	}

	private void setupWindowAnimations() {
		Visibility enterTransition = buildEnterTransition();
		getWindow().setEnterTransition(enterTransition);
	}

	private Visibility buildEnterTransition() {
		Fade enterTransition = new Fade();
		enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
		return enterTransition;
	}

	private Visibility buildReturnTransition() {
		Visibility enterTransition = new Slide();
		enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
		return enterTransition;
	}
}
