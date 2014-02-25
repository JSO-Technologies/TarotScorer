package com.jso.technologies.tarot.scorer.stats.players;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.player.config.modify.ModifyPlayerActivity;

public class StartModifyPlayerActivityListener implements OnClickListener {

	private Player player;
	private Activity activity;

	public StartModifyPlayerActivityListener(Activity activity, Player player) {
		this.activity = activity;
		this.player = player;
		
	}

	@Override
	public void onClick(View arg0) {
		DialogHandler.showWaitingDialog(activity);
		Intent i = new Intent();
		i.setClass(activity, ModifyPlayerActivity.class);
		i.putExtra(Constantes.SELECTED_PLAYERS, player);
		activity.startActivityForResult(i, 0);
	}

}
