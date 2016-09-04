package com.mzennis.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;

import com.mzennis.demo.databinding.ActivityTransitionExplodeBinding;
import com.mzennis.demo.object.MainObject;

/**
 * Created by mzennis on 9/4/16.
 */
public class TransactionSlideActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bindData();
		setupWindowAnimations();
		setupLayout();
	}

	private void bindData() {
		ActivityTransitionExplodeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_transition_explode);
		MainObject sample = (MainObject) getIntent().getExtras().getSerializable(MainObject.SAMPLE_TAG);
		binding.setTransitionExplode(sample);
	}

	private void setupWindowAnimations() {
		Transition transition;

		transition = buildEnterTransition();

		//transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom);

		getWindow().setEnterTransition(transition);
	}

	private void setupLayout() {
		findViewById(R.id.exit_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finishAfterTransition();
			}
		});
	}

	private Transition buildEnterTransition() {
		Slide enterTransition = new Slide();
		enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
		return enterTransition;
	}
}