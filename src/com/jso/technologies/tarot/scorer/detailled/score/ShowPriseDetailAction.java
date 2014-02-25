package com.jso.technologies.tarot.scorer.detailled.score;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.common.bean.Prise;
import com.jso.technologies.tarot.scorer.common.iface.ITarotElementaryAction;

public class ShowPriseDetailAction implements ITarotElementaryAction {

	private DetailledScoreActivity activity;

	public ShowPriseDetailAction(DetailledScoreActivity activity) {
		this.activity = activity;
	}

	@Override
	public void execute() {
		//recuperation de la prise de la lign selectionnee
		int index = activity.getRows().indexOf(activity.getSelectedRow());
		Prise prise = activity.getGame().getPrises().get(index);

		//creation du dialog
		final Dialog dialog = new Dialog(activity);
		dialog.setContentView(R.layout.detailled_score_prise_details);

		//insertion des donnees
		((TextView)dialog.findViewById(R.id.priseDetailsPreneur)).setText(prise.getPreneur().getPseudo());
		if(prise.getAppel() != null) {
			((TextView)dialog.findViewById(R.id.priseDetailsAppel)).setText(prise.getAppel().getPseudo());
		}
		((TextView)dialog.findViewById(R.id.priseDetailsPrise)).setText(prise.getPrise().toString());
		((TextView)dialog.findViewById(R.id.priseDetailsPoints)).setText(String.valueOf(prise.getPoints()));
		((TextView)dialog.findViewById(R.id.priseDetailsOudler)).setText(String.valueOf(prise.getNbOudlers()));
		((TextView)dialog.findViewById(R.id.priseDetailsPetiteAuBout)).setText(prise.getPetiteAuBout().toString());
		((TextView)dialog.findViewById(R.id.priseDetailsPoignee)).setText(prise.getPoignee().toString());
		((TextView)dialog.findViewById(R.id.priseDetailsChelem)).setText(prise.getChelem().toString());		

		//action fermeture du dialog
		((Button)dialog.findViewById(R.id.priseDetailsOkButton)).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		//affichage
		dialog.show();
	}

}
