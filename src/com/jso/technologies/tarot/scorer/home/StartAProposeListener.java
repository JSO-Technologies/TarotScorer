package com.jso.technologies.tarot.scorer.home;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.jso.technologies.tarot.scorer.apropos.AProposActivity;

public class StartAProposeListener implements OnClickListener {

	private HomePageActivity activity;

	public StartAProposeListener(HomePageActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent();
		i.setClass(activity, AProposActivity.class);
		activity.startActivity(i);
	}

}
