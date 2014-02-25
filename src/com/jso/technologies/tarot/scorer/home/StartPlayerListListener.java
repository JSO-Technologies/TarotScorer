package com.jso.technologies.tarot.scorer.home;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.stats.players.PlayersListActivity;

public class StartPlayerListListener implements OnClickListener {

	private HomePageActivity activity;

	public StartPlayerListListener(HomePageActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		DialogHandler.showWaitingDialog(activity);
		
		Intent i = new Intent();
		i.setClass(activity, PlayersListActivity.class);
		activity.startActivityForResult(i, 0);
	}

}
