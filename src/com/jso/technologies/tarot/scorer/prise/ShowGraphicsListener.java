package com.jso.technologies.tarot.scorer.prise;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.Utils.MessageUtils;
import com.jso.technologies.tarot.scorer.prise.graphics.GameGraphicsActivity;

public class ShowGraphicsListener implements OnClickListener {

	private PriseActivity activity;

	public ShowGraphicsListener(PriseActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		if(activity.getGame().getId() == null) {
			MessageUtils.showToast(activity, R.string.no_prise_played);
		}
		else{
			DialogHandler.showWaitingDialog(activity);
			Intent i = new Intent();
			i.setClass(activity, GameGraphicsActivity.class);
			i.putExtra(Constantes.GAME, activity.getGame());
			activity.startActivityForResult(i, 0);
		}
	}

}
