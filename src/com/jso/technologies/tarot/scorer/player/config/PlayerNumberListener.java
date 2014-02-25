package com.jso.technologies.tarot.scorer.player.config;

import java.util.List;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * Listener du seekBar de la page de configuration des joueurs pour une nouvelle partie
 * @author Jimmy
 *
 */
public class PlayerNumberListener implements OnSeekBarChangeListener {
	private PlayersConfigActivity activity;

	public PlayerNumberListener(PlayersConfigActivity playersConfigActivity) {
		this.activity = playersConfigActivity;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		List<LinearLayout> playerGroups = activity.getPlayerGroups();
		int nbPlayers = progress + 3;
		
		// Rendre les champs des joueurs visibles
		for(int i = 3; i < nbPlayers; ++i) {
			playerGroups.get(i).setVisibility(View.VISIBLE);
		}
		
		// Rendre les champs des joueurs invisibles
		for(int i = nbPlayers; i < playerGroups.size(); ++i) {
			playerGroups.get(i).setVisibility(View.GONE);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

}
