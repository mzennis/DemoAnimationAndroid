package com.mzennis.demo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;

import com.mzennis.demo.adapter.MainAdapter;
import com.mzennis.demo.object.MainObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

	private List<MainObject> samples;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setupWindowAnimations();
		setupSamples();
		setupLayout();
	}

	private void setupWindowAnimations() {
		// Re-enter transition is executed when returning to this activity
		Slide slideTransition = new Slide();
		slideTransition.setSlideEdge(Gravity.LEFT);
		slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
		getWindow().setReenterTransition(slideTransition);
		getWindow().setExitTransition(slideTransition);
	}

	private void setupSamples() {
		samples = Arrays.asList(
				new MainObject("Activity Transition", "Animating between different UI states in an application"),
				new MainObject("Shared Elements", "A shared element transition determines how shared element views are animated from one Activity/Fragment to another during a scene transition"),
				new MainObject("Circular Reveal Animation", "Reveal animations provide users visual continuity when you show or hide a group of UI elements")
		);
	}

	private void setupLayout() {
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_main);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		MainAdapter samplesRecyclerAdapter = new MainAdapter(this, samples);
		recyclerView.setAdapter(samplesRecyclerAdapter);
	}
}
