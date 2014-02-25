package com.jso.technologies.tarot.scorer.detailled.score;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.common.bean.Game;

public class ShowDetailledScoreListener implements OnClickListener {
	private Activity activity;
	private Game game;
	
	public ShowDetailledScoreListener(Activity activity, Game game) {
		this.activity = activity;
		this.game = game;
	}

	@Override
	public void onClick(View v) {
		DialogHandler.showWaitingDialog(activity);
		Intent i = new Intent();
		i.setClass(activity, DetailledScoreActivity.class);
		i.putExtra(Constantes.GAME, game);
		i.putExtra(Constantes.CAN_DELETE, true);
		activity.startActivityForResult(i, Constantes.DETAILLED_SCORE);
	}

}
