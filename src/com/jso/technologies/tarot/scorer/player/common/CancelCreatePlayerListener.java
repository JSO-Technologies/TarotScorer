package com.jso.technologies.tarot.scorer.player.common;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class CancelCreatePlayerListener implements OnClickListener {
	private AbstractPlayerOperationActivity activity;

	public CancelCreatePlayerListener(AbstractPlayerOperationActivity playerActivity) {
		activity = playerActivity;
	}

	@Override
	public void onClick(View v) {
        activity.setResult(Activity.RESULT_CANCELED, null);
		activity.finish();
	}

}
