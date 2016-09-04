package com.mzennis.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import com.mzennis.demo.databinding.ActivitySharedElementsBinding;
import com.mzennis.demo.fragments.SharedElementsFragment;
import com.mzennis.demo.object.MainObject;

/**
 * Created by mzennis on 9/4/16.
 */
public class SharedElementActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainObject sample = (MainObject) getIntent().getExtras().getSerializable(MainObject.SAMPLE_TAG);
		bindData(sample);
		setupWindowAnimations();
	}

	private void bindData(MainObject sample) {
		ActivitySharedElementsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_shared_elements);
		binding.setSharedElements(sample);
		setupLayout(sample);
		setTitle("Shared Elements");
	}

	private void setupWindowAnimations() {
		// We are not interested in defining a new Enter Transition. Instead we change default transition duration
		getWindow().getEnterTransition().setDuration(getResources().getInteger(R.integer.anim_duration_long));
	}

	private void setupLayout(MainObject sample) {
		// Transition for fragment1
		Slide slideTransition = new Slide(Gravity.LEFT);
		slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
		// Create fragment and define some of it transitions
		SharedElementsFragment sharedElementsFragment = SharedElementsFragment.newInstance(sample);
		sharedElementsFragment.setReenterTransition(slideTransition);
		sharedElementsFragment.setExitTransition(slideTransition);
		sharedElementsFragment.setSharedElementEnterTransition(new ChangeBounds());

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, sharedElementsFragment)
				.commit();


		findViewById(R.id.exit_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finishAfterTransition();
			}
		});
	}
}
