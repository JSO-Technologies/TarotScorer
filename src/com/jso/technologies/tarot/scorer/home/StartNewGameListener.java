package com.jso.technologies.tarot.scorer.home;

import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.player.config.PlayersConfigActivity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class StartNewGameListener implements OnClickListener {

	private HomePageActivity activity;

	public StartNewGameListener(HomePageActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View arg0) {
		DialogHandler.showWaitingDialog(activity);
		
		Intent i = new Intent();
		i.setClass(activity, PlayersConfigActivity.class);
		activity.startActivityForResult(i, 0);
	}

}
