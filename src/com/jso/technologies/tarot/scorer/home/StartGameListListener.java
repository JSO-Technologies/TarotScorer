package com.jso.technologies.tarot.scorer.home;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.game.GamesListActivity;

public class StartGameListListener implements OnClickListener {

	private HomePageActivity activity;

	public StartGameListListener(HomePageActivity homePageActivity) {
		this.activity = homePageActivity;
	}

	@Override
	public void onClick(View v) {
		DialogHandler.showWaitingDialog(activity);

		Intent i = new Intent();
		i.setClass(activity, GamesListActivity.class);
		activity.startActivityForResult(i, 0);
	}

}
