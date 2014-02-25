package com.jso.technologies.tarot.scorer.player.config;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.Utils.DialogHandler;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.prise.PriseActivity;

public class ValidationPlayerConfigListener implements OnClickListener {
	private PlayersConfigActivity activity;

	public ValidationPlayerConfigListener(PlayersConfigActivity playersConfigActivity) {
		activity = playersConfigActivity;
	}

	@Override
	public void onClick(View v) {
		//nombre de joueurs selectionne
		SeekBar nbPlayersSeekBar = activity.getNbPlayerSeekBar();
		int nbPlayers = nbPlayersSeekBar.getProgress() + 3;
		
		//verification que les joueurs sont bien selectionne
		ArrayList<Integer> selectedPlayers = new ArrayList<Integer>();
		for(int i = 0; i < nbPlayers; ++i){
			Spinner spinner = activity.getPlayerSpinners().get(i);
			if(spinner.getSelectedItemPosition() == 0){
				String errorMessage = activity.getString(R.string.select_all_players);
				Toast toast = Toast.makeText(activity.getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			selectedPlayers.add(((Player) spinner.getSelectedItem()).getId());
		}
		
		DialogHandler.showWaitingDialog(activity);
		Intent i = new Intent();
		i.setClass(activity, PriseActivity.class);
		i.putIntegerArrayListExtra(Constantes.SELECTED_PLAYERS, selectedPlayers);
		activity.startActivity(i);
		activity.finish();
	}

}
