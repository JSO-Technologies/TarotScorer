package com.jso.technologies.tarot.scorer.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.prise.PriseActivity;

public class StartExistingGameListener implements OnClickListener {

	private Activity activity;
	private Integer gameID;

	public StartExistingGameListener(Activity activity, Integer gameID) {
		this.activity = activity;
		this.gameID = gameID;
	}

	@Override
	public void onClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(activity.getString(R.string.loadGame))
		.setCancelable(false)
		.setPositiveButton(activity.getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//message d'attente
				DialogHandler.showWaitingDialog(activity);
				
				Intent i = new Intent();
				i.setClass(activity, PriseActivity.class);
				i.putExtra(Constantes.SELECTED_GAME, gameID);
				activity.startActivity(i);
				DialogHandler.dismissWaitingDialog(activity);
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
