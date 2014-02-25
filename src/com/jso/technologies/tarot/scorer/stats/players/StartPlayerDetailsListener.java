package com.jso.technologies.tarot.scorer.stats.players;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;

public class StartPlayerDetailsListener implements OnClickListener {

	private Integer playerId;
	private PlayersListActivity activity;

	public StartPlayerDetailsListener(PlayersListActivity activity, Integer id) {
		this.activity = activity;
		this.playerId = id;
	}

	@Override
	public void onClick(View v) {
		DialogHandler.showWaitingDialog(activity);
		Intent i = new Intent();
		i.setClass(activity, StatPlayerActivity.class);
		i.putExtra(Constantes.SELECTED_PLAYER_ID, playerId);
		activity.startActivityForResult(i, 0);
		
	}

}
