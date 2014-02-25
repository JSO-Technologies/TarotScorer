package com.jso.technologies.tarot.scorer.prise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.db.cache.StatistiqueCalculatorCache;

public class PriseFinishListener implements OnClickListener {
	private PriseActivity activity;

	public PriseFinishListener(PriseActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		if(activity.getGame().getPrises().isEmpty()) {
			activity.finishGameActivity(false);
		}
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage(activity.getString(R.string.areYouSure))
			.setCancelable(false)
			.setPositiveButton(activity.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					//message d'attente
					DialogHandler.showWaitingDialog(activity);
					
					//enregistrement du jeu et des prises
					activity.getDatabaseGameAction().save(activity.getGame());
					
					//invalidation des caches de stats des joueurs de la partie
					for(Player player : activity.getGame().getPlayers()) {
						StatistiqueCalculatorCache.invalidate(player);
					}
					
					activity.finishGameActivity(true);	
					
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

}
