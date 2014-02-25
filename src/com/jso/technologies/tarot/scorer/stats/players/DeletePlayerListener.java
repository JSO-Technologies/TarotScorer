package com.jso.technologies.tarot.scorer.stats.players;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.db.cache.PlayerRepositoryCache;

public class DeletePlayerListener implements OnClickListener {

	private Activity activity;
	private Player player;

	public DeletePlayerListener(Activity activity, Player player) {
		this.activity = activity;
		this.player = player;
	}

	@Override
	public void onClick(View arg0) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(activity.getString(R.string.sureToDeletePlayer))
		.setCancelable(false)
		.setPositiveButton(activity.getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				player.setEnabled(false);
				PlayerRepositoryCache.update(player, activity);
				
				Intent intent = new Intent();
				intent.putExtra(Constantes.REFRESH, true);
				activity.setResult(Activity.RESULT_OK, intent);
				activity.finish();
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
