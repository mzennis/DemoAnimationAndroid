package com.mzennis.demo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mzennis.demo.RevealActivity;
import com.mzennis.demo.SharedElementActivity;
import com.mzennis.demo.TransitionActivity;
import com.mzennis.demo.databinding.RowMainBinding;
import com.mzennis.demo.object.MainObject;
import com.mzennis.demo.utilities.TransitionHelper;

import java.util.List;

/**
 * Created by mzennis on 9/3/16.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
	private final Activity activity;
	private final List<MainObject> samples;

	public MainAdapter(Activity activity, List<MainObject> samples) {
		this.activity = activity;
		this.samples = samples;
	}

	@Override
	public MainViewHolder onCreateViewHolder(ViewGroup parent, int position) {
		RowMainBinding binding = RowMainBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
		return new MainViewHolder(binding.getRoot());
	}

	@Override
	public void onBindViewHolder(final MainViewHolder viewHolder, final int position) {
		final MainObject sample = samples.get(viewHolder.getAdapterPosition());
		viewHolder.binding.setMain(sample);
		viewHolder.binding.mainName.setText(sample.getName());
		viewHolder.binding.mainDesc.setText(sample.getDesc());
		viewHolder.binding.rowLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (viewHolder.getAdapterPosition()) {
					case 0:
						transitionToActivity(TransitionActivity.class, sample);
						break;
					case 1:
						transitionToActivity(SharedElementActivity.class, sample);
						break;
					case 2:
						transitionToActivity(RevealActivity.class, sample);
						break;
				}
			}
		});
	}

	private void transitionToActivity(Class target, MainObject sample) {
		final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, true);
		startActivity(target, pairs, sample);
	}

	private void startActivity(Class target, Pair<View, String>[] pairs, MainObject sample) {
		Intent i = new Intent(activity, target);
		ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
		i.putExtra(MainObject.SAMPLE_TAG, sample);
		activity.startActivity(i, transitionActivityOptions.toBundle());
	}

	@Override
	public int getItemCount() {
		return samples.size();
	}


	public class MainViewHolder extends RecyclerView.ViewHolder {
		final RowMainBinding binding;

		public MainViewHolder(View rootView) {
			super(rootView);
			binding = DataBindingUtil.bind(rootView);
		}
	}
}
