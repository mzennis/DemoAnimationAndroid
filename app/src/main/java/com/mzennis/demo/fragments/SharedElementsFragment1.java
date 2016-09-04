package com.mzennis.demo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mzennis.demo.R;
import com.mzennis.demo.object.MainObject;

/**
 * Created by mzennis on 9/4/16.
 */
public class SharedElementsFragment1 extends Fragment {

	public static final String TRANS_NAME = "TRANS_NAME";

	public static SharedElementsFragment1 newInstance(MainObject sample, String transName) {
		Bundle args = new Bundle();
		args.putSerializable(MainObject.SAMPLE_TAG, sample);
		args.putString(TRANS_NAME, transName);
		SharedElementsFragment1 fragment = new SharedElementsFragment1();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shared_element_1, container, false);
		MainObject sample = (MainObject) getArguments().getSerializable(MainObject.SAMPLE_TAG);
		String transName = getArguments().getString(TRANS_NAME);


		ImageView squareBlue = (ImageView) view.findViewById(R.id.img_profile);
		squareBlue.setTransitionName(transName);
		//DrawableCompat.setTint(squareBlue.getDrawable(), sample.color);

		return view;
	}
}
