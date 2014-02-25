package com.jso.technologies.tarot.scorer.prise;

import android.view.View;
import android.view.View.OnClickListener;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.MessageUtils;
import com.jso.technologies.tarot.scorer.Utils.TarotRules;
import com.jso.technologies.tarot.scorer.common.bean.Prise;

public class PriseValidationListener implements OnClickListener {
	private PriseActivity activity;

	public PriseValidationListener(PriseActivity priseActivity) {
		activity = priseActivity;
	}

	@Override
	public void onClick(View v) {
		if(validGame()) {
			Prise currentPrise = activity.getCurrentPrise();
			activity.getGame().addPrise(currentPrise);
			activity.getDatabaseGameAction().save(activity.getGame());
			
			if(currentPrise.isSuccess()) {
				MessageUtils.showToast(activity, 
						String.format(activity.getResources().getString(R.string.prise_contract_succeed), 
								currentPrise.getPoints() - TarotRules.getContractPointsToDo(currentPrise.getNbOudlers())));
			}
			else {
				MessageUtils.showToast(activity, 
						String.format(activity.getResources().getString(R.string.prise_contract_succeed_failed), 
								TarotRules.getContractPointsToDo(currentPrise.getNbOudlers()) - currentPrise.getPoints()));
			}
			activity.resetValues();
			
			//mise a jour des scores dans TextView
			activity.updateScores();
		}
	}

	private boolean validGame() {
		//preneur
		if(activity.getCurrentPrise().getPreneur() == null) {
			MessageUtils.showToast(activity, R.string.preneur_not_checked);
			return false;
		}

		//appel au roi
		if(activity.getPlayers().size() == 5 && activity.getCurrentPrise().getAppel() == null) {
			MessageUtils.showToast(activity, R.string.call_not_checked);
			return false;
		}

		return true;
	}



}
