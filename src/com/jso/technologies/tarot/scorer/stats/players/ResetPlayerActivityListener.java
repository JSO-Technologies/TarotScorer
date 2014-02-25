package com.jso.technologies.tarot.scorer.stats.players;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.db.cache.GameRepositoryCache;

public class ResetPlayerActivityListener implements OnClickListener {

	private StatPlayerActivity activity;
	private Player player;

	public ResetPlayerActivityListener(StatPlayerActivity statPlayerActivity, Player player) {
		this.activity = statPlayerActivity;
		this.player = player;
	}

	@Override
	public void onClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(activity.getString(R.string.sureToResetPlayer))
		.setCancelable(false)
		.setPositiveButton(activity.getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				DialogHandler.showWaitingDialog(activity);
				GameRepositoryCache.deleteAllGamesWithPlayer(player, activity);
				activity.executeDisplayUpdates();
				DialogHandler.dismissWaitingDialog(activity);
			}
		})
		.setNegativeButton(activity.getString(R.string.no), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
