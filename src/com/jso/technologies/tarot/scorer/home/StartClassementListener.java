package com.jso.technologies.tarot.scorer.home;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.stats.classement.ClassementActivity;

public class StartClassementListener implements OnClickListener {

	private HomePageActivity activity;

	public StartClassementListener(HomePageActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View arg0) {
		DialogHandler.showWaitingDialog(activity);
		
		Intent i = new Intent();
		i.setClass(activity, ClassementActivity.class);
		activity.startActivityForResult(i, 0);
	}

}
