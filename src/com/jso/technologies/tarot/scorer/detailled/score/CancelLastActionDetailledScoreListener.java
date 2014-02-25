package com.jso.technologies.tarot.scorer.detailled.score;

import com.jso.technologies.tarot.scorer.common.iface.ITarotElementaryAction;

import android.view.View;
import android.view.View.OnClickListener;

public class CancelLastActionDetailledScoreListener implements OnClickListener {

	private ITarotElementaryAction cancelLastDeletionAction;

	public CancelLastActionDetailledScoreListener(DetailledScoreActivity activity) {
		this.cancelLastDeletionAction = new CancelLastDeletionAction(activity);
	}

	@Override
	public void onClick(View arg0) {
		cancelLastDeletionAction.execute();
	}

}
