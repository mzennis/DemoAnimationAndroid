package com.mzennis.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mzennis.demo.databinding.ActivityRevealBinding;
import com.mzennis.demo.object.MainObject;

/**
 * Created by mzennis on 9/4/16.
 */
public class RevealActivity extends BaseActivity implements View.OnTouchListener {

	private static final int DELAY = 100;
	private RelativeLayout bgViewGroup;
	private Interpolator interpolator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bindData();
		setupWindowAnimations();
		setupLayout();
	}

	private void bindData() {
		ActivityRevealBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_reveal);
		MainObject sample = (MainObject) getIntent().getExtras().getSerializable(MainObject.SAMPLE_TAG);
		binding.setReveal(sample);
		setTitle("Circular Reveal Animation");
	}

	private void setupWindowAnimations() {
		interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
		setupEnterAnimations();
		setupExitAnimations();
	}

	private void setupEnterAnimations() {
		Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
		getWindow().setSharedElementEnterTransition(transition);
		transition.addListener(new Transition.TransitionListener() {
			@Override
			public void onTransitionStart(Transition transition) {
			}

			@Override
			public void onTransitionEnd(Transition transition) {
				// Removing listener here is very important because shared element transition is executed again backwards on exit. If we don't remove the listener this code will be triggered again.
				transition.removeListener(this);
				animateRevealShow(toolbar);
				animateButtonsIn();
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

	private void setupExitAnimations() {
		Fade returnTransition = new Fade();
		getWindow().setReturnTransition(returnTransition);
		returnTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
		returnTransition.setStartDelay(getResources().getInteger(R.integer.anim_duration_medium));
		returnTransition.addListener(new Transition.TransitionListener() {
			@Override
			public void onTransitionStart(Transition transition) {
				transition.removeListener(this);
				animateButtonsOut();
				animateRevealHide(bgViewGroup);
			}

			@Override
			public void onTransitionEnd(Transition transition) {
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

	ImageView btnGreen;
	ImageView btnRed;
	ImageView btnBlue;
	ImageView btnYellow;

	private void setupLayout() {
		bgViewGroup = (RelativeLayout) findViewById(R.id.reveal_root);
		btnGreen = (ImageView) findViewById(R.id.square_green);
		btnRed = (ImageView) findViewById(R.id.square_red);
		btnBlue = (ImageView) findViewById(R.id.square_blue);
		btnYellow = (ImageView) findViewById(R.id.square_yellow);

		btnGreen.setOnTouchListener(this);
		btnRed.setOnTouchListener(this);
		btnBlue.setOnTouchListener(this);
		btnYellow.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v == btnGreen) {
			revealGreen();
		} else if (v == btnRed) {
			revealRed();
		} else if (v == btnBlue) {
			revealBlue();
		} else if (v == btnYellow) {
			revealYellow(event.getRawX(), event.getRawY());
		}
		return false;
	}

	private void revealYellow(float x, float y) {
		animateRevealColorFromCoordinates(bgViewGroup, R.color.sample_yellow, (int) x, (int) y);
	}

	private void revealGreen() {
		animateRevealColor(bgViewGroup, R.color.sample_green);
	}

	private void revealBlue() {
		animateButtonsOut();
		Animator anim = animateRevealColorFromCoordinates(bgViewGroup, R.color.sample_blue, bgViewGroup.getWidth() / 2, 0);
		anim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				animateButtonsIn();
			}
		});
	}

	private void revealRed() {
		final ViewGroup.LayoutParams originalParams = btnRed.getLayoutParams();
		Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
		transition.addListener(new Transition.TransitionListener() {
			@Override
			public void onTransitionStart(Transition transition) {
			}

			@Override
			public void onTransitionEnd(Transition transition) {
				animateRevealColor(bgViewGroup, R.color.sample_red);
				btnRed.setLayoutParams(originalParams);
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
		TransitionManager.beginDelayedTransition(bgViewGroup, transition);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		btnRed.setLayoutParams(layoutParams);
	}

	private void animateButtonsIn() {
		for (int i = 0; i < bgViewGroup.getChildCount(); i++) {
			View child = bgViewGroup.getChildAt(i);
			child.animate()
					.setStartDelay(100 + i * DELAY)
					.setInterpolator(interpolator)
					.alpha(1)
					.scaleX(1)
					.scaleY(1);
		}
	}

	private void animateButtonsOut() {
		for (int i = 0; i < bgViewGroup.getChildCount(); i++) {
			View child = bgViewGroup.getChildAt(i);
			child.animate()
					.setStartDelay(i)
					.setInterpolator(interpolator)
					.alpha(0)
					.scaleX(0f)
					.scaleY(0f);
		}
	}

	private void animateRevealShow(View viewRoot) {
		int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
		int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
		int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

		Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
		viewRoot.setVisibility(View.VISIBLE);
		anim.setDuration(getResources().getInteger(R.integer.anim_duration_long));
		anim.setInterpolator(new AccelerateInterpolator());
		anim.start();
	}

	private void animateRevealColor(ViewGroup viewRoot, @ColorRes int color) {
		int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
		int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
		animateRevealColorFromCoordinates(viewRoot, color, cx, cy);
	}

	private Animator animateRevealColorFromCoordinates(ViewGroup viewRoot, @ColorRes int color, int x, int y) {
		float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());

		Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0, finalRadius);
		viewRoot.setBackgroundColor(ContextCompat.getColor(this, color));
		anim.setDuration(getResources().getInteger(R.integer.anim_duration_long));
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.start();
		return anim;
	}

	private void animateRevealHide(final View viewRoot) {
		int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
		int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
		int initialRadius = viewRoot.getWidth();

		Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, initialRadius, 0);
		anim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				viewRoot.setVisibility(View.INVISIBLE);
			}
		});
		anim.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
		anim.start();
	}
}
