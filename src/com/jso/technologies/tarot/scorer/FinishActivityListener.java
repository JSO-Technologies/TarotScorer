package com.jso.technologies.tarot.scorer;

import com.jso.technologies.tarot.scorer.common.iface.ITarotActivity;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class FinishActivityListener implements OnClickListener {
	private Activity activity;

	public FinishActivityListener(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		if(activity instanceof ITarotActivity) {
			((ITarotActivity)activity).executeBeforeFinish();
		}
		activity.finish();
	}

}
