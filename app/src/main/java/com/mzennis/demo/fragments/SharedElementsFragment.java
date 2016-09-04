package com.mzennis.demo.fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mzennis.demo.R;
import com.mzennis.demo.object.MainObject;

/**
 * Created by mzennis on 9/4/16.
 */
public class SharedElementsFragment extends Fragment {

	public static SharedElementsFragment newInstance(MainObject sample) {

		Bundle args = new Bundle();

		args.putSerializable(MainObject.SAMPLE_TAG, sample);
		SharedElementsFragment fragment = new SharedElementsFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shared_elements, container, false);
		final MainObject sample = (MainObject) getArguments().getSerializable(MainObject.SAMPLE_TAG);

		final ImageView sebastianImg = (ImageView) view.findViewById(R.id.img_profile);
		final ImageView rezekinImg = (ImageView) view.findViewById(R.id.img_profile1);

		ViewCompat.setTransitionName(view.findViewById(R.id.img_profile), "sebastian");
		ViewCompat.setTransitionName(view.findViewById(R.id.img_profile1), "rezeki");

		sebastianImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addNextFragment(sample, sebastianImg, false, "sebastian");
			}
		});

		rezekinImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addNextFragment(sample, rezekinImg, true, "rezeki");
			}
		});

		return view;
	}

	private void addNextFragment(MainObject sample, ImageView squareBlue, boolean overlap, String name) {
		SharedElementsFragment1 sharedElementsFragment1 = SharedElementsFragment1.newInstance(sample, name);

		Slide slideTransition = new Slide(Gravity.RIGHT);
		slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

		ChangeBounds changeBoundsTransition = new ChangeBounds();
		changeBoundsTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

		sharedElementsFragment1.setEnterTransition(slideTransition);
		sharedElementsFragment1.setAllowEnterTransitionOverlap(overlap);
		sharedElementsFragment1.setAllowReturnTransitionOverlap(overlap);
		sharedElementsFragment1.setSharedElementEnterTransition(changeBoundsTransition);

		getFragmentManager().beginTransaction()
				.replace(R.id.content, sharedElementsFragment1)
				.addToBackStack(null)
				.addSharedElement(squareBlue, name)
				.commit();
	}
}
